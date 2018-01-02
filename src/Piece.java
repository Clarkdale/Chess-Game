/*====================================================================
    Class Name:  Piece
       Purpose:  A parent class for all the pieces which will be used
                 in a chess game
  Parent Class:  None
====================================================================*/
import javafx.scene.image.Image;
import java.util.*;

public abstract class Piece {
  private int row;
  private int column;
  private Boolean isWhite;

  public Piece(int x, int y, boolean ivory) {
    row = y;
    column = x;
    isWhite = ivory;
  } //end method

  public int getColumn() {
    return column;
  } //end method

  public int getRow() {
    return row;
  } //end method

  public boolean type() {
    return isWhite;
  } //end method

  public void setX(int k) {
    column = k;
  } //end method

  public void setY(int k) {
    row = k;
  } //end method

  public abstract Image graphic();

  public abstract Set<Tuple> move(Piece [][] in);
} //end class
