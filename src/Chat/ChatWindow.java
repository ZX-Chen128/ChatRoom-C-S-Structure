package Chat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * @File: ChatWindow, ChatRoom
 * @Author: Chen Zixin
 * @Date: 2019/6/28
 * @Description:
 */
public class ChatWindow extends JFrame implements KeyListener {


    public int x, y;

    public ChatWindow() {
        setTitle("聊天室");
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        try {
            Image avatar;
            Image buffer = createImage(getWidth(), getHeight());
            Graphics buffer_g = buffer.getGraphics();
            if(ClientLogin.sex=="男"){
                avatar = ImageIO.read(ChatWindow.class.getResource("../boy.png"));}
            else{
                avatar = ImageIO.read(ChatWindow.class.getResource("../girl.png"));}
            buffer_g.drawImage(avatar, x, y, this);
            g.drawImage(buffer, 0, 0, this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                y -= 20;
                break;
            case KeyEvent.VK_DOWN:
                y += 20;
                break;
            case KeyEvent.VK_LEFT:
                x -= 20;
                break;
            case KeyEvent.VK_RIGHT:
                x += 20;
                break;
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
