package rpg;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

class MainPanel extends JPanel implements KeyListener, Runnable, Common {
	// �p�l���T�C�Y
	public static final int WIDTH = 480;
	public static final int HEIGHT = 480;

	// �}�b�v
	private Map map;
	// �E��
	private Chara hero;

	// �A�N�V�����L�[
	private ActionKey leftKey;
	private ActionKey rightKey;
	private ActionKey upKey;
	private ActionKey downKey;

	// �Q�[�����[�v
	private Thread gameLoop;

	public MainPanel() {
		// �p�l���̐��E�T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		// �p�l����������󂯂�悤�ɓo�^����
		setFocusable(true);
		addKeyListener(this);

		// �L�[�A�N�V�������쐬
		leftKey = new ActionKey();
		rightKey = new ActionKey();
		upKey = new ActionKey();
		downKey = new ActionKey();

		// �}�b�v���쐬
		map = new Map("map.dat", this);
		// �E�҂��쐬
		hero = new Chara(4, 4, "hero.gif", map, this);

		// �Q�[�����[�v�J�n
		gameLoop = new Thread(this);
		gameLoop.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// X�����̃I�t�Z�b�g���v�Z
		int offsetX = MainPanel.WIDTH / 2 - hero.getPx();
		// �}�b�v�[�ł̓X�N���[�����Ȃ��悤�ɂ���
		offsetX = Math.min(offsetX, 0);
		offsetX = Math.max(offsetX, MainPanel.WIDTH - map.getWidth());

		// Y�����̃I�t�Z�b�g���v�Z
		int offsetY = MainPanel.HEIGHT / 2 - hero.getPy();
		// �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
		offsetY = Math.min(offsetY, 0);
		offsetY = Math.max(offsetY, MainPanel.HEIGHT - map.getHeight());

		// �}�b�v������
		map.draw(g, offsetX, offsetY);

		// �E�҂�����
		hero.draw(g, 4, 4);
		
	}

	public void run() {
		while (true) {
			checkInput();
			if (hero.isMoving()){
				if((hero.move())){
					
				}
			}

			// �ĕ`��
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
			if(!hero.isMoving()){ 			//�ړ����łȂ����
				hero.setDirection(LEFT);	//�������Z�b�g����
				hero.setMoving(true);		//�ړ��i�X�N���[���j�J�n
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
	 * �L�[�������ꂽ��L�[�̏�Ԃ��u�����ꂽ�v�ɕς���
	 * 
	 * @param e
	 *            �L�[�C�x���g
	 */
	public void keyPressed(KeyEvent e) {
		// �����ꂽ�L�[�𒲂ׂ�
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
	 * �L�[�������ꂽ��L�[�̏�Ԃ��u�����ꂽ�v�ɕς���
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
