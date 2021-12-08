import java.io.*;
import java.net.*;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application {
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		// Text area for displaying contents
		TextArea ta = new TextArea();
		// Create a scene and place it in the stage
		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		primaryStage.setTitle("Server"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
		
		new Thread( () -> {
		try {
			// Create a server socket
			ServerSocket serverSocket = new ServerSocket(8000);
			Platform.runLater(() ->
				ta.appendText("Server started at " + new Date() + '\n'));

			// Listen for a connection request
			Socket socket = serverSocket.accept();

			// Create data input and output streams
			DataInputStream inputFromClient = new DataInputStream(
				socket.getInputStream());
			DataOutputStream outputToClient = new DataOutputStream(
				socket.getOutputStream());

			while (true) {
				// Receive radius from the client
				int num1 = inputFromClient.readInt();
				String ansOut = "";
				Boolean ticket = true;

				if (num1 <= 1){
					ticket = false;
				}
				for (int i = 2; i < num1; i++){
					if(num1 % i == 0){
						ticket = false;
					}
				}
				
				if(ticket){ansOut = num1 + " Is A Prime Number";}
				else{ansOut = num1 + " Is Not A Prime Number";}


				ta.appendText("Number received from client: " 
					+ num1 + '\n');
				ta.appendText("Prime: " + ticket + '\n'); 
				//send back the awnswer
				outputToClient.writeUTF(ansOut);


				Platform.runLater(() -> {
				//ta.appendText("Number received from client: " 
				//	+ num1 + '\n');
				//ta.appendText("Prime: " + "test Case" + '\n'); 
				});
			}
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		}).start();
	}
	/**
	* The main method is only needed for the IDE with limited
	* JavaFX support. Not needed for running from the command line.
	*/
	public static void main(String[] args) {
		launch(args);
	}
}