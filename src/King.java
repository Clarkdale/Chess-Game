/*====================================================================
    Class Name:  King
       Purpose:  King instance of piece class
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;
import java.util.*;

public class King extends Piece {
  private boolean check;
  private int moved;

  public King(int i, int j, boolean type) {
    super(i, j, type);
    check = false;
    moved = 0;
  } //end method

  public void setX(int in) {
    if (in != super.getColumn()) {
      moved++;
    }

    super.setX(in);
  }

  public void setY(int in) {
    if (in != super.getRow()) {
      moved++;
    }

    super.setY(in);
  }

  public void setCheck(boolean in) {
    check = in;
  }

  public boolean inCheck() {
    return check;
  }

  public int getRow() {
    return super.getRow();
  } //end method

  public int getColumn() {
    return super.getColumn();
  } //end method

  public boolean type() {
    return super.type();
  } //end method

  public boolean castle() {
    return moved == 0;
  }

  public Image graphic() {
    if (super.type()) {
      return (new Image("images/WhiteKing.png"));
      //return (new Image("https://media.licdn.com/mpr/mpr/shrinknp_200_200/p/2/000/18f/383/0ea0035.jpg"));
    } else {
      return (new Image("images/BlackKing.png"));
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/medium/public/images/people/debray.jpg"));
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
    //current variables set to compare back to in later logic
    int currX = super.getColumn();
    int currY = super.getRow();

    //Set of possile moves of tuples initialized
    Set<Tuple> out = new HashSet<>();

    //adds original position to set, original position is considered a "Invalid
    //move"
    out.add(new Tuple(currX, currY));

    //logic for all moves one square to the left of the current king loccation,
    //in relation to the initial position.
    if (currX - 1 >= 0) {
      if (currY - 1 >= 0) {
        if (in[currY - 1][currX - 1] == null || in[currY - 1][currX - 1].type() != super.type()) {
          out.add(new Tuple(currX - 1, currY - 1));
        } //end if
      } //end if

      if (currY + 1 <= 7) {
        if (in[currY + 1][currX - 1] == null || in[currY + 1][currX - 1].type() != super.type()) {
          out.add(new Tuple(currX - 1, currY + 1));
        } //end if
      } //end if

      if (in[currY][currX - 1] == null || in[currY][currX - 1].type() != super.type()) {
        out.add(new Tuple(currX - 1, currY));
      } //end if
    } //end if

    //handles logic for all potential moves to the right of the initial king
    //posiiton
    if (currX + 1 <= 7) {
      if (currY - 1 >= 0) {
        if (in[currY - 1][currX + 1] == null || in[currY - 1][currX + 1].type() != super.type()) {
          out.add(new Tuple(currX + 1, currY - 1));
        } //end if
      } //end if

      if (currY + 1 <= 7) {
        if (in[currY + 1][currX + 1] == null || in[currY + 1][currX + 1].type() != super.type()) {
          out.add(new Tuple(currX + 1, currY + 1));
        } //end if
      } //end if

      if (in[currY][currX + 1] == null || in[currY][currX + 1].type() != super.type()) {
        out.add(new Tuple(currX + 1, currY));
      } //end if
    } //end if

    //logic for handling above/below the initial king position
    if (currY - 1 >= 0) {
      if (in[currY - 1][currX] == null || in[currY - 1][currX].type() != super.type()) {
        out.add(new Tuple(currX, currY - 1));
      } //end if
    } //end if

    //logic for above.below moves in relation to the initial king position
    if (currY + 1 <= 7) {
      if (in[currY + 1][currX] == null || in[currY + 1][currX].type() != super.type()) {
        out.add(new Tuple(currX, currY + 1));
      } //end if
    } //end if

    if ((currY == 7 || currY == 0) && currX == 4) {
      if (in[currY][currX + 1] == null && in[currY][currX + 2] == null) {
        out.add(new Tuple(currX + 2, currY));
      }
    }

    return out;
  } //end method
} //end class
