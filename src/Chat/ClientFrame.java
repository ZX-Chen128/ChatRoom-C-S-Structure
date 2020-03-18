package Chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * 客户端主界面
 *
 * 关于界面的布局：用的是  网格组布局管理器(GridBagLayout)
 * 参考网站：http://yyzjava.iteye.com/blog/1181552
 *
 * 一个控件填充一个panel，并且随panel大小进行调整控件的大小。
 * this.setLayout(new BorderLayout());
 * this.add(splitPane, BorderLayout.CENTER);
 *
 * 关于JScrollPane的使用：
 * 当一个容器内放置了许多组件，而容器的显示区域不足以同时显示所有组件时，
 * 如果让容器带滚动条，通过移动滚动条的滑块，容器中位置上的组件就能看到。
 * 滚动面板JScrollPane能实现这样的要求，JScrollPane是带有滚动条的面板。
 * JScrollPane是Container类的子类，也是一种容器，但是只能添加一个组件。
 * JScrollPane的一般用法是先将一些组件添加到一个JPanel中，然后再把这个JPanel添加到JScrollPane中。
 * 参考http://www.weixueyuan.net/view/6059.html
 *
 * @File: CCChat
 * @Author: Chen Zixin
 * @Date: 2019/6/28
 * @Description:

 *
 */
public class ClientFrame extends JFrame{
    private static final long serialVersionUID = 1L;
    //用户信息栏  存放该客户端用户的信息、可能包含头像
    JPanel userInformationPanel = null;
    JLabel userName = null;

    //聊天记录栏
    JPanel msgRecordPanel = null;
    JScrollPane msgJcrollPane = null;	//使用这个让聊天记录不能编辑
    static JTextArea msgRecordText = null;

    //发送栏
    JPanel sendPanel = null;
    JTextArea msgText = null;
    //	JLabel msgLabel = null;
    JButton sendButton = null;


    //发送的信息
    public static String msg = null;

    //构造器
    public ClientFrame(){
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new ChatWindow();
    }

    protected void init(){
        //窗口的位置和大小
        setBounds(100, 100, 800, 600);
        setTitle("客户端");
        //设置窗口布局
        setLayout(new GridBagLayout());
        //设置窗口图标
        //setIconImage(Toolkit.getDefaultToolkit().createImage(ClientFrame.class.getResource("yz.png")));
        Image image = new ImageIcon("app.png").getImage();
        setIconImage(image);

        //用户信息栏
        userName = new JLabel(ClientLogin.name);
        userName.setFont(new Font("幼圆", Font.BOLD, 28));
        userInformationPanel = new JPanel(new BorderLayout());	//布置容器的边框布局
        userInformationPanel.setBackground(new Color(176, 226, 255));
        //userInformationPanel.setBorder(new TitledBorder("用户信息"));
        //userInformationPanel.setBackground(new Color(225, 225, 225));
        userInformationPanel.add(userName, BorderLayout.CENTER);
        add(userInformationPanel, new GBC(0,0, 1,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
        //聊天记录栏
        msgRecordPanel = new JPanel(new BorderLayout());
        msgJcrollPane = new JScrollPane();		//JScrollPane是带有滚动条的面板
        msgJcrollPane.setViewportView(msgRecordPanel);
        msgJcrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //msgJcrollPane.add(msgRecordPanel);
        //msgJcrollPane = new JScrollPane(msgRecordText);
        msgRecordPanel.setBorder(new TitledBorder("聊天记录"));
        //msgJcrollPane.setBorder(new TitledBorder("聊天记录"));
        msgRecordText = new JTextArea(15, 70);
        msgRecordText.setBackground(new Color(250, 250, 224));	//灰色 245,245,245   	//    233,233,216银色
        //msgRecordText.setText("系统消息:登录成功\n");
        msgRecordText.setFont(new Font("宋体", Font.BOLD, 22));
        msgRecordPanel.add(new JScrollPane(msgRecordText));	//让聊天记录栏带有滚动条
        msgRecordPanel.add(msgRecordText, BorderLayout.CENTER);
        add(msgRecordPanel, new GBC(0,1, 1, 3).setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
        //发送栏
        msgText = new JTextArea(4, 55);
        msgText.setFont(new Font("宋体", Font.BOLD, 16));
        msgText.setBackground(new Color(250, 250, 224));
        sendButton = new JButton("发送");
        sendPanel = new JPanel(new FlowLayout());	//发送栏的布局
        sendPanel.setBackground(new Color(176, 226, 255));
        //sendPanel.setBackground(new Color(225, 225, 225));
        sendPanel.add(msgText);
        sendPanel.add(new JScrollPane(msgText));	//让输入区带滚动条
        sendPanel.add(sendButton);
        add(sendPanel, new GBC(0,10, 1,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));

        ClientSend.send(ClientLogin.name);
        ClientSend.send("[系统消息]登录成功");

        //发送按钮监听事件
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                msg = msgText.getText().toString();
                ClientSend.send();
                msgText.setText(null);
            }
        });
    }


    public static String axa = "07170422 陈子欣 2019java课";
    /**
     * 其中的GBC类继承于GridBagConstraints。
     * 这样做的目的是简化每次对GridBagConstraints对象的直接操作带来的繁琐，
     * 而GBC的各个set方法返回的都是一个GBC对象因此可以接连调用set方法。
     * @author yangzheng
     *
     */
    public class GBC extends GridBagConstraints
    {
        private static final long serialVersionUID = 1L;

        //初始化左上角位置
        public GBC(int gridx, int gridy)
        {
            this.gridx = gridx;
            this.gridy = gridy;
        }

        //初始化左上角位置和所占行数和列数
        public GBC(int gridx, int gridy, int gridwidth, int gridheight)
        {
            this.gridx = gridx;
            this.gridy = gridy;
            this.gridwidth = gridwidth;
            this.gridheight = gridheight;
        }

        //对齐方式
        public GBC setAnchor(int anchor)
        {
            this.anchor = anchor;
            return this;
        }

        //是否拉伸及拉伸方向
        public GBC setFill(int fill)
        {
            this.fill = fill;
            return this;
        }

        //x和y方向上的增量
        public GBC setWeight(double weightx, double weighty)
        {
            this.weightx = weightx;
            this.weighty = weighty;
            return this;
        }

        //外部填充
        public GBC setInsets(int distance)
        {
            this.insets = new Insets(distance, distance, distance, distance);
            return this;
        }

        //外填充
        public GBC setInsets(int top, int left, int bottom, int right)
        {
            this.insets = new Insets(top, left, bottom, right);
            return this;
        }

        //内填充
        public GBC setIpad(int ipadx, int ipady)
        {
            this.ipadx = ipadx;
            this.ipady = ipady;
            return this;
        }
    }
}
