/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.sword;

import ai.AgentCreator;
import core.actors.GameActor;
import core.actors.enemies.SwordSkeleton;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

/**
 *
 * @author 5674867
 */
public class SwordAgentBehavior extends CyclicBehaviour{
    private SwordAgentCore core;
    private SwordSkeleton container;   
    
    public SwordAgentBehavior(SwordAgentCore core, SwordSkeleton container) {
        this.core = core;
        this.container = container;
    }
    
    @Override
    public void action(){
        block(AgentCreator.BEHAVIOR_DELAY);
        if(this.container.canDelete()){
            this.core.doDelete();
            return;
        }
        if(!this.container.isPossibleToRender()){
            return;
        }
        this.defineAction();
        this.container.updatePosition(this.container.getGameScreen().getLastDelta());
        this.checkCollisions();
        this.checkStatus();
        this.container.setPossibleToRender(false);
    }
    
    private void defineAction(){
        this.container.getVelocity().x = this.container.getWalkingSpeed();
        this.container.setFacingRight((int) this.container.getStateTime() % 2 == 1);   
    }
    
    private void checkCollisions(){
        
    }
    
    private void checkStatus(){
        if(this.container.getLifePoints() <= 0){
            this.container.getVelocity().set(0, 0);
            this.container.setCurrentState(GameActor.State.DYING);
        }
    }
    
    
}
