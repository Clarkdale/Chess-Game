package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/*====================================================================
  Class Name:  server
     Purpose:  Starts the actual server so that the clients can
               connect with one another
Parent Class:  None
     @author:  Clark D Penado
====================================================================*/
public class Server {
	
	private static ServerSocket serverSocket;
	private static HashMap<ObjectInputStream, ObjectOutputStream> streams = new HashMap<>();
	private static ObjectInputStream inputClient;
	private static boolean turn = false;

	public static void main(String [] args) throws IOException {
		try {
			//socket created at designated number passed in
			serverSocket = new ServerSocket(3333);
			
			//while true maintains the server open and the output/input 
			//streams flowing between the server and clients
			while (true) {
				Socket client = serverSocket.accept();
				
				//input streams added to overall input streams
				ObjectInputStream inputFromClient = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream outputToClient = new ObjectOutputStream(client.getOutputStream());
				streams.put(inputFromClient, outputToClient);
				
				if (inputClient == null) {
					inputClient = inputFromClient;
					outputToClient.writeObject(false);
				} else {
					outputToClient.writeObject(true);
					ClientHandler clientHandler = new ClientHandler(inputClient, inputFromClient);
					inputClient = null;
					Thread thread = new Thread(clientHandler);
					thread.start();
				}
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
		private ObjectInputStream first;
		private ObjectInputStream second;
		
		public ClientHandler(ObjectInputStream one, ObjectInputStream two) {
			this.first = one;
			this.second = two;
		} //end method
		
		@Override
		public void run() {
			
			//while true used to maintain communication between clients and
			//server
			while (true) {
				try {
					if (turn) {
						int [] start = (int []) first.readObject();
						int [] end = (int []) first.readObject();
						
						start[0] = (start[0] - 7) * -1;
						start[1] = (start[1] - 7) * -1;
						end[0] = (end[0] - 7) * -1;
						end[1] = (end[1] - 7) * -1;
						
						streams.get(second).writeObject(start);
						streams.get(second).writeObject(end);
						streams.get(second).writeObject(true);
						turn = !turn;
					} else {
						int [] start = (int []) second.readObject();
						int [] end = (int []) second.readObject();
						
						start[0] = (start[0] - 7) * -1;
						start[1] = (start[1] - 7) * -1;
						end[0] = (end[0] - 7) * -1;
						end[1] = (end[1] - 7) * -1;
						
						streams.get(first).writeObject(start);
						streams.get(first).writeObject(end);
						streams.get(first).writeObject(true);
						
						turn = !turn;
					}
				} catch (IOException e) {
				} catch (ClassNotFoundException e) {
				} //end try/catch
			} //end while
		} //end method
	} //end class
}

