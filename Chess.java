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

  @Override
  public void start(Stage window) {
    window.setTitle("Chess");

    BorderPane rootPane = new BorderPane();
    screen = new Canvas(800, 800);
    Scene display = new Scene(rootPane, 800, 800);
    rootPane.getChildren().add(screen);
    out = screen.getGraphicsContext2D();
    chessMain();
    window.setScene(display);
    window.show();
  }

  public void chessMain() {
    Piece [][] bobbyFisher = boardGen();
    printBoard(bobbyFisher);
    interact(bobbyFisher);
  }

  public static Piece [][] boardGen() {
    Piece [][] board = new Piece[8][8];

    board[0][0] = new Rook(0, 0, false);
    board[0][1] = new Knight(1, 0, false);
    board[0][2] = new Bishop(2, 0, false);
    board[0][3] = new Queen(3, 0, false);
    board[0][4] = new King(4, 0, false);
    board[0][5] = new Bishop(5, 0, false);
    board[0][6] = new Knight(6, 0, false);
    board[0][7] = new Rook(7, 0, false);

    board[7][0] = new Rook(0, 7, true);
    board[7][1] = new Knight(1, 7, true);
    board[7][2] = new Bishop(2, 7, true);
    board[7][3] = new Queen(3, 7, true);
    board[7][4] = new King(4, 7, true);
    board[7][5] = new Bishop(5, 7, true);
    board[7][6] = new Knight(6, 7, true);
    board[7][7] = new Rook(7, 7, true);

    for (int i = 0; i < board.length; i++) {
      board[1][i] = new Pawn(i, 1, false);
      board[6][i] = new Pawn(i, 6, true);
    }
    return board;
  }

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

  public void interact(Piece [][] in) {
    screen.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      Piece mover = null;
      @Override
      public void handle(MouseEvent event) {
        int x = (int) event.getX() / 100;
        int y = (int) event.getY() / 100;
        if (mover == null) {
          mover = in[y][x];
          if (y % 2 == 0 && x % 2 == 0 || y % 2 != 0 && x % 2 != 0) {
            out.setFill(Color.BURLYWOOD);
          } else {
            out.setFill(Color.SADDLEBROWN);
          }
          out.fillRect(x * 100, y * 100, 100, 100);
          in[y][x] = null;
        } else {
          if (mover.move(x, y, in)) {
            in[y][x] = mover;
            mover = null;
            out.drawImage(in[y][x].graphic(), x * 100, y * 100, 100, 100);
          } else {
            System.out.println("Invalid move. Please try again.");
          }
        } //end if/else

      } //end internal method
    }); //end anonymous handler
  } //end method
} //end class
