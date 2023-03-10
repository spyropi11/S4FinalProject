/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.menus;

import edu.vanier.controllers.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Spyros
 */
public class MainMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MainMenuController welcomeScreen = new MainMenuController();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/MainMenu.fxml"));

        loader.setController(welcomeScreen);
        Pane root = loader.load();
        Scene scene = new Scene(root, 800, 450);
        stage.setScene(scene);

        stage.setTitle("Project");
        stage.sizeToScene();
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);

    }
    
}
