package com.tunnelblast;

import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.Nullable;

import android.icu.util.TimeUnit;
import android.util.TimeUtils;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;

import com.tunnelblast.Objects.Car;
import com.tunnelblast.Objects.GameObject;
import com.tunnelblast.Objects.UserCar;
import com.tunnelblast.databinding.FragmentGameViewBinding;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Toast;

import java.sql.Time;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Map extends View {
    int xWidth = 15;
    int yHeight = 15;
    public static float cellSize, hMargin, vMargin;
    private Paint wallPaint, wallPaintGhost;
    private Paint playerTestPaint, exitPaint;
    private Paint carPaint = new Paint();
    private static final float WALL_THICKNESS = 4;
    public static Cell[][] cells;
    private Cell playerTest;
    private Cell exitSpace;
    public static boolean inv = false;
    private Random random;
    public static FragmentGameViewBinding UIBinding;
    Dpad dpad;

    //store global game data somewhere else?
    public UserCar player;
    //public CPUcar cpu; //user stack or arrayList?
    //public Localcar opponent; //hardcode x4, x5, ..?

    List<GameObject> objects;

    public Map(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        cells = new Cell[xWidth][yHeight];
        carPaint.setColor(Color.GRAY);
        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(WALL_THICKNESS);
        wallPaintGhost = new Paint();
        wallPaintGhost.setColor(Color.BLUE);
        wallPaintGhost.setStrokeWidth(WALL_THICKNESS);
        createMaze();
        playerTestPaint = new Paint();
        playerTestPaint.setColor(Color.RED);
        exitPaint = new Paint();
        exitPaint.setColor(Color.MAGENTA);

        playerTest = cells[0][0];
        exitSpace = cells[xWidth - 1][yHeight - 1];
        dpad = new Dpad();

        /*while (true){
            if (inv = true){
                invalidate();
                inv = false;
            }
        }*/

        objects = new ArrayList<>();
        player = new UserCar(this, 32, 32, carPaint, (byte)10, (byte)10);
        objects.add(player);
        objects.add(new Car(this, 352, 352, carPaint, (byte)10, (byte)10));


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

    private void UpdateObjects()
    {
        for (GameObject o : objects) o.Update();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        UpdateObjects();

        canvas.drawColor(Color.GREEN);
        int width = getWidth();
        int height = getHeight();

        if (width / (height + 1) < xWidth / yHeight)
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

        for (GameObject o : objects) o.Draw(canvas);

        //Wait to cap framerate, then signal to redraw
        try {Thread.sleep(5);}
        catch (InterruptedException ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();}

        invalidate();
    }

    void breakWall(int x, int y, byte wallId, int str) {
        //if cell method return true, destroy adj wall on adj cell
        if (cells[x][y].breakWall(wallId, str)) {
            if (wallId == 0)
                cells[x][y - 1].breakWall((byte)2, str);
            else if (wallId == 1)
                cells[x + 1][y].breakWall((byte)3, str);
            else if (wallId == 2)
                cells[x][y + 1].breakWall((byte)0, str);
            else if (wallId == 3)
                cells[x - 1][y].breakWall((byte)1, str);
            invalidate(); //inefficient, fix later
        }
    }

    void buildWall(int x, int y, byte wallId, int str) {
        //if cell method return true, build adj wall on adj cell
        if (cells[x][y].buildWall(wallId, str)) {
            if (wallId == 0)
                cells[x][y - 1].buildWall((byte)2, str);
            else if (wallId == 1)
                cells[x + 1][y].buildWall((byte)3, str);
            else if (wallId == 2)
                cells[x][y + 1].buildWall((byte)0, str);
            else if (wallId == 3)
                cells[x - 1][y].buildWall((byte)1, str);
            invalidate(); //inefficient, fix later
        }
    }

    /*public void updateCar(Car car){
        //UserCar, optimize later
        Drawable d = getResources().getDrawable(R.drawable.car, null);
        d.setBounds((int)(car.xPos*cellSize), (int)(car.yPos*cellSize),
                (int)((car.xPos+1)*cellSize), (int)((car.yPos+1)*cellSize));
        //d.draw(canvas);
        //return;
    }*/

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        // Check if this event if from a D-pad and process accordingly.
        if (Dpad.isDpadDevice(event)) {
            int press = dpad.getDirectionPressed(event);
            player.moveTo((byte) press);
            invalidate();
            return true;
        }

        // Check if this event is from a joystick movement and process accordingly.

        return super.onGenericMotionEvent(event);
    }

    private void processJoystickInput(MotionEvent event, int historyPos) {
        InputDevice inputDevice = event.getDevice();

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