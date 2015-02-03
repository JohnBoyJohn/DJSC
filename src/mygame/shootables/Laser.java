package mygame.shootables;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

public class Laser {
    Material laser_mat;
    RigidBodyControl laser_phy;
    Sphere sphere;
    
    public void initMaterial(AssetManager assetManager){
        laser_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key = new TextureKey("Textures/Terrain/BrickWall/BrickWall.jpg");
        key.setGenerateMips(true);
        Texture tex = assetManager.loadTexture(key);
        laser_mat.setTexture("ColorMap", tex);
    }
    
    public void initSphere(){
        sphere = new Sphere(32, 32, 0.4f, true, false);
        sphere.setTextureMode(Sphere.TextureMode.Projected);
    }
    
    public void makeCannonBall(Node rootNode, BulletAppState bulletAppState, Camera cam){
        Geometry ball_geo = new Geometry("laser beam", sphere);
        ball_geo.setMaterial(laser_mat);
        rootNode.attachChild(ball_geo);
        ball_geo.setLocalTranslation(cam.getLocation());
        laser_phy = new RigidBodyControl(0.0f);
        ball_geo.addControl(laser_phy);
        bulletAppState.getPhysicsSpace().add(laser_phy);
        laser_phy.setLinearVelocity(cam.getDirection().mult(25));
    }
}
