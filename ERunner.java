import java.util.*;
import java.math.*;
import java.io.*;

public class ERunner {

    public static final void main(String[] args) {
        new ERunner().run(System.in, args);
    }
    
    private String run(InputStream input, String[] args) {
        
        // set up an input scanner
        Scanner in = new Scanner(input);
        
        // set up a board
        EBoard board = new EBoard(args);
        board.randomize();
        
        System.out.println("Please enter a command.");
        
        // run the game
        while(in.hasNextLine()) {
            
            // get the input
            String line = in.nextLine();
            String[] command = line.split(":");
            
            String command_name = command[0];
            if(command_name.equals("display")) {
                board.print();
            } else if(command_name.equals("move")) {
                board.make_move(command[1], command[2]);
                board.print();
            } else if(command_name.equals("randomize")) {
                board.randomize();
                board.print();
            } else if(command_name.equals("")) {
                System.out.println("Please enter a command.");
            } else {
                System.out.println("Unrecognized command: " + command_name + ".");
            }
        }

        System.out.flush();
        return( "OK" );
    }
    
    class ENode {
        
        // flag of ownership and the block's color
        Boolean owned;
        int color;
        
        public ENode() {
            owned = false;
            color = 0;
        }
    }
    
    class EBoard {
        
        public static final int NUM_COLORS = 10;
        public static final int X_DIMENSION = 20;
        public static final int Y_DIMENSION = 50;
        
        // board dimensions
        int x_size;
        int y_size;
        
        // playing field
        ENode[][] field;
        
        // random number generator
        Random generator;
        
        public EBoard(String[] args) {
            
            // initialize variables
            this.x_size = X_DIMENSION;
            this.y_size = Y_DIMENSION;

            // start the random number generator
            this.generator = new Random();
            
            // make a new playing field
            this.field = new ENode[x_size][y_size];
            for(int i = 0; i < this.x_size; i++) {
                for(int j = 0; j < this.y_size; j++) {
                    this.field[i][j] = new ENode();
                }
            }
            
            // set ownership of the top left node
            field[0][0].owned = true;
        }
        
        // prints the board
        public void print() {
            for(int i = 0; i < this.x_size; i++) {
                String current_row = "";
                for(int j = 0; j < this.y_size; j++) {
                    current_row += this.field[i][j].color + " ";
                }
                System.out.println(current_row);
            }
            return;
        }
        
        // randomizes the board; resets ownership
        public void randomize() {
            for(int i = 0; i < this.x_size; i++) {
                for(int j = 0; j < this.y_size; j++) {
                    field[i][j].color = generator.nextInt(NUM_COLORS);
                    field[i][j].owned = false;
                }
            }
            field[0][0].owned = true;
            return;
        }
        
        // makes a move for the given player and color
        public void make_move(String player, String color) {
            // make an array of booleans representing visited
            // make a queue of squares to check by storing their indices: initially with the first square
            // while queue not empty
            //      pop the first square off the queue
            //      for all adjacent squares, if (next color OR owned), AND (NOT already visited)
            //          push into the queue
            //      own this square, if it's not yet owned
            //      paint this square to the next color, if not already that color
            //      mark this square as visited
            // when the loop exits, all reachable squares will have been traversed, owned, and painted
            System.out.println("Player " + player + " changed color to " + color + ".");
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
