/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.engines;

import edu.vanier.managers.ParticleManager;
import edu.vanier.elements.Particle;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author 2181761
 */
public abstract class CollisionEngine {

    /**
     * The simulation's scene
     */
    private Scene simArea;

    /**
     * The nodes that will be displayed in the simulation
     */
    private Group collisionNodes;

    /**
     * The timeline for the collisions and motions
     * <p>
     * Utilizes {@link Timeline Timeline)
     */
    private static Timeline loop;

    /**
     * Frame rate
     */
    private static int fps;

    /**
     * The {@link ParticleManager ParticleManager }
     */
    private static ParticleManager particleManager;

    /**
     * The Constructor
     * <p>
     * It sets the fps and initializes the ParticleManager
     * <p>
     * It will also create the loop
     *
     * @param fps
     */
    public CollisionEngine(final int fps) {
        CollisionEngine.fps = fps;
        CollisionEngine.particleManager = new ParticleManager();

        createLoop();
    }

    /**
     * Creates the loop. Once create the loop will be set as the main loop.
     */
    public final void createLoop() {
        final Duration frameDuration = Duration.millis(1000 / (float) getFps());
        EventHandler<ActionEvent> onFinished = (event) -> {
            // update sprites
            updateParticles();
            // check for collision.
            checkCollisions();
        };
        final KeyFrame frame = new KeyFrame(frameDuration, onFinished);
        // sets the sim's loop (Timeline)
        Timeline loop = new Timeline(frame);
        loop.setCycleCount(Animation.INDEFINITE);
        setLoop(loop);
    }

    public abstract void initialize(final Stage primaryStage);

    public void startLoop() {
        getLoop().play();
    }

    //TODO: finish the fct once ParticleManager is finished
    public void updateParticles() {
        for (Particle par : particleManager.getAllParticles()) {
            handleUpdates(par);
        }
    }

    public void handleUpdates(Particle par) {

    }

    //TODO: finish fct once SpriteManger is finished
    public void checkCollisions() {
        for (Particle parA : particleManager.getCollisionsToCheck()) {
            for (Particle parB : particleManager.getAllParticles()) {
                if (!parA.equals(parB)) {
                    handleCollisions(parA);
                }
            }
        }
    }

    public void handleCollisions(Particle par) {
    }

    //////////////////////////   Getters & Setters   //////////////////////////
    public Scene getSimArea() {
        return simArea;
    }

    public void setSimArea(Scene simArea) {
        this.simArea = simArea;
    }

    public Group getCollisionNodes() {
        return collisionNodes;
    }

    public void setCollisionNodes(Group collisionNodes) {
        this.collisionNodes = collisionNodes;
    }

    public static Timeline getLoop() {
        return loop;
    }

    public static void setLoop(Timeline loop) {
        CollisionEngine.loop = loop;
    }

    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        CollisionEngine.fps = fps;
    }

    public static ParticleManager getparticleManager() {
        return particleManager;
    }

    public static void setParticleManager(ParticleManager particleManager) {
        CollisionEngine.particleManager = particleManager;
    }

}
