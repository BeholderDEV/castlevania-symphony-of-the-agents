/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.behavior;

import ai.AgentCreator;
import core.actors.GameActor;
import core.actors.enemies.Enemy;
import core.actors.enemies.SwordSkeleton;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

/**
 *
 * @author 5674867
 */
public class SwordAgentBehavior extends AgentBehavior{

    public SwordAgentBehavior(Enemy container, Agent a, long period) {
        super(container, a, period);
    }
    
    @Override
    public void defineAction(){
        this.container.getVelocity().x = this.container.getWalkingSpeed();
        this.container.setFacingRight((int) this.container.getStateTime() % 2 == 1);   
    }
    
    @Override
    public void checkCollisions(){
        
    }
    
    @Override
    public void checkStatus(){
        if(this.container.getLifePoints() <= 0){
            this.container.getVelocity().set(0, 0);
            this.container.setCurrentState(GameActor.State.DYING);
        }
    }


    
    
}
