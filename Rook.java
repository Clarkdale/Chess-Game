/*====================================================================
    Class Name:  Rook
       Purpose:  Rook instance of Piece class
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;

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

  public boolean move(int x, int y, Piece [][] in) {
    boolean clear = true;
    boolean case1 = super.getColumn() == x;
    boolean case2 = super.getRow() == y;
    if (case1) {
      if (y > super.getRow()) {
        for (int i = super.getRow(); i < y; i++) {
          if (in[i][x] != null) {
            clear = false;
          }
        }
      } else if (super.getRow() > y) {
        for (int i = y; i <= super.getRow(); i++) {
          if (in[i][x] != null) {
            clear = false;
          }
        }
      }
    } else if (case2) {
      if (x > super.getColumn()) {
        for (int i = super.getColumn(); i < x; i++) {
          if (in[y][i] != null) {
            clear = false;
          }
        }
      } else if (super.getColumn() > x) {
        for (int i = x; i < super.getColumn(); i++) {
          if (in[y][i] != null) {
            clear = false;
          }
        }
      }
    }

    if (in[y][x] != null) {
      clear = in[y][x].type() != this.type();
    }

    if ((case1 || case2) && clear) {
      super.setX(x);
      super.setY(y);
    }
    return (case1 || case2) && clear;
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
