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





public class ShortsFragment extends Fragment {
    private CalendarView calendarView;
    private Button emojiButton;
    DatabaseReference databaseRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shorts, container, false);

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

                getDataFromFirebase(d[0],d[1],d[2]);
            }
        });

        // Obtenir une référence à la base de données Firebase
        databaseRef = FirebaseDatabase.getInstance().getReference();
        // Récupérer les données
       // getDataFromFirebase();


        return rootView;
    }

    private void getDataFromFirebase(String jour1,String mois1,String annee1) {
        for (int i=1;i<3;i++) {
            DatabaseReference dataRef = databaseRef.child(i+"");
            dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String annee = dataSnapshot.child("année").getValue(String.class);
                        String contenu = dataSnapshot.child("contenu").getValue(String.class);
                        long jour = dataSnapshot.child("jour").getValue(Long.class);
                        String mois = dataSnapshot.child("mois").getValue(String.class);
                        String titre = dataSnapshot.child("titre").getValue(String.class);

                        // Utilisez les valeurs récupérées
                        if(annee1.equals(annee) && mois1.equals(mois) && jour1.equals(Long.toString(jour))){
                        System.out.println("Année short : " + annee);
                        System.out.println("Contenu short: " + contenu);
                        System.out.println("Jour short: " + jour);
                        System.out.println("Mois short: " + mois);
                        System.out.println("Titre short: " + titre);}
                    } else {
                        System.out.println("Aucune donnée trouvée");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("Erreur : " + databaseError.getMessage());
                }
            });
        }
    }

}







