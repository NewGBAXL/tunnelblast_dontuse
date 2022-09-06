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

import com.tunnelblast.Objects.GameObject;
import com.tunnelblast.databinding.ActivityUsercarBinding;

class Car extends GameObject
{
    private AppBarConfiguration appBarConfiguration;
    private ActivityUsercarBinding binding;
    int bombs = 100;
    int blocksLeft = 100;
    private Paint skin;
    byte baseSpd; //planned feature (oil slick)
    byte pwrRate; //pwr regeneration rate
    byte spd;
    int power;
    int timer;
    byte lastPos = 0;

    Car(Map game, int x, int y, Paint nSkin, byte nBaseSpd, byte nPwrRate)
    {
        super(game);
        power = 10;
        timer = 10;
        skin = nSkin;
        position = new Coordinates(x, y);
        baseSpd = nBaseSpd;
        pwrRate = nPwrRate;
        bombs = 10;
        //color = ?? //is color separate from skin?
    }

    public boolean destroy(byte cardinal, int str)
    {
        Map.cells[position.gridX()][position.gridY()].breakWall(cardinal, str);
        --bombs;
        return true;
    }

    public boolean build(byte cardinal)
    {
        if (!isValidMove(cardinal))
            return false;

        Map.cells[position.gridX()][position.gridY()].buildWall(cardinal);
        --blocksLeft;
        return true;
    }

    public boolean isValidMove(byte dir)
    {
        if (lastPos == dir)
            return false;
        if (dir==0 && Map.cells[position.gridX()][position.gridY()].nWall == 0)
            return true;
        if (dir==1 && Map.cells[position.gridX()][position.gridY()].eWall == 0)
            return true;
        if (dir==2 && Map.cells[position.gridX()][position.gridY()].sWall == 0)
            return true;
        if (dir==3 && Map.cells[position.gridX()][position.gridY()].wWall == 0)
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
            --position.Y;
        else if (newDir == 1)
            ++position.X;
        else if (newDir == 2)
            ++position.Y;
        else if (newDir == 3)
            --position.X;
        lastPos = (byte)((newDir+2)%4); //update lastPos, cycles 0-3
        Map.inv = true;
        //Map.updateCar(this);//update map
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