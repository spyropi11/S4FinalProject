/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.engines;

import edu.vanier.elements.Pixel;

/**
 *
 * @author Spyros
 */
public class HeatPhysics {
    
    private double totalTime = 0;
    private double alpha = 4;
    private double deltaTime = 0.02;
    private double deltaX = 1;
    private double deltaY = 1;

    public HeatPhysics() {
        
        
    }
    
    
    public void updateTempK(Pixel[][] mesh, int i, int j){
        
        mesh[i][j].setTempK(((alpha*deltaTime)
                *(((mesh[i+1][j].getPrevTempK() + mesh[i-1][j].getPrevTempK() -2*mesh[i][j].getPrevTempK())/(Math.pow(deltaX, 2))) 
                + ((mesh[i][j+1].getPrevTempK() + mesh[i][j-1].getPrevTempK() -2*mesh[i][j].getPrevTempK())/(Math.pow(deltaY, 2))))) 
                + mesh[i][j].getPrevTempK());
        
        mesh[i][j].setPrevTempK(mesh[i][j].getTempK()); 
        totalTime += deltaTime;
        
        
    }
    
}
