package mygame.gamegui;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

public class GameGUI {
    
    AssetManager assetManager;
    private BitmapText UISpeed;
    
    public GameGUI(AssetManager assetManager){
        this.assetManager = assetManager;
    }
    
    public void init(BitmapFont guiFont){
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        UISpeed = new BitmapText(guiFont, false);
        UISpeed.setSize(guiFont.getCharSet().getRenderedSize());
        UISpeed.setLocalTranslation(300, UISpeed.getLineHeight(), 0);
    }
    
    public void initGUIImage(Node guiNode){
        guiNode.detachAllChildren();
        Picture pic = new Picture("HUD Picture");
        pic.setImage(assetManager, "Interface/GameGUI/gui800x600.png", true);
        pic.setWidth(800);
        pic.setHeight(600);
        pic.setPosition(0,0);
        guiNode.attachChild(pic);
    }
    
    public BitmapText getBitmap(){
        return UISpeed;
    }
}
