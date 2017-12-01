/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.actors.enemies;

import ai.AgentCreator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import core.util.AssetsManager;
import core.actors.GameActor;
import core.map.MapHandler;
import jade.core.Agent;

/**
 *
 * @author Augustop
 */
public class SwordSkeleton extends Enemy{
        
    public SwordSkeleton(int walkingSpeed, Rectangle body, MapHandler map, Array<GameActor> actors) {
        super(walkingSpeed, body, map, actors);
        super.standImg = standImg = new TextureRegion(AssetsManager.assets.get("assets/img/superIV_Enemies.png", Texture.class), 312, 143, 30, 49);
        this.spriteAdjustmentForCollision = new float[]{0.4f, 0.4f, 1.6f, 0.9f};
        AgentCreator.getInstance().createAgent(AgentCreator.AgentType.SKELETON_SWORD, new Object[]{this});
    }

    @Override
    public void updateActor(float deltaTime, MapHandler map, Array<GameActor> stageActors) {
        super.stateTime += deltaTime;
//        System.out.println("Vel " + super.velocity.x);
//        if(super.currentState == State.HURTED){
//            super.updateHurtedStatus(deltaTime);
//        }
    }
    
    @Override
    public void renderActor(SpriteBatch batch) {
        if(this.currentState == State.HURTED && super.blinkPeriod >= Enemy.BLINK_INTERVAL){
            super.blinkPeriod = 0;
            return;
        }
        batch.draw(super.standImg, (super.facingRight) ? super.body.x: super.body.x + super.body.width, 
                super.body.y, 
                (super.facingRight) ? super.body.width: -super.body.width, 
                super.body.height);
    }
}
