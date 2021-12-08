
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainSceneController {

	@FXML
	private TextArea ta;

	@FXML
	private TextField tfTitle;

	@FXML
	void btnClicked(ActionEvent event) {
		System.out.println("Button Pressed");
		DataOutputStream toServer = null;
		DataInputStream fromServer = null;
		int tfNumber = 0;

		try {
			// Create a socket to connect to the server
			Socket socket = new Socket("localhost", 8000);
			
			// Create an input stream to receive data from the server
			fromServer = new DataInputStream(socket.getInputStream());
			// Create an output stream to send data to the server
			toServer = new DataOutputStream(socket.getOutputStream());
		 }
		 catch (IOException ex) {
			ta.setText(ex.toString() + '\n');
		 }

		try {
			tfNumber = Integer.parseInt(tfTitle.getText());
			toServer.writeInt(tfNumber);
			toServer.flush();

			String ansOut = fromServer.readUTF();
			ta.setText(ansOut);
		}catch (NumberFormatException a) {
			ta.setText("Please Enter A Number Value.");
			return;
		} catch (IOException e) {
			ta.setText(e.toString() + "\n");
			return;
		}

		
	}

}
