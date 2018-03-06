package model;
/*====================================================================
    Class Name:  King
       Purpose:  King instance of piece class
  Parent Class:  Piece
====================================================================*/

import java.util.*;

public class King extends Piece {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int moved;

	public King(int i, int j, boolean type, boolean ivory) {
		super(i, j, type, ivory);
		moved = 0;
	} // end method

	public void setX(int in) {
		if (in != super.getColumn() && in != (-super.getColumn() + 7)) {
			moved++;
		} //end if

		super.setX(in);
	} //end method

	public void setY(int in) {
		if (in != super.getRow() && in != (-super.getRow() + 7)) {
			moved++;
		} //end if

		super.setY(in);
	} //end method
	
	/*====================================================================
    Method Name:  inCheck
        Purpose:  Returns a boolean value concerning whether the king
                  is in check
     Parameters:  in: A 2D array of pieces, which represent the board
        Returns:  boolean value concerning check status
 	====================================================================*/
	public boolean inCheck(Piece [][] in) {
		//calls to helper function to reduce redundancy and so check method
		// can be used in other instances as well
		return moveCheck(in, super.getColumn(), super.getRow());
	} //end method
	
	/*====================================================================
    Method Name:  moveCheck
        Purpose:  Takes in a board and potential x and y value for where 
                  the king could move and returns a boolean value as to
                  if that position is a threatened check position by 
                  another piece
     Parameters:  in: A 2D array of pieces, which represent the board
                  x: column position to check
                  y: row position to check
        Returns:  boolean value concerning if that space is threatened
 	====================================================================*/
	public boolean moveCheck(Piece [][] in, int x, int y) {
		Set<Piece> opposite = new HashSet<>();
		
		//all enemy pieces are added to the opposite set
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (in[i][j] != null && in[i][j].type() != this.type())
					opposite.add(in[i][j]);
			} //end for
		} //end for
		
		// for loop moves over enemy pieces, and checks their move set. If
		// any of the tuples inside of this set match the x and y value passed
		// in, the space is threatened. Kings are not checked to avoid stack
		// overflow
		for (Piece enemy : opposite) {
			if (!(enemy instanceof King)) {
				for (Tuple move : in[enemy.getRow()][enemy.getColumn()].move(in)) {
					if (x == move.getFirst() && y == move.getSecond())
						return true;
				} // end for
			} //end if
		} // end for
		
		// following boolean logic handles case where there is a king 
		// in a neighboring position. This assists in preventing stack
		// overflow errors between the two kings on board
		if (x - 1 >= 0) {
			if (y - 1 >= 0) {
				if (in[y - 1][x - 1] instanceof King)
					return true;
			} //end if
			
			if (y + 1 <= 7) {
				if (in[y + 1][x - 1] instanceof King)
					return true;
			} // end if
			
			if (in[y][x - 1] instanceof King)
				return true;
		} //end if
		
		if (x + 1 <= 7) {
			if (y - 1 >= 0) {
				if (in[y - 1][x + 1] instanceof King)
					return true;
			} //end if
			
			if (y + 1 <= 7) {
				if (in[y + 1][x + 1] instanceof King)
					return true;
			} //end if
			
			if (in[y][x + 1] instanceof King)
				return true;
		} //end if
		
		if (y - 1 >= 0) {
			if (in[y - 1][x] instanceof King)
				return true;
		} //end if
		
		if (y + 1 <= 7) {
			if (in[y + 1][x] instanceof King)
				return true;
		} //end if
		
		return false;
	} // end method

	public int getRow() {
		return super.getRow();
	} // end method

	public int getColumn() {
		return super.getColumn();
	} // end method

	public boolean type() {
		return super.type();
	} // end method

	public boolean castle() {
		return moved == 0;
	}

	public String graphic() {
		if (super.type()) {
			return ("file:images/WhiteKing.png");
			// return (new
			// Image("https://media.licdn.com/mpr/mpr/shrinknp_200_200/p/2/000/18f/383/0ea0035.jpg"));
		} else {
			return ("file:images/BlackKing.png");
			// return (new
			// Image("https://www.cs.arizona.edu/sites/cs/files/styles/medium/public/images/people/debray.jpg"));
		} // end if/else
	} // end method

	/*====================================================================
    Method Name:  move
        Purpose:  Creates a set of tuples, which hold (x.y) positions
                  on the board as to where this type of piece could
                  potentially move. Essentially a "scan board" method
     Parameters:  in: A 2D array of pieces, which represent the board
        Returns:  A set of tuples of all possible moves
 	====================================================================*/
	public Set<Tuple> move(Piece[][] in) {
		// current variables set to compare back to in later logic
		int currX = super.getColumn();
		int currY = super.getRow();

		// Set of possile moves of tuples initialized
		Set<Tuple> out = new HashSet<>();

		// adds original position to set, original position is considered a "Invalid
		// move"
		out.add(new Tuple(currX, currY));

		// logic for all moves one square to the left of the current king loccation,
		// in relation to the initial position.
		if (currX - 1 >= 0) {
			if (currY - 1 >= 0) {
				if ((in[currY - 1][currX - 1] == null || in[currY - 1][currX - 1].type() != super.type()) && !moveCheck(in, currX - 1, currY - 1)) {
					out.add(new Tuple(currX - 1, currY - 1));
				} // end if
			} // end if

			if (currY + 1 <= 7) {
				if ((in[currY + 1][currX - 1] == null || in[currY + 1][currX - 1].type() != super.type()) && !moveCheck(in, currX - 1, currY + 1)) {
					out.add(new Tuple(currX - 1, currY + 1));
				} // end if
			} // end if

			if ((in[currY][currX - 1] == null || in[currY][currX - 1].type() != super.type()) && !moveCheck(in, currX - 1, currY)) {
				out.add(new Tuple(currX - 1, currY));
			} // end if
		} // end if

		// handles logic for all potential moves to the right of the initial king
		// posiiton
		if (currX + 1 <= 7) {
			if (currY - 1 >= 0) {
				if ((in[currY - 1][currX + 1] == null || in[currY - 1][currX + 1].type() != super.type()) && !moveCheck(in, currX + 1, currY - 1)) {
					out.add(new Tuple(currX + 1, currY - 1));
				} // end if
			} // end if

			if (currY + 1 <= 7) {
				if ((in[currY + 1][currX + 1] == null || in[currY + 1][currX + 1].type() != super.type()) && !moveCheck(in, currX + 1, currY + 1)) {
					out.add(new Tuple(currX + 1, currY + 1));
				} // end if
			} // end if

			if (in[currY][currX + 1] == null || in[currY][currX + 1].type() != super.type()) {
				out.add(new Tuple(currX + 1, currY));
			} // end if
		} // end if

		// logic for handling above/below the initial king position
		if (currY - 1 >= 0) {
			if ((in[currY - 1][currX] == null || in[currY - 1][currX].type() != super.type()) && !moveCheck(in, currX, currY - 1)) {
				out.add(new Tuple(currX, currY - 1));
			} // end if
		} // end if

		// logic for above.below moves in relation to the initial king position
		if (currY + 1 <= 7) {
			if ((in[currY + 1][currX] == null || in[currY + 1][currX].type() != super.type()) && !moveCheck(in, currX, currY + 1)) {
				out.add(new Tuple(currX, currY + 1));
			} // end if
		} // end if

		// boolean block allows for castling moves to be added to the overall
		// moveset
		if ((currY == 7 || currY == 0) && this.castle()) {
			if (this.type()) {
				if (in[currY][currX + 1] == null && in[currY][currX + 2] == null && (in[currY][currX + 3] instanceof Rook) && !moveCheck(in, currX + 2, currY)) 
					out.add(new Tuple(currX + 2, currY));
				
				if (in[currY][currX - 1] == null && in[currY][currX - 2] == null && in[currY][currX - 3] == null && (in[currY][currX - 4] instanceof Rook) && !moveCheck(in, currX - 2, currY))
					out.add(new Tuple(currX - 3, currY));
			} else {
				if (in[currY][currX - 1] == null && in[currY][currX - 2] == null && (in[currY][currX - 3] instanceof Rook) && !moveCheck(in, currX - 2, currY))
					out.add(new Tuple(currX - 2, currY));
				if (in[currY][currX + 1] == null && in[currY][currX + 2] == null && in[currY][currX + 3] == null && (in[currY][currX + 4] instanceof Rook) && !moveCheck(in, currX + 3, currY))
					out.add(new Tuple(currX + 3, currY));
			} //end if/else
		} //end if

		return out;
	} // end method
} // end class
