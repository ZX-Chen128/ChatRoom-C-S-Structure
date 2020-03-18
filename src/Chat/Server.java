package Chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/*
 * @File: CCChat
 * @Author: Chen Zixin
 * @Date: 2019/6/28
 * @Description:
 */
/**
 * 创建服务端
 * 将接受到的信息，送到到所有客户端（除了发送方）
 * @author yangzheng
 *
 */
public class Server {
    //连接上服务器的客户端的集合
    public static List<ServerForward> allClient = new ArrayList<ServerForward>();

    public static void main(String[] args) throws UnknownHostException {
        ServerFrame serverFrame = new ServerFrame();
        serverFrame.setSize(401, 245);
    }

    //设置服务器
    public static void setServer() throws IOException {
        //从服务器窗口获取ip和端口号
        String ip = ServerFrame.ip;
        int port = ServerFrame.port;

        // ServerSocket(int port) 创建绑定到特定端口的服务器套接字。
//		ServerSocket(int port, int backlog, InetAddress bindAddr)
//        使用指定的端口、侦听 backlog 和要绑定到的本地 IP 地址创建服务器。
        @SuppressWarnings("resource")
        ServerSocket server = new ServerSocket(port, 0,InetAddress.getByName(ip));	//这里的backlog不知道是干嘛的，先设为0
//		ServerSocket server = new ServerSocket(9999);
//		System.out.println(InetAddress.getLocalHost().getHostAddress());
        while(true) {
            Socket client = server.accept();
            System.out.println("连接成功");
            ServerForward serverForward = new ServerForward(client);
            allClient.add(serverForward);
            Thread thread = new Thread(serverForward);		//晕，忘了start了，找了好久才发现
            thread.start();
        }
    }
}