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
      return (new Image("WhiteBishop.png"));
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/medium/public/images/people/mccann.jpg"));
    } else {
      return (new Image("BlackBishop.png"));
      //return (new Image("https://www.amrita.edu/site/ISSISP2016/images/ChristianCollberg.jpg"));
    } //end if/else
  } //end method

  public Set<Tuple> move(Piece [][] in) {
    int currX = super.getColumn();
    int currY = super.getRow();

    Set<Tuple> out = new HashSet<>();

    out.add(new Tuple(currX, currY));

    boolean case1 = true;
    boolean case2 = true;
    boolean case3 = true;
    boolean case4 = true;
    for (int i = 0; i < 8; i++) {
      if (currX - i >= 0) {
        if (currY - i >= 0) {
          if (in[currY - i][currX - i] != null) {
            if (in[currY - i][currX - i].type() != super.type() && case1) {
              out.add(new Tuple(currX - i, currY - i));
            }
            case1 = false;
          }
          if (case1) {
            out.add(new Tuple(currX - i, currY - i));
          }
        }

        if (currY + i <= 7) {
          if (in[currY + i][currX - i] != null) {
            if (in[currY + i][currX - i].type() != super.type() && case2) {
              out.add(new Tuple(currX - i, currY + i));
            }
            case2 = false;
          }
          if (case2) {
            out.add(new Tuple(currX - i, currY + i));
          }
        }
      }

      if (currX + i <= 7) {
        if (currY - i >= 0) {
          if (in[currY - i][currX + i] != null) {
            if (in[currY - i][currX + i].type() != super.type() && case3) {
              out.add(new Tuple(currX + i, currY - i));
            }
            case3 = false;
          }
          if (case3) {
            out.add(new Tuple(currX + i, currY - i));
          }
        }

        if (currY + i <= 7) {
          if (in[currY + i][currX + i] != null) {
            if (in[currY + i][currX + i].type() != super.type() && case4) {
              out.add(new Tuple(currX + i, currY + i));
            }
            case4 = false;
          }
          if (case4) {
            out.add(new Tuple(currX + i, currY + i));
          }
        }
      }
    }
    return out;
  } //end method
} //end class
