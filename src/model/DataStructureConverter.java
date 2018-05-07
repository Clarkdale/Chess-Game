package model;

import java.util.List;
import java.util.Vector;

/*====================================================================
  Class Name:  DataStructureConverter
     Purpose:  Provides static methods that will convert a 2D Piece
               array to a 2D vector and vice versa, with black or 
               white facing the user depending on which method is 
               used
Parent Class:  EventHandler
     @author:  Clark D Penado
====================================================================*/
public class DataStructureConverter {
	
	/*====================================================================
    Method Name:  arrayTovector
        Purpose:  Takes a 2D array in and returns a 2D vector that is
                  formatted the same way
     Parameters:  in: 2D Array to convert
        Returns:  2D vector that is formatted the same way
 	====================================================================*/
	public static List<List<Piece>> arrayToVector(Piece [][] in) {
		List<List<Piece>> out = new Vector<List<Piece>>(8);
		
		//nested for loop iterates over every position in the array,
		//and copies it into the vector to output
		for (int i = 0; i < in.length; i++) { 
			List<Piece> re = new Vector<Piece>(8);
			for (int j = 0; j < in[i].length; j++) {
				re.add(j, in[i][j]);
			} //end for
			
			out.add(i, re);
		} //end for
		
		return out;
	} //end method
	
	/*====================================================================
    Method Name:  vectorToArrayWhite
        Purpose:  Takes in a 2D vector and converts it to a 2D array of
                  pieces, with white facing the user
     Parameters:  in: 2D vector to convert (represents board)
        Returns:  2D Piece array model of gameboard
 	====================================================================*/
	public static Piece [][] vectorToArrayWhite(List<List<Piece>> in) {
		Piece [][] out = new Piece [8][8]; 
		
		//nested for loop copies every position over into the 2D piece
		//array
		for (int i = 0; i < in.size(); i++) {
			for (int j = 0; j < in.get(i).size(); j++) {
				Piece change = in.get(i).get(j);
				
				//boolean modifies the Piece objects so they are able to
				//appropriately scan board, and allow user to make proper
				//moves
				if (change != null) {
					change.setY(i);
					change.setX(j);
					if (change.type())
						change.setMoveable(true);
					else 
						change.setMoveable(false);
				} //end if
				
				out[i][j] = change;
			} //end for
		} //end for
		
		return out;
	} //end method
	
	/*====================================================================
    Method Name:  vectorToArrayBlack
        Purpose:  Takes in a 2D vector and converts it to a 2D array of
                  pieces, with black facing the user
     Parameters:  in: 2D vector to convert (represents board)
        Returns:  2D Piece array model of gameboard
 	====================================================================*/
	public static Piece [][] vectorToArrayBlack(List<List<Piece>> in) {
		Piece [][] out = new Piece [8][8]; 
		
		//nested for loop iterates backwards to copy all the piece objects
	    //into the array to copy
		for (int i = in.size() - 1; i >= 0; i--) {
			for (int j = in.size() - 1; j >= 0; j--) {
				Piece change = in.get(i).get(j);
				
				//Boolean modifies pieces appropriately so user can move
				// as well as allow the board to highlight which spaces
				//the piece can move into
				if (change != null) {
					change.setY(-i + 7);
					change.setX(-j + 7);
					if (!change.type())
						change.setMoveable(true);
					else 
						change.setMoveable(false);
				} //end if
				
				out[-i + 7][-j + 7] = change;
			} //end for
		} //end for
		
		return out;
	} //end method
} //end class
