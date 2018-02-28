package model;

import java.util.List;
import java.util.Vector;

public class DataStructureConverter {
	public static List<List<Piece>> arrayToVector(Piece [][] in) {
		List<List<Piece>> out = new Vector<List<Piece>>(8);
		for (int i = 0; i < in.length; i++) { 
			List<Piece> re = new Vector<Piece>(8);
			for (int j = 0; j < in[i].length; j++) {
				re.add(j, in[i][j]);
			}
			
			out.add(i, re);
		}
		
		return out;
	}
	
	public static Piece [][] vectorToArrayWhite(List<List<Piece>> in) {
		Piece [][] out = new Piece [8][8]; 
		for (int i = 0; i < in.size(); i++) {
			
			for (int j = 0; j < in.get(i).size(); j++) {
				Piece change = in.get(i).get(j);
				if (change != null) {
					change.setY(i);
					change.setX(j);
					if (change.type())
						change.setMoveable(true);
					else 
						change.setMoveable(false);
				}
				out[i][j] = change;
				
			}
		}
		
		return out;
	}
	
	public static Piece [][] vectorToArrayBlack(List<List<Piece>> in) {
		Piece [][] out = new Piece [8][8]; 
		for (int i = in.size() - 1; i >= 0; i--) {
			
			for (int j = in.size() - 1; j >= 0; j--) {
				Piece change = in.get(i).get(j);
				if (change != null) {
					change.setY(-i + 7);
					change.setX(-j + 7);
					if (!change.type())
						change.setMoveable(true);
					else 
						change.setMoveable(false);
				}
				out[-i + 7][-j + 7] = change;
				
			}
		}
		
		return out;
	}
}
