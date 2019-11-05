package com.nazim2.prototype3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends Activity {

    int shopMoney,count=0,mode=0;
    TextView moneyText;
   // MediaPlayer mp=null;


    boolean buy=false;
    ImageView[] images=new ImageView[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
//        mp=MediaPlayer.create(ShopActivity.this,R.raw.sound);
//        mp.start();

        Typeface tp=Typeface.createFromAsset(getAssets(),"chems_font.otf");


        images[0]=(ImageView)findViewById(R.id.img1);
        images[1]=(ImageView)findViewById(R.id.img2);

        moneyText=(TextView)findViewById(R.id.money);
        moneyText.setTypeface(tp);

        load();
        shopMoney=getIntent().getIntExtra("MONEY",0);
        moneyText.setText(""+shopMoney+" $");

        images[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ShopActivity.this);
                builder.setMessage("A solid Steel armor , can handle 2 meteor crashes (Teleport , no Sliding) ");
                if (shopMoney < 1000  ||( mode!=1 && mode!=0)) {

                   if(shopMoney<1000) builder.setTitle("You can't Buy this amelioration");
                    else builder.setTitle("You can only buy one item at a time");
                            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });
                } else {
                    builder.setTitle("You can Buy this amelioration").setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //add an mp3 sound effect
                            shopMoney = shopMoney - 1000;
                            //number of this amelioration bought
                            count++;
                            mode = 1;
                            moneyText.setText("" + shopMoney + " $");
                            buy = true;

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }
                builder.show();

            }
        });


        images[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(ShopActivity.this);
                builder.setMessage("A High Potential Electric  Field envlop, performs a lightning strike each click");
                if(shopMoney<4000 || (mode!=3 && mode!=0)){

                    if(shopMoney<4000) builder.setTitle("You can't Buy this amelioration");
                    else builder.setTitle("You can only buy one item at a time");

                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });
                }
                else {
                    builder.setTitle("You can Buy this amelioration").setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //add an mp3 sound effect
                            shopMoney= shopMoney - 4000;

                            count++;
                            mode=3;
                            moneyText.setText("" + shopMoney + " $");
                            buy=true;

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }
                builder.show();

            }
        });

    }
    public void   load(){

        SharedPreferences load = getSharedPreferences("SAVE", 0);
        shopMoney=load.getInt("THE_MONEY",shopMoney);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences save=getSharedPreferences("SAVE",0);
        SharedPreferences.Editor editor =save.edit();
        editor.putInt("THE_MONEY",shopMoney);
        editor.commit();
       // mp.pause();
        finish();
    }


    @Override
    public void onBackPressed() {

        Intent i=new Intent(ShopActivity.this,MainActivity.class);
        i.putExtra("ITEM1",count);
        i.putExtra("MODE",mode);
        i.putExtra("MONEY_SHOP",shopMoney);
        i.putExtra("BUY",buy);
        startActivity(i);
    }


}
