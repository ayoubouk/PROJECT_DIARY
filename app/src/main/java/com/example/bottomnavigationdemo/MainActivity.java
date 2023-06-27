package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomappbar.BottomAppBar;
import androidx.core.content.ContextCompat;
import com.example.bottomnavigationdemo.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;
import java.text.DateFormatSymbols;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.EditText;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;


public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView gotoRegisterTextView;
    private EditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;
    DatabaseReference databaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //la partie home
        loginButton = findViewById(R.id.btnLogin);

        // Initialisation de Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.inputEmail);
        passwordEditText = findViewById(R.id.inputPassword);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Vérification des champs e-mail et mot de passe
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Connexion avec l'adresse e-mail et le mot de passe
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Connexion réussie
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String uid = user.getUid();
                                        UserId userid=new UserId(uid);
                                        Toast.makeText(MainActivity.this, "Connexion réussie en tant que "+ user.getEmail(), Toast.LENGTH_SHORT).show();


                                        navigateToHome();
                                    } else {
                                        // Erreur de connexion
                                        Toast.makeText(MainActivity.this, "Échec de la connexion. Veuillez vérifier vos identifiants", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
        }
        });

        //la partie register
        gotoRegisterTextView = findViewById(R.id.gotoRegister);
        gotoRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRegisterActivity();
            }
        });

          //la partie reinitialisation de code
        TextView forgotPasswordTextView = findViewById(R.id.forgotPassword);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

    }



    private void navigateToRegisterActivity() {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
        //finish();
    }
    private void navigateToHome() {
        Intent intent = new Intent(MainActivity.this, app.class);
        startActivity(intent);
        //finish();
    }
}






