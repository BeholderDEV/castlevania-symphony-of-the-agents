/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import java.awt.Dimension;

/**
 *
 * @author Augustop
 */
public class MapHandler {
    
    public enum Layer{
        GROUND, STAIR
    }
    
    public static final float unitScale = 1 / 8f;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private final Pool<Rectangle> rectanglePool = Pools.get(Rectangle.class);
    private int mapWidth;
    private int mapHeight;
    
    public MapHandler(String mapName) {
        
        this.map = new TmxMapLoader().load(mapName);
        this.mapWidth = this.map.getProperties().get("width", Integer.class);
        this.mapHeight = this.map.getProperties().get("height", Integer.class);
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        this.prepareMapObjects();
    }
    
    private void prepareMapObjects(){
        MapObjects objects = this.map.getLayers().get("objects").getObjects();
        Rectangle rectObject = null;
        for (MapObject object : objects) {
            if(!(object instanceof RectangleMapObject)){
                continue;
            }
            rectObject = ((RectangleMapObject)object).getRectangle();
            rectObject.x *= unitScale; 
            rectObject.y *= unitScale; 
            rectObject.width *= unitScale; 
            rectObject.height *= unitScale;
        }
    }
    
    public boolean checkLayerCollision(Layer layer, int startX, int startY, int endX, int endY){
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) this.map.getLayers().get(layer.name().toLowerCase());
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if(tileLayer.getCell(x, y) != null){
                    return true;
                }
            }
        }
        return false;
    }
    
    public Vector2 getCloseTileFromLayer(Layer layer, int tileX, int tileY, boolean upstairs, boolean facesRight, Dimension layerDistance){
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) this.map.getLayers().get(layer.name().toLowerCase());
        if(upstairs && facesRight && tileLayer.getCell(tileX + layerDistance.width,  tileY + layerDistance.height) != null){
            return new Vector2(tileX + layerDistance.width,  tileY + layerDistance.height);
        }
        if(upstairs && !facesRight && tileLayer.getCell(tileX - layerDistance.width,  tileY + layerDistance.height) != null){
            return new Vector2(tileX - layerDistance.width,  tileY + layerDistance.height);
        }
        if(!upstairs && facesRight && tileLayer.getCell(tileX + layerDistance.width,  tileY - layerDistance.height) != null){
            return new Vector2(tileX + layerDistance.width,  tileY - layerDistance.height);
        }
        if(!upstairs && !facesRight && tileLayer.getCell(tileX - layerDistance.width,  tileY - layerDistance.height) != null){
            return new Vector2(tileX - layerDistance.width,  tileY - layerDistance.height);
        }
        return null;
    }
        
    public Rectangle checkCollisionWithStairBoundary(float x, float y, float width, float height){
        MapObjects objects = this.map.getLayers().get("objects").getObjects();
        Rectangle objectToCollide = this.rectanglePool.obtain();
        objectToCollide.set(x, y, width, height);
        for (MapObject object : objects) {
            if(object.getName().equals("stairBoundary")){
                if(((RectangleMapObject)object).getRectangle().overlaps(objectToCollide)){
                    this.rectanglePool.free(objectToCollide);
                    return ((RectangleMapObject)object).getRectangle();
                }
            }            
        }
        this.rectanglePool.free(objectToCollide);
        return null;
    }
    
    public String checkStairsDirection(int startX, int startY, int endX, int endY){
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) this.map.getLayers().get("stair");
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if(tileLayer.getCell(x, y) != null){
                    if(tileLayer.getCell(x + 1, y + 1) != null){
                        return "rightUp";
                    }
                    if(tileLayer.getCell(x - 1, y + 1) != null){
                        return "leftUp";
                    }
                    if(tileLayer.getCell(x + 1, y - 1) != null){
                        return "rightDown";
                    }
                    if(tileLayer.getCell(x - 1, y - 1) != null){
                        return "leftDown";
                    }
                }
            }
        }
        return "Failed";
    }
    
    public int getUpermostGroundTile(int tileX, int tileY){
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) this.map.getLayers().get("ground");
        int i = 1;
        while(true){
            if(tileLayer.getCell(tileX, tileY + i) == null){
                return tileY + i - 1;
            }
            i++;
        }
    }
    
    public boolean checkValidLayerMove(Layer layer, int tileX, int tileY){
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) this.map.getLayers().get(layer.name().toLowerCase());
        return tileLayer.getCell(tileX ,  tileY) != null;
    }
    
    public void renderMapObjects(SpriteBatch batch, Texture objectTexture){
        MapObjects objects = this.map.getLayers().get("objects").getObjects();
        Rectangle rectObject;
        for (MapObject object : objects) {
            if(!(object instanceof RectangleMapObject)){
                continue;
            }
            rectObject = ((RectangleMapObject)object).getRectangle();
            batch.draw(objectTexture, rectObject.x, rectObject.y, rectObject.width, rectObject.height);
        }
    }
    
    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }
    
    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }
    
    public void disposeMap(){
        this.map.dispose();
    }

}
