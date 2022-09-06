package com.tunnelblast;

import android.graphics.Paint;
import android.view.InputDevice;
import android.view.MotionEvent;

public class UserCar extends Car{
    //we might not even need this class
    int xPos = 0;
    int yPos = 0;
    Dpad dpad;
    //public boolean moving = false;
    UserCar(int x, int y, Paint nSkin, byte nBaseSpd, byte nPwrRate) {
        super(x, y, nSkin, nBaseSpd, nPwrRate);
        dpad = new Dpad();
    }

    //use power
    //timer
    //..

    private void processJoystickInput(MotionEvent event, int historyPos) {
        InputDevice inputDevice = event.getDevice();

    }

    /*public void onGenericMotionEvent(MotionEvent event) {

        // Check if this event if from a D-pad and process accordingly.
        if (Dpad.isDpadDevice(event)) {
            int press = dpad.getDirectionPressed(event);
            moveTo((byte) press);
            //moving = true;
        }
    }*/
        // Check if this event is from a joystick movement and process accordingly.
}