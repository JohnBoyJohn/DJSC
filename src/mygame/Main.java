package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapText;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import java.util.ArrayList;

import mygame.ships.Enemy;
import mygame.controls.KeyboardInput;
import mygame.environment.Sky;
import mygame.environment.Floor;
import mygame.gamegui.GameGUI;
import mygame.shootables.Laser;
import mygame.effects.ShipJetAnimation;

public class Main extends SimpleApplication {

    protected Node player;
    protected Node enemy;
    public Spatial enemy_x;
    public Enemy enemy1;
    GameGUI gui;
    BitmapText UISpeed;
    boolean isRunning = true;
    float ship_speed = 0f;
    
    CameraNode camNode;
    Vector3f camDelta;
    
    private BulletAppState bulletAppState;
    private ArrayList<Laser> laserbeams;
    private ShipJetAnimation shipJetAni;
    private ParticleEmitter shipJetParticleEmitter;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        laserbeams = new ArrayList<Laser>();
        
        new KeyboardInput().initKeys(inputManager, actionListener, analogListener);
        
        Texture skytex = new Sky().setupSkyBox(renderer, assetManager);
        rootNode.attachChild(SkyFactory.createSky(assetManager, skytex, new Vector3f(-1,-1,-1), true));
        
        Geometry floorGeom = new Floor().setupFloor(assetManager, bulletAppState);
        rootNode.attachChild(floorGeom);
              
        //br jaeger
        player = (Node) assetManager.loadModel("Models/br_jaeger/br_jaeger.mesh.xml");
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setTexture("ColorMap", assetManager.loadTexture("Textures/br_jaeger.png"));
        
        //kl jaeger
        //player = (Node) assetManager.loadModel("Models/kl_jaeger/kl_jaeger.mesh.xml");
        //Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat1.setTexture("ColorMap", assetManager.loadTexture("Textures/kl_jaeger_.png"));
        
        //box
        //player = (Node) assetManager.loadModel("Models/box/box.mesh.xml");
        //Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat1.setTexture("ColorMap", assetManager.loadTexture("Textures/box.png"));
        
        //enemy without enemy class
        enemy = (Node) assetManager.loadModel("Models/br_jaeger/br_jaeger.mesh.xml");
        Material mat_en = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_en.setTexture("ColorMap", assetManager.loadTexture("Textures/br_jaeger.png"));
        enemy.setMaterial(mat_en);
        enemy.scale(0.5f);
        enemy.setLocalTranslation(100, 5, 100);
        enemy.rotate(0, -135, 0);
        
        //enemy with enemy class - non-functional yet
        enemy1 = new Enemy();
        enemy1.setXYZ(15,5,15);
        enemy1.setEnemyVectorFromXYZ();
        enemy1.setTexture(assetManager);
        enemy_x = enemy1.getNode();
        rootNode.attachChild(enemy_x);
                
        player.setMaterial(mat1);
        player.scale(0.5f);
        player.setLocalTranslation(0,5,0);
        
        shipJetAni = new ShipJetAnimation();
        shipJetParticleEmitter = shipJetAni.initShipJetAnimation(assetManager);
        player.attachChild(shipJetParticleEmitter);
 
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);

        rootNode.attachChild(player);
        rootNode.attachChild(enemy);
        
        camDelta = new Vector3f(0f, 0f, -0.00001f);
        setCamera();
        
        guiNode.detachAllChildren();
        gui = new GameGUI(assetManager);
        gui.init(guiFont);
        UISpeed = gui.getBitmap();
        gui.initGUIImage(guiNode);
        guiNode.attachChild(UISpeed);
        
        
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf){
            if(name.equals("Pause") && !keyPressed){
                isRunning = !isRunning;
            }
            if(name.equals("Shoot") && !keyPressed){
                Laser laser = new Laser();
                laser.initMaterial(assetManager);
                laser.initSphere();
                laser.makeCannonBall(rootNode, bulletAppState, cam);
                laserbeams.add(laser);
            }
            if(ship_speed > 0){
                shipJetParticleEmitter.setNumParticles((int) ship_speed);
            }else{
                shipJetParticleEmitter.setNumParticles(0);
            }
        }
    };
    
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf){
            if(isRunning){
                if(name.equals("AccUp")){ ship_speed += value*speed; }
                if(name.equals("AccDown")){ ship_speed -= value*speed; }
                if(name.equals("CamRight")){ player.rotate(0, -value*speed, 0); }
                if(name.equals("CamLeft")){ player.rotate(0, +value*speed, 0); }
                if(name.equals("CamUp")){ player.rotate(-value*speed, 0, 0); }
                if(name.equals("CamDown")){ player.rotate(+value*speed, 0, 0); }
                if(name.equals("CamRollL")){ player.rotate(0, 0, -value*speed); }
                if(name.equals("CamRollR")){ player.rotate(0, 0, +value*speed); }
                if(name.equals("View1")){ 
                    camDelta = new Vector3f(0, 0, -20);
                    camNode.setLocalTranslation(camDelta);
                    camNode.lookAt(player.getLocalTranslation(), Vector3f.UNIT_Y);
                    guiNode.detachAllChildren();
                }
                if(name.equals("View2")){
                    camDelta = new Vector3f(3, -10, 3);
                    camNode.setLocalTranslation(camDelta);
                    camNode.lookAt(player.getLocalTranslation(), Vector3f.UNIT_Y);
                    guiNode.detachAllChildren();
                }
                if(name.equals("View3")){
                    camDelta = new Vector3f(-5, 10, -5);
                    camNode.setLocalTranslation(camDelta);
                    camNode.lookAt(player.getLocalTranslation(), Vector3f.UNIT_Y);
                    guiNode.detachAllChildren();
                }
                if(name.equals("View4")){
                    camDelta = new Vector3f(0f, 0f, -0.00001f);
                    camNode.setLocalTranslation(camDelta);
                    camNode.lookAt(player.getLocalTranslation(), Vector3f.UNIT_Y);
                    gui.initGUIImage(guiNode);
                }
            }else{
                System.out.println("Press P to unpause.");
            }
        }
    };
    
    private void setCamera(){
        flyCam.setEnabled(false);
        camNode = new CameraNode("Camera Node", cam);
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        player.attachChild(camNode);
        camNode.setLocalTranslation(camDelta);
        camNode.lookAt(player.getLocalTranslation(), Vector3f.UNIT_Y);
    }
    
    @Override
    public void simpleUpdate(float tpf){
        Vector3f v = player.getLocalRotation().getRotationColumn(2);
        v = v.mult(tpf).mult(ship_speed);
        player.move(v);
        shipJetParticleEmitter.move(v);
                
        Vector3f v_en = enemy.getLocalRotation().getRotationColumn(2);
        enemy.move(v_en.mult(tpf).mult(2f));
                
        UISpeed.setText(String.valueOf(ship_speed));
    }
}