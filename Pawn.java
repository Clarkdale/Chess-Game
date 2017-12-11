/*====================================================================
    Class Name:  Pawn
       Purpose:  Instancec of Piece class to be used for pawn objects
  Parent Class:  Piece
====================================================================*/
import javafx.scene.image.Image;
import java.util.*;

public class Pawn extends Piece {
  private int moved;
  private Image pic;

  public Pawn(int k, int l, boolean color) {
    super(k, l, color);
    moved = 1;
  } //end method


  public Set<Tuple> move(Piece [][] in) {
    int currX = super.getColumn();
    int currY = super.getRow();
    Set<Tuple> possible = new HashSet<>();
    possible.add(new Tuple(currX, currY));
    if (super.type()) {
      if (in[currY - 1][currX] == null) {
        possible.add(new Tuple(currX, currY - 1));
      }

      if (moved <= 1 && in[currY - 2][currX] == null && in[currY - 1][currX] == null) {
        possible.add(new Tuple(currX, currY - 2));
      }

      if (currX != 0) {
        if (in[currY - 1][currX - 1] != null && !in[currY - 1][currX - 1].type()) {
          possible.add(new Tuple(currX - 1, currY - 1));
        }
      }

      if (currX != 7) {
        if (in[currY - 1][currX + 1] != null && !in[currY - 1][currX + 1].type()) {
          possible.add(new Tuple(currX + 1, currY - 1));
        }
      }

    } else {
      if (in[currY + 1][currX] == null) {
        possible.add(new Tuple(currX, currY + 1));
      }

      if (moved <= 1 && in[currY + 2][currX] == null && in[currY + 1][currX] == null) {
        possible.add(new Tuple(currX, currY + 2));
      }
      if (currX != 0) {
        if (in[currY + 1][currX - 1] != null && in[currY + 1][currX - 1].type()) {
          possible.add(new Tuple(currX - 1, currY + 1));
        }
      }

      if (currX != 7) {
        if (in[currY + 1][currX + 1] != null && in[currY + 1][currX + 1].type()) {
          possible.add(new Tuple(currX + 1, currY + 1));
        }
      }
    }

    return possible;
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

  public void setY(int in) {
    if (in != super.getRow()) {
      moved++;
    }
    super.setY(in);
  }

  public boolean type() {
    return super.type();
  } //end methods
} //end class
