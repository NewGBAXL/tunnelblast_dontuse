package com.tunnelblast;

import android.graphics.Color;
import android.graphics.Paint;
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

public class Map extends View {
    int xWidth = 15; //14
    int yHeight = 15; //7
    private float cellSize, hMargin, vMargin;
    private Paint wallPaint;
    private Paint wallPaintGhost;
    private static final float WALL_THICKNESS = 4;
    private Cell[][] cells;

    public Map(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(WALL_THICKNESS);
        wallPaintGhost = new Paint();
        wallPaintGhost.setColor(Color.BLUE);
        wallPaintGhost.setStrokeWidth(WALL_THICKNESS);
        createMaze();
    }

    private void createMaze() {
        for (int x = 0; x < xWidth; ++x) {
            for (int y = 0; y < yHeight; ++y) {
                cells[x][y] = new Cell(x, y);
            }
        }

        for (int x = 0; x < xWidth; ++x) {
            cells[x][0].buildWall((byte)0, -1); //sets first nWall to -1
            cells[x][yHeight - 1].buildWall((byte)2, -1); //sets last sWall to -1
        }
        for (int y = 0; y < yHeight; ++y) {
            cells[0][y].buildWall((byte)3, -1); //sets first wWall to -1
            cells[xWidth - 1][y].buildWall((byte)1, -1); //sets last eWall to -1
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        int width = getWidth();
        int height = getHeight();

        if (width / height < xWidth / yHeight)
            cellSize = width / (xWidth + 1);
        else
            cellSize = height / (yHeight + 1);

        hMargin = (width - xWidth * cellSize) / 2;
        vMargin = (height - yHeight * cellSize) / 2;
        canvas.translate(hMargin, vMargin);

        //north walls
        for (int i = 0; i < xWidth; ++i) {
            for (int j = 0; j < yHeight; ++j) {
                canvas.drawLine( //if -1 or >0 wall, if 0 show ghost
                        i * cellSize, j * cellSize, (i + 1) * cellSize,
                        j * cellSize, (cells[i][j].nWall != 0) ? wallPaint : wallPaintGhost);
            }
        }
        //south walls
        for (int i = 0; i < xWidth; ++i) {
            for (int j = 0; j < yHeight; ++j) {
                canvas.drawLine(
                        i * cellSize, (j+1) * cellSize, (i + 1) * cellSize,
                        (j+1) * cellSize, (cells[i][j].sWall != 0) ? wallPaint : wallPaintGhost);
            }
        }
        //east walls
        for (int i = 0; i < xWidth; ++i) {
            for (int j = 0; j < yHeight; ++j) {
                canvas.drawLine(
                        (i+1) * cellSize, j * cellSize, (i+1) * cellSize,
                        (j + 1) * cellSize, (cells[i][j].eWall != 0) ? wallPaint : wallPaintGhost);
            }
        }
        //west walls
        for (int i = 0; i < xWidth; ++i) {
            for (int j = 0; j < yHeight; ++j) {
                canvas.drawLine(
                        i * cellSize, j * cellSize, i * cellSize,
                        (j + 1) * cellSize, (cells[i][j].wWall != 0) ? wallPaint : wallPaintGhost);
            }
        }
    }

    void breakWall(int x, int y, byte wallId, int str) {
        //if cell method return true, destroy adj wall on adj cell
        if (cells[x][y].breakWall(wallId, str)) {
            if (wallId == 0)
                cells[x][y - 1].breakWall((byte)2, str);
            if (wallId == 1)
                cells[x + 1][y].breakWall((byte)3, str);
            if (wallId == 2)
                cells[x][y + 1].breakWall((byte)0, str);
            if (wallId == 3)
                cells[x - 1][y].breakWall((byte)1, str);
        }
    }

    void buildWall(int x, int y, byte wallId, int str) {
        //if cell method return true, build adj wall on adj cell
        if (cells[x][y].buildWall(wallId, str)) {
            if (wallId == 0)
                cells[x][y - 1].buildWall((byte)2, str);
            if (wallId == 1)
                cells[x + 1][y].buildWall((byte)3, str);
            if (wallId == 2)
                cells[x][y + 1].buildWall((byte)0, str);
            if (wallId == 3)
                cells[x - 1][y].buildWall((byte)1, str);
        }
    }

    //addCar method
    //eraseMap method //planned feature
    //randomGenerate method //planned feature

    //methods in other classes
    //winUI method
    //loseUI method
    //carBlowUp method
    //carGun method
    //moveTo (car) method (where?)
}

//if edge, set to -1
//if no wall, set to 0
//if wall has strength n, set to n
//if exit set to -2