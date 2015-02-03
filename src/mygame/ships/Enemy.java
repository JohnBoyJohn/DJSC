package mygame.ships;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Enemy extends Node{
        
    public Spatial enemy;
    public float x, y, z;
    public Vector3f look;
    public float max_speed;
    public float defense;
    public float attack;
    public float shield;
    public float enemy_speed;
    public Material mat_en;
    
    public Enemy(){
        max_speed = 10;
        defense = 10;
        attack = 10;
        shield = 10;
        enemy_speed = 2f;
        enemy = new Node();
        look = new Vector3f(5,5,5);
    }
    
    
    public void setX(float val){ x = val; }
    public float getX(){return x;}
    public void setY(float val){ y = val; }
    public float getY(){return y;}
    public void setZ(float val){ z = val; }
    public float getZ(){return z;}
    public void setXYZ(float x, float y, float z){this.x = x; this.y = y; this.z = z;}
    
    public void setEnemyVector(Vector3f v){look = v;}
    public void setEnemyVectorFromXYZ(){
        look = new Vector3f(x, y, z);
    }
    
    
    public void moveShip(float tpf){
        Vector3f v_en = enemy.getLocalRotation().getRotationColumn(2);
        setEnemyVector(v_en.mult(tpf).mult(5f));
    }
    
    public void setTexture(AssetManager assetManager){
        mat_en = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_en.setTexture("ColorMap", assetManager.loadTexture("Textures/br_jaeger.png"));
        enemy.setMaterial(mat_en);
    }
    
    public Spatial getNode(){
        return enemy;
    }
    
    @Override
    public void setLocalTranslation(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
