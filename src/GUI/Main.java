/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author setti
 */
public class Main extends Application {
    public static Stage x;
    @Override
    public void start(Stage primaryStage) throws IOException {
        x = primaryStage;
        LoginController login = new LoginController();
        login.GoToLogin();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         new Connect("RMS");
        launch(args);
    }
    
}
