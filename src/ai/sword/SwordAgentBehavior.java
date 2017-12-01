/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.sword;

import core.actors.GameActor;
import core.actors.enemies.SwordSkeleton;
import core.screens.GameScreen;
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
        if(this.container.canDelete()){
            this.core.doDelete();
            return;
        }
        if(!this.container.isPossibleToRender()){
            return;
        }
        if((int) this.container.getStateTime() % 2 == 1){
            this.container.getVelocity().x = this.container.getWalkingSpeed();
        }else{
//            this.container.getVelocity().x = 0;
        }
        this.container.updatePosition(GameScreen.lastDelta);
        System.out.println(this.container.getBody());
        this.container.setPossibleToRender(false);
//        System.out.println("Result " + (int) container.getStateTime() % 2);
    }
    
}
