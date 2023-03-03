package edu.vanier.menus;

import edu.vanier.elements.Pixel;
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
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This is a JavaFX project template to be used for creating GUI applications.
 * JavaFX 18 is already linked to this project in the build.gradle file.
 * @link: https://openjfx.io/javadoc/18/
 * @see: Build Scripts/build.gradle
 * 
 */
public class HeatModel extends MainMenu {
    
    //This 2D array acts as a mesh that holds all the pixel objects
    private Pixel[][] mesh;
    
    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;
        
    private final int meshRows = 60;
    private final int meshColumns = 60;
    
    private double leftBoundTemp = 300;
    private double rightBoundTemp = 300;
    private double upperBoundTemp = 300;
    private double lowerBoundTemp = 300;
    
    private final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            
            /*System.out.println("point: (" + mesh[meshRows/2][meshRows/2].getI() + "," + mesh[meshRows/2][meshRows/2].getJ() + ") , "+ mesh[meshRows/2][meshRows/2].getTempK() + ","
            + mesh[meshRows/2][meshRows/2].getTotalTime() + ", " + mesh[meshRows/2][meshRows/2].getAlpha() + ", "
            + leftBoundTemp);*/
            
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
                
                mesh[i][j] = new Pixel(i,j,10,10,10);
                mesh[i][j].translateYProperty().set(i*10);
                mesh[i][j].translateXProperty().set(j*10);                
                
            }
            
        }
        
        for (Pixel[] row : mesh){
            for (Pixel pixel : row){
                
                
                setBoundaries(pixel);
               
                
                
                
                if(!pixel.isBoundary()){
                
                    pixel.setTempK((500*(Math.pow(Math.E, -(Math.pow(pixel.getI() - meshRows/2, 2) + Math.pow(pixel.getJ() - meshColumns/2, 2))/20))) + 300);
                    pixel.setPrevTempK((500*(Math.pow(Math.E, -(Math.pow(pixel.getI() - meshRows/2, 2) + Math.pow(pixel.getJ() - meshColumns/2, 2))/20))) + 300);

                
                }
                
                
                updateColour(pixel);
                
                group.getChildren().add(pixel);
                //System.out.println("(" + pixel.getI() + "," + pixel.getJ() + ")");
                
            }
            
        }
        
        
        
        

        
        Camera camera = new PerspectiveCamera(true);
        
        Rotate rotateXAxis = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateYAxis = new Rotate(0, Rotate.Y_AXIS);
        
        camera.getTransforms().addAll(rotateXAxis,rotateYAxis);
        
        
        Scene scene = new Scene(group, WIDTH,HEIGHT);

        camera.translateZProperty().set(-1300);
        camera.translateXProperty().set(meshColumns*5);
        camera.translateYProperty().set(meshRows*5);
        rotateXAxis.angleProperty().set(0);
        
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
                    camera.translateXProperty().set(camera.getTranslateX() + 50);
                    break;
                    
                case A:
                    camera.translateXProperty().set(camera.getTranslateX() - 50);
                    break;
                    
                case R:
                    camera.translateYProperty().set(camera.getTranslateY() - 50);
                    break;
                    
                case F:
                    camera.translateYProperty().set(camera.getTranslateY() + 150);
                    break;
                
                case N:
                    rotateXAxis.angleProperty().set(rotateXAxis.getAngle() + 5);
                    break;
                    
                case M:
                    rotateXAxis.angleProperty().set(rotateXAxis.getAngle() - 5);
                    break;
                    
                case X:
                    rotateYAxis.angleProperty().set(rotateYAxis.getAngle() + 5);
                    break;
                    
                case C:
                    rotateYAxis.angleProperty().set(rotateYAxis.getAngle() - 5);
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
                    
                    /*if(pixel.getI() == 29 && pixel.getJ() == 29){
                    
                    pixel.setTempK(500);
                    pixel.setPrevTempK(500);
                    
                    }*/
                    //pixel.setTempK((500*(Math.pow(Math.E, -(Math.pow(pixel.getI() - meshRows/2, 2) + Math.pow(pixel.getJ() - meshColumns/2, 2))/20))) + 150);
                    //pixel.setPrevTempK((500*(Math.pow(Math.E, -(Math.pow(pixel.getI() - meshRows/2, 2) + Math.pow(pixel.getJ() - meshColumns/2, 2))/20))) + 150);
                    
                    
                
                    pixel.updateTempK(mesh);
                }
                
                pixel.setTempC(pixel.getTempK() - 273);
                
                
                //pixel.translateZProperty().set( -1*pixel.getTempK());
                
                
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