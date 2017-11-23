/*====================================================================
    Class Name:  Queen
       Purpose:  Queen instance of Piece class... I'll admit it, I didn't
                 really comment these, and really they were just implemented
                 for the png images to be accessible with minimal work
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;

public class Queen extends Piece {
  public Queen(int i, int j, boolean type) {
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
      return (new Image("WhiteQueen.png"));
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/medium/public/images/people/allison2.jpg"));
    } else {
      return (new Image("BlackQueen.png"));
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/medium/public/images/people/obagy_0.jpg"));
    } //end if/else
  } //end method

  public boolean move(int x, int y, Piece [][] in) {
    boolean case1 = super.getColumn() == x;
    boolean case2 = super.getRow() == y;
    boolean case3 = super.getColumn() - x == super.getRow() - y;
    boolean case4 = super.getColumn() - x == y - super.getRow();
    boolean case5 = x - super.getColumn() == y - super.getRow();
    boolean case6 = x - super.getColumn() == super.getRow() - y;
    if (case1 || case2 || case3 || case4 || case5 || case6) {
      super.setY(y);
      super.setX(x);
    }

    return (case1 || case2 || case3 || case4 || case5 || case6);
  } //emd method

  public boolean type() {
    return super.type();
  } //end method
} //end class
