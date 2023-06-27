package com.example.bottomnavigationdemo;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private ImageView image;
    private ValueAnimator animator;

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
}

