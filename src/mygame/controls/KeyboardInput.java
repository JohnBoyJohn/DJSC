package mygame.controls;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

public class KeyboardInput {
    public void initKeys(InputManager inputManager, ActionListener actionListener, AnalogListener analogListener){
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("AccUp", new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping("AccDown", new KeyTrigger(KeyInput.KEY_Y));
        
        inputManager.addMapping("CamUp", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CamLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CamDown", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("CamRight", new KeyTrigger(KeyInput.KEY_D));
        
        inputManager.addMapping("CamRollL", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("CamRollR", new KeyTrigger(KeyInput.KEY_E));
        
        inputManager.addMapping("View1", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("View2", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("View3", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("View4", new KeyTrigger(KeyInput.KEY_4));
        
        inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        
        inputManager.addListener(actionListener, "Pause", "Shoot");
        inputManager.addListener(analogListener, "CamUp", "CamDown", "CamLeft", "CamRight", "CamRollL", "CamRollR", "AccUp", "AccDown", "View1", "View2", "View3", "View4");
    }
}
