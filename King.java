/*====================================================================
    Class Name:  King
       Purpose:  King instance of piece class
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;

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

  public boolean move(int x, int y, Piece [][] in) {
    boolean case0 = super.getColumn() == x && super.getRow() == y;
    boolean case1 = super.getColumn() - x == 1 || super.getRow() - y == 1 || x - super.getColumn() == 1 || y - super.getRow() == 1;
    if (case1) {
      super.setX(x);
      super.setY(y);
    }
    return case1 || case0;
  } //end method
} //end class
