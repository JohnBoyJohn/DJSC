package mygame.environment;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.Caps;
import com.jme3.renderer.Renderer;
import com.jme3.texture.Texture;

public class Sky {
    public Texture setupSkyBox(Renderer renderer, AssetManager assetManager){
        Texture envMap;
        if (renderer.getCaps().contains(Caps.FloatTexture)){
            envMap = assetManager.loadTexture("Textures/Sky/St Peters/StPeters.hdr");
        }else{
            envMap = assetManager.loadTexture("Textures/Sky/St Peters/StPeters.jpg");
        }
        return envMap;
    }
}
