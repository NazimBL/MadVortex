package com.nazim2.prototype3;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

public class LoadingActivity extends Activity {
    int num = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(new MainPanel(this));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (num > -1) {
                    LoadingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            num--;
                            if (num < 0) {
                                if(!LoadingActivity.this.isFinishing()){
                                    Intent i=new Intent(LoadingActivity.this,GameActivty.class);
                                    i.putExtra("item",getIntent().getIntExtra("item1",0));
                                    i.putExtra("mode",getIntent().getIntExtra("mode",0));
                                    startActivity(i);
                                }
                            }
                        }
                    });
                    try {
                        Thread.sleep(1000);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void runOnUiThread() {

        num--;
    }
    @Override
    protected void onPause() {
        super.onPause();
    this.finish();
    }
}
