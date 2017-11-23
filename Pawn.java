/*====================================================================
    Class Name:  Pawn
       Purpose:  Instancec of Piece class to be used for pawn objects
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;

public class Pawn extends Piece {
  private int moved;
  private Image pic;

  public Pawn(int k, int l, boolean color) {
    super(k, l, color);
    moved = 0;
  } //end method


  public boolean move(int x, int y, Piece [][] in) {
    boolean case0 = x == super.getColumn() && y == super.getRow();
    boolean case1;
    boolean case2;
    boolean case3;
    if (super.type()) {
      case1 = super.getColumn() == x && super.getRow() - y == 2 && in[y][x] == null && in[y + 1][x] == null && moved == 0;
      case2 = super.getColumn() == x && super.getRow() - y == 1 && in[y][x] == null;
      case3 = (super.getColumn() - 1 == x || super.getColumn() + 1 == x) && super.getRow() - y == 1 && in[y][x] != null && !in[y][x].type();
    } else {
      case1 = super.getColumn() == x && y - super.getRow() == 2 && in[y][x] == null && in[y - 1][x] == null && moved == 0;
      case2 = super.getColumn() == x && y - super.getRow() == 1 && in[y][x] == null;
      case3 = (super.getColumn() - 1 == x || super.getColumn() + 1 == x) && y - super.getRow() == 1 && in[y][x] != null && in[y][x].type();
    }

    if (case1 || case2 || case3) {
      super.setX(x);
      super.setY(y);
      moved++;
    }
    return case0 || case1 || case2 || case3;
  } //end method

  public Image graphic() {
    if (super.type()) {
      //return (new Image("tim.jpg"));
      return (new Image("WhitePawn.png"));
    } else {
      //return (new Image("stephen.jpg"));
      return (new Image("BlackPawn.png"));
    } //end if/else
  } //end method

  public boolean type() {
    return super.type();
  } //end methods
} //end class
