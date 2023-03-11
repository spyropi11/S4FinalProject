package edu.vanier.menus;

import edu.vanier.elements.Pixel;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
//import java.awt.Color;


/**
 * This is a JavaFX project template to be used for creating GUI applications.
 * JavaFX 18 is already linked to this project in the build.gradle file.
 * @link: https://openjfx.io/javadoc/18/
 * @see: Build Scripts/build.gradle
 * 
 */
public class HeatModel extends MainMenu {
    
    private java.awt.Color ColorAWT;
    
    //This 2D array acts as a mesh that holds all the pixel objects
    private Pixel[][] mesh;
    
    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;
        
    private final int meshRows = 50;
    private final int meshColumns = 50;
    
    private double leftBoundTemp = 260;
    private double rightBoundTemp = 260;
    private double upperBoundTemp = 260;
    private double lowerBoundTemp = 260;
    
    private boolean updatePointer = false;
    private Pixel pointedPixel;
    
    public Camera heatModelCamera;
    public Pane heatModelPane;
    
    private final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            
            if(updatePointer == true){
                
                //System.out.println("currently hovering");
                System.out.println(pointedPixel.getTempK());
                
            }
            
            /*System.out.println("point: (" + mesh[meshRows/2][meshRows/2].getI() + "," + mesh[meshRows/2][meshRows/2].getJ() + ") , "+ mesh[meshRows/2][meshRows/2].getTempK() + ","
            + mesh[meshRows/2][meshRows/2].getTotalTime() + ", " + mesh[meshRows/2][meshRows/2].getAlpha() + ", "
            + leftBoundTemp);*/
            
            update();
            
        }
    };

    
    //The constructor will initiate the simulation when an instance is created
    public HeatModel() {
        Pane HeatModelPane = new Pane();
        
        System.out.println(HeatModelPane.getHeight());
        System.out.println(HeatModelPane.getWidth());
        
        HeatModelPane.setStyle("-fx-background-color: #caeced;");
        
        HeatModelPane.getChildren().add(new AmbientLight());
        
        
        // A 3 row and 3 column mesh should look something like:
        
        //[[p,p,p],
        //[p,p,p],
        //[p,p,p]]
        
        //in other words, the first [] in Pixel[][] is the amount of sub lists, 
        //and the second [] is the size of each list
        
        
        mesh = new Pixel[meshRows][meshColumns];
        
        
        for (int i=0; i < meshRows; i++){
            
            for (int j=0; j < meshColumns; j++){
                
                mesh[i][j] = new Pixel(i,j,10,10,0);
                mesh[i][j].translateYProperty().set(i*10 + 100);
                mesh[i][j].translateXProperty().set(j*10 + 100);                
                
            }
            
        }
        
        for (Pixel[] row : mesh){
            for (Pixel pixel : row){
                
                
                setBoundaries(pixel);
                
                pixel.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                    
                    if (newValue) {
                        
                        //System.out.println("hovering");
                        pointedPixel = pixel;
                        updatePointer = true;
                        
                    }else{
                        
                        updatePointer = false;
                    }
                });
        
                
                
                
                if(!pixel.isBoundary()){
                
                    pixel.setTempK((360*(Math.pow(Math.E, -(Math.pow(pixel.getI() - meshRows/2, 2) + Math.pow(pixel.getJ() - meshColumns/2, 2))/20))) + 260);
                    pixel.setPrevTempK((360*(Math.pow(Math.E, -(Math.pow(pixel.getI() - meshRows/2, 2) + Math.pow(pixel.getJ() - meshColumns/2, 2))/20))) + 260);

                
                }
                
                
                updateColour(pixel);
                
                HeatModelPane.getChildren().add(pixel);
                //System.out.println("(" + pixel.getI() + "," + pixel.getJ() + ")");
                
            }
            
        }
        
        
        
        

        
        Camera camera = new PerspectiveCamera(true);
        
        Rotate rotateXAxis = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateYAxis = new Rotate(0, Rotate.Y_AXIS);
        
        camera.getTransforms().addAll(rotateXAxis,rotateYAxis);
        
        
        //Scene scene = new Scene(HeatModelPane, WIDTH,HEIGHT);

        //camera.translateZProperty().set(-1300);
        camera.translateXProperty().set(meshColumns*5);
        camera.translateYProperty().set(meshRows*5);
        rotateXAxis.angleProperty().set(0);
        
        camera.setNearClip(1);
        //camera.setFarClip(100000);
        
        heatModelCamera = camera;
        heatModelPane = HeatModelPane;
        //scene.setCamera(camera);
        
        //scene.setFill(Color.PINK);
        
        
        
        
        
        //heatModelScene = scene;
        
        /*stage.setTitle("HeatSim");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        */
        startTimer();
        
        
        
        
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
        
        
        
        
        
        //int pixelHue = (int)(255*Math.atan(0.01*pixel.getTempC())/(Math.PI/2));
        
        double pixelHue = (-2.4)*(pixel.getTempK() - 360);
        
        //  System.out.println(pixelHue);
        
        if(pixel.getTempK() >= 260 && pixel.getTempK() <= 360) {
            pixel.setMaterial(new PhongMaterial(Color.hsb(pixelHue, 1, 1)));

        }if(pixel.getTempK() < 260){
            
            pixel.setMaterial(new PhongMaterial(Color.hsb(240, 1, 1)));
            
        }if(pixel.getTempK() > 360){
        
            pixel.setMaterial(new PhongMaterial(Color.hsb(0, 1, 1)));
        
        }
        

    }
    
    public void update(){
        
        
        for (Pixel[] row : mesh){
            for (Pixel pixel : row){
                
                if(!pixel.isBoundary()){
                    
                    
                    
                    pixel.updateTempK(mesh);
                }
                
                pixel.setTempC(pixel.getTempK() - 273);
                
                
                //pixel.translateZProperty().set( -1*pixel.getTempK());
                
                
                updateColour(pixel);
                

                
            }
            
        }
        
    }
    
    public void setCameraControls(Stage stage, Camera camera){
        
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
        
        /*
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
        */
        
        }
        
        
        });
        
        
    }
    
    public void startTimer() {
        timer.start();
    }
    
    public void stopTimer() {
        timer.stop();
    }
        
}