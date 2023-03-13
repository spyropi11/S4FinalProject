/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.elements;

import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Spyros
 */
public class Pixel extends Rectangle{
    
    private double totalTime = 0;
    private double tempK;
    private double prevTempK;
    private double tempC;
    //Shift arctan function!
    private double alpha = 4;
    private double deltaTime = 0.02;
    private double deltaX = 1;
    private double deltaY = 1;
    
    //this represents the row that a pixel is in
    private int i;
    
    //this represents the colomn that a pixel is in
    private int j;
    
    private boolean boundary;
    private boolean leftBound;
    private boolean rightBound;
    private boolean upperBound;
    private boolean lowerBound;
    
    
    
    private Material colour;

    public Pixel(int i, int j, double d, double d1) {
        super(d, d1);
        this.i = i;
        this.j = j;
    }
 
    
    
    public void updateTempK(Pixel[][] mesh){
        
        this.tempK = ((alpha*deltaTime)
                *(((mesh[i+1][j].getPrevTempK() + mesh[i-1][j].getPrevTempK() -2*this.prevTempK)/(Math.pow(deltaX, 2))) 
                + ((mesh[i][j+1].getPrevTempK() + mesh[i][j-1].getPrevTempK() -2*this.prevTempK)/(Math.pow(deltaY, 2))))) 
                + this.prevTempK;
        
        this.prevTempK = this.tempK;
        totalTime += deltaTime;
        
        
    }
    
    public void setBoundaries(int meshRows, int meshColumns, double upperBoundTemp, 
            double lowerBoundTemp, double rightBoundTemp, double leftBoundTemp){
        
        if(this.getI() == 0){
                    
            this.setBoundary(true);
            this.setUpperBound(true);

            this.setTempK(upperBoundTemp);
            this.setPrevTempK(upperBoundTemp);
                    
        }else if(this.getI() == (meshRows - 1)){

            this.setBoundary(true);
            this.setLowerBound(true);

            this.setTempK(lowerBoundTemp);
            this.setPrevTempK(lowerBoundTemp);

        }else if(this.getJ() == 0){

            this.setBoundary(true);
            this.setLeftBound(true);

            this.setTempK(leftBoundTemp);
            this.setPrevTempK(leftBoundTemp);

        }else if(this.getJ() == (meshColumns -1)){

            this.setBoundary(true);
            this.setRightBound(true);

            this.setTempK(rightBoundTemp);
            this.setPrevTempK(rightBoundTemp);
            
            

        }
        
    }

    
        public void updateColour() {

        //int pixelHue = (int)(255*Math.atan(0.01*pixel.getTempC())/(Math.PI/2));
        
        double pixelHue = (-2.4)*(this.getTempK() - 360);
        
        //  System.out.println(pixelHue);
        
        if(this.getTempK() >= 260 && this.getTempK() <= 360) {
            this.setFill(Color.hsb(pixelHue, 1, 1));

        }if(this.getTempK() < 260){
            
            this.setFill(Color.hsb(240, 1, 1));
            
        }if(this.getTempK() > 360){
        
            this.setFill(Color.hsb(0, 1, 1));
        
        }
        

    }
    
    public double getTempK() {
        return this.tempK;
    }

    public void setTempK(double tempK) {
        this.tempK = tempK;
    }

    public double getTempC() {
        return this.tempC;
    }

    public void setTempC(double tempC) {
        this.tempC = tempC;
    }

    public double getAlpha() {
        return this.alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public int getI() {
        return this.i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return this.j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public boolean isBoundary() {
        return this.boundary;
    }

    public void setBoundary(boolean boundary) {
        this.boundary = boundary;
    }

    public Material getColour() {
        return this.colour;
    }

    public void setColour(Material colour) {
        this.colour = colour;
    }

    public boolean isLeftBound() {
        return this.leftBound;
    }

    public void setLeftBound(boolean leftBound) {
        this.leftBound = leftBound;
    }

    public boolean isRightBound() {
        return this.rightBound;
    }

    public void setRightBound(boolean rightBound) {
        this.rightBound = rightBound;
    }

    public boolean isUpperBound() {
        return this.upperBound;
    }

    public void setUpperBound(boolean upperBound) {
        this.upperBound = upperBound;
    }

    public boolean isLowerBound() {
        return this.lowerBound;
    }

    public void setLowerBound(boolean lowerBound) {
        this.lowerBound = lowerBound;
    }

    public double getPrevTempK() {
        return this.prevTempK;
    }

    public void setPrevTempK(double prevTempK) {
        this.prevTempK = prevTempK;
    }

    public double getTotalTime() {
        return this.totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }
    
    
    
    
    
    
    
}
