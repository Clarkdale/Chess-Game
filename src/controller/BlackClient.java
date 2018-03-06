package controller;
/*====================================================================
    Class Name:  Chess
       Purpose:  Entry point for the game
  Parent Class:  Application
	   @author:  Clark D Penado
====================================================================*/
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.event.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import model.*;

public class BlackClient extends Application {
	private GraphicsContext out;
	private Canvas screen;
	private boolean turn;
	private Piece[][] bobbyFisher;
	private ObjectOutputStream outputToServer;
	private ObjectInputStream inputFromServer;
	private Socket socket;
	private King king;

	public static void main(String[] args) {
		launch(args);
	} // end method

	/*====================================================================
    Method Name:  start
        Purpose:  initializes a graphical interface window, in this
                  case being an 800 by 800 pixel window. This window
                  is then used to allow a user to interact with a
                  chess game, generated within said window.
     Parameters:  window: an instance of a stage which will be used
                  as the basis of the graphical window
        Returns:  None
 	====================================================================*/
	@Override
	public void start(Stage window) {
		window.setTitle("Chess");
		window.getIcons().add(new Image("file:images/BlackKnight.png"));
		// overall borderpain created
		BorderPane rootPane = new BorderPane();

		// Interactive window is made, which will later have a listener for clicking
		// on screen
		screen = new Canvas(640, 640);

		// Scene made to later use as a means of setting the scene of GUI
		Scene display = new Scene(rootPane, 640, 640);

		rootPane.setLeft(screen);

		out = screen.getGraphicsContext2D();

		openConnection();

		screen.addEventHandler(MouseEvent.MOUSE_CLICKED, new MoveHandler());

		// Scene is added, and the contents of this scene are displayed ot the GUI
		// using these methods.
		window.setScene(display);
		window.show();
	} // end method
	
	/*====================================================================
    Method Name:  openConnection
        Purpose:  Connects this client to the overall server. This is
                  what allows the client to run
     Parameters:  None
        Returns:  None
 	====================================================================*/
	@SuppressWarnings("unchecked")
	private void openConnection() {

		try {
			//InetAddress add = InetAddress.getByName("150.135.165.4");
			
			//socket is connected to, in this case it is the local host for
			//testing purposes
			socket = new Socket("localhost", 4000);

			//output and input to server is initialized, so objects can be sent
			//between the cleints and servers
			outputToServer = new ObjectOutputStream(socket.getOutputStream());
			inputFromServer = new ObjectInputStream(socket.getInputStream());
			
			//board variable set to the vector recieved from the server, which
			//is then converted to a 2D Piece array
			bobbyFisher = DataStructureConverter.vectorToArrayBlack((List<List<Piece>>) inputFromServer.readObject());
			turn = (boolean) inputFromServer.readObject();
			findKing();
			
			ServerReader listener = new ServerReader();

			printBoard();
			
			//thread started
			Thread thread = new Thread(listener);
			thread.start();
		} catch (IOException | ClassNotFoundException e) {
		} //end try/catch
	} //end method

