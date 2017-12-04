/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.behavior;

import core.actors.GameActor;
import core.actors.enemies.Enemy;
import jade.core.Agent;

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
        super.container.getVelocity().x = super.container.getWalkingSpeed();
        super.container.setFacingRight((int) super.container.getStateTime() % 2 == 1);
        switch(super.container.getCurrentState()){
            case STANDING:
//                System.out.println("Hm");
            break;
            case HURTED:
                super.updateHurtedStatus();
            break;
            case DYING:
                super.myAgent.doDelete();
            break;
        }
    }
        

    @Override
    public void checkCollisions(){
        
    }
    
    @Override
    public void checkStatus(){
        if(super.container.getLifePoints() <= 0){
            super.container.getVelocity().set(0, 0);
            super.container.setCurrentState(GameActor.State.DYING);
        }
    }


    
    
}
