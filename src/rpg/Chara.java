package rpg;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Chara implements Common{
	//キャラクターの移動スピード
	private static final int SPEED = 4;
	
    //キャラクターのイメージ
    private Image image;
    //勇者の座標
    private int x, y;
    //キャラクターの座標
    private int px, py;
    //勇者の向いてる方向
    private int direction;
    
    //勇者のアニメーションカウンタ
    private int count;
    
    //移動中（スクロール中）か
    private boolean isMoving;
    //移動中の場合の移動ピクセル数
    private int movingLength;
    
    //キャラクターアニメーション用スレッド
    private Thread threadAnime;
    
    //マップへの参照
    private Map map;
    
    //パネルへの参照
    private MainPanel panel;
    
    public Chara(int x, int y, String filename, Map map ,MainPanel panel){
        this.x = x;
        this.y = y;
        
        direction = DOWN;
        count = 0;
        
        this.map = map;
        this.panel = panel;
        
        //イメージをロード
        loadImage(filename);
        
        //キャラクターアニメーション用スレッド開始
        threadAnime = new Thread(new AnimationThread());
        threadAnime.start();
    }
    
    public void draw(Graphics g, int offsetX, int offsetY){
        //countの値に応じて画像を切り替える
        g.drawImage(image, 
                    px + offsetX, py + offsetY,
                    px + CS + offsetX, py + CS  + offsetY, 
                    count * CS,  direction * CS,
                    CS + count * CS, direction * CS + CS, 
                    null);
    }
    
    /**
     * 移動処理
     * @return 1マス移動が完了したらtrueを返す。移動中はfalseを返す。
     */
    public boolean move(){
        switch(direction){
            case LEFT :
                if(moveLeft()){
                	//移動が完了した
                	return true;
                }
                break;
            case RIGHT :
            	if(moveRight()){
            		//移動が完了した
            		return true;
            	}
            	break;
            case UP :
            	if(moveUp()){
            		//移動が完了した
            		return true;
            	}
            	break;
            case DOWN :
            	if(moveDown()){
            		//移動が完了した
            		return true;
            	}
            	break;
        }
        
        //完了していない
        return false;
    }
    
    /**
     *左へ移動する。
     * @return 1マス移動が完了したらtrueを返す。移動中はfalseを返す。
     */
    private boolean moveLeft(){
    	//1マス先の座標
    	int nextX = x - 1;
    	int nextY = y;
    	if(nextX < 0) nextX = 0;
    	//その場所に障害物がなければ移動を開始
    	if(!map.isHit(nextX, nextY)){
    		//SPEEDピクセル分移動
    		px -= Chara.SPEED;
    		if(px < 0) px = 0;
    		//移動距離を加算
    		movingLength += Chara.SPEED;
    		//移動が1マスを超えていたら
    		if(movingLength >= CS){
    			//移動する
    			x--;
    			if(x < 0) x = 0;
    			px = x * CS;
    			//移動が完了
    			isMoving = false;
    			return true;
    		}
    	}else{
    		
    		isMoving = false;
    		//元の位置に戻す
    		px = x * CS;
    		py = y * CS;
    	}
    	
    	return false;
    }
    
    /**
     * 右へ移動
     * @return 1マス移動が完了したらtrueを返す。移動中はfalseを返す。
     */
    private boolean moveRight(){
    	int nextX = x + 1;
    	int nextY = y;
    	if(nextX > map.getCol() - 1) nextX = map.getCol() - 1;
    	//その場所に障害物がなければ移動を開始
    	if(!map.isHit(nextX, nextY)){
    		//SPEEDピクセル分移動
    		px += Chara.SPEED;
    		if( px > map.getWidth() - CS)
    			px = map.getWidth() - CS ;
    	//移動距離を加算
    	movingLength += Chara.SPEED;
    	//移動が1マスを超えていたら
    	if(movingLength >= CS){
    		//移動する
    		x++;
    		if(x > map.getCol() - 1)
    			x = map.getCol() - 1;
    		px = x * CS;
    		//移動が完了
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
     * 上へ移動する
     * @return 1マス移動が完了したらtrue返す。移動中はfalseを返す。
     */
    private boolean moveUp(){
    	//1マス先の座標
    	int nextX = x;
    	int nextY = y -1;
    	if(py < 0){
    		py = 0;
    	}
    	if(!map.isHit(nextX, nextY)){
    		//SPEEDピクセル分移動
    		py -= Chara.SPEED;
    		if(py < 0) 
    			py = 0;
    		//移動距離を加算
    		movingLength += Chara.SPEED;
    		//移動が1マス分を超えていたら
    		if(movingLength >= CS){
    			//移動する
    			y--;
    			if(y < 0) y = 0;
    			py = y * CS;
    			//移動が完了
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
     * 下へ移動
     * @return １マス移動が完了したらtrueを返す。移動中はfalseを返す。 
     */
    private boolean moveDown(){
    	int nextX = x;
    	int nextY = y  + 1;
    	if(nextY > map.getRow() - 1) nextY = map.getRow() - 1;
    	//その場所に障害物がなければ移動を開始
    	if(!map.isHit(nextX, nextY)){
    		//SPEEDピクセル分移動
    		py += Chara.SPEED;
    		if(py > map.getHeight() - CS)
    			py = map.getHeight() - CS;
    		//移動距離を加算
    		movingLength += Chara.SPEED;
    		//移動が1マス分を超えていたら
    		if(movingLength >= CS){
    			//移動する
    			y++;
    			if(y > map.getRow() - 1) y = map.getRow() - 1;
    			py = y * CS;
    		//移動が完了
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
    	//移動距離を初期化
    	movingLength = 0;
    }
    
    private void loadImage(String filename){
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        image = icon.getImage();
    }
    
    private class AnimationThread extends Thread {
        public void run(){
            while(true){
                //countを切り替える
                if(count == 0){
                    count = 1;
                }else if(count == 1){
                   count = 0;
                }
                
                panel.repaint();
                
                //300ミリ秒休止=300ミリ秒おきに勇者の絵を切り替える
                try{
                    Thread.sleep(300);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
