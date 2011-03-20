import java.util.*;
import java.math.*;
import java.io.*;

public class ERunner {

    public static final void main(String[] args) {
        new ERunner().run(System.in, args);
    }
    
    private String run(InputStream input, String[] args) {
        
        Scanner in = new Scanner(input);
		EBoard board = new EBoard(args);
        
        // run the game
        while( in.hasNextLine() ) {
			
			// print the board and ask for input
			board.print();
			board.prompt();
			
			// get the input
			String line = in.nextLine();
			String[] command = line.split(":");
			
			String command_name = command[0];
			if(command_name == "display") {
				 board.print();
			}
            board.execute(command);
			
        }

        System.out.flush();
        return( "OK" );
    }
    
    class ENode {
		String owned;
		
        public ENode() {
			
		}
    }
    
    class EBoard {
		
		// player
		String player;
		
		// board dimensions
		int x_size;
		int y_size;
		
		// playing field
		ENode[][] field;
		
        public EBoard(String[] args) {
			x_size = 10;
			y_size = 10;
			player = "A";
			field = new ENode()
		}
		
		public execute(String[] command) {
			String command_name = command[0];
			if(command_name == "display") {
				System.out.println("The board got printed.");
			}
			return;
		}
    }
}

/* 
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

public class ESquare {

  public Color color;
  public Boolean owned;

  public ESquare (Color[] colorsAvailable){
    int n  = (int) Math.floor((Math.random()*colorsAvailable.length));
    this.color = colorsAvailable[n];
    this.owned = false;
  }
  public Boolean check(Color compare){
    if (compare == this.color){
      this.owned = true;
      return true;
    }
    return false;
  }
  public void shift(Color thatColor){
    if (owned){
      this.color = thatColor;
    }
  }
}
*/
