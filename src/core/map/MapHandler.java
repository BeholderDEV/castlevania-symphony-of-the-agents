/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Augustop
 */
public class MapHandler {
    public enum Layer{
        GROUND, STAIR
    }
    
    private float unitScale;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
//    private final Pool<Rectangle> tilePool = Pools.get(Rectangle.class);
//    private final Array<Rectangle> tileArray = new Array<>();
    
    
    public MapHandler(String mapName, float unitScale) {
        this.unitScale = unitScale;
        map = new TmxMapLoader().load(mapName);
        mapRenderer = new OrthogonalTiledMapRenderer(map, this.unitScale);
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
    
    public String checkCollisionWithStairEntrance(int startX, int startY, int endX, int endY, boolean facesRight){

        TiledMapTileLayer tileLayer = (TiledMapTileLayer) this.map.getLayers().get("stair");
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if(tileLayer.getCell(x, y) != null){
                    if(facesRight && tileLayer.getCell(x + 1, y + 1) != null){
                        return "upstairs";
                    }
                    if(!facesRight && tileLayer.getCell(x - 1, y + 1) != null){
                        return "upstairs";
                    }
                    if(facesRight && tileLayer.getCell(x + 1, y - 1) != null){
                        return "downstairs";
                    }
                    if(!facesRight && tileLayer.getCell(x - 1, y - 1) != null){
                        return "downstairs";
                    }
                }
            }
        }
        return "No collision";
    }
    
    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }
    
    public void disposeMap(){
        this.map.dispose();
    }
    
}
