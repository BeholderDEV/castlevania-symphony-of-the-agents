/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.actors.enemies;

import ai.AgentCreator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import core.util.AssetsManager;
import core.actors.GameActor;
import core.actors.player.PlayerAnimation;
import core.map.MapHandler;
import core.screens.GameScreen;
import core.util.AnimationManager;

/**
 *
 * @author Augustop
 */
public class SwordSkeleton extends Enemy{
        
    public SwordSkeleton(int walkingSpeed, Rectangle body, GameScreen gameScreen) {
        super(walkingSpeed, body, gameScreen);
        super.standImg = standImg = new TextureRegion(AssetsManager.assets.get("assets/img/superIV_Enemies.png", Texture.class), 312, 143, 30, 49);
        super.movingAnimation = AnimationManager.generateAnimation(new TextureRegion(AssetsManager.assets.get("assets/img/superIV_Enemies.png", Texture.class), 312, 143, 58, 51), 29, 51, Animation.PlayMode.LOOP, 0.40f);
        super.atkAnimation = AnimationManager.generateAnimation(AssetsManager.assets.get("assets/img/superIV_Enemies.png", Texture.class),  new int[]{276, 250, 188}, new int[]{143, 124, 143}, new int[]{30, 26, 62}, new int[]{51, 69, 50}, Animation.PlayMode.NORMAL, PlayerAnimation.STANDARD_ATK_FRAME_TIME);
        this.spriteAdjustmentForCollision = new float[]{0.4f, 0.4f, 1.6f, 0.9f};
        AgentCreator.getInstance().createAgent(AgentCreator.AgentType.SKELETON_SWORD, new Object[]{this});
    }

    @Override
    public void updateActor(float deltaTime, MapHandler map, Array<GameActor> stageActors) {
        super.stateTime += deltaTime;
    }
    
    @Override
    public void renderActor(SpriteBatch batch) {
        if(super.blinking && super.blinkPeriod >= Enemy.BLINK_INTERVAL){
            super.blinkPeriod = 0;
            return;
        }
        if(super.body.x < 0){
            super.body.setX(0);
        }
        TextureRegion currentFrame = this.getCurrentFrame();
        float[] renderValues = super.getSpriteRenderValues(currentFrame);
        batch.draw(currentFrame, renderValues[0], renderValues[1], renderValues[2], renderValues[3]);
    }

    @Override
    protected void adjustRenderCorrections(TextureRegion currentFrame) {
        super.renderCorrection.x = -1f;
    }
}
