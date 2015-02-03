/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.environment;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;

public class Floor {
    private RigidBodyControl floor_phy;
    public Geometry setupFloor(AssetManager assetManager, BulletAppState bulletAppState){
        Material mat = assetManager.loadMaterial("Textures/Terrain/BrickWall/BrickWall.j3m");
        mat.getTextureParam("DiffuseMap").getTextureValue().setWrap(Texture.WrapMode.Repeat);
        mat.getTextureParam("NormalMap").getTextureValue().setWrap(Texture.WrapMode.Repeat);
        mat.getTextureParam("ParallaxMap").getTextureValue().setWrap(Texture.WrapMode.Repeat);
        
        Box floor = new Box(Vector3f.ZERO, 500, 1f, 500);
        TangentBinormalGenerator.generate(floor);
        floor.scaleTextureCoordinates(new Vector2f(50, 50));
        Geometry floorGeom = new Geometry("Floor", floor);
        floorGeom.setMaterial(mat);
        floorGeom.setShadowMode(RenderQueue.ShadowMode.Receive);
        floor_phy = new RigidBodyControl(0.0f);
        floorGeom.addControl(floor_phy);
        bulletAppState.getPhysicsSpace().add(floor_phy);
        return floorGeom;
    }
}
