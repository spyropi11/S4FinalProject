package edu.vanier.template;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;

/**
 * This is a JavaFX project template to be used for creating GUI applications.
 * JavaFX 18 is already linked to this project in the build.gradle file.
 * @link: https://openjfx.io/javadoc/18/
 * @see: Build Scripts/build.gradle
 * @author Sleiman Rabah.
 */
public class MainApp extends Application {
    
    //This 2D array acts as a mesh that holds all the pixel objects
    private Pixel[][] mesh;
    
    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;
        
    private final int meshRows = 60;
    private final int meshColumns = 60;
    
    private double leftBoundTemp = 0;
    private double rightBoundTemp = 0;
    private double upperBoundTemp = 0;
    private double lowerBoundTemp = 0;
    
    private final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            
            
            
            update();
        }
    };

    @Override
    public void start(Stage stage) throws Exception {
        
        Group group = new Group();
        group.getChildren().add(new AmbientLight());
        
        /* A 3 row and 3 column mesh should look something like:
        
        [[p,p,p],
        [p,p,p],
        [p,p,p]]
        
        in other words, the first [] in Pixel[][] is the amount of sub lists, 
        and the second [] is the size of each list
        */
        
        mesh = new Pixel[meshRows][meshColumns];
        
        
        for (int i=0; i < meshRows; i++){
            
            for (int j=0; j < meshColumns; j++){
                
                mesh[i][j] = new Pixel(i,j,100,100,100);
                mesh[i][j].translateYProperty().set(i*100);
                mesh[i][j].translateXProperty().set(j*100);
                //mesh[i][j].setMaterial(new PhongMaterial(Color.BLUE));
                
            }
            
        }
        
        for (Pixel[] row : mesh){
            for (Pixel pixel : row){
                
                
                setBoundaries(pixel);
                
                
                
                
                if(!pixel.isBoundary()){
                    
                    
                    
                    
                    pixel.setTempK((1500*(Math.pow(Math.E, -(Math.pow(pixel.getI() - meshRows/2, 2) + Math.pow(pixel.getJ() - meshColumns/2, 2))/60))));
                    pixel.setPrevTempK((1500*(Math.pow(Math.E, -(Math.pow(pixel.getI() - meshRows/2, 2) + Math.pow(pixel.getJ() - meshColumns/2, 2))/60))));
                    
                    
                }
                
                
                updateColour(pixel);
                
                group.getChildren().add(pixel);
                System.out.println("(" + pixel.getI() + "," + pixel.getJ() + ")");
                
            }
            
        }
        
        
        
        
        
        System.out.println(group.getChildren().size());
        
        Camera camera = new PerspectiveCamera(true);
        Scene scene = new Scene(group, WIDTH,HEIGHT);

        camera.translateZProperty().set(-20000);
        camera.translateXProperty().set(meshColumns*50);
        camera.translateYProperty().set(meshRows*50);
        
        camera.setNearClip(1);
        camera.setFarClip(100000);
        
        scene.setCamera(camera);
        scene.setFill(Color.PINK);
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            
            switch(event.getCode()){
                
                case W: 
                    camera.translateZProperty().set(camera.getTranslateZ() + 100);
                    break;
                    
                case S:
                    camera.translateZProperty().set(camera.getTranslateZ() - 100);
                    break;
                    
                case D:
                    camera.translateXProperty().set(camera.getTranslateX() + 25);
                    break;
                    
                case A:
                    camera.translateXProperty().set(camera.getTranslateX() - 25);
                    break;
                    
                case R:
                    camera.translateYProperty().set(camera.getTranslateY() - 25);
                    break;
                    
                case F:
                    camera.translateYProperty().set(camera.getTranslateY() + 25);
                    break;
            }
            
            
        });
        
        
        
        stage.setTitle("HeatSim");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        startTimer();
        
        
    }

    public static void main(String[] args) {
        launch(args);
        
    }
    
    public void setBoundaries(Pixel pixel){
        
        if(pixel.getI() == 0){
                    
            pixel.setBoundary(true);
            pixel.setUpperBound(true);

            pixel.setTempK(upperBoundTemp);
            pixel.setPrevTempK(upperBoundTemp);
                    
        }else if(pixel.getI() == (meshRows - 1)){

            pixel.setBoundary(true);
            pixel.setLowerBound(true);

            pixel.setTempK(lowerBoundTemp);
            pixel.setPrevTempK(lowerBoundTemp);

        }else if(pixel.getJ() == 0){

            pixel.setBoundary(true);
            pixel.setLeftBound(true);

            pixel.setTempK(leftBoundTemp);
            pixel.setPrevTempK(leftBoundTemp);

        }else if(pixel.getJ() == (meshColumns -1)){

            pixel.setBoundary(true);
            pixel.setRightBound(true);

            pixel.setTempK(rightBoundTemp);
            pixel.setPrevTempK(rightBoundTemp);
            
            

        }
        
    }
    
    
    public void updateColour(Pixel pixel) {
        int temperatureC = (int)(255*Math.atan(0.01*pixel.getTempC())/(Math.PI/2));
        if(temperatureC > 0) {
            pixel.setMaterial(new PhongMaterial(Color.rgb(temperatureC, 0, 0)));

        }
        else{
            
            pixel.setMaterial(new PhongMaterial(Color.rgb(0, 0, -temperatureC)));

        }

    }
    
    public void update(){
        
        for (Pixel[] row : mesh){
            for (Pixel pixel : row){
                
                if(!pixel.isBoundary()){
                    pixel.updateTempK(mesh);
                }
                
                pixel.setTempC(pixel.getTempK() - 273);
                updateColour(pixel);

                
            }
            
        }
        
    }
    
    public void startTimer() {
        timer.start();
    }
    
    public void stopTimer() {
        timer.stop();
    }
        
}