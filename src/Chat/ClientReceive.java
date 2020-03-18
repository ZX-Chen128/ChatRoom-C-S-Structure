package Chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 客户端接收线程
 * 将客户端从服务端接收到的信息打印在客户端窗口中
 * @author yangzheng
 *
 */
public class ClientReceive implements Runnable{
    boolean isRunning = true;
    DataInputStream dis = null;

    public ClientReceive(Socket client){
        try {
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive(){
        String msg = null;
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

    public void run() {
        String msg = null;
        while(isRunning){
            //将接受信息输出到客户端窗口中
            msg = receive();
            System.out.println(msg);
            ClientFrame.msgRecordText.append(msg+"\n");
        }
    }
}
