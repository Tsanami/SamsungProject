package com.example.samsungproject;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private final Joystick joystick; // Джойстик
    private final Player player; // Игрок

    Bitmap image, wall;
    Paint paint;

    public static float wallX, wallY;

    public static Resources res;

    MyThread myThread;
    //контроль столкновений и размеров

    float hs, ws;//ширина и высота области рисования
    boolean isFirstDraw = true;
    GameMap gameMap;

    Rect wallRect, imageRect;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        res = getResources();

        wall = BitmapFactory.decodeResource(res, R.drawable.wall);



        player = new Player();
        joystick = new Joystick();


        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(5);
        setAlpha(0);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        myThread = new MyThread(getHolder(), this);
        myThread.setRunning(true);
        myThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        myThread.setRunning(false);
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(isFirstDraw){
            hs = getHeight();
            ws = getWidth();
            wallX = ws / 2;
            Random random = new Random();
            wallY = random.nextInt((int)(hs - wall.getHeight() - 5));
            wallRect = new Rect((int)wallX, (int)wallY, (int)(wallX + wall.getWidth()),
                    (int)(wallY + wall.getHeight()));
            gameMap = new GameMap((int)ws, (int)hs, res);

            player.iX = ws / 2; // Параметры игрока
            player.iY = hs-100; //4 * hs / 5 ; // Параметры игрока

            isFirstDraw = false;

        }

        joystick.draw(); // Рисовать джойстик

        gameMap.draw(canvas); // Рисовать карту

        player.draw(canvas); // Рисовать игрока



        //canvas.drawBitmap(wall, wallX, wallY, paint);
        //canvas.drawLine(iX, iY, tX, tY, paint);
        //if(tX != 0)
        //delta();
//        imageRect = new Rect((int)iX, (int)iY, (int) (iX + wi), (int)(iY + hi));
//
//        if(imageRect.intersect(wallRect)){
//            dy = 0;
//            dx = 0;
//        }


        update();
    }

    private void update(){
        player.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){

            player.tX = event.getX();
            player.tY = event.getY();
            player.isJump = true;
        }
        return true;
    }




}

//TODO Запретить в манифесте поворот
    //при создании ScreenOrientation LandScape
    //Метод в активности для фиксирования
    //SetRequestOrientation(LANDSCAPE)
    //конструктор включающий атрибуты
