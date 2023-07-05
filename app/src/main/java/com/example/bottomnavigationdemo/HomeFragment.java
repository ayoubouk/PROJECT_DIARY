package com.example.bottomnavigationdemo;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.graphics.Typeface;
import android.graphics.Color;
import androidx.core.content.res.ResourcesCompat;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import android.content.Intent;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.util.Locale;
import android.os.Handler;

public class HomeFragment extends Fragment {

    private ImageView image;
    private ValueAnimator animator;
    DatabaseReference databaseRef;
    LinearLayout journalHome;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        image = rootView.findViewById(R.id.myImage);

        // Création de l'animation de défilement de bas en haut
        animator = ValueAnimator.ofFloat(0f, -30f);
        animator.setDuration(1000);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                image.setTranslationY(value);
            }
        });

        // Démarrage de l'animation
        animator.start();






        /// partie afdichage des journals
        ConstraintLayout constraintLayout = rootView.findViewById(R.id.myConstraintLayout);
        databaseRef = FirebaseDatabase.getInstance().getReference();
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

                        if(annee!=null && titre!=null && contenu!=null && mois!=null && jourValue!=null){


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Votre code à exécuter après un délai de 3 secondes
                                    constraintLayout.setVisibility(View.GONE);
                                }
                            }, 2000);
                        LinearLayout journalLayout = new LinearLayout(getContext());
                        int generatedId = key.hashCode();
                        journalLayout.setId(generatedId); // Remplacez R.id.journalshort1 par l'ID souhaité
                        journalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        journalLayout.setOrientation(LinearLayout.VERTICAL);
                        journalLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                        journalLayout.setBackgroundResource(R.drawable.shadow);
                        journalLayout.setPadding(20, 20, 20, 20);
                          //margin 20dp
                            int marginInPixels = (int) getResources().getDimension(R.dimen.margin_20dp);
                            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            layoutParam.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
                            journalLayout.setLayoutParams(layoutParam);
                          /////////////////////////////////

                            journalLayout.setVisibility(View.VISIBLE);

                        TextView dateTextView = new TextView(getContext());

                        dateTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                        dateTextView.setText(jourValue+" "+mois+" "+annee);
                        dateTextView.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
                        dateTextView.setTextSize(23);
                        dateTextView.setTextColor(Color.parseColor("#000000"));
                        dateTextView.setTypeface(ResourcesCompat.getFont(getContext(), R.font.ramonabold));

                        TextView titreTextView = new TextView(getContext());

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 7, 0, 4);
                        titreTextView.setText(titre);
                        titreTextView.setTextSize(25);
                        titreTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                        titreTextView.setTypeface(ResourcesCompat.getFont(getContext(), R.font.ramonabold));
                         //////////////////////////////////////

                          ////////////////////////////////////////////////////

                            TextView contenuTextView = new TextView(getContext());
                        contenuTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        contenuTextView.setText(contenu);
                        contenuTextView.setTypeface(ResourcesCompat.getFont(getContext(), R.font.ramonalight));
                        contenuTextView.setTextSize(23);
                        contenuTextView.setTextColor(Color.parseColor("#4E4545"));
                        contenuTextView.setTypeface(contenuTextView.getTypeface(), Typeface.BOLD);

                        // Ajouter les TextView au LinearLayout
                        journalLayout.addView(dateTextView);
                        journalLayout.addView(titreTextView);
                        journalLayout.addView(contenuTextView);


                        journalHome = rootView.findViewById(R.id.jhome);
                        journalHome.addView(journalLayout);



                        Toast.makeText(getContext(), key+" "+annee+" "+titre, Toast.LENGTH_SHORT).show();



                        // quand je clique =>
                            journalLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Démarrer l'activité de destination ici

                                    UserId idregister=new UserId();
                                    idregister.setIdregister(key);
                                    navigateToSuppModifier();

                                }

                                private void navigateToSuppModifier() {
                                    Intent intent = new Intent(requireContext(), ModiferSupprimer.class);
                                    startActivity(intent);
                                }
                            });

                    }
                    }
                } else {
                    System.out.println("Aucune donnée trouvée");



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Erreur : " + databaseError.getMessage());
            }
        });










        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Arrêt de l'animation lorsque la vue est détruite
        if (animator != null) {
            animator.cancel();
        }
    }
    public static String getMoisEnFrancais(int mois) {
        String[] moisEnFrancais = new DateFormatSymbols(Locale.FRENCH).getMonths();
        return moisEnFrancais[mois - 1];
    }


}

