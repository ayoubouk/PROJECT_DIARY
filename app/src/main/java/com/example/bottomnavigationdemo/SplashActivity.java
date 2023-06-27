
package com.example.bottomnavigationdemo;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000; // Durée du SplashScreen en millisecondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Assurez-vous d'avoir une disposition (layout) pour votre SplashScreen
        setContentView(R.layout.activity_splash);

        // Utilisez un Handler pour afficher le SplashScreen pendant la durée spécifiée
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Après la durée spécifiée, passez à l'activité principale (MainActivity)
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Termine l'activité SplashScreen pour qu'elle ne puisse pas être revenir en arrière
            }
        }, SPLASH_DURATION);
    }
}

