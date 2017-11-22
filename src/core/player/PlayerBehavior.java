/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import core.AssetsManager;
import core.map.MapHandler;
import java.awt.Dimension;

/**
 *
 * @author Alisson
 */
public class PlayerBehavior {
    
    public enum State {
        STANDING, WALKING, JUMPING, CROUNCHING, ATTACKING, ON_STAIRS
    }
    
    private final int NORMAL_GROUND_DISTANCE = 1; // used for ground detection from stairs
    private final float NORMAL_WIDTH = 4f;
    private final float NORMAL_HEIGHT = 6f;
    private final int WALKING_SPEED = 12;
    private final int JUMPING_SPEED = WALKING_SPEED * 3;
    private final Dimension FOOT_SIZE = new Dimension(32, 25);
    private boolean facesRight = true;
    private boolean upstairs = false;
    private float stateTime;
    private State currentState = State.STANDING;
    private Vector2 velocity = new Vector2(); 
    private Rectangle playerBody;

    public PlayerBehavior() {
        this.playerBody = new Rectangle(0, 0, this.NORMAL_WIDTH, this.NORMAL_HEIGHT);
        this.stateTime = 0;
    }
    
    public void defineAction(float deltaTime, MapHandler map){
        this.stateTime += deltaTime;
        switch(this.currentState){
            case WALKING:
            case STANDING:
                this.defineActionStanding(deltaTime);
            break;
            case JUMPING:
                this.defineActionJumping(deltaTime);
            break;
            case CROUNCHING:
                this.defineActionCrounching(deltaTime);
            break;
            case ON_STAIRS:
                this.defineActionOnStairs(deltaTime, map);
            break;
        }
    }
    
    
    private void defineActionStanding(float deltaTime){
        this.currentState = State.STANDING;
        this.velocity.set(0, 0);
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))){
            return;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.currentState = State.WALKING;
            this.velocity.x = WALKING_SPEED;
            this.facesRight = false;        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.currentState = State.WALKING;
            this.velocity.x = WALKING_SPEED;
            this.facesRight = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            this.currentState = State.CROUNCHING;
            this.playerBody.height = Math.round(this.NORMAL_HEIGHT - this.NORMAL_HEIGHT * 0.25);
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            this.currentState = State.JUMPING;
            this.velocity.y = JUMPING_SPEED;
        }
    }
    
    private void defineActionJumping(float deltaTime){
        this.velocity.y = (this.velocity.y < 0) ? this.velocity.y - JUMPING_SPEED / 28f : this.velocity.y - JUMPING_SPEED /21f;
        this.velocity.x = (this.velocity.x > 0) ? this.velocity.x + WALKING_SPEED / 1.1f: 0;
        if(this.velocity.x >= WALKING_SPEED){
            this.velocity.x = WALKING_SPEED;
        }
    }
    
    private void defineActionCrounching(float deltaTime){
        this.velocity.set(0, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            this.currentState = State.CROUNCHING;
            return;
        }
        this.currentState = State.STANDING;
        this.playerBody.height = this.NORMAL_HEIGHT;
    }
    
    private void defineActionOnStairs(float deltaTime, MapHandler map){
        this.velocity.set(0, 0);

        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))){
            this.stateTime -= deltaTime;
            return;
        }
        
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) || (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))){
            this.velocity.set(WALKING_SPEED, WALKING_SPEED);
            
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
                if((this.upstairs && !this.facesRight) || (!this.upstairs && this.facesRight)){
                    this.upstairs = false;
                    this.velocity.y *= -1;
                }else if(!this.upstairs && !this.facesRight){
                    this.upstairs = true;
                }
                this.facesRight = true;
            }else{
                if((this.upstairs && this.facesRight) || (!this.upstairs && !this.facesRight)){
                    this.upstairs = false;
                    this.velocity.y *= -1;
                }else if(!this.upstairs && this.facesRight){
                    this.upstairs = true;
                }
                this.facesRight = false;
            }
            int footTileX = (this.facesRight) ? Math.round((this.playerBody.x + this.velocity.x * deltaTime * ((this.facesRight) ? 1: -1) + this.playerBody.width) - this.playerBody.width * (this.FOOT_SIZE.width / 100f)): 
                                                Math.round(this.playerBody.x + this.velocity.x * deltaTime * ((this.facesRight) ? 1: -1) + this.playerBody.width * (this.FOOT_SIZE.width / 100f)); 
            int footTileY = Math.round(this.playerBody.y + this.velocity.y * deltaTime + this.FOOT_SIZE.height / 100f);
            if(this.checkIfReachedGroundFromStairs(map, footTileX, footTileY)){
                return;
            }
