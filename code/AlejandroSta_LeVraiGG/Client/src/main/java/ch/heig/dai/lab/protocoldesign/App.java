package ch.heig.dai.lab.protocoldesign;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.net.Socket;

public class App extends Application {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 4242;

    public final static String MSG_EXPRESSION_HANDLER = "Client: exception : ";


    @FXML
    private Tab t_calculate;

    @FXML
    private TabPane tp;

    @FXML
    private TextField txt_op1;

    @FXML
    private TextField txt_op2;

    @FXML
    private ComboBox<String> cbx_op;

    @FXML
    private Label l_result;

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(App.class.getResource("view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        //scene.getStylesheets().add(URL_CSS_SHEET);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @FXML
    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream());) {
            Worker wrk = new Worker(out, in);

            switch (cbx_op.getSelectionModel().getSelectedItem()) {
                default:
                    return;
                case "+":
                    wrk.add(txt_op1.getText(), txt_op2.getText());
                    break;
                case "-":
                    wrk.sub(txt_op1.getText(), txt_op2.getText());
                    break;
                case "*":
                    wrk.mul(txt_op1.getText(), txt_op2.getText());
                    break;
                case "/":
                    wrk.div(txt_op1.getText(), txt_op2.getText());
            }

            String data = wrk.read();
            System.out.println("DATA Got : " + data);
            if (NumberUtils.isParsable(data)) {
                l_result.setText("Result : " + data);
            } else {
                l_result.setText(MSG_EXPRESSION_HANDLER + data);
            }

        } catch (IOException ex) {
            System.out.println(MSG_EXPRESSION_HANDLER + ex);
        }
    }

    public void onConnectButtonClick(ActionEvent actionEvent) {
        cbx_op.getItems().add("+");
        cbx_op.getItems().add("-");
        cbx_op.getItems().add("/");
        cbx_op.getItems().add("*");
        cbx_op.getSelectionModel().select("+");

        t_calculate.setDisable(false);
        tp.getSelectionModel().select(t_calculate);
    }
}