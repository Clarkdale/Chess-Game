/*====================================================================
    Class Name:  Bishop
       Purpose:  Bishop instance of piece class
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;

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

  public boolean move(int x, int y, Piece [][] in) {
    boolean clear = true;
    boolean case1 = super.getColumn() - x == super.getRow() - y;
    boolean case2 = super.getColumn() - x == y - super.getRow();
    boolean case3 = x - super.getColumn() == y - super.getRow();
    boolean case4 = x - super.getColumn() == super.getRow() - y;

    if (case1 && x != super.getColumn() && super.getRow() != y) {
      for (int i = 0; i < x; i++) {
        if (in[super.getRow() - i][super.getColumn() - i] != null) {
          clear = false;
        }
      }
    } else if (case2 && x != super.getColumn() && super.getRow() != y) {
      for (int i = 0; i < super.getRow() - y; i++) {
        if (in[y - i][super.getColumn() + i] != null) {
          clear = false;
        }
      }
    } else if (case3 && x != super.getColumn() && super.getRow() != y) {
      for (int i = 0; i < x - super.getColumn(); i++) {
        if (in[y - i][x - i] != null) {
          clear = false;
        }
      }
    } else if (case4 && x != super.getColumn() && super.getRow() != y) {
      for (int i = 0; i < super.getRow() - y; i++) {
        if (in[x - i][super.getRow() - i] != null) {
          clear = false;
        }
      }
    }

    if ((case1 || case2 || case3 || case4) && clear) {
      super.setX(x);
      super.setY(y);
    }
    return (case1 || case2 || case3 || case4) && clear;
  } //end method
} //end class