	/*====================================================================
    Class Name:  ServerReader
       Purpose:  A thread for running this client. This client recieves 
                 data from the server and updates the screen, as well
                 as synchronizes this thread, so that there are no 
                 race conditions.
  Parent Class:  Runnable
	   @author:  Clark D Penado
	====================================================================*/
	private class ServerReader implements Runnable {
		@SuppressWarnings("unchecked")
		@Override
		public synchronized void run() {
			try {
				
				//while true is used to keep thread running while connected
				//to server
				while (true) {
					//Board recieved and converted
					bobbyFisher = DataStructureConverter.vectorToArrayBlack((List<List<Piece>>) inputFromServer.readObject());
					turn = (boolean) inputFromServer.readObject();
					//anonymous handler prevents race conditions from happening
					//by fully synchronizing
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							findKing();
							printBoard();
						} //end method
					}); //end anonymous handler
				} //end while
			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
			} //end try/catch
		} //end method
	} //end class
	
	/*====================================================================
    Method Name:  findKing
        Purpose:  updates the global king variable from the board by parsing
                  over every piece until this client's king is found
     Parameters:  None
        Returns:  None
 	====================================================================*/
	public void findKing() {
		// nested for loop used for parsing over each individual piece
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (bobbyFisher[i][j] != null && !bobbyFisher[i][j].type() && (bobbyFisher[i][j] instanceof King)) 
					king = (King) bobbyFisher[i][j];
			} // end for
		} //end for 
	} // end method

	/*====================================================================
    Method Name:  printBoard
        Purpose:  Create the chess board on screen of the graphic
                  interface, taking in the 2D array representation to do
                  so. Colors were googled to get a proper wood board
                  feel, and the drawImage method associated with the
                  graphics context module was used to draw piece representations
                  using a downloaded zip file of pngs for each piece
     Parameters:  in: 2D array representation of board
        Returns:  None
 	====================================================================*/
	public void printBoard() {
		// nested for loop to move across even indicies for alternating colors
		// scheme usually found on a chess board, as well as odd number indicies.
		// The starting variable in each for loop is modified based on whether or
		// not this index is odd or not.
		for (int i = 0; i < 8; i++) {
			// even indicies of board rows
			if (i % 2 == 0) {
				for (int j = 0; j < 8; j += 2) {
					// graphics context prepared and used to draw a 100 by 100 pixel
					// square as a means of reference for each position on the board
					out.setFill(Color.BURLYWOOD);
					out.fillRect(j * 80, i * 80, 80, 80);
					// same process with a different color for alternating color scheme
					out.setFill(Color.SADDLEBROWN);
					out.fillRect((j + 1) * 80, i * 80, 80, 80);
				} // end for
					// case of odd numbered row indicies
			} else {
				// j loop start modified to start at 1 for proper color scheme
				for (int j = 1; j < 8; j += 2) {
					// same process as above
					out.setFill(Color.BURLYWOOD);
					out.fillRect(j * 80, i * 80, 80, 80);

					out.setFill(Color.SADDLEBROWN);
					out.fillRect((j - 1) * 80, i * 80, 80, 80);
				} // end for
			} // end if/ else
		} // end for

		// nested for loop used again to potentially draw each necessary piece on
		// screen. I know, two nested for loops, and I know I'm always concerned about
		// time complexity, but hey, still O(n^2) either way
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (bobbyFisher[i][j] != null) {
					out.drawImage(new Image(bobbyFisher[i][j].graphic()), j * 80, i * 80, 80, 80);
					if (bobbyFisher[i][j] instanceof King) {
						King reCheck = (King) bobbyFisher[i][j];
						
						//a red transparent square is drawn over the king if the king
						// is in check at this point
						if (reCheck.inCheck(bobbyFisher)) {
							out.setFill(Color.rgb(255, 0, 0, 0.5));
							out.fillRect(j * 80, i * 80, 80, 80);
						} //end if
					} //end if
				} // end if
			} // end for
		} // end for
	} // end method
	
	/*====================================================================
    Method Name:  flipBoard
        Purpose:  Changes the side of the board back to white facing the
                  user... To be used when sending the edited board back to
                  the server, in order to maintain consitancy
     Parameters:  None
        Returns:  None
 	====================================================================*/
	private void flipBoard() {
		// 2D piece array used as a holder for the new converted board
		Piece [][] holder = new Piece[8][8];
		
		//nested for loop iterates over all locations in the board
		for (int i = 0; i < bobbyFisher.length; i++) {
			for (int j = 0; j < bobbyFisher[i].length; j++) {
				Piece add = bobbyFisher[i][j];
				
				//when the piece is not null, the associated x and y
				//coordinates are reassigned
				if (add != null) {
					add.setY(-i + 7);
					add.setX(-j + 7);
				} //end if
				
				//the "oppositie" location in the board is set to this
				//piece object
				holder[-i + 7][-j + 7] = add;
			} //end for
		} //end for
		bobbyFisher = holder;
	} //end method

	/*====================================================================
		    Class Name:  MoveHandler
		       Purpose:  Allows the user to click around on the gameboard, 
		                 moving pieces when necessary around the board
		                 and sending the moves back to the server
		  Parent Class:  EventHandler
			   @author:  Clark D Penado
	====================================================================*/
	private class MoveHandler implements EventHandler<MouseEvent> {
		// mover will be the piece the user clicks to move around screen
		Piece mover = null;
		Set<Tuple> potential = null;

		// actual method for using the mouse to move pieces around
		@Override
		public synchronized void handle(MouseEvent event) {
			// x and y values will be the pixel value over a 100 in this case,
			// as the board size is 800 by 800, with an 8x8 grid on top of this,
			// so each position will 100 pixels difference from on another,
			// allowing for this pixel value / 100 invariant to be true.
			int x = (int) event.getX() / 80;
			int y = (int) event.getY() / 80;
			// this boolean catches if the user has not selected a piece yet,
			// upon which the mover variable is reset, and the board is drawing
			// over to remove where the piecec was.
			if (mover == null) {
				// this check will determine which player's turn it is
				if (bobbyFisher[y][x] != null && !turn && bobbyFisher[y][x].getMoveable()) {

					mover = bobbyFisher[y][x];
					bobbyFisher[y][x] = null;

				}

				//This boolean block will decide whether or not a move is valid
				// corresponding to whether or not the user is in check. If the
				// user is not in check, then the set of possible moves is set 
				// to the default one that is returned from the appropriate Piece
				// objects
				if (mover != null) {
					if (!king.inCheck(bobbyFisher)) {
						potential = mover.move(bobbyFisher);
					} else {
						Set<Tuple> prelim = mover.move(bobbyFisher);
						potential = new HashSet<>();
						for (Tuple each : prelim) {
							Piece holder = bobbyFisher[each.getSecond()][each.getFirst()];
							bobbyFisher[each.getSecond()][each.getFirst()] = mover;
							if (!king.inCheck(bobbyFisher))
								potential.add(each);
							bobbyFisher[each.getSecond()][each.getFirst()] = holder;
						} //end for
						
						// same square is always a valid move
						Tuple last = new Tuple(x, y);
						potential.add(last);
					} //end if/else

					// this for loop will parse over all the possible moves,
					// and highlight where the player can move based on
					// which piece was selected.
					for (Tuple space : potential) {
						// the position of the original piece is highlighted
						// with this space
						if (space.getFirst() == mover.getColumn() && space.getSecond() == mover.getRow()) {
							out.setFill(Color.rgb(80, 80, 80, 0.75));
							out.fillRect(space.getFirst() * 80, space.getSecond() * 80, 80, 80);
							out.drawImage(new Image(mover.graphic()), mover.getColumn() * 80, mover.getRow() * 80, 80,
									80);

							// all other possible moves are highlighted using
							// sea foam green circles
						} else {
							out.setFill(Color.AQUAMARINE);
							out.setStroke(Color.WHITE);
							out.fillOval(space.getFirst() * 80 + 20, space.getSecond() * 80 + 20, 40, 40);
							out.strokeOval(space.getFirst() * 80 + 20, space.getSecond() * 80 + 20, 40, 40);
						} // end if/else
					} // end for
				} // end if

				// If the user already has a piece "in hand," or really stored to
				// the mover here, then the process of moving the piece is followed
				// within this else statement.
			} else {
				Tuple clicked = new Tuple(x, y);

				// if the user selects an appropriate space, the following
				// is exectued
				if (potential.contains(clicked)) {

					// logic to flip turn after a user inputs a move
					if (mover.getColumn() != x || mover.getRow() != y) {
						turn = !turn;
					} // end if

					// additional logic to allow castling
					if (mover instanceof King) {
						Piece assist;
						
						if (x == 1) {
							assist = bobbyFisher[7][0];
							assist.setX(2);
							bobbyFisher[7][0] = null;
							bobbyFisher[7][2] = assist;
						}
						
						if (x == 6) {
							assist = bobbyFisher[7][7];
							assist.setX(5);
							bobbyFisher[7][7] = null;
							bobbyFisher[7][5] = assist;
						}
					} // end if
					
					boolean out = false;
					if (mover.getColumn() != x || mover.getRow() != y)
						out = true;
					// these statements will changeg the piece object
					// according to where it was moved on the board
					mover.setX(x);
					mover.setY(y);

					// board is adjusted after move to fully represent game
					bobbyFisher[y][x] = mover;

					// mover is reset to null for next move
					mover = null;

					try {
						printBoard();
						flipBoard();
						outputToServer.writeObject(bobbyFisher);
						outputToServer.writeObject(out);
					} catch (IOException e) {
					} //end try/catch
				} //end if
			} // end if/else
		} // end internal method
	} // end class
} // end class