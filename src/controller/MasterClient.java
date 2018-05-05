package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MasterClient extends Application {
	public static void main(String [] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Cicada 3301 Presents Chess");
		stage.getIcons().add(new Image("file:images/BlackKnight.png"));
		
		BorderPane rootPane = new BorderPane();
		
		Canvas screen = new Canvas(640, 640);
		
		rootPane.setCenter(screen);
		
		Scene display = new Scene(rootPane, 640, 640);
		
		openConnection();
		
		GraphicsContext out = screen.getGraphicsContext2D();
		
		stage.setScene(display);
		stage.show();
	}
	
	private void openConnection() {
		try {
			Socket socket = new Socket("localhost", 4000);
			
			ObjectOutputStream outputToServer = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inputFromServer = new ObjectInputStream(socket.getInputStream());
			
			boolean turn = inputFromServer.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
