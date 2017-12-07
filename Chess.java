import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import java.io.*;

public class Chess extends Application {
  private GraphicsContext out;
  private Canvas screen;

  public static void main(String [] args) {
    launch(args);
  }

  /*====================================================================
     Method Name:  start
         Purpose:  initializes a graphical interface window, in this
                   case being an 800 by 800 pixel window. This window
                   is then used to allow a user to interact with a
                   chess game, generated within said window.
      Parameters:  window: an instance of a stage which will be used
                   as the basis of the graphical window
         Returns:  None
  ====================================================================*/
  @Override
  public void start(Stage window) {
    window.setTitle("Chess");

    //overall borderpain created
    BorderPane rootPane = new BorderPane();

    //Interactive window is made, which will later have a listener for clicking
    //on screen
    screen = new Canvas(800, 800);

    //Scene made to later use as a means of setting the scene of GUI
    Scene display = new Scene(rootPane, 800, 800);

    rootPane.getChildren().add(screen);

    out = screen.getGraphicsContext2D();

    chessMain();

    //Scene is added, and the contents of this scene are displayed ot the GUI
    //using these methods.
    window.setScene(display);
    window.show();
  } //end method

  /*====================================================================
     Method Name:  chessMain
         Purpose:  A bit of a seperate main for the chess game. This main
                   creates the initial board using a 2D array in the background
                   to keep track of the different piece objects which will
                   jump around based on user moves.
      Parameters:  None
         Returns:  None
  ====================================================================*/
  public void chessMain() {
    Piece [][] bobbyFisher = boardGen();
    printBoard(bobbyFisher);
    interact(bobbyFisher);
  } //end method

  /*====================================================================
     Method Name:  boardGen
         Purpose:  This method generates the 2D array representation of
                   the playing board, which will include the different kinds
                   of pieces associated in each appropriate position.
                   The 2D list is then returned to be used in other methods.
                   Recall that a chess board is classically an 8 by 8 square
                   board, which is represented in the length of the outer,
                   and interior lists
      Parameters:  None
         Returns:  2D array representation of the playing barod
  ====================================================================*/
  public static Piece [][] boardGen() {
    //2D array is used in background to keep track of pieces on board
    Piece [][] board = new Piece[8][8];

    //This side of the board is generagted to be black pieces
    board[0][0] = new Rook(0, 0, false);
    board[0][1] = new Knight(1, 0, false);
    board[0][2] = new Bishop(2, 0, false);
    board[0][3] = new Queen(3, 0, false);
    board[0][4] = new King(4, 0, false);
    board[0][5] = new Bishop(5, 0, false);
    board[0][6] = new Knight(6, 0, false);
    board[0][7] = new Rook(7, 0, false);

    //side of the board for white pieces
    board[7][0] = new Rook(0, 7, true);
    board[7][1] = new Knight(1, 7, true);
    board[7][2] = new Bishop(2, 7, true);
    board[7][3] = new Queen(3, 7, true);
    board[7][4] = new King(4, 7, true);
    board[7][5] = new Bishop(5, 7, true);
    board[7][6] = new Knight(6, 7, true);
    board[7][7] = new Rook(7, 7, true);

    //Furthermore, generates pawns using a for loop to reduce redundancy
    for (int i = 0; i < board.length; i++) {
      board[1][i] = new Pawn(i, 1, false);
      board[6][i] = new Pawn(i, 6, true);
    } //end for
    return board;
  } //end method

