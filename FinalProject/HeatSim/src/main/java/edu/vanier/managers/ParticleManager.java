/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.managers;

import edu.vanier.elements.Particle;
import java.util.*;

/**
 *
 * @author 2181761
 */
public class ParticleManager {

    private static final List<Particle> particles = new ArrayList<Particle>();

    private static final List<Particle> collisionsToCheck = new ArrayList<Particle>();
    
    private static final Set<Particle> particlesToRemove = new HashSet<Particle>();
    
   
    public void addParticles(Particle... inParticles){
        particles.addAll(Arrays.asList(inParticles));
    }
    
    public void removeParticles(Particle... inParticles){
        particles.removeAll(Arrays.asList(inParticles));
    }
    
    public void addParticlesToRemove(Particle... inParticles){
        particlesToRemove.addAll(Arrays.asList(inParticles));
    }

    public void resetCollisionsToCheck(){
        collisionsToCheck.clear();
        collisionsToCheck.addAll(particles);
    }
    
    public void cleanup(){
        particles.removeAll(particlesToRemove);
        particlesToRemove.clear();
    }
    
    //////////////////////////   Getters & Setters   //////////////////////////
    public List<Particle> getAllParticles() {
        return this.particles;
    }
    
    public Set<Particle> getParticlesToRemove(){
        return this.particlesToRemove;
    }

    public List<Particle> getCollisionsToCheck() {
        return this.collisionsToCheck;
    }
    
    
}
