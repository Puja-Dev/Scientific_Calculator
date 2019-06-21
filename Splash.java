package pooja.devlal.com.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new android.os.Handler().postDelayed(new Runnable() {
                                                 @Override
                                                 public void run() {




                                                     startActivity(new Intent(Splash.this,Calculator.class));

                                                 }
                                             },3000
        );
    }
}
