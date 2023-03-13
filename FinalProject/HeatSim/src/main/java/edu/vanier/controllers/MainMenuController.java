/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.controllers;

import edu.vanier.menus.HeatModel;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Spyros
 */
public class MainMenuController {
    
    @FXML
    public void handleHeatSimButton() throws IOException {
        Stage secondWindow = new Stage();

        HeatSimController window2 = new HeatSimController();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/HeatModelMenu.fxml"));
        loader.setController(window2);
        BorderPane root = loader.load();
        
        HeatModel heatModelSim = new HeatModel(50,50);
        heatModelSim.heatModelPane.setPrefHeight(700);
        heatModelSim.heatModelPane.setPrefWidth(700);
        heatModelSim.heatModelPane.setMaxHeight(700);
        heatModelSim.heatModelPane.setMaxWidth(700);
        root.setCenter(heatModelSim.heatModelPane);
        root.getLeft().setStyle("-fx-background-color: #faecdf;");
        //root.getCenter().setStyle("-fx-background-color: #faecdf;");
        root.getTop().setStyle("-fx-background-color: #faecdf;");
        
        
        System.out.println(heatModelSim.heatModelPane.getPrefHeight());
        System.out.println(heatModelSim.heatModelPane.getPrefWidth());
        
        
        Scene scene = new Scene(root, 1400.0, 750.0);
        secondWindow.setScene(scene);
        
        //scene.setCamera(heatModelSim.heatModelCamera);
        //heatModelSim.setCameraControls(secondWindow, scene.getCamera());
        secondWindow.setTitle("Heat Simulation Package");
        secondWindow.sizeToScene();
        secondWindow.show();
    }
    
    @FXML
    public void handleTherSimButton() throws IOException {
        Stage secondWindow = new Stage();
        ParticleThermodynamicsController window3 = new ParticleThermodynamicsController();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/ParticleTHermoDynamicsMainMenu.fxml"));
        loader.setController(window3);
        Pane third = loader.load();
        Scene scene = new Scene(third, 1200.0, 1000.0);
        secondWindow.setScene(scene);

        secondWindow.setTitle("Particle Thermodynamics Simulator");
        secondWindow.sizeToScene();
        secondWindow.show();
    }
}
