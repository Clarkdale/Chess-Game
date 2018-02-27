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
	
	public static Piece [][] vectorToArray(List<List<Piece>> in) {
		Piece [][] out = new Piece [8][8]; 
		for (int i = 0; i < in.size(); i++) {
			
			for (int j = 0; j < in.get(i).size(); j++) {
				out[i][j] = in.get(i).get(j);
				
			}
		}
		
		return out;
	}
}
