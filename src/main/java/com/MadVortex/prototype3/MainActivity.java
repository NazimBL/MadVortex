package com.nazim2.prototype3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends Activity {

   TextView bestText=null,moneyText=null,best2=null;
    ImageButton shop=null;
  //  MediaPlayer sound=null;
    ImageButton b=null,sound=null;
    ImageButton button=null;
    int mainMoney,best,price,mode,item;

   boolean buy=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        Typeface tp=Typeface.createFromAsset(getAssets(),"chems_font.otf");
        button=(ImageButton)findViewById(R.id.round);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Developed By Bellabaci Nazim, Desgined By Chemssou Alouia",Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, " Rate and Comment my Apps :p  ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse("market://details?id=com.e_monsite.nazim_app.binazim"));
                if (!MyStartActivity(intent)) {

                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.e_monsite.nazim_app.binazim"));
                    if (!MyStartActivity(intent)) {

                        Toast.makeText(MainActivity.this, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        b=(ImageButton)findViewById(R.id.begin);
        shop=(ImageButton)findViewById(R.id.shop);
        sound=(ImageButton)findViewById(R.id.sound_on);
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // sound code

            }
        });


        bestText=(TextView)findViewById(R.id.best_id);


        bestText.setTypeface(tp);
       // moneyText=(TextView)findViewById(R.id.money_id);
        //moneyText.setTypeface(tp);

        ImageView img=(ImageView)findViewById(R.id.id1);

//        sound=MediaPlayer.create(MainActivity.this,R.raw.sound);
//        sound.start();
//        sound.setLooping(true);


        bestScoreLoad();

        buy=getIntent().getBooleanExtra("BUY", false);
        item=getIntent().getIntExtra("ITEM1",0);
        mode=getIntent().getIntExtra("MODE",0);

        if(mode==1)price=1000;
        else if (mode==2) price=4000;
        else if (mode==3)price=7500;
        else price=0;
        mainMoney=mainMoney+getIntent().getIntExtra("GAME_MONEY",0);
        best=getIntent().getIntExtra("GAME_BEST",best);
        if(buy) mainMoney-=(item*price);
        buy=false;

        bestText.setText("BEST "+best);
        //moneyText.setText("" + mainMoney + " $");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, GameActivty.class);
               // Intent intent = new Intent(MainActivity.this, GameActivty.class);
                intent.putExtra("item1", item);
                intent.putExtra("mode",mode);
                startActivity(intent);
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Intent intent=new Intent(MainActivity.this,ShopActivity.class);
                intent.putExtra("MONEY", mainMoney);
                startActivity(intent);
            }
        });

        myAnimation(10,0,img);

    }
    public void myAnimation(int DX,int DY,ImageView view){


        TranslateAnimation animation = new TranslateAnimation(-10, 500, DX, DY);

        animation.setDuration(4000);
        animation.setFillAfter(true);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
      //  animation.setRepeatMode(Animation.INFINITE);
        view.startAnimation(animation);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //sound.pause();
        System.gc();
        SharedPreferences save = getSharedPreferences("SAVE",0);
        SharedPreferences.Editor editor = save.edit();
        editor.putInt("SAVE_BEST", best);
        editor.putInt("SAVE_MONEY", mainMoney);
        editor.commit();
        finish();
    }

    public void bestScoreLoad(){

        SharedPreferences load = getSharedPreferences("SAVE", 0);
        best=load.getInt("SAVE_BEST",200);
        mainMoney = load.getInt("SAVE_MONEY",0);
    }
    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit Game").setMessage("Are you sur you want to quit ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();

    }
    private boolean MyStartActivity(Intent aIntent) {
        try
        {
            startActivity(aIntent);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }
}
