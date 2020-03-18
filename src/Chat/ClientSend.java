package Chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 客户端发送线程
 * 从窗口中获取信息，用该线程发送，服务端接收
 * @author Chen Zixin
 *
 */
public class ClientSend implements Runnable{
    static boolean isRunning = true;
    //管道输出流
    static DataOutputStream dos = null;
    BufferedReader console = null;

    public ClientSend(){
        console = new BufferedReader(new InputStreamReader(System.in));
    }

    public ClientSend(Socket client){
        this();
        try {
            dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            isRunning = false;
        }
    }

    public String getMsgFromConsole(){
        String msg = null;
        try {
            msg = console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static String getMsgFromClient() {
        return ClientFrame.msg;
    }

    public static String getNameFromLogin() {
        return ClientLogin.name;
    }

    /**
     * 发送窗口中的内容
     */
    @SuppressWarnings("null")
    public static void send(){
        String msg = getMsgFromClient();	//从窗口中获取
        if (msg != null || !msg.trim().equals("")){
            try {
                dos.writeUTF(msg);
                dos.flush();//强制刷新
            } catch (IOException e) {
                e.printStackTrace();
                isRunning = false;	//出现异常停止发送
                try {
                    dos.close();	//出现异常关闭流。
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 发送参数中的字符串
     * @param msg
     */
    @SuppressWarnings("null")
    public static void send(String msg){
        if (msg != null || !msg.trim().equals("")){
            try {
                dos.writeUTF(msg);
                dos.flush();//强制刷新
            } catch (IOException e) {
                e.printStackTrace();
                isRunning = false;	//出现异常停止发送
                try {
                    dos.close();	//出现异常关闭流。
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    //线程体
    public void run() {
    }
}
