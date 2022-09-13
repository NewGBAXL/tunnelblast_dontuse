package com.tunnelblast.Objects;

import android.graphics.Paint;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.tunnelblast.Coordinates;
import com.tunnelblast.Map;
import com.tunnelblast.Objects.GameObject;
import com.tunnelblast.databinding.ActivityUsercarBinding;

public class Car extends GameObject
{
    private AppBarConfiguration appBarConfiguration;
    private ActivityUsercarBinding binding;
    int bombs = 50;
    int blocksLeft = 50;
    private Paint skin;
    byte baseSpd; //planned feature (oil slick)
    byte pwrRate; //pwr regeneration rate
    byte spd;
    int power;
    int timer;
    byte lastPos = 0;

    public Car(Map game, int x, int y, Paint nSkin, byte nBaseSpd, byte nPwrRate)
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
        boolean returnBool = Map.cells[position.gridX()][position.gridY()].breakWall(cardinal, str);
        --bombs;
        return returnBool;
    }

    public boolean build(byte cardinal)
    {
        if (!isValidMove(cardinal))
            return false;

        boolean returnBool = Map.cells[position.gridX()][position.gridY()].buildWall(cardinal);
        --blocksLeft;
        return returnBool;
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
        return;
    }

    //planned feature
    /*public void oilSlick(int intensity){
        Map.cells[xPos][yPos].setSpd(intensity);
        return;
    }*/
}