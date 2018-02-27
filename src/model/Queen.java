package model;
/*====================================================================
    Class Name:  Queen
       Purpose:  Queen instance of Piece class... I'll admit it, I didn't
                 really comment these, and really they were just implemented
                 for the png images to be accessible with minimal work
  Parent Class:  Piece
====================================================================*/

import java.util.*;

public class Queen extends Piece {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public Queen(int i, int j, boolean type, boolean ivory) {
    super(i, j, type, ivory);
  } //end method

  public int getRow() {
    return super.getRow();
  } //end method

  public int getColumn() {
    return super.getColumn();
  } //end method

  public String graphic() {
    if (super.getIvory()) {
      return ("file:images/WhiteQueen.png");
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/medium/public/images/people/allison2.jpg"));
    } else {
      return ("file:images/BlackQueen.png");
      //return (new Image("https://www.cs.arizona.edu/sites/cs/files/styles/medium/public/images/people/obagy_0.jpg"));
    } //end if/else
  } //end method

  /*====================================================================
     Method Name:  move
         Purpose:  Creates a set of tuples, which hold (x.y) positions
                   on the board as to where this type of piece could
                   potentially move. Essentially a "scan board" method
      Parameters:  in: A 2D array of pieces, which represent the board
         Returns:  A set of tuples of all possible moves
  ====================================================================*/
  public Set<Tuple> move(Piece [][] in) {
    //Rook and Bishop objects are created to handle adding moves to the moveset
    //for this piece
    Rook rookRep = new Rook(super.getColumn(), super.getRow(), super.type(), super.getIvory());
    Bishop bishopRep = new Bishop(super.getColumn(), super.getRow(), super.type(), super.getIvory());

    //Sets for piece representations extracted into variablesto add to an overall
    //output set
    Set<Tuple> case1 = rookRep.move(in);
    Set<Tuple> case2 = bishopRep.move(in);
    Set<Tuple> out = new HashSet<>();

    //Adding tuples from rook into the overall set
    for (Tuple rook : case1) {
      out.add(rook);
    } //end for

    //adding tuples from bishop to the overall set
    for (Tuple bishop : case2) {
      out.add(bishop);
    } //end for

    return out;
  } //emd method

  public boolean type() {
    return super.type();
  } //end method
} //end class
