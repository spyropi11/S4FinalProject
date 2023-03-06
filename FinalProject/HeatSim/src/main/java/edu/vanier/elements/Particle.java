/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.elements;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author 2181761
 */
public class Particle {

    //A node that will hold the graphics (i.e. shape/image) of the particle
    protected Node node;
    private Image image;
    protected double vX;
    protected double vY;
    private double width;
    private double height;

    protected Node collidingNode;

    public Particle() {
        vX = 0;
        vY = 0;
    }

    public void setImage(Image image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void setImage(String fileName) {
        Image image = new Image(fileName);
        setImage(image);
    }

    public void setVelocity(double x, double y) {
        this.vX = x;
        this.vY = y;
    }

    public void increaseVelocity(double x, double y) {
        vX += x;
        vY += y;
    }

    /**
     * Detects if the a node collides with another node
     *
     * @param other - The other Particle
     * @return boolean - True if collision, false if not
     */
    public boolean collide(Particle other) {
        return collidingNode.getBoundsInParent().intersects(other.node.getBoundsInParent());
    }
    
    public boolean intersects(Particle par){
        //localToScene transform coordinate system from local system to the system of the scene
        Bounds parBounds = par.getNode().localToScene(par.getNode().getBoundsInLocal());
        return node.intersects(parBounds);
    }

    public void update() {
        getNode().setTranslateX(getNode().getTranslateX() + this.vX);
        getNode().setTranslateY(getNode().getTranslateY() + this.vY);
    }

    
    /**
     * @return a Circle shape representing a JavaFX node (for convenience)
     */
    public ImageView getImageViewNode(){
        return (ImageView) getNode();
    }
    
    //////////////////////////   Getters & Setters   //////////////////////////
    public Node getNode() {
        return this.node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public double getvX() {
        return this.vX;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }

    public double getvY() {
        return this.vY;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Node getCollidingNode() {
        return this.collidingNode;
    }

    public void setCollidingNode(Node collidingNode) {
        this.collidingNode = collidingNode;
    }

}