//            if(!map.checkValidLayerMove(MapHandler.Layer.STAIR, footTileX + 1, footTileY, facesRight)){
//                this.velocity.set(0, 0);
//                this.stateTime -= deltaTime;
//                return;
//            }
            this.stateTime += deltaTime;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            this.currentState = State.JUMPING;
            this.velocity.y = JUMPING_SPEED;
        }

        this.stateTime -= deltaTime;
    }
    
    public void checkCollisions(MapHandler map){
        this.checkGroundCollision(map);
        this.checkStairsCollision(map);
    }
    
    private void checkGroundCollision(MapHandler map){
        if(map.checkLayerCollision(MapHandler.Layer.GROUND, Math.round(this.playerBody.x), Math.round(this.playerBody.y), Math.round(this.playerBody.x + this.playerBody.width), Math.round(this.playerBody.y + this.playerBody.height * 0.01f))){
            if(this.currentState == State.JUMPING){
                this.currentState = State.STANDING;
                this.playerBody.y = Math.round(this.playerBody.y) + 0.4f;
            }
        }else if(this.currentState != State.JUMPING && this.currentState != State.ON_STAIRS){
            this.currentState = State.JUMPING;
        }
    }
    
    private boolean checkIfReachedGroundFromStairs(MapHandler map, int footTileX, int footTileY){
        Vector2 ground = map.checkCloseToGroundFromTile(footTileX, footTileY, this.upstairs, this.facesRight, this.NORMAL_GROUND_DISTANCE);
        if(ground != null){
            this.velocity.y = 0;
            this.playerBody.y = ground.y + 0.4f;
            this.currentState = State.STANDING;
            return true;
        }
        return false;
    }
    
    private void checkStairsCollision(MapHandler map){
        if(this.currentState == State.STANDING || this.currentState == State.WALKING){
            float x = (this.facesRight) ? (this.playerBody.x + this.playerBody.width) - this.playerBody.width * (this.FOOT_SIZE.width / 100f): 
                                           this.playerBody.x + this.playerBody.width * (this.FOOT_SIZE.width / 100f);            
            Rectangle stairBoundary = map.checkCollisionWithStairBoundary(x, this.playerBody.y, this.playerBody.width * (this.FOOT_SIZE.width / 8f / 100f), this.playerBody.height * (this.FOOT_SIZE.height / 100f));
            if(stairBoundary == null){
                return;
            }
            String stairDirection = map.checkStairsDirection(Math.round(stairBoundary.x), Math.round(stairBoundary.y), Math.round(stairBoundary.x + stairBoundary.width), Math.round(stairBoundary.y + stairBoundary.height));
            if(stairDirection.equals("Failed")){
                System.out.println(stairDirection);
                return;
            }
//            System.out.println(stairDirection);
            if((stairDirection.equals("rightUp") && this.facesRight) || (stairDirection.equals("leftUp") && !this.facesRight)){
                if(!(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))){
                    return;
                }
                this.upstairs = true;
            }else if((stairDirection.equals("rightDown") && this.facesRight) || (stairDirection.equals("leftDown") && !this.facesRight)){
                this.upstairs = false;
            }else{
                return;
            }
            this.currentState = State.ON_STAIRS;
        }
    }
    
    public void updatePosition(float delta){
        this.playerBody.x += this.velocity.x * delta * ((this.facesRight) ? 1: -1);
        this.playerBody.y += this.velocity.y * delta;
    }
    
    public void drawRec(SpriteBatch batch){
        if(this.currentState == State.ON_STAIRS){
            int footTileX = (this.facesRight) ? Math.round((this.playerBody.x + this.playerBody.width) - this.playerBody.width * (this.FOOT_SIZE.width / 100f)): 
                                                Math.round(this.playerBody.x + this.playerBody.width * (this.FOOT_SIZE.width / 100f)); 
            int footTileY = Math.round(this.playerBody.y + this.FOOT_SIZE.height / 100f);
            batch.draw(AssetsManager.assets.get("assets/img/square.png", Texture.class), footTileX, footTileY, 1, 1);
            return;
        }
        float x = (this.facesRight) ? (this.playerBody.x + this.playerBody.width) - this.playerBody.width * (this.FOOT_SIZE.width / 100f): this.playerBody.x + this.playerBody.width * (this.FOOT_SIZE.width / 100f);
        batch.draw(AssetsManager.assets.get("assets/img/square.png", Texture.class), x, this.playerBody.y, this.playerBody.width * (this.FOOT_SIZE.width / 8f / 100f), this.playerBody.height * (this.FOOT_SIZE.height / 100f));
//        batch.draw(AssetsManager.assets.get("assets/img/square.png", Texture.class), 40, 15, 1, 1);
    }
    
    public Rectangle getPlayerBody() {
        return playerBody;
    }
    
    public State getCurrentState() {
        return currentState;
    }

    public float getStateTime() {
        return stateTime;
    }
    
    public Vector2 getVelocity() {
        return velocity;
    }

    public boolean isFacingRight() {
        return facesRight;
    }

    public boolean isUpstairs() {
        return upstairs;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}
