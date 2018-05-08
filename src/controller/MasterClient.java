package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Bishop;
import model.King;
import model.Knight;
import model.Pawn;
import model.Piece;
import model.Queen;
import model.Rook;
import model.Tuple;

public class MasterClient extends Application {
	
	private Piece [][] bobbyFischer;
	private Canvas screen;
	private GraphicsContext out;
	private FlyWeight drawer;
	private Socket socket;
	private ObjectOutputStream outputToServer;
	private ObjectInputStream inputFromServer;
	private boolean turn;
	private King king;
	
	public static void main(String [] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Cicada 3301 Presents Chess");
		stage.getIcons().add(new Image("file:images/BlackKnight.png"));
		
		BorderPane rootPane = new BorderPane();
		
		drawer = new FlyWeight();
		
		screen = new Canvas(640, 640);
		
		rootPane.setCenter(screen);
		
		Scene display = new Scene(rootPane, 640, 640);
		
		openConnection();
		renderGame();
		findKing();
		
		out = screen.getGraphicsContext2D();
		
		printBoard();
		
		screen.addEventHandler(MouseEvent.MOUSE_CLICKED, new MoveHandler());
		
		stage.setScene(display);
		stage.show();
	}
	
	private void renderGame() {
		bobbyFischer = new Piece [8][8];
		
		bobbyFischer[0][0] = new Rook(0, 0, !turn, false);
		bobbyFischer[7][0] = new Rook(0, 7, turn, true);
		bobbyFischer[0][1] = new Knight(1, 0, !turn, false);
		bobbyFischer[7][1] = new Knight(1, 7, turn, true);
		bobbyFischer[0][2] = new Bishop(2, 0, !turn, false);
		bobbyFischer[7][2] = new Bishop(2, 7, turn, true);
		
		if (turn) {
			bobbyFischer[0][3] = new Queen(3, 0, !turn, false);
			bobbyFischer[0][4] = new King(4, 0, !turn, false);
			bobbyFischer[7][3] = new Queen(3, 7, turn, true);
			bobbyFischer[7][4] = new King(4, 7, turn, true);
		} else {
			bobbyFischer[0][3] = new King(3, 0, !turn, false);
			bobbyFischer[0][4] = new Queen(4, 0, !turn, false);
			bobbyFischer[7][3] = new King(3, 7, turn, true);
			bobbyFischer[7][4] = new Queen(4, 7, turn, true);
		}
		
		bobbyFischer[0][5] = new Bishop(5, 0, !turn, false);
		bobbyFischer[7][5] = new Bishop(5, 7, turn, true);
		bobbyFischer[0][6] = new Knight(6, 0, !turn, false);
		bobbyFischer[7][6] = new Knight(6, 7, turn, true);
		bobbyFischer[0][7] = new Rook(7, 0, !turn, false);
		bobbyFischer[7][7] = new Rook(7, 7, turn, true);
		
		for (int i = 0; i < 8; i++) {
			bobbyFischer[1][i] = new Pawn(i, 1, !turn, false);
			bobbyFischer[6][i] = new Pawn(i, 6, turn, true);
		}
	}
	
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
				if (bobbyFischer[i][j] != null) {
					out.drawImage(drawer.retrieveImage(bobbyFischer[i][j].graphic()), j * 80, i * 80, 80, 80);
					if (bobbyFischer[i][j] instanceof King) {
						King reCheck = (King) bobbyFischer[i][j];

						// a red transparent square is drawn over the king if the
						// king is in check
						if (reCheck.inCheck(bobbyFischer)) {
							out.setFill(Color.rgb(255, 0, 0, 0.5));
							out.fillRect(j * 80, i * 80, 80, 80);
						} // end if
					} // end if
				} // end if
			} // end for
		} // end for
	}
	
	private void findKing() {
		// nested for loop used for iterating over each piece
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (bobbyFischer[i][j] != null && bobbyFischer[i][j].type() && (bobbyFischer[i][j] instanceof King))
					king = (King) bobbyFischer[i][j];

			} // end for
		} // end for
	}
	
	private void openConnection() {
		try {
			socket = new Socket("localhost", 4000);
			
			outputToServer = new ObjectOutputStream(socket.getOutputStream());
			inputFromServer = new ObjectInputStream(socket.getInputStream());
			
			turn = (boolean) inputFromServer.readObject();
			
			ServerReader readServer = new ServerReader();
			Thread thread = new Thread(readServer);
			thread.start();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Oops, an error occurred.");
		}
	}
	
	private class ServerReader implements Runnable {
		
		private Piece holder;
		
		@Override
		public synchronized void run() {
			try {
				while (true) {
					int[] one = (int[]) inputFromServer.readObject();
					int[] two = (int[]) inputFromServer.readObject();
					
					turn = (boolean) inputFromServer.readObject();
					
					if (bobbyFischer[one[1]][one[0]] instanceof King) {
						if (two[0] == 1) {
							King re = (King) bobbyFischer[one[1]][one[0]];
							if (re.castle()) {
								bobbyFischer[0][0].setX(2);
								bobbyFischer[0][2] = bobbyFischer[0][0];
								bobbyFischer[0][0] = null;
							}
							
						} else if (two[0] == 6) {
							King re = (King) bobbyFischer[one[1]][one[0]];
							if (re.castle()) {
								bobbyFischer[0][7].setX(5);
								bobbyFischer[0][5] = bobbyFischer[0][7];
								bobbyFischer[0][7] = null;
							}
						}
					}
					
					bobbyFischer[one[1]][one[0]].setX(two[0]);
					bobbyFischer[one[1]][one[0]].setY(two[1]);
					bobbyFischer[two[1]][two[0]] = bobbyFischer[one[1]][one[0]];
					bobbyFischer[one[1]][one[0]] = null;
					
					printBoard();
				}
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Could not read input from server");
			}
		}
	}
	
	private class MoveHandler implements EventHandler<MouseEvent> {
		
		Piece mover = null;
		Set<Tuple> potential = null;
		int [] past = new int[2];
		
		@Override
		public synchronized void handle(MouseEvent event) {
			int x = (int) event.getX() / 80;
			int y = (int) event.getY() / 80;
			
			if (mover == null) {
				if (bobbyFischer[y][x] != null && turn && bobbyFischer[y][x].getMoveable()) {
					mover = bobbyFischer[y][x];
					past = new int [2];
					past[0] = mover.getColumn();
					past[1] = mover.getRow();
					bobbyFischer[y][x] = null;
				}
				
				if (mover != null) {
					if (!king.inCheck(bobbyFischer)) {
						potential = mover.move(bobbyFischer);
					} else {
						Set<Tuple> prelim = mover.move(bobbyFischer);
						potential = new HashSet<>();
						for (Tuple each : prelim) {
							Piece holder = bobbyFischer[each.getSecond()][each.getFirst()];
							bobbyFischer[each.getSecond()][each.getFirst()] = mover;
							if (!king.inCheck(bobbyFischer))
								potential.add(each);
							bobbyFischer[each.getSecond()][each.getFirst()] = holder;
						} // end for

						//Moving the piece to its original location is always valid
						Tuple last = new Tuple(x, y);
						potential.add(last);
					}
					
					// this for loop will parse over all the possible moves,
					// and highlight where the player can move based on
					// which piece was selected.
					for (Tuple space : potential) {
						// the position of the original piece is highlighted
						// with this space
						if (space.getFirst() == mover.getColumn() && space.getSecond() == mover.getRow()) {
							out.setFill(Color.rgb(80, 80, 80, 0.75));
							out.fillRect(space.getFirst() * 80, space.getSecond() * 80, 80, 80);
							out.drawImage(drawer.retrieveImage(mover.graphic()), mover.getColumn() * 80, mover.getRow() * 80, 80, 80);

							// all other possible moves are highlighted using
							// sea foam green circles
						} else {
							out.setFill(Color.AQUAMARINE);
							out.setStroke(Color.WHITE);
							out.fillOval(space.getFirst() * 80 + 20, space.getSecond() * 80 + 20, 40, 40);
							out.strokeOval(space.getFirst() * 80 + 20, space.getSecond() * 80 + 20, 40, 40);
						} // end if/else
					} // end for
				}
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
						if (x == 6) {
							King re = (King) mover;
							// helper method from king class used to prevent unintentional
							// movement of the rook
							if (re.castle()) {
								Piece assist;
								assist = (Rook) bobbyFischer[7][7];
								assist.setX(5);
								assist.setY(7);
								bobbyFischer[7][7] = null;
								bobbyFischer[7][5] = assist;
							} // end if
						} else if (x == 1) {
							King re = (King) mover;
							// helper method from king class used to prevent unintentional
							// movement of the rook
							if (re.castle()) {
								Piece assist;
								assist = (Rook) bobbyFischer[7][0];
								assist.setX(2);
								assist.setY(7);
								bobbyFischer[7][0] = null;
								bobbyFischer[7][2] = assist;

							} // end if
						}
					}

					boolean out = true;
					if (mover.getColumn() != x || mover.getRow() != y)
						out = false;
					// these statements will changeg the piece object
					// according to where it was moved on the board
					mover.setX(x);
					mover.setY(y);

					// board is adjusted after move to fully represent game
					bobbyFischer[y][x] = mover;

					// mover is reset to null for next move
					mover = null;
					printBoard();
					if (past[0] != x || past[1] != y) {
						try {
							outputToServer.writeObject(past);
							int [] toWrite = new int [2];
							toWrite[0] = x;
							toWrite[1] = y;
							outputToServer.writeObject(toWrite);
						} catch (IOException e) {
						} //end try/catch
					}
				} //end if
			}
		}
	}
	
	private class FlyWeight {
		private HashMap<String, Image> images;
		
		public FlyWeight() {
			images = new HashMap<>();
		}
		
		public Image retrieveImage(String in) {
			String url = "file:images/" + in + ".png";
			if (!images.containsKey(url)) {
				images.put(in, new Image(url));
			}
			
			return images.get(in);
		}
	}
}
