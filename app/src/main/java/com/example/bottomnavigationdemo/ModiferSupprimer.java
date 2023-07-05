package com.example.bottomnavigationdemo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.HashMap;

public class ModiferSupprimer extends AppCompatActivity {
    DatabaseReference databaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dataRef = databaseRef.child("données").child(UserId.uid);

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.modifier_supprimer);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //la couleur de dialoge
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,255,255)));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);



        TextView textView3 = dialog.findViewById(R.id.textView3);
        TextView textView4 = dialog.findViewById(R.id.textView4);
        TextView textView5 = dialog.findViewById(R.id.textView5);


        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ModiferSupprimer.this, new DatePickerDialog.OnDateSetListener() {
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

        EditText e=dialog.findViewById(R.id.editTextTitle);
        EditText l=dialog.findViewById(R.id.editTextContent);

        DatabaseReference elementRef = dataRef.child(UserId.idregister);

        elementRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Handle the data change event
                    String titre = dataSnapshot.child("titre").getValue(String.class);
                    String contenu = dataSnapshot.child("contenu").getValue(String.class);
                    Long jourValue = dataSnapshot.child("jour").getValue(Long.class);
                    String mois = dataSnapshot.child("mois").getValue(String.class);
                    String annee = dataSnapshot.child("annee").getValue(String.class);
                    e.setText(titre);
                    l.setText(contenu);
                    textView3.setText(jourValue+" |");
                    textView4.setText(mois+" "+annee);
                    //textView5.setText(getNomJour(2,mois,annee));
                    //Toast.makeText(ModiferSupprimer.this, UserId.idregister+" "+titre, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the cancellation event (optional)
            }
        });

        //////////////////////////////////// LA MODEIFICATION
        Button saveButton = dialog.findViewById(R.id.buttonEnregister);

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


                // Création d'un objet Data pour représenter les données mises à jour
                Data data = new Data(titre, contenu, Integer.parseInt(jour.split(" ")[0]), mois_annee.split(" ")[0], mois_annee.split(" ")[1]);

                // Construction du chemin de la référence de la base de données pour les données à mettre à jour
                String chemin = "données/" + UserId.uid + "/" + UserId.idregister;

                // Création d'un objet HashMap pour contenir les champs à mettre à jour
                HashMap<String, Object> updates = new HashMap<>();
                updates.put(chemin, data.toMap());

               // Mise à jour des données dans la base de données Firebase
                databaseRef.updateChildren(updates)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Mise à jour réussie
                                Toast.makeText(ModiferSupprimer.this, "Données mises à jour avec succès", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Erreur lors de la mise à jour
                                Toast.makeText(ModiferSupprimer.this, "Erreur lors de la mise à jour des données", Toast.LENGTH_SHORT).show();
                            }
                        });








            }
        });


        //////////////////////////////////// LA SUPPRESSION

        Button deleteButton = dialog.findViewById(R.id.buttonSupprimer);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseRef.child("données").child(UserId.uid).child(UserId.idregister).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Suppression réussie
                                Toast.makeText(ModiferSupprimer.this, "Données supprimées avec succès", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Erreur lors de la suppression
                                Toast.makeText(ModiferSupprimer.this, "Erreur lors de la suppression des données", Toast.LENGTH_SHORT).show();
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
