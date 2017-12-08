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
  }

  public int getColumn() {
    return column;
  }

  public int getRow() {
    return row;
  }

  public boolean type() {
    return isWhite;
  }

  public void setX(int k) {
    column = k;
  }

  public void setY(int k) {
    row = k;
  }

  public abstract Image graphic();

  public abstract Set<Tuple> move(Piece [][] in);
}
