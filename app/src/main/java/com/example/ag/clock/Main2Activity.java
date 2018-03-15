package com.example.ag.clock;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class Main2Activity extends AppCompatActivity {

    private TextView mTextMessage;
    int myProgress = 0;
    ProgressBar progressBarView;
    Button btn_start;
    TextView tv_time;
    EditText et_timer;
    int progress;
    CountDownTimer countDownTimer;
    int endTime = 250;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i=new Intent(Main2Activity.this,MainActivity.class);
                    startActivity(i);
                    break;
                case R.id.navigation_dashboard:
                    //Toast.makeText(getApplicationContext(), "WELCOME TO TIMER", Toast.LENGTH_LONG).show();

                    mTextMessage.setText(R.string.title_dashboard);
                    Intent j=new Intent(Main2Activity.this,Main2Activity.class);

                    startActivity(j);
                    //Toast.makeText(getApplicationContext(), "WELCOME TO TIMER", Toast.LENGTH_LONG).show();
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        progressBarView = (ProgressBar) findViewById(R.id.view_progress_bar);
        btn_start = (Button) findViewById(R.id.btn_start);
        tv_time = (TextView) findViewById(R.id.tv_timer);
        et_timer = (EditText) findViewById(R.id.et_timer);
        //et_timer2_secs = (EditText) findViewById(R.id.et_timer2_secs);
        //et_timer3_hrs =(EditText) findViewById(R.id.et_timer3_hrs);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toast.makeText(getApplicationContext(), "TIMER", Toast.LENGTH_LONG).show();



        /*Animation*/
        RotateAnimation makeVertical = new RotateAnimation(0, -90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        makeVertical.setFillAfter(true);
        progressBarView.startAnimation(makeVertical);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(0);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fn_countdown();

            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_the_app) {
            Intent intent=new  Intent(getApplicationContext(),Main3Activity.class);
            startActivity(intent);
            return true;
        }
//        else if(id==R.id.Settings){
//            Intent intent2=new  Intent(getApplicationContext(),Main3Activity.class);
//            startActivity(intent2);
//            return true;
//        }


        return super.onOptionsItemSelected(item);
    }
    private void fn_countdown() {

        if (et_timer.getText().toString().length() > 0) {
            myProgress = 0;

            try {
                countDownTimer.cancel();

            } catch (Exception e) {

            }

            String timeInterval = et_timer.getText().toString();
            //String timeInterval2=et_timer3_hrs.getText().toString();
            int hours,seconds,minutes;
            String [] time;
            String delimiter=":";
            time=timeInterval.split(delimiter);
            //minutes= Integer.parseInt(timeInterval);

            progress = 1;
            // up to finish time
            hours = Integer.parseInt(time[0]);
            minutes = Integer.parseInt(time[1]);
            seconds = Integer.parseInt(time[2]);
            endTime=(hours*60*60)+(minutes*60)+seconds;
            countDownTimer = new CountDownTimer(endTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setProgress(progress, endTime);
                    progress = progress + 1;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                    int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                    String newtime = hours + ":" + minutes + ":" + seconds;

                    if (newtime.equals("0:0:0")) {
                        tv_time.setText("00:00:00");
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText("0" + hours + ":0" + minutes + ":0" + seconds);
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1)) {
                        tv_time.setText("0" + hours + ":0" + minutes + ":" + seconds);
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText("0" + hours + ":" + minutes + ":0" + seconds);
                    } else if ((String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText(hours + ":0" + minutes + ":0" + seconds);
                    } else if (String.valueOf(hours).length() == 1) {
                        tv_time.setText("0" + hours + ":" + minutes + ":" + seconds);
                    } else if (String.valueOf(minutes).length() == 1) {
                        tv_time.setText(hours + ":0" + minutes + ":" + seconds);
                    } else if (String.valueOf(seconds).length() == 1) {
                        tv_time.setText(hours + ":" + minutes + ":0" + seconds);
                    } else {
                        tv_time.setText(hours + ":" + minutes + ":" + seconds);
                    }

                }

                @Override
                public void onFinish() {
                    setProgress(progress, endTime);



                }
            };
            countDownTimer.start();
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the value", Toast.LENGTH_LONG).show();
        }

    }

    public void setProgress(int startTime, int endTime) {
        btn_start.setEnabled(false);
        progressBarView.setMax(endTime);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(startTime);
        btn_start.setEnabled(true);
    }


}







