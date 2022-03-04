package com.example.samsungproject;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player {

    float jumpCount = 10; // прыжок игрока
    boolean isJump = false; // падать или нет
    Bitmap image = BitmapFactory.decodeResource(MySurfaceView.res , R.drawable.obito);
    float iX, iY, tX = 0, tY = 0;
    float dx = 0, dy = 0;
    double k = 20; // velocity or koeff
    float hi, wi;//ширина и высота изображения
    Paint paint;

    public Player(){

    }

    public void update() {
        iX += dx; // Параметры игрока
        iY += dy; // Параметры игрока

        // Падение
        if ((jumpCount >= -10) && (isJump)){
            iY -= jumpCount * Math.abs(jumpCount) *0.5;
            jumpCount -= 1;
            delta();
        }

        else {
            jumpCount = 10;
            isJump = false;
            dx = 0;
        }
    }

    //расчет смещения картинки по x и y
    void delta(){
        double ro = Math.sqrt((tX- iX)*(tX- iX)+(tY-iY)*(tY-iY));
        dx = (float) (k * (tX - iX)/ro);
        //dy = (float) (k * (tY - iY)/ro);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, iX, iY, paint);
    }
}
