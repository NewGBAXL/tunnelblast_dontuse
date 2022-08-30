package com.tunnelblast.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.tunnelblast.Coordinates;
import com.tunnelblast.Map;

import java.io.IOException;
import java.io.InputStream;

public class GameObject
{
    public Coordinates position;
    public Bitmap sprite;

    public GameObject()
    {
        position = new Coordinates(0, 0);

        try {
            InputStream input = Map.context.getAssets().open("car.png");

            sprite = BitmapFactory.decodeStream(input);
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void LoadSprite()
    {

    }
}
