/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.actors.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import core.actors.GameActor;
import core.map.MapHandler;
import core.screens.GameScreen;
import jade.core.Agent;

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
    protected GameScreen gameScreen;
    protected boolean canDelete = false;
    
    
    public Enemy(int walkingSpeed, Rectangle body, GameScreen gameScreen) {
        super(walkingSpeed, body);
        super.facingRight = false;
        this.gameScreen = gameScreen;
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

    public GameScreen getGameScreen() {
        return gameScreen;
    }
    
    @Override
    public boolean isDead() {
        return super.currentState == GameActor.State.DYING;
    }

    public boolean canDelete() {
        return canDelete;
    }

    public float getBlinkPeriod() {
        return blinkPeriod;
    }

    public void setBlinkPeriod(float blinkPeriod) {
        this.blinkPeriod = blinkPeriod;
    }
    
    public void setForDelete(){
        this.canDelete = true;
    }
}
