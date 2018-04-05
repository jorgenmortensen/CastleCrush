package components;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class Button {

    private int Xpos;
    private int Ypos;
    private int btnWidth;
    private int btnHeight;
    private Sprite btn;

    public Button(int xpos, int ypos, int btnWidth, int btnHeight, Sprite btn) {
        this.Xpos = xpos;
        this.Ypos = ypos;
        this.btnWidth = btnWidth;
        this.btnHeight = btnHeight;
        this.btn = btn;
    }

    public int getXpos() {
        return Xpos;
    }

    public void setXpos(int xpos) {
        Xpos = xpos;
    }

    public int getYpos() {
        return Ypos;
    }

    public void setYpos(int ypos) {
        Ypos = ypos;
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
