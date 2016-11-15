package rpg;

import java.awt.Container;
import javax.swing.JFrame;

public class RPG extends JFrame{
    public RPG() {
        //タイトルを設定
        setTitle("RPG");
        
        //メインパネルを作成してフレームに追加
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);
        
        //パネルサイズに合わせてフレームサイズを自動設定
        pack();
    }
    public static void main(String[] args) {
        RPG frame = new RPG();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}