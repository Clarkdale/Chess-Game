package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import model.Bishop;
import model.DataStructureConverter;
import model.King;
import model.Knight;
import model.Pawn;
import model.Piece;
import model.Queen;
import model.Rook;

/*====================================================================
  Class Name:  server
     Purpose:  Starts the actual server so that the clients can
               connect with one another
Parent Class:  None
     @author:  Clark D Penado
====================================================================*/
public class Server {
	
	private static ServerSocket serverSocket;
	private static List<List<Piece>> board;
	private static List<ObjectOutputStream> outputStreams = new Vector<>();
	private static ObjectOutputStream outputClient;
	private static boolean turn = true;

	public static void main(String [] args) throws IOException {
		//initial board must be generated
		boardGen();

		try {
			//socket created at designated number passed in
			serverSocket = new ServerSocket(4000);
			
			//while true maintains the server open and the output/input 
			//streams flowing between the server and clients
			while (true) {
				Socket client = serverSocket.accept();
				
				//input streams added to overall input streams
				ObjectInputStream inputFromClient = new ObjectInputStream(client.getInputStream());
				outputClient = new ObjectOutputStream(client.getOutputStream());
				outputStreams.add(outputClient);
				
				//information about initial game is written to the 
				//corresponding clients
				outputClient.writeObject(board);
				outputClient.writeObject(turn);
				
				//thread started
				ClientHandler clientHandler = new ClientHandler(inputFromClient);
				Thread thread = new Thread(clientHandler);
				thread.start();
			} //end while
			
		} catch (IOException e) {
			e.printStackTrace();
		} //end try/catch
	} //end method
	
	/*====================================================================
    Class Name:  ClientHandler
       Purpose:  The actual thread that will receive input from the
                 streams, and write back to client objects
  Parent Class:  Runnable
	   @author:  Clark D Penado
	====================================================================*/
	private static class ClientHandler implements Runnable {
		private ObjectInputStream input;
		
		public ClientHandler(ObjectInputStream inputFromClient) {
			this.input = inputFromClient;
		} //end method
		
		@Override
		public void run() {
			
			//while true used to maintain communication between clients and
			//server
			while (true) {
				try {
					//input from clients received
					board = DataStructureConverter.arrayToVector((Piece [][]) input.readObject());
					turn = (boolean) input.readObject();
					
					//information is then converted and sent back to clients
					//to update point in game in both clients
					for (ObjectOutputStream outputToClient : outputStreams) {
						outputToClient.reset();
						outputToClient.writeObject(board);
						outputToClient.writeObject(turn);
					} //end for

				} catch (IOException e) {
				} catch (ClassNotFoundException e) {
				} //end try/catch
			} //end while
		} //end method
	} //end class
	
	/*====================================================================
    Method Name:  boardGen
        Purpose:  Generates the initial board, with white side facing the 
                  user initially into a 2D vector of Piece objects
     Parameters:  None
        Returns:  None
 	====================================================================*/
	public static void boardGen() {
		board = new Vector<List<Piece>>(8);
		
		//this first row is the row of black pieces behind pawns. 
		//After this row is generated it is added, as is
		//the rest of rows that are generated
		List<Piece> rowOne = new Vector<Piece>(8);
		
		rowOne.add(0, new Rook(0, 0, false, false));
		rowOne.add(1, new Knight(1, 0, false, false));
		rowOne.add(2, new Bishop(2, 0, false, false));
		rowOne.add(3, new Queen(3, 0, false, false));
		rowOne.add(4, new King(4, 0, false, false));
		rowOne.add(5, new Bishop(5, 0, false, false));
		rowOne.add(6, new Knight(6, 0, false, false));
		rowOne.add(7, new Rook(7, 0, false, false));
		
		board.add(0, rowOne);
		
		//row two created, with is just a repetitive amount of panws
		List<Piece> rowTwo = new Vector<Piece>(8);
		
		for (int i = 0; i < 8; i++) {
			rowTwo.add(i, new Pawn(i, 1, false, false));
		} //end for

		board.add(1, rowTwo);
		
		//In order to maintain structure of an 8x8 "list," a 
		//few null pieces are added to lists to be stored in
		//the overall vector
		for (int i = 2; i < 6; i++) {
			List<Piece> add = new Vector<Piece>(8);
			for (int j = 0; j < 8; j++) {
				add.add(j, null);
			} //end for
			
			board.add(i, add);
		} //end for
		
		//Creates the row of white pawns
		List<Piece> rowFour = new Vector<Piece>(8);
		
		for (int i = 0; i < 8; i++) {
			rowFour.add(i, new Pawn(i, 6, true, true));
		} //end for
		
		board.add(6, rowFour);
		
		//creates row of white pieces that are not 
		//pawns
		List<Piece> rowThree = new Vector<Piece>(8);
		
		rowThree.add(0, new Rook(0, 7, true, false));
		rowThree.add(1, new Knight(1, 7, true, false));
		rowThree.add(2, new Bishop(2, 7, true, false));
		rowThree.add(3, new Queen(3, 7, true, false));
		rowThree.add(4, new King(4, 7, true, false));
		rowThree.add(5, new Bishop(5, 7, true, false));
		rowThree.add(6, new Knight(6, 7, true, false));
		rowThree.add(7, new Rook(7, 7, true, false));
		
		board.add(7, rowThree);
	  } //end method
} //end class
