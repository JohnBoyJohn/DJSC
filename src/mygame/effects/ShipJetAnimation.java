package mygame.effects;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;

public class ShipJetAnimation {
    
    private ParticleEmitter emit;
        
    public ParticleEmitter initShipJetAnimation(AssetManager assetManager){
        emit = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 300);
        emit.setGravity(0, 0, 0);
        emit.setVelocityVariation(1);
        emit.setLowLife(1);
        emit.setHighLife(1);
        emit.setInitialVelocity(new Vector3f(0, .5f, 0));
        emit.setImagesX(15);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Smoke/Smoke.png"));
        emit.setMaterial(mat);
        emit.setLocalTranslation(new Vector3f(0f, 0f, -0.5f));
        return emit;    
    }
}
