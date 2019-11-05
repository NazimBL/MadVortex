package com.nazim2.prototype3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.WindowManager;

public class GameActivty extends Activity {
    GamePanel panel;
    //MediaPlayer mp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        mp=MediaPlayer.create(GameActivty.this,R.raw.sound);
//        mp.start();

        panel=new GamePanel(this);
        panel.itemBerserk=getIntent().getIntExtra("item1",0);
        panel.itemMode=getIntent().getIntExtra("mode",0);
        panel.tp= Typeface.createFromAsset(getAssets(),"chems_font.otf");
        bestScoreSave();
        setContentView(panel);

    }

    public void bestScoreSave(){

        SharedPreferences load = getSharedPreferences("save",0);
        panel.best = load.getInt("max", 200);
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences save = getSharedPreferences("save",200);
        SharedPreferences.Editor editor = save.edit();
        editor.putInt("max", panel.best);
        editor.commit();

    }

    @Override
    protected void onPause() {
        super.onPause();
      //  mp.pause();
        System.gc();
        finish();
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(GameActivty.this);
        builder.setTitle("Quit").setMessage("Do you want to quit the Game ? ").setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(GameActivty.this, MainActivity.class);
                intent.putExtra("GAME_MONEY", panel.money);
                intent.putExtra("GAME_BEST", panel.best);
                System.gc();
                startActivity(intent);

            }
        }).show();
    }

}
