package com.tunnelblast;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.tunnelblast.databinding.ActivityUsercarBinding;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class Map extends View
{
    int rows = 20;
    int cols = 20;
    public int[][] array = new int[rows][cols];

    public Map (Context context, @Nullable AttributeSet attrs){
        super (context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.GREEN);
    }

    /*Map(int nRows, int nCols)
    {
        rows = nRows * 2 + 1;
        cols = nCols + 1;
        array = new int[rows][cols];

        //sets first & last rows to edge property
        for (int i = 0; i < cols; ++i)
        {
            array[0][i] = -1;
            array[rows - 1][i] = -1;
        }
        //sets first & last cols to edge property
        for (int i = 0; i < rows; ++i)
        {
            array[i][0] = -1;
            if (i % 2 == 0){
                array[i][cols - 2] = -1;
                array[i][cols - 1] = -2;
            }
            else
                array[i][0] = -1;
        }
    }*/

    boolean buildWall(int x, int y) {
        if (array[x][y] == 0) //if no wall exists
        {
            array[x][y] = 1;
            return true;
        }
        return false;
    }

    boolean buildWall(int x, int y, int strength)
    {
        if (array[x][y] == 0) //if no wall exists
        {
            array[x][y] = strength;
            return true;
        }
        return false;
    }

    void breakWall(int x, int y)
    {
        if (array[x][y] > 0)
            --array[x][y];
    }

    //if edge, set to -1
    //if no wall, set to 0
    //if wall has strength n, set to n

    //-----
    //||||||
    //-----
    //||||||
    //-----

    //following nums are all negative
    //sample 5x5 array
    //111112
    //100001 row 1
    //100012
    //100001 row 2
    //100012
    //100001 row 3
    //100012
    //100001 row 4
    //100012
    //100001 row 5
    //111112

}