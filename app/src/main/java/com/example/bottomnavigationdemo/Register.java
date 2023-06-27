package com.example.bottomnavigationdemo;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class Register extends AppCompatActivity {
    private Button backButton;
    private EditText inputEmail, inputPassword,userName;
    private Button btnSignUp;
    private FirebaseAuth auth;

    DatabaseReference databaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Activer le bouton de retour dans la barre d'action
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        /////////////////////////////////////////////////////
        // Initialise Firebase Auth
        auth = FirebaseAuth.getInstance();
        userName=findViewById(R.id.inputUser);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnSignUp = findViewById(R.id.btnLogin);
        //inserer username dans la class



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Vérifier si les champs email et mot de passe sont vides
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Entrez une adresse e-mail !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Entrez un mot de passe !", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Créer un nouvel utilisateur avec l'adresse e-mail et le mot de passe
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Inscription réussie !", Toast.LENGTH_SHORT).show();
                                    UserId username=new UserId("",userName.getText()+"");
                                    //cette partie pour sauvgarder uid dans la class UserId
                                    FirebaseUser user = auth.getCurrentUser();
                                    String uid = user.getUid();
                                    UserId userid=new UserId(uid);
                                    databaseRef = FirebaseDatabase.getInstance().getReference("données");

                                    databaseRef.child(UserId.uid).child("name").setValue(UserId.username);
                                    databaseRef.child(UserId.uid).child("email").setValue(email);
                                    onSupportNavigateUp();

                                } else {
                                    Toast.makeText(Register.this, "Erreur lors de l'inscription. Veuillez réessayer.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



    }

    public boolean onSupportNavigateUp() {
        // Gérer le clic sur la flèche de retour
        navigateToLoginActivity(); // Exemple : Redirection vers LoginActivity
        return true;
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
        //finish(); // Termine l'activité actuelle
    }

}
