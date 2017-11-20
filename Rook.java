/*====================================================================
    Class Name:  Rook
       Purpose:  Rook instance of Piece class
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;

public class Rook extends Piece {
  public Rook(int i, int j, boolean type) {
    super(i, j, type);
  } //end method

  public int getRow() {
    return super.getRow();
  } //end method

  public int getColumn() {
    return super.getColumn();
  } //end method

  public boolean move(int i, int j, Piece [][] in) {
    return true;
  } //end method

  public Image graphic() {
    if (super.type()) {
      return (new Image("WhiteRook.png"));
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/medium/public/images/people/homer.jpg"));
    } else {
      return (new Image("BlackRook.png"));
      //return (new Image("http://www.surdeanu.info/mihai/website/mihai/Surdeanu5-small.jpg"));
    } //end if/else
  } //end method

  public boolean type() {
    return super.type();
  } //end method
} //end class
