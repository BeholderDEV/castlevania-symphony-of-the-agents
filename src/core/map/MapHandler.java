/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Augustop
 */
public class MapHandler {
    private float unitScale;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    
    
    public MapHandler(String mapName, float unitScale) {
        this.unitScale = unitScale;
        map = new TmxMapLoader().load(mapName);
        mapRenderer = new OrthogonalTiledMapRenderer(map, this.unitScale);
    }

    public void testCollision(Rectangle player, OrthographicCamera cam){
        RectangleMapObject rectobj = (RectangleMapObject) map.getLayers().get("controlObjects").getObjects().get(0); // get by name later
//        if(rectobj.getRectangle().overlaps(player)){
//            System.out.println("Ayy");
//            return;
//        }
//        System.out.println("lmao");
        
        ShapeRenderer a = new ShapeRenderer();
        a.begin(ShapeRenderer.ShapeType.Filled);
//        a.rect(rectobj.getRectangle().x*unitScale, rectobj.getRectangle().y*unitScale, rectobj.getRectangle().width*unitScale, rectobj.getRectangle().height*unitScale);
//        a.rect(rectobj.getRectangle().x, rectobj.getRectangle().y, rectobj.getRectangle().width / unitScale, rectobj.getRectangle().height);
        a.end();
        a.dispose();
//        System.out.println(rectobj.getRectangle().width * unitScale);
        System.out.println(rectobj.getRectangle().width);
        System.out.println(rectobj.getRectangle().height) ;
        System.out.println(rectobj.getRectangle().x);
        System.out.println(rectobj.getRectangle().y);
        
    }

    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }
}
