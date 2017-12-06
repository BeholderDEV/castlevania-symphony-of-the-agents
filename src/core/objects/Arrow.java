/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects;

import com.badlogic.gdx.math.Rectangle;
import core.actors.CollisionHandler;
import core.map.MapHandler;

/**
 *
 * @author Adson Esteves
 */
public class Arrow {
    public static final float ARROW_SPEED = 0.3f;
    public static final float DISTANCE_FROM_GROUND = 3f;
    private final Rectangle collisionBody;
    private boolean faceToRight;

    public Arrow(float positionX, float positionY, boolean faceToRight) {
        this.collisionBody = CollisionHandler.rectanglePool.obtain();
        this.faceToRight = faceToRight;
//        float arrowXAdjustment = (this.faceToRight) ? -5f: 5f;
        this.collisionBody.set(positionX, positionY, 33f * MapHandler.unitScale, 8f * MapHandler.unitScale);
        this.collisionBody.x = (this.faceToRight) ? this.collisionBody.x - this.collisionBody.width: this.collisionBody.x + this.collisionBody.width;
    }

    public Rectangle getCollisionBody() {
        return collisionBody;
    }

    public boolean isFaceToRight() {
        return faceToRight;
    }

    public void setFaceToRight(boolean faceToRight) {
        this.faceToRight = faceToRight;
    }
    
}
