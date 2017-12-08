/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import core.map.MapHandler;

/**
 *
 * @author Augustop
 */
public class CollisionHandler {
    public static final Pool<Rectangle> rectanglePool = Pools.get(Rectangle.class);
    private static final Rectangle collisionBodies[] = new Rectangle[2];
    
    public static Rectangle checkCollisionBetweenTwoActorsBodies(GameActor actor1, GameActor actor2){
        GameActor actor;
        Rectangle body;
        float x, w;
        for (int i = 0; i < collisionBodies.length; i++) {
            actor = (i % 2 == 0) ? actor1: actor2;
            body = actor.getBody();
            x = (actor.isFacingRight()) ? body.x + actor.getSpriteAdjustmentForCollision()[0]:
                   body.x + body.width - actor.getSpriteAdjustmentForCollision()[0];
            w = (actor.isFacingRight()) ? body.width + actor.getSpriteAdjustmentForCollision()[2]:
                   -body.width - actor.getSpriteAdjustmentForCollision()[2];
            collisionBodies[i].set(x, body.y + actor.getSpriteAdjustmentForCollision()[1], w, body.height + actor.getSpriteAdjustmentForCollision()[3]);
        }        
        if(collisionBodies[0].overlaps(collisionBodies[1])){
            return collisionBodies[1];
        }
        return null;
    }
    
    public static boolean checkCollisionBetweenBodyAndObject(GameActor actor, Rectangle object){
        Rectangle body = actor.getBody();
        float x = (actor.isFacingRight()) ? body.x + actor.getSpriteAdjustmentForCollision()[0]:
                   body.x + body.width - actor.getSpriteAdjustmentForCollision()[0];
        float w = (actor.isFacingRight()) ? body.width + actor.getSpriteAdjustmentForCollision()[2]:
                  -body.width - actor.getSpriteAdjustmentForCollision()[2];
        collisionBodies[0].set(x,
                body.y + actor.getSpriteAdjustmentForCollision()[1], 
                w, 
                body.height + actor.getSpriteAdjustmentForCollision()[3]);
        return collisionBodies[0].overlaps(object);
    }
    
    public static void checkGroundCollision(MapHandler map, GameActor actor){
        int startX = Math.round(actor.getBody().x), 
            startY = Math.round(actor.getBody().y), 
            endX = Math.round(actor.getBody().x + actor.getBody().width), 
            endY = Math.round(actor.getBody().y + actor.getBody().height * 0.01f);
        int centerX = Math.round((actor.getBody().x + actor.getBody().width / 2f ));
        if(map.checkLayerCollision(MapHandler.Layer.GROUND, centerX - 1, startY, centerX + 1, startY)){
            if(actor.getCurrentState() == GameActor.State.JUMPING || (actor.getCurrentState() == GameActor.State.ATTACKING && actor.getAtkState() == GameActor.Atk_State.JUMP_ATK)){
                if(actor.getVelocity().y > 0){
                    return;
                }
                if(actor.getCurrentState() != GameActor.State.HURTED){
                    actor.setCurrentState(GameActor.State.STANDING);
                }
                float newTileY = map.getUpermostGroundTile((actor.facingRight) ? endX: startX, startY);
                actor.getBody().y = newTileY + GameActor.DISTANCE_FROM_GROUND_LAYER;
            }
        }else if(actor.getCurrentState() != GameActor.State.JUMPING && actor.getCurrentState() != GameActor.State.ON_STAIRS && actor.getCurrentState() != GameActor.State.ATTACKING && actor.getCurrentState() != GameActor.State.HURTED){
            actor.setCurrentState(GameActor.State.JUMPING);
        }
    }
    
    public static boolean checkWallCollision(MapHandler map, GameActor actor, float delta){
        float x = actor.getBody().x + actor.getSpriteAdjustmentForCollision()[0];
        float y = actor.getBody().y + actor.getSpriteAdjustmentForCollision()[1];
        float w = actor.getBody().width + actor.getSpriteAdjustmentForCollision()[2];
        float h = actor.getBody().height + actor.getSpriteAdjustmentForCollision()[3];
        int startX = Math.round(x), 
            startY = Math.round(y + h * 0.05f), 
            endX = Math.round(x + w), 
            endY = Math.round(y + h * 0.95f);
//        int centerX = Math.round((x + w / 2f ));
        if(map.checkLayerCollision(MapHandler.Layer.WALL, startX, startY, endX, endY)){
            actor.velocity.x *= -1;
            float oldVelY = actor.velocity.y;
            actor.velocity.y = 0;
            actor.updatePosition(delta);
            actor.velocity.y = oldVelY;
            if(actor.getCurrentState() == GameActor.State.WALKING){
                actor.setCurrentState(GameActor.State.STANDING);
                actor.velocity.y = 0;
            }
            return true;
        }
//        if(map.checkLayerCollision(MapHandler.Layer.GROUND, startX, startY, endX, endY)){
//            if(actor.getCurrentState() == GameActor.State.WALKING){
//                actor.setCurrentState(GameActor.State.STANDING);
//                actor.velocity.y = 0;
//                actor.velocity.x *= -1;
//                actor.updatePosition(delta);
//            }
//        }
        return false;
    }
    
    
    public static void iniatilizeRectangles(){
        for (int i = 0; i < 2; i++) {
            collisionBodies[i] = new Rectangle(){
                @Override
                public boolean overlaps (Rectangle r) {
                    return Math.min(x, x + width) < Math.max(r.x, r.x + r.width) && Math.max(x, x + width) > Math.min(r.x, r.x + r.width) && Math.min(y, y + height) < Math.max(r.y, r.y + r.height) && Math.max(y, y + height) > Math.min(r.y, r.y + r.height);
                }
            };
        }
    }
}
