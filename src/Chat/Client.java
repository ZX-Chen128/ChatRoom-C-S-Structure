package Chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * @File: CCChat
 * @Author: Chen Zixin
 * @Date: 2019/6/28
 * @Description:
 */

/**
 * 	打开客户端窗口(先是注册登录界面)
 * 	启动ClientReceive线程和ClientSend线程
 * @author yangzheng
 *
 */
public class Client {
    public static void main(String[] args) throws UnknownHostException{
        //打开客户端窗口
        ClientLogin login = new ClientLogin();
        login.setSize(401, 245);
//		new ClientFrame();
    }

    public static void connectServer() throws UnknownHostException, IOException  {
        //启动发送接受线程
        //创建一个流套接字并将其连接到指定 IP地址的指定端口号。
        String host = ClientLogin.ip;
        int port = ClientLogin.port;

        Socket client = new Socket(InetAddress.getByName(host), port);
        Thread sendThread = new Thread(new ClientSend(client));
        sendThread.start();
        Thread receiveThread = new Thread(new ClientReceive(client));
        receiveThread.start();
    }
}
