package com.example.ag.clock;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    TextView textView;

    ImageView start, pause, reset, lap,iv_seconds;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;

    Handler handler;

    int Seconds, Minutes, MilliSeconds;

    ListView listView;

    String[] ListElements = new String[]{};

    List<String> ListElementsArrayList;

    ArrayAdapter<String> adapter;

    long old_degree;
    int lapcount=0;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i=new Intent(MainActivity.this,MainActivity.class);
                    startActivity(i);
                    break;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    Intent j=new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(j);
                    break;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Toast.makeText(getApplicationContext(), "StopWatch", Toast.LENGTH_LONG).show();
        iv_seconds=(ImageView)findViewById(R.id.iv_seconds);
        textView = (TextView) findViewById(R.id.tv_time);
        start = (ImageView) findViewById(R.id.iv_start);
        pause = (ImageView) findViewById(R.id.iv_pause);
        reset = (ImageView) findViewById(R.id.iv_reset);
        lap = (ImageView) findViewById(R.id.iv_lap);
        listView = (ListView) findViewById(R.id.listview1);

        handler = new Handler();

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        ) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };



        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = System.currentTimeMillis();

                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);
                reset.setImageResource(R.drawable.resetnew);
                start.setEnabled(false);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;


                handler.removeCallbacks(runnable);

                reset.setEnabled(true);
                reset.setImageResource(R.drawable.reset);
                start.setEnabled(true);
                start.setImageResource(R.drawable.playnew);


            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                Seconds = 0;
                Minutes = 0;
                MilliSeconds = 0;
                start.setImageResource(R.drawable.play);
                textView.setText("00:00:00");

                ListElementsArrayList.clear();

                adapter.notifyDataSetChanged();


                rotate(old_degree,360);
                old_degree=0;
                lapcount=0;

            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lapcount++;
                ListElementsArrayList.add("Lap "+lapcount+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+textView.getText().toString());

                adapter.notifyDataSetChanged();

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
// else if(id==R.id.Settings){
//            Intent intent2=new  Intent(getApplicationContext(),Main3Activity.class);
//            startActivity(intent2);
//            return true;
//        }


        return super.onOptionsItemSelected(item);
}

    private void rotate(float from_degree, float to_degree) {
        RotateAnimation rotateAnimation = new RotateAnimation(from_degree, to_degree, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.91f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);
        iv_seconds.startAnimation(rotateAnimation);

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = System.currentTimeMillis() - StartTime;


            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);
            textView.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));
            rotate(old_degree,UpdateTime*3/500);

            old_degree=UpdateTime*3/500;
            handler.postDelayed(this, 0);
        }

    };






}
