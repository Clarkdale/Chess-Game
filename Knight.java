/*====================================================================
    Class Name:  Knight
       Purpose:  Knight instance of piece class
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;

public class Knight extends Piece {
  public Knight(int k, int l, boolean color) {
    super(k, l, color);
  } //end method

  public int getRow() {
    return super.getRow();
  } //end method

  public int getColumn() {
    return super.getColumn();
  } //end method

  public boolean move(int x, int y, Piece [][] check) {
    boolean case0 = super.getRow() == y && super.getColumn() == x;
    boolean case1 = super.getRow() - 2 == y && (super.getColumn() == x + 1 || super.getColumn() == x - 1);
    boolean case2 = super.getRow() + 2 == y && (super.getColumn() == x + 1 || super.getColumn() == x - 1);
    boolean case3 = super.getRow() + 1 == y && (super.getColumn() == x + 2 || super.getColumn() == x - 2);
    boolean case4 = super.getRow() - 1 == y && (super.getColumn() == x + 2 || super.getColumn() == x - 2);
    if (case0 || case1 || case2 || case3 || case4) {
      super.setX(x);
      super.setY(y);
    }
    return (case0 || case1 || case2 || case3 || case4);
  } //end method

  public Image graphic() {
    if (super.type()) {
      return (new Image("WhiteKnight.png"));
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/full_width_12_column/public/media/proebsting4_0.png"));
    } else {
      return (new Image("BlackKnight.png"));
      //return (new Image("https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAIA_wDGAAAAAQAAAAAAAAqFAAAAJGM5YmU3MjU5LTJmOTItNGQzYS1iY2U5LTc4NjMxMTY2MmNkNg.jpg"));
    } //end if/else
  } //end method

  public boolean type() {
    return super.type();
  } //end method
} //end class