  /*====================================================================
     Method Name:  printBoard
         Purpose:  Create the chess board on screen of the graphic
                   interface, taking in the 2D array representation to do
                   so. Colors were googled to get a proper wood board
                   feel, and the drawImage method associated with the
                   graphics context module was used to draw piece representations
                   using a downloaded zip file of pngs for each piece
      Parameters:  in: 2D array representation of board
         Returns:  None
  ====================================================================*/
  public void printBoard(Piece [][] in) {
    //nested for loop to move across even indicies for alternating colors
    //scheme usually found on a chess board, as well as odd number indicies.
    //The starting variable in each for loop is modified based on whether or
    //not this index is odd or not.
    for (int i = 0; i < 8; i++) {
      //even indicies of board rows
      if (i % 2 == 0) {
        for (int j = 0; j < 8; j += 2) {
          //graphics context prepared and used to draw a 100 by 100 pixel
          //square as a means of reference for each position on the board
          out.setFill(Color.BURLYWOOD);
          out.fillRect(j * 100, i * 100, 100, 100);
          //same process with a different color for alternating color scheme
          out.setFill(Color.SADDLEBROWN);
          out.fillRect((j + 1) * 100, i * 100, 100, 100);
        } //end for
        //case of odd numbered row indicies
      } else {
        //j loop start modified to start at 1 for proper color scheme
        for (int j = 1; j < 8; j += 2) {
          //same process as above
          out.setFill(Color.BURLYWOOD);
          out.fillRect(j * 100, i * 100, 100, 100);

          out.setFill(Color.SADDLEBROWN);
          out.fillRect((j - 1) * 100, i * 100, 100, 100);
        } //end for
      } //end if/ else
    } //end for

    //nested for loop used again to potentially draw each necessary piece on
    //screen. I know, two nested for loops, and I know I'm always concerned about
    //time complexity, but hey, still O(n^2) either way
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (in[i][j] != null) {
          out.drawImage(in[i][j].graphic(), j * 100, i * 100, 100, 100);
        } //end if
      } //end for
    } //end for
  } //end method

  /*====================================================================
     Method Name:  interact
         Purpose:  Takes in a 2D array to be modified based on different
                   interactions. This method allows for a clicker thread
                   to be used on the graphical interface, which allows
                   the user to move pieces across different positions on
                   screen. Another class is implemented inside of This
                   method to allow for the clickable screen to be
                   used
      Parameters:  init: An anchorpane object which will contain this
                   button at some point
         Returns:  None
  ====================================================================*/
  public void interact(Piece [][] in) {
    //an event handler is added to the graphics context, using an
    //anonymous class to avoid creating an external privatized classes
    //for this
    screen.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      //mover will be the piece the user clicks to move around screen
      Piece mover = null;

      //actual method for using the mouse to move pieces around
      @Override
      public void handle(MouseEvent event) {
        //x and y values will be the pixel value over a 100 in this case,
        //as the board size is 800 by 800, with an 8x8 grid on top of this,
        //so each position will 100 pixels difference from on another,
        //allowing for this pixel value / 100 invariant to be true.
        int x = (int) event.getX() / 100;
        int y = (int) event.getY() / 100;

        //this boolean catches if the user has not selected a piece yet,
        //upon which the mover variable is reset, and the board is drawing
        //over to remove where the piecec was.
        if (mover == null) {
          //resetting of var
          mover = in[y][x];

          //these internal boolean checks decide what color the square
          //click on needs to beccome, and the square is covered,
          //in order to avoid redrawing the board every single time a
          //click is made.
          if (y % 2 == 0 && x % 2 == 0 || y % 2 != 0 && x % 2 != 0) {
            out.setFill(Color.BURLYWOOD);
          } else {
            out.setFill(Color.SADDLEBROWN);
          } //end if/ else

          //call to fill the squre clicked on
          out.fillRect(x * 100, y * 100, 100, 100);

          //resetting of position in the background 2D array to maintain
          //consistency in gameplay
          in[y][x] = null;

        //If the user already has a piece "in hand," or really stored to
        //the mover here, then the process of moving the piece is followed
        //within this else statement.
        } else {
          //this internal boolean check uses the move method from the
          //piece class to determine if a move is "valid."
          if (mover.move(x, y, in)) {
            //resets background 2D array for consistency
            in[y][x] = mover;
            //removes piece from mover variable so the user can pick
            //up other pieces after this
            mover = null;

            //Again, this boolean logic is used to avoid redrawing then
            //entire board after one click from the user
            if (y % 2 == 0 && x % 2 == 0 || y % 2 != 0 && x % 2 != 0) {
              out.setFill(Color.BURLYWOOD);
            } else {
              out.setFill(Color.SADDLEBROWN);
            } //end if/else

            //The process of drawing the new square, followed by the
            //appropriate piece. The square must be covered in the event
            //that there was a piece in the square the user clicked on
            out.fillRect(x * 100, y * 100, 100, 100);
            out.drawImage(in[y][x].graphic(), x * 100, y * 100, 100, 100);

          //Else statement prints an error to the user with standard out
          } else {
            System.out.println("Invalid move. Please try again.");
          } //end if/ else
        } //end if/else
      } //end internal method
    }); //end anonymous handler
  } //end method
} //end class
