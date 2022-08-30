package com.tunnelblast;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.tunnelblast.databinding.ActivityUsercarBinding;

class Usercar
{
    private AppBarConfiguration appBarConfiguration;
    private ActivityUsercarBinding binding;
    int bombs = 0;
    int blocksLeft = 5;
    int skin; //not required
    byte baseSpd; //not required
    byte pwrRate;
    byte xPos; //todo: setup coordinate object
    byte yPos; //todo: setup coordinate object
    byte spd; //r
    int power;
    int timer;
    byte lastPos = 0;

    Usercar(int nSkin, int nLoc, byte nBaseSpd, byte nPwrRate)
    {
        power = 10;
        timer = 10;
        skin = nSkin;
        //loc = nLoc;
        baseSpd = nBaseSpd;
        pwrRate = nPwrRate;

    }

    public boolean destroy(byte cardinal)
    {
        //decrease wall integrity by 1
        --bombs;
        return true;
    }

    public boolean build(byte cardinal)
    {
        if (cardinal == lastPos)
            return false;

        if (cardinal == 0)
        {
            //return grid.buildWall();
            --blocksLeft;
        }
        if (cardinal == 1)
        {
            //return grid.buildWall();
            --blocksLeft;
        }
        if (cardinal == 2)
        {
            //return grid.buildWall();
            --blocksLeft;
        }
        if (cardinal == 3)
        {
            //return grid.buildWall();
            --blocksLeft;
        }
        return true;
    }

    public boolean isValidMove(byte dir)
    {
        //if no wall at .. and no block at ..
        return true;
    }

    public void moveTo(int newDir){
        /*
        //if newDir == 0, move, then
        lastPos = 2;
        //if newDir == 1, move, then
        lastPos = 3;
        //if newDir == 2, move, then
        lastPos = 0;
        //if newDir == 3, move, then
        lastPos = 1;

        if (lastPos == newDir) {
            //if dir is not specified
            if (isValidMove(getPos(), 2))
                dir = 2;
            else if (isValidMove(getPos(), 1))
                dir = 1;
            else if (isValidMove(getPos(), 3))
                dir = 3;
            else if (isValidMove(getPos(), 0))
                dir = 0;
        }
        if isValidMove(space, dir)
        {
            //setPos(..)
            return;
        }*/
        return;
    }

    //posToGrid();
    //todo: create 2D coordinate object !!
    //todo: create extending car objects
    //todo: create oil slick object?

    //the coordinate object will have two variables, x & y, and have accessor and mutator functions,
    //as well as functions that return the x & y on the grid array (slightly different) so that we
    //won't have to convert by hand every time
}