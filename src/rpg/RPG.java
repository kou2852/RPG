package rpg;

import java.awt.Container;
import javax.swing.JFrame;

public class RPG extends JFrame{
    public RPG() {
        //�^�C�g����ݒ�
        setTitle("RPG");
        
        //���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);
        
        //�p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }
    public static void main(String[] args) {
        RPG frame = new RPG();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}