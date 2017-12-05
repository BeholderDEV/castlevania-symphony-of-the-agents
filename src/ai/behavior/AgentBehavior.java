/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.behavior;

import core.actors.GameActor;
import core.actors.enemies.Enemy;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

/**
 *
 * @author Augustop
 */
public abstract class AgentBehavior extends TickerBehaviour{
    protected Enemy container;
    
    public AgentBehavior(Enemy container, Agent a, long period) {
        super(a, period);
        this.container = container;
    }
    
    @Override
    protected void onTick() {
        if(this.container.canDelete()){
            this.myAgent.doDelete();
            return;
        }
        if(!this.container.isPossibleToRender()){
            return;
        }
//        this.container.setStateTime(this.container);
        this.defineAction();
        this.container.updatePosition(this.container.getGameScreen().getLastDelta());
        this.checkCollisions();
        this.checkStatus();
        this.container.setPossibleToRender(false);
    }
    
    protected void updateHurted(){
        this.container.setBlinkPeriod(this.container.getBlinkPeriod() + this.container.getGameScreen().getLastDelta());
        if(this.container.getStateTime() >= Enemy.HURTED_DURATION){
            this.container.setIsBlinking(false);
        }
    }
    
    protected void updateAtk(){
        if(this.container.getAtkAnimation().isAnimationFinished(this.container.getStateTime())){
            this.container.setCurrentState(GameActor.State.STANDING);
        }
    }
    
    public abstract void defineAction();
    
    public abstract void checkCollisions();
    
    public abstract void checkStatus();    
}
