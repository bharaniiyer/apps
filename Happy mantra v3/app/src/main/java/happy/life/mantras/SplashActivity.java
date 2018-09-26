package happy.life.mantras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import info.androidhive.expandablelistview.R;

public class SplashActivity extends Activity {

    private long splashdelay=3000;
    TextView splastTopText;
    TextView splastBottomfirstText;
    TextView splastBottomsecondText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splastTopText = (TextView)findViewById(R.id.titleTopSplash);
        splastBottomfirstText = (TextView)findViewById(R.id.titleBottomoneSplash);
        splastBottomsecondText = (TextView)findViewById(R.id.titleBottomtwoSplash);
        splastTopText.setText("வேதங்களை கற்போம் ! கற்பிப்போம் !");
        splastBottomfirstText.setText("தங்கள் சித்தம் எங்கள் பாக்கியம் !");
        splastBottomsecondText.setText("HappyLife குழுமம்");


        TimerTask task=new TimerTask()
        {
            @Override
            public void run()
            {
                // to prevent executing again when ‘BACK’ is pressed

                Intent mainintent=new Intent().setClass(SplashActivity.this,MainActivity.class);
                startActivity(mainintent);

                finish();
            }
        };
        Timer timer=new Timer();
        timer.schedule(task,splashdelay);

    }
}