package com.tunnelblast;

import android.graphics.Paint;
import android.view.InputDevice;
import android.view.MotionEvent;

public class UserCar extends Car{
    int xPos = 0;
    int yPos = 0;

    UserCar(int x, int y, Paint nSkin, byte nBaseSpd, byte nPwrRate) {
        super(x, y, nSkin, nBaseSpd, nPwrRate);
    }

    //use power
    //timer
    //..

    private void processJoystickInput(MotionEvent event, int historyPos) {
        InputDevice inputDevice = event.getDevice();

    }

    Dpad dpad = new Dpad();

    //should work - 8/31/22
    public void onGenericMotionEvent(MotionEvent event) {

        // Check if this event if from a D-pad and process accordingly.
        if (Dpad.isDpadDevice(event)) {
            int press = dpad.getDirectionPressed(event);
            moveTo((byte) press);
        }
    }
        // Check if this event is from a joystick movement and process accordingly.
}