package rpg;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

class MainPanel extends JPanel implements KeyListener, Runnable, Common {
	// パネルサイズ
	public static final int WIDTH = 480;
	public static final int HEIGHT = 480;

	// マップ
	private Map map;
	// 勇者
	private Chara hero;

	// アクションキー
	private ActionKey leftKey;
	private ActionKey rightKey;
	private ActionKey upKey;
	private ActionKey downKey;

	// ゲームループ
	private Thread gameLoop;

	public MainPanel() {
		// パネルの推薦サイズを設定、pack()するときに必要
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		// パネルが操作を受けるように登録する
		setFocusable(true);
		addKeyListener(this);

		// キーアクションを作成
		leftKey = new ActionKey();
		rightKey = new ActionKey();
		upKey = new ActionKey();
		downKey = new ActionKey();

		// マップを作成
		map = new Map("map.dat", this);
		// 勇者を作成
		hero = new Chara(4, 4, "hero.gif", map, this);

		// ゲームループ開始
		gameLoop = new Thread(this);
		gameLoop.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// X方向のオフセットを計算
		int offsetX = MainPanel.WIDTH / 2 - hero.getPx();
		// マップ端ではスクロールしないようにする
		offsetX = Math.min(offsetX, 0);
		offsetX = Math.max(offsetX, MainPanel.WIDTH - map.getWidth());

		// Y方向のオフセットを計算
		int offsetY = MainPanel.HEIGHT / 2 - hero.getPy();
		// マップの端ではスクロールしないようにする
		offsetY = Math.min(offsetY, 0);
		offsetY = Math.max(offsetY, MainPanel.HEIGHT - map.getHeight());

		// マップを書く
		map.draw(g, offsetX, offsetY);

		// 勇者を書く
		hero.draw(g, 4, 4);
		
	}

	public void run() {
		while (true) {
			checkInput();
			if (hero.isMoving()){
				if((hero.move())){
					
				}
			}

			// 再描画
			repaint();

			//

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
	private void checkInput(){
		if(leftKey.isPressed()){
			if(!hero.isMoving()){ 			//移動中でなければ
				hero.setDirection(LEFT);	//方向をセットして
				hero.setMoving(true);		//移動（スクロール）開始
			}
		}
		if(rightKey.isPressed()){
			if(!hero.isMoving()){
				hero.setDirection(RIGHT);
				hero.setMoving(true);
			}
		}
		if(upKey.isPressed()){
			if(!hero.isMoving()){
				hero.setDirection(UP);
				hero.setMoving(true);
			}
		}
		if(downKey.isPressed()){
			if(!hero.isMoving()){
				hero.setDirection(DOWN);
				hero.setMoving(true);
			}
		}
	}
	
	/**
	 * キーが押されたらキーの状態を「押された」に変える
	 * 
	 * @param e
	 *            キーイベント
	 */
	public void keyPressed(KeyEvent e) {
		// 押されたキーを調べる
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT) {
			leftKey.press();
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			rightKey.press();
		}
		if (keyCode == KeyEvent.VK_UP) {
			upKey.press();
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			downKey.press();
		}
	}

	/**
	 * キーが離されたらキーの状態を「離された」に変える
	 * 
	 * 
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT) {
			leftKey.release();
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			rightKey.release();
		}
		if (keyCode == KeyEvent.VK_UP) {
			upKey.release();
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			downKey.release();
		}
	}

	public void keyTyped(KeyEvent e) {
	}
}
