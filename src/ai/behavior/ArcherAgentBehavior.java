/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.behavior;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import core.actors.CollisionHandler;
import core.actors.GameActor;
import core.actors.enemies.ArcherSkeleton;
import core.actors.enemies.Enemy;
import core.objects.Arrow;
import jade.core.Agent;

/**
 *
 * @author Adson Esteves
 */
public class ArcherAgentBehavior extends AgentBehavior{
    
    public static float TIME_BEFORE_WALKING = 3f;
    private float attack_cooldown = 2f;
    private boolean arrowCreatedOnAtk = false;
    
    public ArcherAgentBehavior(Enemy container, Agent a, long period) {
        super(container, a, period, 30);
        super.container.setFacingRight(true);
        super.container.setCurrentState(GameActor.State.WALKING);
        super.container.getVelocity().set(super.container.getWalkingSpeed(), 0);
    }
    
    @Override
    public void defineAction(){
        if(super.container.isIsBlinking()){
            super.updateHurted();
        }
        switch(super.container.getCurrentState()){
            case STANDING:
                this.defineActionStanding();
            break;
            case WALKING:
                this.defineActionWalking();
            break;
            case ATTACKING:
                this.defineActionAtk();
            break;
            case DYING:
                super.myAgent.doDelete();
            break;
        }
    }
    
    private void defineActionStanding(){
        super.container.getVelocity().set(0, 0);
        if(super.container.getStateTime() >= this.attack_cooldown && super.checkIfCanAtk()){
            return;
        }
        if(super.container.getStateTime() >= TIME_BEFORE_WALKING){
            super.container.getVelocity().x = super.container.getWalkingSpeed();
            super.container.setFacingRight(!super.container.isFacingRight());
            super.container.setCurrentState(GameActor.State.WALKING);
        }
    }
        
    private void defineActionWalking(){
        if(super.checkIfCanAtk()){
            return;
        }
        Vector2 initialPosition = ((ArcherSkeleton)super.container).getInitialPosition();
        float range = ((ArcherSkeleton)super.container).getWalkingRange();
        float xNew = super.container.getBody().x += super.container.getVelocity().x * super.container.getGameScreen().getLastDelta() * ((super.container.isFacingRight()) ? 1: -1);
        if((super.container.isFacingRight() && xNew > initialPosition.x + range) || (!super.container.isFacingRight() && xNew < initialPosition.x - range)){
            super.container.setStateTime(0);
            super.container.setCurrentState(GameActor.State.STANDING);
        }
    }
        
    private void defineActionAtk(){
        if(!arrowCreatedOnAtk && super.container.getStateTime() >= GameActor.STANDARD_ATK_FRAME_TIME * 3){
            ((ArcherSkeleton)super.container).getArrows().add(
                new Arrow((super.container.isFacingRight()) ? super.container.getBody().x + super.container.getBody().width : super.container.getBody().x, 
                super.container.getBody().y,
                super.container.isFacingRight()));
            this.arrowCreatedOnAtk = true;
        }
        super.updateAtk();
        if(super.container.getCurrentState() == GameActor.State.STANDING){
            this.arrowCreatedOnAtk = false;
        }
    }

    @Override
    protected void checkIfFoundPlayer() {
        if (super.container.isFacingRight() && super.container.getBody().x + super.container.getSpriteAdjustmentForCollision()[0] > this.player.getBody().x
        || !super.container.isFacingRight() && super.container.getBody().x - super.container.getSpriteAdjustmentForCollision()[0] < this.player.getBody().x){
            return;
        }
        super.checkIfFoundPlayer();
    }
    
    @Override
    public void checkCollisions(){
        if(((ArcherSkeleton)super.container).getArrows().size != 0){
            this.updateWeaponHit();
        }        
        
    }
    
    @Override
    public void checkStatus(){
        if(super.container.getLifePoints() <= 0){
            super.container.getVelocity().set(0, 0);
            super.container.setCurrentState(GameActor.State.DYING);
        }
    }
    
    private void updateWeaponHit(){
        Array<Arrow> arrows = ((ArcherSkeleton)super.container).getArrows();
        Rectangle arrow;
        for (int i = 0; i < arrows.size; i++) {
            arrow = arrows.get(i).getCollisionBody();
            Rectangle weaponArea = CollisionHandler.rectanglePool.obtain();
            weaponArea.set(arrow.x, arrow.y + Arrow.DISTANCE_FROM_GROUND, (arrows.get(i).isFaceToRight()) ? arrow.width: -arrow.width, arrow.height);
            if(CollisionHandler.checkCollisionBetweenBodyAndObject(super.player, weaponArea)){
                super.player.receiveDamage(super.container.getBody(), 1);
                arrows.removeIndex(i);
                i--;
            }
            CollisionHandler.rectanglePool.free(weaponArea);
        }
    }
}
