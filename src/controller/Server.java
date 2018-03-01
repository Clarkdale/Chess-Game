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

public class Server {
	
	private static ServerSocket serverSocket;
	private static List<List<Piece>> board;
	private static List<ObjectOutputStream> outputStreams = new Vector<>();
	private static ObjectOutputStream outputClient;
	private static boolean turn = true;

	public static void main(String [] args) throws IOException {
		boardGen();

		try {
			serverSocket = new ServerSocket(4000);
			
			while (true) {
				Socket client = serverSocket.accept();
				ObjectInputStream inputFromClient = new ObjectInputStream(client.getInputStream());
				outputClient = new ObjectOutputStream(client.getOutputStream());
				outputStreams.add(outputClient);
				
				outputClient.writeObject(board);
				outputClient.writeObject(turn);
				
				ClientHandler clientHandler = new ClientHandler(inputFromClient);
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static class ClientHandler implements Runnable {
		private ObjectInputStream input;
		
		public ClientHandler(ObjectInputStream inputFromClient) {
			this.input = inputFromClient;
		}
		
		@Override
		public void run() {
			
			while (true) {
				try {
					
					board = DataStructureConverter.arrayToVector((Piece [][]) input.readObject());
					turn = (boolean) input.readObject();
					
					for (ObjectOutputStream outputToClient : outputStreams) {
						outputToClient.reset();
						outputToClient.writeObject(board);
						outputToClient.writeObject(turn);
					}

				} catch (IOException e) {
				} catch (ClassNotFoundException e) {
				}
			}
		}
	}
	
	public static void boardGen() {
		board = new Vector<List<Piece>>(8);
		
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
		
		List<Piece> rowTwo = new Vector<Piece>(8);
		
		for (int i = 0; i < 8; i++) {
			rowTwo.add(i, new Pawn(i, 1, false, false));
			
		}

		board.add(1, rowTwo);
		
		for (int i = 2; i < 6; i++) {
			List<Piece> add = new Vector<Piece>(8);
			for (int j = 0; j < 8; j++) {
				add.add(j, null);
			}
			board.add(i, add);
		}
		
		List<Piece> rowFour = new Vector<Piece>(8);
		
		for (int i = 0; i < 8; i++) {
			rowFour.add(i, new Pawn(i, 6, true, true));
		}
		
		board.add(6, rowFour);
		
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
}
