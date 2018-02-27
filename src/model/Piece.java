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
	private Boolean ivory;

	public Piece(int x, int y, boolean type, boolean color) {
		row = y;
		column = x;
		isWhite = type;
		setIvory(color);
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

	public void setX(int k) {
		column = k;
	} // end method

	public void setY(int k) {
		row = k;
	} // end method

	public abstract String graphic();

	public abstract Set<Tuple> move(Piece[][] in);

	public Boolean getIvory() {
		return ivory;
	} //end method

	public void setIvory(Boolean ivory) {
		this.ivory = ivory;
	} //end method
} // end class
