package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomappbar.BottomAppBar;
import androidx.core.content.ContextCompat;
import com.example.bottomnavigationdemo.databinding.ActivityAppBinding;
//import com.example.bottomnavigationdemo.databinding.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;

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


public class app extends AppCompatActivity {

    ActivityAppBinding binding;
    DatabaseReference databaseRef;
    private int i=1;

    String uid=UserId.uid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  Toast.makeText(app.this, "UID"+uid, Toast.LENGTH_SHORT).show();

        binding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.shorts:
                    replaceFragment(new ShortsFragment());
                    break;
                case R.id.subscriptions:
                    replaceFragment(new SubscriptionFragment());
                    break;
                case R.id.library:
                    replaceFragment(new ProfilFragment());
                    break;
            }

            return true;
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });

        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setBackground(ContextCompat.getDrawable(this, R.drawable.gradient));



    }



    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //traitement de calendrier sur dialog
        TextView textView3 = dialog.findViewById(R.id.textView3);
        TextView textView4 = dialog.findViewById(R.id.textView4);
        TextView textView5 = dialog.findViewById(R.id.textView5);


        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(app.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        //Showing the picked value in the textView
                        textView3.setText(String.valueOf(day)+" |");
                        System.out.println("month***************"+month+getMoisEnFrancais(month));
                        textView4.setText(getMoisEnFrancais(month)+" "+String.valueOf(year));

                        textView5.setText(getNomJour(day,month,year));

                    }
                }, 2023, 01, 20);

                datePickerDialog.show();

            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //la couleur de dialoge
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,255,255)));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);


        //connecter a la base de donnees et saisie les donnees
        databaseRef = FirebaseDatabase.getInstance().getReference();


        Button saveButton = dialog.findViewById(R.id.buttonSave);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des données saisies

                EditText editTextTitle = dialog.findViewById(R.id.editTextTitle);
                EditText editTextContent = dialog.findViewById(R.id.editTextContent);

                String titre = editTextTitle.getText().toString();
                String contenu = editTextContent.getText().toString();
                String jour=textView3.getText().toString();
                String mois_annee=textView4.getText().toString();


                // Création d'un objet Data pour représenter les données saisies
                Data data = new Data(titre,contenu,Integer.parseInt(jour.split(" ")[0]),mois_annee.split(" ")[0],mois_annee.split(" ")[1]);

                // Enregistrement des données dans la base de données Firebase
                databaseRef.child("données").child(uid).push().setValue(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Enregistrement réussi
                                i++;
                                Toast.makeText(app.this, "Données enregistrées avec succès", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Erreur lors de l'enregistrement
                                Toast.makeText(app.this, "Erreur lors de l'enregistrement des données", Toast.LENGTH_SHORT).show();
                            }
                        });







            }
        });



    }
    public static String getMoisEnFrancais(int mois) {
        String[] moisEnFrancais = new DateFormatSymbols(Locale.FRENCH).getMonths();
        return moisEnFrancais[mois];
    }
    public static String getNomJour(int jour, int mois, int annee) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, annee);
        calendar.set(Calendar.MONTH, mois - 1);
        calendar.set(Calendar.DAY_OF_MONTH, jour);

        Date date = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.FRENCH);
        return sdf.format(date);
    }


}




