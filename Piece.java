import javafx.scene.image.Image;

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

  public abstract Image graphic();

  public abstract boolean move(int i, int j, Piece [][] in);
}
