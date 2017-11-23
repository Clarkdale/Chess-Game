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
    boolean case1 = super.getColumn() - x == super.getRow() - y;
    boolean case2 = super.getColumn() - x == y - super.getRow();
    boolean case3 = x - super.getColumn() == y - super.getRow();
    boolean case4 = x - super.getColumn() == super.getRow() - y;
    if (case1 || case2 || case3 || case4) {
      super.setX(x);
      super.setY(y);
    }
    return (case1 || case2 || case3 || case4);
  } //end method
} //end class
