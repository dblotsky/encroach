import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


/*	written to: Holy Thunderfoce Rhapsody - Dawn of Victory
 *	La'petach chatat rovetz (The final embrace)
 *	Our Truth Lacunca Coil - Karmacode
 *	The Divine Wings of Tragedy Symphony X - The Divine Wings of Tragedy
 *	Dirge for November Opeth - Blackwater Park
 *	Prepare for War DragonForce - Inhuman Rampage
 *	Reborn Through Hate Coroner - Coroner
 *	The Jester Race In Flames - The Jester Race
 *	Dark Chest of Wonders Nightwish - End of an Era (disc 1)
 */

public class Encroach extends JPanel{
	
	Boolean debug = true;
	
	//Constants
	static int BOARD_WIDTH = 300;
	static int BOARD_HEIGHT = 300;
	static int TOPMENU_HEIGHT = 100;
	static int BOTMENU_HEIGHT = 100;
	static int WINDOW_WIDTH = 300;
	static int WINDOW_HEIGHT = BOARD_HEIGHT + TOPMENU_HEIGHT + BOTMENU_HEIGHT;
	static int BUTTON_WIDTH = 40;
	static int BUTTON_HEIGHT = 40;
	static int BUTTON_Y = 30;//from within botmenu
	static int BUTTON_LEFT_X = 70;
	static int BUTTON_MID_X  = 130;
	static int BUTTON_RIGHT_X = 190;
	static int SQUARE_SIZE = 10;
	
	
	//GUI components
	Image boardBuffer;
	Image topBuffer;
	Image botBuffer;
	Color[] pallet = {Color.BLUE,Color.RED,Color.GREEN,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW};
	String status= "begining";
	BufferedImage leftImg;
	BufferedImage rightImg;
	
	
	//Vars	
	EPlayer player1;
	EPlayer player2;
	EBoard board;
	ESquare[][] field;
	int numColours = 7;
	int choiceOffset = 1;
	int choiceLeft;
	int choiceRight;
	int choiceMid;
	
	public Encroach() {
		//Admit it, it's beautiful that these line up so well.
		topBuffer = createImage(BOARD_WIDTH,TOPMENU_HEIGHT);
		boardBuffer = createImage(BOARD_WIDTH,BOARD_HEIGHT);
		botBuffer = createImage(BOARD_WIDTH,BOTMENU_HEIGHT);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		player1 = new EPlayer("Ryan",0);
		player2 = new EPlayer("Comp",0);
		board = new EBoard(numColours,30,30);
		board.initialize(player1, player2);
		field = board.get_field();
		try{
			leftImg = ImageIO.read(new File("Image/Left_Arrow.png"));
			rightImg = ImageIO.read(new File("Image/Right_Arrow.png"));
		}catch(IOException e){
			System.out.println("arrow images files not located");
		}
	}
	
	public void paintAll(){
		paintTop();
		paintBoard();
		paintBot();
		repaint();
	}
	
