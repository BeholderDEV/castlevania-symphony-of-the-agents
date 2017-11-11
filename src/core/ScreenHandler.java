/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import core.screens.MainMenuScreen;

/**
 *
 * @author Augustop
 */
public class ScreenHandler extends Game{
    public SpriteBatch batch;
    public BitmapFont font;

    public void create() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
    }
}
