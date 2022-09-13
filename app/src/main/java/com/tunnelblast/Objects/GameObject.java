package com.tunnelblast.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.tunnelblast.Coordinates;
import com.tunnelblast.Map;
import com.tunnelblast.R;

import java.io.IOException;
import java.io.InputStream;

public class GameObject
{
    protected Map game;

    public Coordinates position;
    public Drawable sprite;

    public GameObject(Map game)
    {
        this.game = game;
        position = new Coordinates(0, 0);

        sprite = game.getResources().getDrawable(R.drawable.car, null);
    }

    public void Update(){}

    public void Draw(Canvas canvas)
    {
        sprite.setBounds((int)position.X - sprite.getMinimumWidth() / 2, (int)position.Y - sprite.getMinimumHeight() / 2, (int)position.X + sprite.getMinimumWidth() / 2, (int)position.Y + sprite.getMinimumHeight() / 2);
        sprite.draw(canvas);
    }
}
