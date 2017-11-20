import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import java.io.*;

public class Chess extends Application {
  private GraphicsContext out;
  public static void main(String [] args) {
    launch(args);
  }

  @Override
  public void start(Stage window) {
    window.setTitle("Chess");

    BorderPane rootPane = new BorderPane();
    Canvas screen = new Canvas(800, 800);
    Scene display = new Scene(rootPane, 800, 800);
    rootPane.getChildren().add(screen);
    out = screen.getGraphicsContext2D();
    window.setScene(display);
    window.show();
  }
}
