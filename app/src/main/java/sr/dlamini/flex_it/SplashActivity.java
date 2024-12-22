package sr.dlamini.flex_it;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static sr.dlamini.flex_it.R.id.progressBar;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 8000;
    ProgressBar progressBar;
    TextView headerText;
    TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);
        headerText = findViewById(R.id.headerText);
        welcomeText = findViewById(R.id.welcomeText);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
