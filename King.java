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

  public boolean move(int i, int j, Piece [][] in) {
    return true;
  } //end method
} //end class
