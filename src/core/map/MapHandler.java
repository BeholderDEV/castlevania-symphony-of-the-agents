/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

/**
 *
 * @author Augustop
 */
public class MapHandler {
    public enum Layer{
        ground, stair
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

    public void testCollision(Rectangle player, OrthographicCamera cam){
        
        System.out.println(this.checkLayerCollision(Layer.ground, Math.round(player.x), Math.round(player.y), Math.round(player.x + player.width), Math.round(player.y + player.height)));
//        for (Rectangle tile : tileArray) {
//            if(tile.overlaps(player.o))
//        }
//        if(rectobj.getRectangle().overlaps(player)){
//            System.out.println("Ayy");
//            return;
//        }
//        System.out.println("lmao");
        
//        ShapeRenderer a = new ShapeRenderer();
//        a.begin(ShapeRenderer.ShapeType.Filled);
//        a.rect(player.x, player.y, 100, 100);
//        a.end();
//        a.dispose();
//        System.out.println(rectobj.getRectangle().width);
//        System.out.println(rectobj.getRectangle().height) ;
//        System.out.println(player.x);
//        System.out.println(player.y);
    }
    
    public boolean checkLayerCollision(Layer layer, int startX, int startY, int endX, int endY){
        TiledMapTileLayer ground = (TiledMapTileLayer) this.map.getLayers().get(layer.name());
//        this.tilePool.freeAll(this.tileArray);
//        this.tileArray.clear();
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if(ground.getCell(x, y) != null){
                    return true;
                }
            }
        }
        return false;
    }

    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }
}
