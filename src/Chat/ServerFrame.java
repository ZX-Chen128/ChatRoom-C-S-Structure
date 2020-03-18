package Chat;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * 登录界面
 * 这里就不设密码了。
 */
public class ServerFrame extends JFrame{
    private static final long serialVersionUID = 1L;
    private JPanel jp2,jp3,jp4;
    private JLabel jl_ip,jl_port;
    private JTextField jtf_ip,jtf_port;
    private JButton btn_set;

    public static String name = null;
    public static String ip = null;
    public static int port = 0;

    public ServerFrame() throws UnknownHostException
    {
        this.setLayout(new GridLayout(3,1));

        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jl_ip = new JLabel("IP地址:");
        jl_ip.setForeground(Color.BLACK);
        jl_ip.setFont(new Font("宋体", Font.BOLD, 22));
        jtf_ip = new JTextField(12);
        jtf_ip.setText(InetAddress.getLocalHost().getHostAddress());	//默认IP地址
        jl_port = new JLabel("端口号:");
        jl_port.setForeground(Color.BLACK);
        jl_port.setFont(new Font("宋体", Font.BOLD, 22));
        jtf_port = new JTextField(12);
        jtf_port.setText(Integer.toString(9999));
        btn_set = new JButton("确定");

        this.setTitle(Chat.ClientFrame.axa);
        this.setBounds(750, 400, 400, 250);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jp2.add(jl_ip);
        jp2.add(jtf_ip);
        jp3.add(jl_port);
        jp3.add(jtf_port);
        jp4.add(btn_set);

        //添加背景图片
        ImageIcon icon=new ImageIcon("");
        //Image im=new Image(icon);
        //将图片放入label中
        JLabel label=new JLabel(icon);
        //设置label的大小
        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
        //获取frame的顶层容器,并设置为透明
        JPanel j=(JPanel)this.getContentPane();
        j.setOpaque(false);

        //必须设置为透明的。否则看不到图片
        jp2.setOpaque(false);
        jp3.setOpaque(false);
        jp4.setOpaque(false);

        //获取窗口的第二层，将label放入
        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);

        //给登录按钮添加监听事件
        btn_set.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ip = jtf_ip.getText().trim();
                port = Integer.parseInt(jtf_port.getText().toString());
                //关闭窗口
                dispose();

                //点击确定后，设置服务器ip和端口号
                try {
                    Server.setServer();
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}