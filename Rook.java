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

  public Set<Tuple> move(Piece [][] in) {
    Set<Tuple> possible = new HashSet<>();
    int currX = super.getColumn();
    int currY = super.getRow();
    boolean clear = true;
    possible.add(new Tuple(currX, currY));
    boolean case1 = true;
    boolean case2 = true;
    boolean case3 = true;
    boolean case4 = true;
    for (int i = 0; i < 8; i++) {
      if (currY - i >= 0) {
        if (in[currY - i][currX] != null) {
          if (in[currY - i][currX].type() != super.type() && case1) {
            possible.add(new Tuple(currX, currY - i));
          }
          case1 = false;
        }
        if (case1) {
          possible.add(new Tuple(currX, currY - i));
        }
      }

      if (currY + i <= 7) {
        if (in[currY + i][currX] != null) {
          if (in[currY + i][currX].type() != super.type() && case2) {
            possible.add(new Tuple(currX, currY + i));
          }
          case2 = false;
        }
        if (case2) {
          possible.add(new Tuple(currX, currY + i));
        }
      }


      if (currX - i >= 0) {
        if (in[currY][currX - i] != null) {
          if (in[currY][currX - i].type() != super.type() && case3) {
            possible.add(new Tuple(currX - i, currY));
          }
          case3 = false;
        }
        if (case3) {
          possible.add(new Tuple(currX - i, currY));
        }
      }

      if (currX + i <= 7) {
        if (in[currY][currX + i] != null) {
          if (in[currY][currX + i].type() != super.type() && case4) {
            possible.add(new Tuple(currX + i, currY));
          }
          case4 = false;
        }
        if (case4) {
          possible.add(new Tuple(currX + i, currY));
        }
      }
    }

    return possible;
  } //end method

  public Image graphic() {
    if (super.type()) {
      return (new Image("WhiteRook.png"));
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/medium/public/images/people/homer.jpg"));
    } else {
      return (new Image("BlackRook.png"));
      //return (new Image("http://www.surdeanu.info/mihai/website/mihai/Surdeanu5-small.jpg"));
    } //end if/else
  } //end method

  public boolean type() {
    return super.type();
  } //end method
} //end class
