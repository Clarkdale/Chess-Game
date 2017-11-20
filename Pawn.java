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


  public boolean move(int i, int j, Piece [][] in) {
    return true;
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
