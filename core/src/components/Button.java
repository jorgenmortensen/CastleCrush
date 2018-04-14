package components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class Button {

    protected Vector3 position;
    private int btnWidth;
    private int btnHeight;
    private Sprite btn;

    public Button(float xpos, float ypos, int btnWidth, int btnHeight, Sprite btn) {
        position = new Vector3(xpos,ypos,0);
        this.btnWidth = btnWidth;
        this.btnHeight = btnHeight;
        this.btn = btn;
    }

    public float getXpos() {
        return position.x;
    }

    public void setXpos(float xpos) {
        position.x = xpos;
    }

    public float getYpos() {
        return position.y;
    }

    public void setYpos(float ypos) {
        position.y = ypos;
    }

    public int getBtnWidth() {
        return btnWidth;
    }

    public void setBtnWidth(int btnWidth) {
        this.btnWidth = btnWidth;
    }

    public int getBtnHeight() {
        return btnHeight;
    }

    public void setBtnHeight(int btnHeight) {
        this.btnHeight = btnHeight;
    }

    public Sprite getBtn() {
        return btn;
    }

    public void setBtn(Sprite btn) {
        this.btn = btn;
    }


}
