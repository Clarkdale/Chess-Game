package model;
/*====================================================================
    Class Name:  Rook
       Purpose:  Rook instance of Piece class
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;
import java.util.*;

public class Rook extends Piece {
  public Rook(int i, int j, boolean type) {
    super(i, j, type);
  } //end method

  public int getRow() {
    return super.getRow();
  } //end method

  public int getColumn() {
    return super.getColumn();
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
    //moveset to be output is initialized
    Set<Tuple> possible = new HashSet<>();

    //Current position variables are set to the initial location
    int currX = super.getColumn();
    int currY = super.getRow();

    //initial board position is added to the moveset, this is considered a
    //valid move
    possible.add(new Tuple(currX, currY));

    //Case variables for clear paths are initialized
    boolean case1 = true;
    boolean case2 = true;
    boolean case3 = true;
    boolean case4 = true;

    //For loop iterates up to 8, this is the maximum number of squares on the
    //board
    for (int i = 0; i < 8; i++) {
      //This statement adds all potential moves above/below the piece to the
      //overall moveset
      if (currY - i >= 0) {
        //As long as the path is not obstructed, case1 remains true, and adds
        //pieces
        if (in[currY - i][currX] != null) {
          //allows for pieces to be taken
          if (in[currY - i][currX].type() != super.type() && case1) {
            possible.add(new Tuple(currX, currY - i));
          } //end if
          case1 = false;
        } //end if

        //adds pieces as long as the path is not obstructed
        if (case1) {
          possible.add(new Tuple(currX, currY - i));
        } //end if
      } //end if

      //The below logic has a similar structure to the code above.

      //Adds potential moves that are above/below the initial position
      if (currY + i <= 7) {
        if (in[currY + i][currX] != null) {
          if (in[currY + i][currX].type() != super.type() && case2) {
            possible.add(new Tuple(currX, currY + i));
          } //end if
          case2 = false;
        } //end if

        if (case2) {
          possible.add(new Tuple(currX, currY + i));
        } //end if
      } //end if

      //Adds pieces to the left of the initial position
      if (currX - i >= 0) {
        if (in[currY][currX - i] != null) {
          if (in[currY][currX - i].type() != super.type() && case3) {
            possible.add(new Tuple(currX - i, currY));
          } //end if
          case3 = false;
        } //end if

        if (case3) {
          possible.add(new Tuple(currX - i, currY));
        } //end if
      } //end if

      //adds pieces to the right of the initial location
      if (currX + i <= 7) {
        if (in[currY][currX + i] != null) {
          if (in[currY][currX + i].type() != super.type() && case4) {
            possible.add(new Tuple(currX + i, currY));
          } //end if
          case4 = false;
        } //end if

        if (case4) {
          possible.add(new Tuple(currX + i, currY));
        } //end if
      } //end if
    } //end for

    return possible;
  } //end method

  public Image graphic() {
    if (super.type()) {
      return (new Image("file:images/WhiteRook.png"));
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/medium/public/images/people/homer.jpg"));
    } else {
      return (new Image("file:images/BlackRook.png"));
      //return (new Image("http://www.surdeanu.info/mihai/website/mihai/Surdeanu5-small.jpg"));
    } //end if/else
  } //end method

  public boolean type() {
    return super.type();
  } //end method
} //end class
