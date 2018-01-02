/*====================================================================
    Class Name:  Chess
       Purpose:  Entry point for the game
  Parent Class:  None
====================================================================*/
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
import java.util.*;

public class Chess extends Application {
  private GraphicsContext out;
  private Canvas screen;
  private boolean turn;
  private Piece [][] bobbyFisher;
  private Computer clark;

  public static void main(String [] args) {
    launch(args);
  } //end method

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
    window.getIcons().add(new Image("images/WhiteKnight.png"));
    //overall borderpain created
    BorderPane rootPane = new BorderPane();

    //Interactive window is made, which will later have a listener for clicking
    //on screen
    screen = new Canvas(640, 640);

    //Scene made to later use as a means of setting the scene of GUI
    Scene display = new Scene(rootPane, 840, 640);

    rootPane.setLeft(screen);

    AnchorPane pressMe = buttons();

    rootPane.setRight(pressMe);

    out = screen.getGraphicsContext2D();

    chessMain();

    //Scene is added, and the contents of this scene are displayed ot the GUI
    //using these methods.
    window.setScene(display);
    window.show();
  } //end method

  public AnchorPane buttons() {
    AnchorPane right = new AnchorPane();

    Button reset = new Button("Reset Board");

    ResetButton full = new ResetButton();

    reset.setOnAction(full);
    reset.setMinWidth(200);

    right.getChildren().add(reset);

    Button computer = new Button("Play The Mind of Clark");

    ComputerButton toClick = new ComputerButton();

    computer.setOnAction(toClick);
    computer.setMinWidth(200);

    AnchorPane.setTopAnchor(computer, 30.0);

    right.getChildren().add(computer);

    return right;
  }

  private class ResetButton implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      clark = null;
      chessMain();
    }
  }

  private class ComputerButton implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      clark = new Computer(bobbyFisher);
    }
  }

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
    bobbyFisher = boardGen();

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
  public Piece [][] boardGen() {
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
          out.fillRect(j * 80, i * 80, 80, 80);
          //same process with a different color for alternating color scheme
          out.setFill(Color.SADDLEBROWN);
          out.fillRect((j + 1) * 80, i * 80, 80, 80);
        } //end for
        //case of odd numbered row indicies
      } else {
        //j loop start modified to start at 1 for proper color scheme
        for (int j = 1; j < 8; j += 2) {
          //same process as above
          out.setFill(Color.BURLYWOOD);
          out.fillRect(j * 80, i * 80, 80, 80);

          out.setFill(Color.SADDLEBROWN);
          out.fillRect((j - 1) * 80, i * 80, 80, 80);
        } //end for
      } //end if/ else
    } //end for

    //nested for loop used again to potentially draw each necessary piece on
    //screen. I know, two nested for loops, and I know I'm always concerned about
    //time complexity, but hey, still O(n^2) either way
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (in[i][j] != null) {
          out.drawImage(in[i][j].graphic(), j * 80, i * 80, 80, 80);
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
    //the first turn is set to be true, to reflect that the game
    //starts with white moving first
    turn = true;

    //an event handler is added to the graphics context, using an
    //anonymous class to avoid creating an external privatized classes
    //for this
    screen.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      //mover will be the piece the user clicks to move around screen
      Piece mover = null;
      Set<Tuple> potential = null;

      //actual method for using the mouse to move pieces around
      @Override
      public void handle(MouseEvent event) {
        //x and y values will be the pixel value over a 100 in this case,
        //as the board size is 800 by 800, with an 8x8 grid on top of this,
        //so each position will 100 pixels difference from on another,
        //allowing for this pixel value / 100 invariant to be true.
        int x = (int) event.getX() / 80;
        int y = (int) event.getY() / 80;
        //this boolean catches if the user has not selected a piece yet,
        //upon which the mover variable is reset, and the board is drawing
        //over to remove where the piecec was.
        if (mover == null) {
          //this check will determine which player's turn it is
          if (in[y][x] != null) {
            if (in[y][x].type() == turn) {
              mover = in[y][x];

              //otherwise a message is output to user that the wrong
              //color piece is being selected
            } else {
              System.out.println("It is not your turn.");
            } //end if/else
          }

          //resetting of position in the background 2D array to maintain
          //consistency in gameplay
          if (mover != null) {
            in[y][x] = null;

            potential = mover.move(in);

            //this for loop will parse over all the possible moves,
            //and highlight where the player can move based on
            //which piece was selected.
            for (Tuple space : potential) {
              //the position of the original piece is highlighted
              //with this space
              if (space.getFirst() == mover.getColumn() && space.getSecond() == mover.getRow()) {
                out.setFill(Color.rgb(80, 80, 80, 0.75));
                out.fillRect(space.getFirst() * 80, space.getSecond() * 80, 80, 80);
                out.drawImage(mover.graphic(), mover.getColumn() * 80, mover.getRow() * 80, 80, 80);

              //all other possible moves are highlighted using
              //sea foam green circles
              } else {
                out.setFill(Color.AQUAMARINE);
                out.setStroke(Color.WHITE);
                out.fillOval(space.getFirst() * 80 + 20, space.getSecond() * 80 + 20, 40, 40);
                out.strokeOval(space.getFirst() * 80 + 20, space.getSecond() * 80 + 20, 40, 40);
              } //end if/else
            } //end for
          } //end if

        //If the user already has a piece "in hand," or really stored to
        //the mover here, then the process of moving the piece is followed
        //within this else statement.
        } else {
          Tuple clicked = new Tuple(x, y);

          //if the user selects an appropriate space, the following
          //is exectued
          if (potential.contains(clicked)) {

            //logic to flip turn after a user inputs a move
            if (mover.getColumn() != x || mover.getRow() != y) {
              turn = !turn;
            } //end if

            //additional logic to allow castling
            if (mover instanceof King && x == 6) {
              King re = (King) mover;

              //helper method from king class used to prevent unintentional
              //movement of the rook
              if (re.castle()) {
                Piece assist;

                //White side
                if (mover.type()) {
                  assist = (Rook) in[7][7];
                  assist.setX(5);
                  assist.setY(7);
                  in[7][7] = null;
                  in[7][5] = assist;

                //black side
                } else {
                  assist = (Rook) in[0][7];
                  assist.setX(5);
                  assist.setY(0);
                  in[0][7] = null;
                  in[0][5] = assist;
                } //end if/ else
              } //end if
            } //end if

            //these statements will changeg the piece object
            //according to where it was moved on the board
            mover.setX(x);
            mover.setY(y);

            if (in[y][x] != null) {
              clark.removePiece(in[y][x]);
            }

            //board is adjusted after move to fully represent game
            in[y][x] = mover;

            //mover is reset to null for next move
            mover = null;

            //move is made by the computer with the following statement

            printBoard(in);

            if (clark != null && !turn) {
              clark.makeMove();
              turn = !turn;
              printBoard(in);
            }
          }
        } //end if/else
      } //end internal method
    }); //end anonymous handler
  } //end method
} //end class
