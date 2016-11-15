package rpg;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;

public class Map implements Common{
    
    //�}�b�v�̍s���E�񐔁i�P�ʁF�}�X�j
    private int row;
    private int col;
    
    
    //�}�b�v�S�̂̑傫��(�P�ʁF�s�N�Z��)
    private int width;
    private int height;
    
    //�}�b�v
    private int[][] map;
    
    //�`�b�v�Z�b�g
    private Image floorImage;
    private Image wallImage;
    private Image throneImage;
    
    //���C���p�l���ւ̎Q��
    private MainPanel panel;
    
    public Map(String filename, MainPanel panel){
    	//�}�b�v�̃��[�h
    	load(filename);
        //�C���[�W�����[�h
        loadImage();
    }
    
    public void draw(Graphics g, int offsetX, int offsetY){
        //�I�t�Z�b�g����ɕ`��͈͂����߂�
        int firstTileX = pixelsToTiles( -offsetX);
        int lastTileX = firstTileX + pixelsToTiles(MainPanel.WIDTH) + 1;
        //�`��͈͂��}�b�v�̑傫�����傫���Ȃ�Ȃ��悤�ɒ���
        lastTileX = Math.min(lastTileX, col);
        
        int firstTileY = pixelsToTiles( -offsetY);
        int lastTileY = firstTileY + pixelsToTiles(MainPanel.HEIGHT) + 1;
        //�`��͈͂��}�b�v�̑傫�����傫���Ȃ�Ȃ��悤�ɒ���
        lastTileY = Math.min(lastTileY, row);
        
        for(int i = firstTileY; i < lastTileY; i++){
            for(int j = firstTileX; j < lastTileX; j++){
                //map�̒l�ɉ����ĉ摜������
                switch(map[i][j]){
                    case 0 ://��
                        g.drawImage(floorImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                    case 1 : //��
                        g.drawImage(wallImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                    case 2://�ʍ� 
                    	g.drawImage(throneImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                    	break;
                }
            }
        }
    }
    
    /**
     * (x,y)�ɂԂ�����̂����邩���ׂ�B
     * @param x �}�b�v��x���W
     * @param y �}�b�v�̍�y�W
     * @return (x, y)�ɂԂ�����̂���������false��Ԃ��B
     */
    public boolean isHit(int x, int y){
        //(x, y)�ɕǂ��ʍ�����������Ԃ���
        if(map[y][x] == 1 || map[y][x] == 2 ){
            return true;
        }
        //�Ȃ���΂Ԃ���Ȃ�
        return false;
    }
    
    /**
     * �s�N�Z���P�ʂ��}�X�P�ʂɕύX����
     * @param pixels �s�N�Z���P��
     * @return �}�X�P��
    */
    public static int pixelsToTiles(double pixels){
        return (int)Math.floor(pixels / CS);
    }
    
    /**
     * �}�X�P�ʂ��s�N�Z���P�ʂɕύX����
     * @param tiles �}�X�P��
     * @return �s�N�Z���P��
    */
    public static int tilesToPixels(int tiles){
        return tiles * CS;
    }
    
    public void loadImage(){
        ImageIcon icon = new ImageIcon(getClass().getResource("floor.gif"));
        floorImage = icon.getImage();
        
        icon  = new ImageIcon(getClass().getResource("wall.gif"));
        wallImage = icon.getImage();
        
        icon = new ImageIcon(getClass().getResource("throne.gif"));
        throneImage = icon.getImage();
    }
    
    public int getRow(){
    	return row;
    }
    
    public int getCol(){
    	return col;
    }
    
    public int getWidth(){
    	return width;
    }
    
    public int getHeight(){
    	return height;
    }
    
    /**
     * �t�@�C������}�b�v�f�[�^��ǂݍ���
     * @param filename  �ǂݍ��݃}�b�v�f�[�^�̃t�@�C����
     */
    private void load(String filename){
    	try{
    		BufferedReader br = new BufferedReader(
    				new InputStreamReader(
    						getClass().getResourceAsStream(filename)));
    		//row��ǂݍ���
    		String line = br.readLine();
    		row = Integer.parseInt(line);
    		//col��ǂݍ���
    		line = br.readLine();
    		col = Integer.parseInt(line);
    		//�}�b�v�T�C�Y��ݒ�
    		width = col * CS;
    		height = row * CS;
    		//�}�b�v���쐬
    		map = new int[row][col];
    		for(int i = 0; i < row; i++){
    			line = br.readLine();
    			for(int j = 0; j < col; j++){
    				map[i][j] = Integer.parseInt(line.charAt(j) + "");	
    			}
    		}
//    			show()
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    }
    public void show(){
    	for(int i = 0; i < col; i++){
    		for(int j = 0; j < row; j++){
    			System.out.println(map[i][j]);
    		}
    		System.out.println();
    	}
    }
}