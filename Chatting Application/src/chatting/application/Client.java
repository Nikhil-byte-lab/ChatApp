
package chatting.application;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.border.*;


public class Client implements ActionListener {

    JTextField text1;
    static JPanel p2;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;

    static  JFrame f = new JFrame();

    Client(){
        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 350, 65);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);


        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50,50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(220, 20, 30,30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(265, 20, 30,30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(11, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(310, 20, 11,25);
        p1.add(morevert);

        JLabel name = new JLabel("Ford");
        name.setBounds(110, 15, 100, 20);
        p1.add(name);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 20));

        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 20);
        p1.add(status);
        status.setForeground(Color.white);
        status.setFont(new Font("Georgia", Font.BOLD, 10));

        p2 = new JPanel();
        p2.setBounds(0, 75, 350, 540);
        f.add(p2);

        text1 = new JTextField();
        text1.setBounds(2, 615, 275, 33);
        text1.setFont(new Font("RUBIK", Font.PLAIN, 16));
        f.add(text1);

        JButton send = new JButton("Send");
        send.setBounds(285, 615, 63, 33);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        f.add(send);

        send.addActionListener(this);

        f.setSize(350, 650);
        f.setLocation(700, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);

        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            String out = text1.getText();

            JPanel p3 = formatLabel(out);


            p2.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p3, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            p2.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text1.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception g){
            g.printStackTrace();
        }
    }
    public static  JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 15));
        output.setBackground(new Color(37,211, 152));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);


        return panel;
    }

    public static void main(String[] args){
        new Client();

        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true){
                p2.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                p2.add(vertical, BorderLayout.PAGE_START);

                f.validate();

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
