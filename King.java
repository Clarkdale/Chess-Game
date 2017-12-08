/*====================================================================
    Class Name:  King
       Purpose:  King instance of piece class
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;
import java.util.*;
public class King extends Piece {
  public King(int i, int j, boolean type) {
    super(i, j, type);
  } //end method

  public int getRow() {
    return super.getRow();
  } //end method

  public int getColumn() {
    return super.getColumn();
  } //end method

  public boolean type() {
    return super.type();
  } //end method

  public Image graphic() {
    if (super.type()) {
      return (new Image("WhiteKing.png"));
      //return (new Image("https://media.licdn.com/mpr/mpr/shrinknp_200_200/p/2/000/18f/383/0ea0035.jpg"));
    } else {
      return (new Image("BlackKing.png"));
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/medium/public/images/people/debray.jpg"));
    } //end if/else
  } //end method

  public Set<Tuple> move(Piece [][] in) {
    int currX = super.getColumn();
    int currY = super.getRow();
    Set<Tuple> out = new HashSet<>();
    out.add(new Tuple(currX, currY));

    if (currX - 1 >= 0) {
      if (currY - 1 >= 0) {
        if (in[currY - 1][currX - 1] == null || in[currY - 1][currX - 1].type() != super.type()) {
          out.add(new Tuple(currX - 1, currY - 1));
        }
      }

      if (currY + 1 <= 7) {
        if (in[currY + 1][currX - 1] == null || in[currY + 1][currX - 1].type() != super.type()) {
          out.add(new Tuple(currX - 1, currY + 1));
        }
      }

      if (in[currY][currX - 1] == null || in[currY][currX - 1].type() != super.type()) {
        out.add(new Tuple(currX - 1, currY));
      }
    }

    if (currX + 1 <= 7) {
      if (currY - 1 >= 0) {
        if (in[currY - 1][currX + 1] == null || in[currY - 1][currX + 1].type() != super.type()) {
          out.add(new Tuple(currX + 1, currY - 1));
        }
      }

      if (currY + 1 <= 7) {
        if (in[currY + 1][currX + 1] == null || in[currY + 1][currX + 1].type() != super.type()) {
          out.add(new Tuple(currX + 1, currY + 1));
        }
      }

      if (in[currY][currX + 1] == null || in[currY][currX + 1].type() != super.type()) {
        out.add(new Tuple(currX + 1, currY));
      }
    }

    if (currY - 1 >= 0) {
      if (in[currY - 1][currX] == null || in[currY - 1][currX].type() != super.type()) {
        out.add(new Tuple(currX, currY - 1));
      }
    }

    if (currY + 1 <= 7) {
      if (in[currY + 1][currX] == null || in[currY + 1][currX].type() != super.type()) {
        out.add(new Tuple(currX, currY + 1));
      }
    }
    return out;
  } //end method
} //end class
