package com.hfad.stopwatch;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity
{
    private boolean running=true;
    private int sec=0;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if(savedInstanceState!=null)
        {
            sec=savedInstanceState.getInt("sec");
            running=savedInstanceState.getBoolean("running");
            wasRunning=savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }
    public void onClickStart(View view)
    {
        running=true;
    }
    public void onClickStop(View view)
    {
        running=false;
    }
    public void onClickReset(View view)
    {
        running=false;
        sec=0;

    }
    private void runTimer()
    {
        final TextView timeView=(TextView)findViewById(R.id.time_view);
        final Handler handler=new Handler();
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                int hour=sec/3600;
                int min=(sec%3600)/60;
                int seconds=(sec%3600)%60;
                String time=String.format("%02d:%02d:%02d",hour,min,seconds);
                timeView.setText(time);
                if(running)
                {
                    sec++;
                }
                handler.postDelayed(this,1000);
            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putInt("sec",sec);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }
    @Override
    public void onStop()
    {
        super.onStop();
        wasRunning=running;
        running=false;
    }
    public void onStart()
    {
        super.onStart();
        if(wasRunning)
        running=true;
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        if(wasRunning)
            running=false;
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        if(wasRunning)
            running=true;
    }
}
