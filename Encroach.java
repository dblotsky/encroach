import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Encroach extends JPanel {
  
  //Back end
  ESquare[][] colorBoard;
  Boolean[][] checkBoard;
  Color colorsAvailable[];
  int sizex;
  int sizey;
  int blockSize;

  //Front end
  JPanel gameBoard;
  Image buffer;

  public static void main (String[] args){
    final Encroach main = new Encroach();
  }

  public Encroach () {
     
    JFrame frame = new JFrame();
		frame.setVisible(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);

    //init back end
    sizex = 20;
    sizey = 20;
    blockSize = 20;
    colorsAvailable = new Color[] {Color.BLUE,Color.RED,Color.YELLOW,Color.GREEN};
    initBoard();

    //action listeners

    //init front end
     gameBoard = new JPanel();
    //gameBoard.setBackground(Color.BLACK);
    frame.add(gameBoard);
   
    setVisible(true);
    displayBoard();
  }

  public void initBoard (){
    colorBoard = new ESquare[sizex][sizey];
    for (int j = 0; j < sizey; j++){
      for (int i = 0; i < sizex; i++){
        colorBoard[i][j] = new ESquare(colorsAvailable);
      }
    }
  }

  public void displayBoard(){
    buffer = createImage(gameBoard.getWidth(),gameBoard.getHeight());
		Graphics g = buffer.getGraphics();
    for (int j = 0; j < sizey; j++){
      for (int i = 0; i < sizex; i++){
        g.setColor(colorBoard[i][j].color);
        paintSquare(i,j,g);    
      }
    }
    Graphics draw = gameBoard.getGraphics();
    draw.drawImage(buffer,0,0,gameBoard); 
  }

  public void paintSquare(int x, int y, Graphics g){
		g.fillRect(x * blockSize,y * blockSize,blockSize,blockSize);
		Color old = g.getColor();
		g.setColor(Color.BLACK);
		g.drawRect(x * blockSize,y * blockSize,blockSize,blockSize);
		g.setColor(old);
	}
}

