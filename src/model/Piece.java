package model;

/*====================================================================
    Class Name:  Piece
       Purpose:  A parent class for all the pieces which will be used
                 in a chess game
  Parent Class:  None
====================================================================*/


import java.io.Serializable;
import java.util.*;

public abstract class Piece implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int row;
	private int column;
	private Boolean isWhite;
	private Boolean moveable;

	public Piece(int x, int y, boolean type, boolean mover) {
		row = y;
		column = x;
		isWhite = type;
		setMoveable(mover);
	} // end method

	public int getColumn() {
		return column;
	} // end method

	public int getRow() {
		return row;
	} // end method

	public boolean type() {
		return isWhite;
	} // end method
	
	public void setType(Boolean in) {
		isWhite = in;
	}

	public void setX(int k) {
		column = k;
	} // end method

	public void setY(int k) {
		row = k;
	} // end method

	public abstract String graphic();

	public abstract Set<Tuple> move(Piece[][] in);

	public Boolean getMoveable() {
		return moveable;
	} //end method

	public void setMoveable(Boolean ivory) {
		this.moveable = ivory;
	} //end method
} // end class
