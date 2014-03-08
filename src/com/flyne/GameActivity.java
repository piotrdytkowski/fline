package com.flyne;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Debug.startMethodTracing("myTEST");
        super.onCreate(savedInstanceState);
        View bouncingBallView = new GameView(this);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(bouncingBallView);
    }


}
