package com.tunnelblast;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.tunnelblast.Objects.GameObject;
import com.tunnelblast.databinding.ActivityUsercarBinding;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class Map extends View
{
    int rows = 15;
    int cols = 15;
    public int[][] array = new int[rows][cols];
    private float cellSize, hMargin, vMargin;
    private Paint wallPaint;
    private Paint wallPaintGhost;
    private static final float WALL_THICKNESS = 4;

    GameObject testObj;

    public static Context context;

    public Map (Context context, @Nullable AttributeSet attrs){ super (context, attrs);
        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(WALL_THICKNESS);
        wallPaintGhost = new Paint();
        wallPaintGhost.setColor(Color.BLUE);
        wallPaintGhost.setStrokeWidth(WALL_THICKNESS);

        //rows = nRows * 2 + 1;
        //cols = nCols + 1;
        array = new int[rows][cols];

        //sets first & last rows to edge property
        for (int i = 0; i < cols; ++i)
        {
            array[0][i] = -1;
            array[rows - 1][i] = -1;
        }
        //sets first & last cols to edge property
        for (int i = 0; i < rows; ++i) {
            array[i][0] = -1;
            if (i % 2 == 0) {
                array[i][cols - 2] = -1;
                array[i][cols - 1] = -2;
            } else
                array[i][0] = -1;


        }

        Map.context = context;

        testObj = new GameObject();
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.GREEN);
        int width = getWidth();
        int height  = getHeight();

        if (width / height < cols / rows)
            cellSize = width / (cols + 1);
        else
            cellSize = height / (rows + 1);

        hMargin = (width - cols * cellSize)/2;
        vMargin = (height - rows * cellSize)/2;
        canvas.translate(hMargin, vMargin);

        for (int i = 0; i < cols; ++i){
            for (int j = 0; j < rows; ++j){
                //if (array[i][j] != 0 && i % 2 == 0) //if is top wall
                canvas.drawLine(
                        i*cellSize, j*cellSize, (i+1)* cellSize,
                        j*cellSize, (array[i][j]==-1)?wallPaint:wallPaintGhost);
                //if (array[i][j] != 0 && i % 2 == 1)
                    canvas.drawLine(
                            i*cellSize, j*cellSize, i* cellSize,
                            (j+1)*cellSize, (array[i][j]==-1)?wallPaint:wallPaintGhost);
            }//if -2 dont show, if -1 or >0 wall, if 0 show ghost
        }

        //canvas.drawBitmap(testObj.sprite, null, new Rect(0, 0, 32, 32), null);
        //testObj.sprite.describeContents();
    }

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
    //formatting set to -2

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