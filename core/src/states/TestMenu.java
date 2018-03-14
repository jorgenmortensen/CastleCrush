package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by erikkjernlie on 13/03/2018.
 */

public class TestMenu extends states.menuStates.SuperMenu {
    private Skin skin;
    private Stage stage;
    private TextButton removeButton;
    private TextButton singlePlayer;
    private TextButton localMultiplayer;
    private TextButton onlineMultiplayer;


    public TestMenu(GameStateManager gsm) {
        super(gsm);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);// Make the stage consume events
        createBasicSkin();
        removeButton = new TextButton("Fjern denne knappen", skin);
        removeButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight() - 2*removeButton.getHeight());
        stage.addActor(removeButton);
        singlePlayer = new TextButton("Play singleplayer", skin);
        singlePlayer.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight() -  4*removeButton.getHeight());
        stage.addActor(singlePlayer);
        localMultiplayer = new TextButton("Play local multiplayer", skin); // Use the initialized skin
        localMultiplayer.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight() - 6*removeButton.getHeight());
        stage.addActor(localMultiplayer);
        onlineMultiplayer = new TextButton("Play online multiplayer", skin);
        onlineMultiplayer.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight() -  8*removeButton.getHeight());
        stage.addActor(onlineMultiplayer);

    }

    public void render(float delta) {
        Gdx.gl.glClearColor((float) 0.223,(float) 0.098,(float) 0.439, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    private void createBasicSkin(){
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    }

    @Override
    protected void handleInput() {
        if (removeButton.isPressed()){
            // Gdx.input.setInputProcessor(stage);
            //gsm.set(new TestMenu(gsm));
            System.out.println("Pressed....");
            gsm.set(new TestScreen(gsm));
            Gdx.input.setInputProcessor(stage);
            dispose();

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        render(dt);
    }

    @Override
    public void render(SpriteBatch sb) {

    }


    @Override
    public void dispose() {

    }

}
