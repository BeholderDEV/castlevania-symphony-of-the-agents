/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.actors.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import core.actors.GameActor;

/**
 *
 * @author Augustop
 */
public abstract class Enemy extends GameActor{
    public static final float BLINK_INTERVAL = 0.05f;
    public static final float HURTED_DURATION = 0.3f;
    protected float blinkPeriod;
    protected TextureRegion standImg;
    protected Animation<TextureRegion> movingAnimation;
    protected Animation<TextureRegion> atkAnimation;
    protected Animation<TextureRegion> deathAnimation;
//    protected Agent enemyBehavior  ----- integration with JADE

    public Enemy(int walkingSpeed, Rectangle body) {
        super(walkingSpeed, body);
        super.facingRight = false;
    }
    
    @Override
    public void receiveDamage(Rectangle dmgReason, int dmgPoints) {
        if(super.currentState == State.HURTED || super.currentState == State.DYING){
            return;
        }
        super.stateTime = 0;
        super.loseLifePoints(dmgPoints);
        this.blinkPeriod = 0;
        super.currentState = (super.lifePoints > 0) ? State.HURTED: State.DYING;
    }
    
    protected void updateHurtedStatus(float deltaTime){
        this.blinkPeriod += deltaTime;
        if(super.stateTime >= Enemy.HURTED_DURATION){
            super.currentState = State.STANDING;
        }
    }
    
    @Override
    public boolean isDead() {
        return super.currentState == GameActor.State.DYING;
    }
    
    
}