	public void paintBoard(){
		boardBuffer = createImage(BOARD_WIDTH, BOARD_HEIGHT);
		Graphics bg = boardBuffer.getGraphics();
		for (int x = 0; x < board.x_size;x++){
			for (int y = 0; y < board.y_size; y++) {
				bg.setColor(pallet[field[x][y].color]);
				bg.fillRect(x*SQUARE_SIZE, y*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
		}
	}
	
	public void paintTop(){
		topBuffer = createImage(BOARD_WIDTH,TOPMENU_HEIGHT);
		Graphics bg = topBuffer.getGraphics();
		bg.drawString(player1.name + ": " + Integer.toString(player1.score), 10, 60);
		bg.drawString("Games Won: ",10,80);
		bg.drawString(player2.name + ": " + Integer.toString(player2.score), BOARD_WIDTH/2 + 10, 60);
		bg.drawString("Games Won: ",BOARD_WIDTH/2 + 10,80);
	}
	
	public void paintBot(){
		botBuffer = createImage(BOARD_WIDTH,BOTMENU_HEIGHT);
		Graphics bg = botBuffer.getGraphics();
		bg.setColor(pallet[choiceLeft]);
		bg.fillRect(BUTTON_LEFT_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		bg.setColor(pallet[choiceMid]);
		bg.fillRect(BUTTON_MID_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		bg.setColor(pallet[choiceRight]);
		bg.fillRect(BUTTON_RIGHT_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		bg.drawImage(leftImg, 10, 25, getParent());
		bg.drawImage(rightImg, BUTTON_RIGHT_X + BUTTON_WIDTH + 10, 25, getParent());
	}
	
	public void paintComponent (Graphics g){
		clear(g);
		g.drawImage(topBuffer, 0, 0, getParent());
		g.drawImage(boardBuffer, 0, TOPMENU_HEIGHT, getParent());
		g.drawImage(botBuffer, 0, TOPMENU_HEIGHT + BOARD_HEIGHT, getParent());
		/*for (int i = 0; i < pallet.length; i++){
			g.setColor(pallet[i]);
			g.fillRect(100+10*i, 350, 10, 10);
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(i), 101+i*10, 350);
		}
		g.drawString(status, 100, 400);*/
	}
	
	protected void clear (Graphics g){
		super.paintComponent(g);
	}
	
	void makeMove(int move){
		if (board.can_play(player1, move)){
			board.play_color(player1, move);
			board.play_color(player2, player2.ai_next_color_choice(board));
			status = "made move";
		}else{
			status = "nope";
		}
		field = board.get_field();
		shiftRight();
		paintAll();
	}
	
	void setChoices(){
		choiceLeft = choiceOffset - 1;
		choiceMid = choiceOffset;
		choiceRight = choiceOffset + 1;
		if (choiceLeft == player2.color){
			choiceLeft--;
		}
		if (choiceLeft < 0 && player2.color != numColours - 1){
			choiceLeft = numColours - 1;
		}else if (choiceLeft < 0){
			choiceLeft = numColours - 2;
			
		}if (choiceRight == player2.color){
			choiceRight++;
		}	
		if (choiceRight > numColours -1 && player2.color != 0){
			choiceRight = 0;
		}else if (choiceRight > numColours -1){
			choiceRight = 1;
		}
		if (debug){
			System.out.println("choiceLeft:"+choiceLeft+" choiceMid:"+choiceMid+" choiceRight:"+choiceRight+" Offset:"+choiceOffset);
		}
		paintAll();
	}
	
	void shiftRight(){
		choiceOffset++;
		if (choiceOffset == player2.color){
			choiceOffset++;
		}
		if (choiceOffset > numColours - 1 && player2.color != 0){
			choiceOffset = 0;
		}else if (choiceOffset > numColours - 1){
			choiceOffset = 1;
		}
		setChoices();
	}
	
	void shiftLeft(){
		choiceOffset--;
		if (choiceOffset == player2.color){
			choiceOffset--;
		} 
		if (choiceOffset < 0 && player2.color != numColours - 1){
			choiceOffset = numColours - 1;
		}else if (choiceOffset < 0){
			choiceOffset = numColours - 2;
		}
		setChoices();
	}
	
	public static void main (String args[]) {
		JFrame main = new JFrame();
		main.setVisible(false);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(WINDOW_WIDTH + 20, WINDOW_HEIGHT + 40);
		//main.setResizable(false);
		final Encroach game = new Encroach();
		main.add(game);
		main.setVisible(true);
		game.setChoices();
		game.paintAll();
		
		KeyListener actions = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				int move = 0;
				
				switch (arg0.getKeyCode()){
				case KeyEvent.VK_0:
					move = 0;
					break;
				case KeyEvent.VK_1:
					move = 1;
					break;
				case KeyEvent.VK_2:
					move = 2;
					break;
				case KeyEvent.VK_3:
					move = 3;
					break;
				case KeyEvent.VK_4:
					move = 4;
					break;
				case KeyEvent.VK_5:
					move = 5;
					break;
				case KeyEvent.VK_6:
					move = 6;
					break;
				default:
					game.status = "invalid input";
				}
				game.makeMove(move);
			}
		};
		MouseListener clicks = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int x = e.getX();
				int y = e.getY();
				if (y > TOPMENU_HEIGHT + BOARD_HEIGHT + BUTTON_Y 
					&& y < TOPMENU_HEIGHT + BOARD_HEIGHT + BUTTON_Y + BUTTON_HEIGHT){
					if (x < BUTTON_LEFT_X){
						game.shiftLeft();
					}else if (x > BUTTON_LEFT_X && x < BUTTON_LEFT_X + BUTTON_WIDTH){
						game.makeMove(game.choiceLeft);
					}else if (x > BUTTON_MID_X && x < BUTTON_MID_X + BUTTON_WIDTH){
						game.makeMove(game.choiceMid);
					}else if (x > BUTTON_RIGHT_X && x < BUTTON_RIGHT_X + BUTTON_WIDTH){
						game.makeMove(game.choiceRight);
					}else if (x > BUTTON_RIGHT_X + BUTTON_WIDTH){
						game.shiftRight();
					}
				}else if (y > TOPMENU_HEIGHT && y < TOPMENU_HEIGHT + BOARD_HEIGHT){
					game.makeMove(game.field[x/SQUARE_SIZE][(y-TOPMENU_HEIGHT)/SQUARE_SIZE].color);
				}
			}
		};
		game.addKeyListener(actions);
		game.addMouseListener(clicks);
		game.requestFocusInWindow();
	}
}