package model;
/*====================================================================
    Class Name:  Knight
       Purpose:  Knight instance of piece class
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;
import java.util.*;

public class Knight extends Piece {
  public Knight(int k, int l, boolean color, boolean ivory) {
    super(k, l, color, ivory);
  } //end method

  public int getRow() {
    return super.getRow();
  } //end method

  public int getColumn() {
    return super.getColumn();
  } //end method

  public Set<Tuple> move(Piece [][] in) {
    int currX = super.getColumn();
    int currY = super.getRow();
    Set<Tuple> out = new HashSet<>();
    out.add(new Tuple(currX, currY));
    if (currX - 2 >= 0) {
      if (currY - 1 >= 0) {
        if (in[currY - 1][currX - 2] == null || in[currY - 1][currX - 2].type() != super.type()) {
          out.add(new Tuple(currX - 2, currY - 1));
        }
      }

      if (currY + 1 <= 7) {
        if (in[currY + 1][currX - 2] == null || in[currY + 1][currX - 2].type() != super.type()) {
          out.add(new Tuple(currX - 2, currY + 1));
        }
      }
    }

    if (currX + 2 <= 7) {
      if (currY - 1 >= 0) {
        if (in[currY - 1][currX + 2] == null || in[currY - 1][currX + 2].type() != super.type()) {
          out.add(new Tuple(currX + 2, currY - 1));
        }
      }

      if (currY + 1 <= 7) {
        if (in[currY + 1][currX + 2] == null || in[currY + 1][currX + 2].type() != super.type()) {
          out.add(new Tuple(currX + 2, currY + 1));
        }
      }
    }

    if (currY - 2 >= 0) {
      if (currX - 1 >= 0) {
        if (in[currY - 2][currX - 1] == null || in[currY - 2][currX - 1].type() != super.type()) {
          out.add(new Tuple(currX - 1, currY - 2));
        }
      }

      if (currX + 1 <= 7) {
        if (in[currY - 2][currX + 1] == null || in[currY - 2][currX + 1].type() != super.type()) {
          out.add(new Tuple(currX + 1, currY - 2));
        }
      }
    }

    if (currY + 2 <= 7) {
      if (currX - 1 >= 0) {
        if (in[currY + 2][currX - 1] == null || in[currY + 2][currX - 1].type() != super.type()) {
          out.add(new Tuple(currX - 1, currY + 2));
        }
      }

      if (currX + 1 <= 7) {
        if (in[currY + 2][currX + 1] == null || in[currY + 2][currX + 1].type() != super.type()) {
          out.add(new Tuple(currX + 1, currY + 2));
        }
      }
    }
    return out;
  } //end method

  public Image graphic() {
    if (super.getIvory()) {
      return (new Image("file:images/WhiteKnight.png"));
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/full_width_12_column/public/media/proebsting4_0.png"));
    } else {
      return (new Image("file:images/BlackKnight.png"));
      //return (new Image("https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAIA_wDGAAAAAQAAAAAAAAqFAAAAJGM5YmU3MjU5LTJmOTItNGQzYS1iY2U5LTc4NjMxMTY2MmNkNg.jpg"));
    } //end if/else
  } //end method

  public boolean type() {
    return super.type();
  } //end method
} //end class
