package rpg;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Chara implements Common{
	//�L�����N�^�[�̈ړ��X�s�[�h
	private static final int SPEED = 4;
	
    //�L�����N�^�[�̃C���[�W
    private Image image;
    //�E�҂̍��W
    private int x, y;
    //�L�����N�^�[�̍��W
    private int px, py;
    //�E�҂̌����Ă����
    private int direction;
    
    //�E�҂̃A�j���[�V�����J�E���^
    private int count;
    
    //�ړ����i�X�N���[�����j��
    private boolean isMoving;
    //�ړ����̏ꍇ�̈ړ��s�N�Z����
    private int movingLength;
    
    //�L�����N�^�[�A�j���[�V�����p�X���b�h
    private Thread threadAnime;
    
    //�}�b�v�ւ̎Q��
    private Map map;
    
    //�p�l���ւ̎Q��
    private MainPanel panel;
    
    public Chara(int x, int y, String filename, Map map ,MainPanel panel){
        this.x = x;
        this.y = y;
        
        direction = DOWN;
        count = 0;
        
        this.map = map;
        this.panel = panel;
        
        //�C���[�W�����[�h
        loadImage(filename);
        
        //�L�����N�^�[�A�j���[�V�����p�X���b�h�J�n
        threadAnime = new Thread(new AnimationThread());
        threadAnime.start();
    }
    
    public void draw(Graphics g, int offsetX, int offsetY){
        //count�̒l�ɉ����ĉ摜��؂�ւ���
        g.drawImage(image, 
                    px + offsetX, py + offsetY,
                    px + CS + offsetX, py + CS  + offsetY, 
                    count * CS,  direction * CS,
                    CS + count * CS, direction * CS + CS, 
                    null);
    }
    
    /**
     * �ړ�����
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    public boolean move(){
        switch(direction){
            case LEFT :
                if(moveLeft()){
                	//�ړ�����������
                	return true;
                }
                break;
            case RIGHT :
            	if(moveRight()){
            		//�ړ�����������
            		return true;
            	}
            	break;
            case UP :
            	if(moveUp()){
            		//�ړ�����������
            		return true;
            	}
            	break;
            case DOWN :
            	if(moveDown()){
            		//�ړ�����������
            		return true;
            	}
            	break;
        }
        
        //�������Ă��Ȃ�
        return false;
    }
    
    /**
     *���ֈړ�����B
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveLeft(){
    	//1�}�X��̍��W
    	int nextX = x - 1;
    	int nextY = y;
    	if(nextX < 0) nextX = 0;
    	//���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
    	if(!map.isHit(nextX, nextY)){
    		//SPEED�s�N�Z�����ړ�
    		px -= Chara.SPEED;
    		if(px < 0) px = 0;
    		//�ړ����������Z
    		movingLength += Chara.SPEED;
    		//�ړ���1�}�X�𒴂��Ă�����
    		if(movingLength >= CS){
    			//�ړ�����
    			x--;
    			if(x < 0) x = 0;
    			px = x * CS;
    			//�ړ�������
    			isMoving = false;
    			return true;
    		}
    	}else{
    		
    		isMoving = false;
    		//���̈ʒu�ɖ߂�
    		px = x * CS;
    		py = y * CS;
    	}
    	
    	return false;
    }
    
    /**
     * �E�ֈړ�
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveRight(){
    	int nextX = x + 1;
    	int nextY = y;
    	if(nextX > map.getCol() - 1) nextX = map.getCol() - 1;
    	//���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
    	if(!map.isHit(nextX, nextY)){
    		//SPEED�s�N�Z�����ړ�
    		px += Chara.SPEED;
    		if( px > map.getWidth() - CS)
    			px = map.getWidth() - CS ;
    	//�ړ����������Z
    	movingLength += Chara.SPEED;
    	//�ړ���1�}�X�𒴂��Ă�����
    	if(movingLength >= CS){
    		//�ړ�����
    		x++;
    		if(x > map.getCol() - 1)
    			x = map.getCol() - 1;
    		px = x * CS;
    		//�ړ�������
    		isMoving = false;
    		return true;
    		}
    	}else{
    		isMoving = false;
    		px = x * CS;
    		py = y * CS;
    	}
    	
    	return false;
    }
    
    /**
     * ��ֈړ�����
     * @return 1�}�X�ړ�������������true�Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveUp(){
    	//1�}�X��̍��W
    	int nextX = x;
    	int nextY = y -1;
    	if(py < 0){
    		py = 0;
    	}
    	if(!map.isHit(nextX, nextY)){
    		//SPEED�s�N�Z�����ړ�
    		py -= Chara.SPEED;
    		if(py < 0) 
    			py = 0;
    		//�ړ����������Z
    		movingLength += Chara.SPEED;
    		//�ړ���1�}�X���𒴂��Ă�����
    		if(movingLength >= CS){
    			//�ړ�����
    			y--;
    			if(y < 0) y = 0;
    			py = y * CS;
    			//�ړ�������
    			isMoving = false;
    			return true;
    		}
    	}else{
    		isMoving = false;
    		px = x * CS;
    		py = y * CS;
    	}
    	
    	return false;
    }
    
    /**
     * ���ֈړ�
     * @return �P�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B 
     */
    private boolean moveDown(){
    	int nextX = x;
    	int nextY = y  + 1;
    	if(nextY > map.getRow() - 1) nextY = map.getRow() - 1;
    	//���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
    	if(!map.isHit(nextX, nextY)){
    		//SPEED�s�N�Z�����ړ�
    		py += Chara.SPEED;
    		if(py > map.getHeight() - CS)
    			py = map.getHeight() - CS;
    		//�ړ����������Z
    		movingLength += Chara.SPEED;
    		//�ړ���1�}�X���𒴂��Ă�����
    		if(movingLength >= CS){
    			//�ړ�����
    			y++;
    			if(y > map.getRow() - 1) y = map.getRow() - 1;
    			py = y * CS;
    		//�ړ�������
    			isMoving = false;
    			return true;
    		}
    	}else{
    		isMoving = false;
    		px = x * CS;
    		py = y * CS;
    	}
    	
    	return false;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getPx(){
    	return px;
    }
    
    public int getPy(){
    	return py;
    }
    
    public void setDirection(int dir){
    	direction = dir;
    }
    
    public boolean isMoving(){
    	return isMoving;
    }
    
    public void setMoving(boolean flag){
    	isMoving = flag;
    	//�ړ�������������
    	movingLength = 0;
    }
    
    private void loadImage(String filename){
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        image = icon.getImage();
    }
    
    private class AnimationThread extends Thread {
        public void run(){
            while(true){
                //count��؂�ւ���
                if(count == 0){
                    count = 1;
                }else if(count == 1){
                   count = 0;
                }
                
                panel.repaint();
                
                //300�~���b�x�~=300�~���b�����ɗE�҂̊G��؂�ւ���
                try{
                    Thread.sleep(300);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
