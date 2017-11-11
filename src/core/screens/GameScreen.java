/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import core.ScreenHandler;

/**
 *
 * @author Augustop
 */
public class GameScreen implements Screen {
    private final ScreenHandler game;
    private float unitScale = 1 / 32f; // tile size
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private OrthographicCamera camera;

    public GameScreen(final ScreenHandler game) {
        this.game = game;

        // load the drop sound effect and the rain background "music"
        
//        rainMusic.setLooping(true);
//
//        this.map = new TmxMapLoader().load("assets/map/testMapSet.tmx");
//        this.mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        
        this.camera = new OrthographicCamera();
        
        
//        this.camera.setToOrtho(false, 30, 20);
        
        
        this.camera.update();
        
        
//        this.map = new TmxMapLoader(new ExternalFileHandleResolver()).load("assets/map/testMapSet.tmx");
//        // create a Rectangle to logically represent the bucket
//        bucket = new Rectangle();
//        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
//        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
//                                        // the bottom screen edge
//        bucket.width = 64;
//        bucket.height = 64;

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.game.batch.setProjectionMatrix(camera.combined);
        
//        this.mapRenderer.setView(camera);
//        this.mapRenderer.render();
//        this.game.batch.begin();
//        this.mapRenderer.renderTileLayer((TiledMapTileLayer)this.map.getLayers().get(0));
//        this.game.batch.end();
        
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
//        this.rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
//        this.dropImage.dispose();
//        this.bucketImage.dispose();
//        this.dropSound.dispose();
//        this.rainMusic.dispose();
        this.map.dispose();
        this.mapRenderer.dispose();
        this.game.dispose();
    }

}