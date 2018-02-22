package model;
/*====================================================================
    Class Name:  Pawn
       Purpose:  Instancec of Piece class to be used for pawn objects
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;
import java.util.*;

public class Pawn extends Piece {
  private int moved;

  public Pawn(int k, int l, boolean color, boolean ivory) {
    super(k, l, color, ivory);
    moved = 1;
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
    //variables created to hold current positions to compare while scanning
    //board
    int currX = super.getColumn();
    int currY = super.getRow();

    //set of tuples to be returned
    Set<Tuple> possible = new HashSet<>();

    //original position added so piece can be placed back
    possible.add(new Tuple(currX, currY));

    //boolean check for if the piece is white
    if (super.type()) {
      //Check to add common move of one space forward
      if (currY > 0 && in[currY - 1][currX] == null) {
        possible.add(new Tuple(currX, currY - 1));
      } //end if

      //check for moving pawn two spaces forward
      if (moved <= 1 && currY > 1 && in[currY - 2][currX] == null && in[currY - 1][currX] == null) {
        possible.add(new Tuple(currX, currY - 2));
      } //end if

      //checks for taking pieces in diagonal direction
      if (currX != 0 && currY > 0) {
        if (in[currY - 1][currX - 1] != null && !in[currY - 1][currX - 1].type()) {
          possible.add(new Tuple(currX - 1, currY - 1));
        } //end if
      } //end if

      if (currX != 7 && currY > 0) {
        if (in[currY - 1][currX + 1] != null && !in[currY - 1][currX + 1].type()) {
          possible.add(new Tuple(currX + 1, currY - 1));
        } //end if
      } //end if

    //similar logic in opposite direction for black pieces
    } else {
      if (currY < 7 && in[currY + 1][currX] == null) {
        possible.add(new Tuple(currX, currY + 1));
      } //end if

      if (moved <= 1 && currY < 6 && in[currY + 2][currX] == null && in[currY + 1][currX] == null) {
        possible.add(new Tuple(currX, currY + 2));
      } //end if

      if (currX != 0 && currY < 7) {
        if (in[currY + 1][currX - 1] != null && in[currY + 1][currX - 1].type()) {
          possible.add(new Tuple(currX - 1, currY + 1));
        } //end if
      } //end if

      if (currX != 7 && currY < 7) {
        if (in[currY + 1][currX + 1] != null && in[currY + 1][currX + 1].type()) {
          possible.add(new Tuple(currX + 1, currY + 1));
        } //end if
      } //end if
    } //end if/else

    return possible;
  } //end method

  /*====================================================================
     Method Name:  graphic
         Purpose:  Gives an image representation of this type of piecec
                   on a gui
      Parameters:  None
         Returns:  Image to be displayed on GUI
  ====================================================================*/
  public Image graphic() {
    if (super.getIvory()) {
      //return (new Image("tim.jpg"));
      return (new Image("file:images/WhitePawn.png"));
    } else {
      //return (new Image("stephen.jpg"));
      return (new Image("file:images/BlackPawn.png"));
    } //end if/else
  } //end method

  /*====================================================================
     Method Name:  setY
         Purpose:  Y setter is overwritten in this class in order to
                   move the number of moves up, to prevent moving
                   forward two spaces more than once
      Parameters:  in: Value which y position will be set to
         Returns:  None
  ====================================================================*/
  public void setY(int in) {
    if (in != super.getRow()) {
      moved++;
    } //end if
    super.setY(in);
  } //end method

  public boolean type() {
    return super.type();
  } //end methods
} //end class
