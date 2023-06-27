package com.example.bottomnavigationdemo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;


import java.text.DateFormatSymbols;
import java.util.Locale;



public class ShortsFragment extends Fragment {
    private CalendarView calendarView;
    private Button emojiButton;
    DatabaseReference databaseRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shorts, container, false);
        LinearLayout journalshortLayout = rootView.findViewById(R.id.journalshort);
        TextView dateTextView = rootView.findViewById(R.id.date);
        TextView titreTextView = rootView.findViewById(R.id.titre);
        TextView contenuTextView = rootView.findViewById(R.id.contenu);
        ImageView img1=rootView.findViewById(R.id.imageView2);
        calendarView = rootView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Gérer l'événement de changement de date ici
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(getActivity(), "Date sélectionnée : " + selectedDate, Toast.LENGTH_SHORT).show();
                test(selectedDate);
            }

            private void test(String date) {
                System.out.println("la date est :"+date);
                String[] d = date.split("/");

                getDataFromFirebase(d[0],d[1],d[2],journalshortLayout,dateTextView,titreTextView,contenuTextView,img1);

            }

        });

        // Obtenir une référence à la base de données Firebase
        databaseRef = FirebaseDatabase.getInstance().getReference();



        return rootView;
    }


    private void getDataFromFirebase(String jour1, String mois1, String annee1, LinearLayout L, TextView d, TextView t, TextView c,ImageView img1) {
        DatabaseReference dataRef = databaseRef.child("données").child(UserId.uid);

        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                        String key = childSnapshot.getKey();
                        String annee = childSnapshot.child("annee").getValue(String.class);
                        String contenu = childSnapshot.child("contenu").getValue(String.class);
                        Long jourValue = childSnapshot.child("jour").getValue(Long.class);
                        String mois = childSnapshot.child("mois").getValue(String.class);
                        String titre = childSnapshot.child("titre").getValue(String.class);
                        // Vérifiez si jourValue est null avant d'appeler longValue()
                        long jour = (jourValue != null) ? jourValue.longValue() : 0;
                        System.out.println(key+" "+titre+" "+mois+" "+annee+" "+annee1+"******************"+Long.toString(jour)+" "+jour1+"***********************"+mois+" "+getMoisEnFrancais(Integer.parseInt(mois1)));
                         String moisInt=getMoisEnFrancais(Integer.parseInt(mois1));
                        // Utilisez les valeurs récupérées
                        if (annee1.equals(annee) && moisInt.equals(mois) && jour1.equals(Long.toString(jour))) {
                            System.out.println("Clé : " + key);
                            System.out.println("Année short : " + annee);
                            System.out.println("Contenu short: " + contenu);
                            System.out.println("Jour short: " + jour);
                            System.out.println("Mois short: " + mois);
                            System.out.println("Titre short: " + titre);
                            L.setVisibility(View.VISIBLE);
                            img1.setVisibility(View.GONE);
                            d.setText(jour1 + " " + getMoisEnFrancais(Integer.parseInt(mois1)) + " " + annee1);
                            t.setText(titre);
                            c.setText(contenu);
                            break;
                        }
                        else {
                            L.setVisibility(View.GONE);
                            img1.setVisibility(View.VISIBLE);
                        }
                    }
                }


                else {
                    System.out.println("Aucune donnée trouvée");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Erreur : " + databaseError.getMessage());
            }
        });
    }




    public static String getMoisEnFrancais(int mois) {
        String[] moisEnFrancais = new DateFormatSymbols(Locale.FRENCH).getMonths();
        return moisEnFrancais[mois - 1];
    }
}







