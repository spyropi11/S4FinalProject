package edu.vanier.menus;

import edu.vanier.elements.Pixel;
import edu.vanier.engines.HeatPhysics;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Pane;


public class HeatModel extends MainMenu {
    
    //This 2D array acts as a mesh that holds all the pixel objects
    private Pixel[][] mesh;
    
    private HeatPhysics heatPhysics = new HeatPhysics();
    
    private double leftBoundTemp = 260;
    private double rightBoundTemp = 260;
    private double upperBoundTemp = 260;
    private double lowerBoundTemp = 260;
    
    private boolean updatePointer = false;
    private Pixel pointedPixel;
    
    public Pane heatModelPane;
    
    private final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            
            //Activates if a pixel is being hovered over
            if(updatePointer == true){
                
                System.out.println(pointedPixel.getTempK());
                
                
            }
            
            update();
            
        }
    };

    
    //The constructor will initiate the simulation when an instance is created
    public HeatModel(int meshRows, int meshColumns) {
        heatModelPane = new Pane();
        
        heatModelPane.setStyle("-fx-background-color: #caeced;");
        
        
        
        // A 3 row and 3 column mesh should look something like:
        
        //[[p,p,p],
        //[p,p,p],
        //[p,p,p]]
        
        //in other words, the first [] in Pixel[][] is the amount of sub lists, 
        //and the second [] is the size of each list
        mesh = new Pixel[meshRows][meshColumns];
        
        for (int i=0; i < meshRows; i++){
            
            for (int j=0; j < meshColumns; j++){
                
                mesh[i][j] = new Pixel(i,j,14,14);
                mesh[i][j].translateYProperty().set(i*14);
                mesh[i][j].translateXProperty().set(j*14);                
                
            }
            
        }
        
        for (Pixel[] row : mesh){
            for (Pixel pixel : row){
                
                
                pixel.setBoundaries(meshRows, meshColumns, upperBoundTemp, lowerBoundTemp, rightBoundTemp, leftBoundTemp);
                
                pixel.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                    
                    if (newValue) {
                        
                        pixel.setOpacity(0.10);
                        
                        pointedPixel = pixel;
                        updatePointer = true;
                        
                    }else{
                        
                        pixel.setOpacity(1);
                        
                        updatePointer = false;
                    }
                });
        
                
                
                
                if(!pixel.isBoundary()){
                
                    pixel.setTempK((360*(Math.pow(Math.E, -(Math.pow(pixel.getI() - meshRows/2, 2) + Math.pow(pixel.getJ() - meshColumns/2, 2))/20))) + 260);
                    pixel.setPrevTempK((360*(Math.pow(Math.E, -(Math.pow(pixel.getI() - meshRows/2, 2) + Math.pow(pixel.getJ() - meshColumns/2, 2))/20))) + 260);

                
                }
                
                
                pixel.updateColour();
                
                heatModelPane.getChildren().add(pixel);
                
            }
            
        }
        
        //this starts the animation timer, allowing the update method to be called every x seconds
        startTimer();
        
        
        
        
    }

    public void update(){
        
        for (Pixel[] row : mesh){
            for (Pixel pixel : row){
                
                if(!pixel.isBoundary()){
                                        
                    heatPhysics.updateTempK(mesh, pixel.getI(), pixel.getJ());
                }
                pixel.updateColour();
                
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