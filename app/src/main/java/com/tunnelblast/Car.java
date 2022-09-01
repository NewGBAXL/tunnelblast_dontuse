package com.tunnelblast;

import android.graphics.Paint;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.tunnelblast.databinding.ActivityUsercarBinding;

class Car
{
    private AppBarConfiguration appBarConfiguration;
    private ActivityUsercarBinding binding;
    int bombs = 0;
    int blocksLeft = 5;
    private Paint skin;
    byte baseSpd; //planned feature (oil slick)
    byte pwrRate; //pwr regeneration rate
    int xPos; //todo: setup coordinate object
    int yPos; //todo: setup coordinate object
    byte spd;
    int power;
    int timer;
    byte lastPos = 0;

    Car(int x, int y, Paint nSkin, byte nBaseSpd, byte nPwrRate)
    {
        power = 10;
        timer = 10;
        skin = nSkin;
        xPos = x;
        yPos = y;
        baseSpd = nBaseSpd;
        pwrRate = nPwrRate;
        bombs = 10;
        //color = ?? //is color separate from skin?
    }

    public boolean destroy(byte cardinal, int str)
    {
        Map.cells[xPos][yPos].breakWall(cardinal, str);
        --bombs;
        return true;
    }

    public boolean build(byte cardinal)
    {
        if (!isValidMove(cardinal))
            return false;

        Map.cells[xPos][yPos].buildWall(cardinal);
        --blocksLeft;
        return true;
    }

    public boolean isValidMove(byte dir)
    {
        if (lastPos == dir)
            return false;
        if (dir==0 && Map.cells[xPos][yPos].nWall == 0)
            return true;
        if (dir==1 && Map.cells[xPos][yPos].eWall == 0)
            return true;
        if (dir==2 && Map.cells[xPos][yPos].sWall == 0)
            return true;
        if (dir==3 && Map.cells[xPos][yPos].wWall == 0)
            return true;
        return false;
    }

    public void moveTo(byte newDir){
        //check all routes clockwise for valid move
        for (int counter = 0; !isValidMove(newDir) || counter < 4; ++counter){
            newDir = (byte)((newDir+1)%4); //cycles 0-3
        }

        //if is still invalid turn around or game over?
        //(if counter >=4)
        //Map.loseUI()?

        if (newDir == 0)
            --yPos;
        else if (newDir == 1)
            ++xPos;
        else if (newDir == 2)
            ++yPos;
        else if (newDir == 3)
            --xPos;
        lastPos = (byte)((newDir+2)%4); //update lastPos, cycles 0-3
        return;
    }

    /*void drawPosition(){
        //should this be done by grid?
        return;
    }*/

    //planned feature
    /*public void oilSlick(int intensity){
        Map.cells[xPos][yPos].setSpd(intensity);
        return;
    }*/

    //todo: create 2D coordinate object ??
    //todo: create extending car objects

    //note: cell object makes the following unnecessary
    //the coordinate object will have two variables, x & y, and have accessor and mutator functions,
    //as well as functions that return the x & y on the grid array (slightly different) so that we
    //won't have to convert by hand every time
}