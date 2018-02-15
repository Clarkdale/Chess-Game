package model;
/*====================================================================
    Class Name:  Bishop
       Purpose:  Bishop instance of piece class
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;
import java.util.*;

public class Bishop extends Piece {
  public Bishop(int i, int j, boolean type) {
    super(i, j, type);
  } //end method

  public int getRow() {
    return super.getRow();
  } //end method

  public int getColumn() {
    return super.getColumn();
  } //end method

  public Image graphic() {
    if (super.type()) {
      return (new Image("file:images/WhiteBishop.png"));
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/medium/public/images/people/mccann.jpg"));
    } else {
      return (new Image("file:images/BlackBishop.png"));
      //return (new Image("https://www.amrita.edu/site/ISSISP2016/images/ChristianCollberg.jpg"));
    } //end if/else
  } //end method

  /*====================================================================
     Method Name:  move
         Purpose:  Creates a set of tuples, which hold (x.y) positions
                   on the board as to where this type of piece could
                   potentially move. Essentially a "scan board" method
      Parameters:  in: A 2D array of pieces, which represent the board
         Returns:  A set of tuples of all possible moves
  ====================================================================*/
  public Set<Tuple> move(Piece [][] in) {
    //initial current variables set
    int currX = super.getColumn();
    int currY = super.getRow();

    //output moveset initialized
    Set<Tuple> out = new HashSet<>();

    //Original space piece occupied is added, this is technically a valid move
    out.add(new Tuple(currX, currY));

    //Booleans created to use as checks for the circumstance that a piecec
    //obstructs one of the diagonal paths of this piece
    boolean case1 = true;
    boolean case2 = true;
    boolean case3 = true;
    boolean case4 = true;

    //For loop iterates up to 8, this is the maximum number of squares on the
    //board
    for (int i = 0; i < 8; i++) {
      //booleans for pieces to the left of the initial square occupied
      if (currX - i >= 0) {
        //this piece of logic handles positions to the left, and down on the
        //board
        if (currY - i >= 0) {
          //Process is stopped using case booleans if one of the squares is
          //occupied by any piece
          if (in[currY - i][currX - i] != null) {

            //if statement makes it possible to take opponents pieces
            if (in[currY - i][currX - i].type() != super.type() && case1) {
              out.add(new Tuple(currX - i, currY - i));
            } //end if
            case1 = false;
          } //end if

          //as long as this boolean is false, it means that the path is Another
          //obstructed, and this position can be added to the overall moveset
          if (case1) {
            out.add(new Tuple(currX - i, currY - i));
          } //end if
        } //end if

        //This piece of logic handles moves to the left, and upwards on then
        //board.
        if (currY + i <= 7) {
          //process is stopped if there is an obstruction from another piece
          if (in[currY + i][currX - i] != null) {
            //statement makes it possible to attack other pieces
            if (in[currY + i][currX - i].type() != super.type() && case2) {
              out.add(new Tuple(currX - i, currY + i));
            } //end if
            case2 = false;
          } //end if

          //So long as there is not an obstruction, possible move is added
          //to the moveset
          if (case2) {
            out.add(new Tuple(currX - i, currY + i));
          } //end if
        } //end if
      } //end if

      //This block of logic handles pieces to the right of the initial square
      //occupied by the instance of this piece, where this logic follows a
      //similar structure to the block of logic above.
      if (currX + i <= 7) {
        if (currY - i >= 0) {
          if (in[currY - i][currX + i] != null) {
            if (in[currY - i][currX + i].type() != super.type() && case3) {
              out.add(new Tuple(currX + i, currY - i));
            } //end if
            case3 = false;
          } //end if

          if (case3) {
            out.add(new Tuple(currX + i, currY - i));
          } //end if
        } //end if

        if (currY + i <= 7) {
          if (in[currY + i][currX + i] != null) {
            if (in[currY + i][currX + i].type() != super.type() && case4) {
              out.add(new Tuple(currX + i, currY + i));
            } //end if
            case4 = false;
          } //end if

          if (case4) {
            out.add(new Tuple(currX + i, currY + i));
          } //end if
        } //end if
      } //end if
    } //end for

    return out;
  } //end method
} //end class
