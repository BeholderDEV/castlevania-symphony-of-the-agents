/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import core.AssetsManager;
import core.map.MapHandler;

/**
 *
 * @author Augustop
 */
public abstract class GameActor {
    public static final float DISTANCE_FROM_GROUND_LAYER = 0.4f;
    protected float stateTime = 0;
    protected Rectangle body;
    protected boolean facingRight = true;
    protected int walkingSpeed = 12;
    protected int jumpingSpeed = walkingSpeed * 3;
    protected Vector2 velocity = new Vector2(); 
    protected State currentState = State.STANDING;
    protected Atk_State atkState = Atk_State.STAND_ATK;
    private final static Rectangle collisionBody1 = new Rectangle();
    private final static Rectangle collisionBody2 = new Rectangle();
    
    public enum State {
        STANDING, WALKING, JUMPING, CROUNCHING, ON_STAIRS, DYING, ATTACKING, HURTED
    }
    
    public enum Atk_State {
        STAND_ATK, CROUCH_ATK, JUMP_ATK, STAIRS_ATK
    }

    public GameActor(Rectangle body) {
        this.body = body;
    }

    public GameActor(int walkingSpeed, Rectangle body) {
        this.walkingSpeed = walkingSpeed;
        this.body = body;
    }
    
    public abstract void updateActor(float deltaTime, MapHandler map, Array<GameActor> stageActors);
    
    public abstract void renderActor(SpriteBatch batch);
    
    public void updatePosition(float delta){
        this.body.x += this.velocity.x * delta * ((this.facingRight) ? 1: -1);
        this.body.y += this.velocity.y * delta;
    }
    
    public void checkGroundCollision(MapHandler map){
        if(map.checkLayerCollision(MapHandler.Layer.GROUND, Math.round(this.body.x), Math.round(this.body.y), Math.round(this.body.x + this.body.width), Math.round(this.body.y + this.body.height * 0.01f))){
            if(this.currentState == State.JUMPING || (this.currentState == State.ATTACKING && this.atkState == Atk_State.JUMP_ATK)){
                if(this.currentState != State.HURTED){
                    this.currentState = State.STANDING;
                }
                this.body.y = Math.round(this.body.y) + GameActor.DISTANCE_FROM_GROUND_LAYER;
            }
        }else if(this.currentState != State.JUMPING && this.currentState != State.ON_STAIRS && this.currentState != State.ATTACKING && this.currentState != State.HURTED){
            this.currentState = State.JUMPING;
        }
    }
    
    public void drawRecOverBody(SpriteBatch batch){
        float ad = 1.6f;
        batch.draw(AssetsManager.assets.get("assets/img/squarer.png", Texture.class),(this.facingRight) ?this.body.x + 0.4f: this.body.x + 0.4f, 
                (this.getCurrentState() == State.ON_STAIRS) ? this.body.y + 0.4f: this.body.y, 
                this.body.width - ad, 
                this.body.height - 0.5f);
    }
    
    public boolean checkCollisionBetweenTwoActors(GameActor actor1, GameActor actor2){
        float xAdjust = 0.4f, widthAdjust = 1.6f, heigthAdjust = 0.5f;
        collisionBody1.set(actor1.body.x + xAdjust,
                (actor1.getCurrentState() == State.ON_STAIRS) ? actor1.body.y + 0.4f: actor1.body.y, 
                actor1.body.width - widthAdjust, 
                actor1.body.height - heigthAdjust);
        collisionBody2.set(actor2.body.x + xAdjust, 
                (actor2.getCurrentState() == State.ON_STAIRS) ? actor2.body.y + 0.4f: actor2.body.y, 
                actor2.body.width - widthAdjust, 
                actor2.body.height - heigthAdjust);
        return collisionBody1.overlaps(collisionBody2);
    }

    public Rectangle getBody() {
        return body;
    }

    public boolean isFacingRight() {
        return facingRight;
    }
    
    public abstract boolean isDead();

    public State getCurrentState() {
        return currentState;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public int getWalkingSpeed() {
        return walkingSpeed;
    }

    public int getJumpingSpeed() {
        return jumpingSpeed;
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }
}
