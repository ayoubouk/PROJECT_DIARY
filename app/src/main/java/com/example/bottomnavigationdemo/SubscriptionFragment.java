package com.example.bottomnavigationdemo;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;


public class SubscriptionFragment extends Fragment {
    private LinearLayout imageContainer;
    private ImageView imageView;
    private ValueAnimator animator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView=inflater.inflate(R.layout.fragment_subscription, container, false);
        imageContainer = rootView.findViewById(R.id.imageContainer);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imagesRef = storage.getReference().child(UserId.uid);
        ConstraintLayout galerieLayout = rootView.findViewById(R.id.galerielayout);

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
                galerieLayout.setTranslationY(value);
            }
        });

        // Démarrage de l'animation
        animator.start();

        // Récupération de la liste des références des images
        imagesRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {

                // Parcours de la liste des références des images
                for (StorageReference imageRef : listResult.getItems()) {
                    // Obtention de l'URL de téléchargement de l'image
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Votre code à exécuter après un délai de 3 secondes
                                    galerieLayout.setVisibility(View.GONE);                                }
                            }, 0);
                            String imageUrl = uri.toString();

                            // Chargement de l'image dans un ImageView en utilisant Picasso
                            ImageView imageView = new ImageView(getContext());
                            Picasso.get().load(imageUrl).into(imageView);

                            imageView.setPadding(20, 20, 20, 20);
                            // Ajout de l'ImageView au conteneur d'images
                            imageContainer.addView(imageView);
                        }
                    });
                }
            }
        });

        return rootView;
    }
}