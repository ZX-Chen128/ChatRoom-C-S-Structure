package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 服务器接受一个客户端得信息，再转发给其他客户端
 * 接受与转发是连续的，所以写在一个类中。
 * @author Chen Zixin
 *
 */
public class ServerForward implements Runnable{
    boolean isRunning = true;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    String msg = null;
    String name = null;

    //构造器
    public ServerForward(Socket client){
        try {
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从客户端接收消息
    public String receive(){
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            isRunning = false;
            try {
                dis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return msg;
    }

    public String getClientName() {
        try {
            this.name = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return name;
    }

    //发送信息到客户端
    @SuppressWarnings("null")
    public void send(String msg){
        if (msg != null || !msg.trim().equals("")){
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
                isRunning = false;
                try {
                    dos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    //发送到所有客户端
    @SuppressWarnings("null")
    public void sendToClient(String msg){
        if (msg != null || !msg.trim().equals("")) {			//少了！  2017/1/5  15:34找到

            if (msg.startsWith("[系统消息]")) {
                sendSystemMsg(msg);
                return ;
            }

            if (msg.startsWith("@")) {
                sendPrivateMsg(msg);
                return ;
            }

            for (ServerForward other : Server.allClient) {
                if (other == this)
                    this.send("我：" + msg);
                else
                    other.send(this.name + "：" + msg);
            }
        }
    }

    //发送私聊消息
    public void sendPrivateMsg(String msg) {
        //利用正则表达式获取私聊信息的内容
        Pattern pattern = Pattern.compile("@([\\w\\u4e00-\\u9fa5]+)[:：]{1}(.+)");
        Matcher matcher = pattern.matcher(msg);
        StringBuffer nameBuffer = new StringBuffer();
        StringBuffer msgBuffer = new StringBuffer();
        while (matcher.find()) {
            nameBuffer.append(matcher.group(1));
            msgBuffer.append(matcher.group(2));
        }
        for (ServerForward other : Server.allClient) {
            if (other == this)
                this.send("我" + msg);
            else {
                if (other.name.equals(nameBuffer.toString())) {
                    other.send(this.name + "@我：" + msgBuffer);
                }
            }
        }
    }

    //发送系统消息
    public void sendSystemMsg(String msg){
        for(ServerForward other : Server.allClient){
            if (this == other)
                this.send(msg);
            else
                other.send("[系统消息]" + this.name + "进入聊天室");
        }
    }

    //线程体  将接收到的消息转发到其他客户端
    //只要不出现异常，就不间断的等待接收
    public void run() {
        //接受用户的昵称
        while(isRunning) {
            if (name == null)
                name = getClientName();
            else
                break;
        }

        while(isRunning) {
            sendToClient(receive());
        }
    }
}
