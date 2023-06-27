package com.example.bottomnavigationdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ProfilFragment extends Fragment {
    TextView logout,username,userid,email;
    DatabaseReference databaseRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_profile, container, false);

        logout=rootView.findViewById(R.id.logout);
        // Ajoutez un écouteur de clic au TextView logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Déconnectez Firebase ici
                FirebaseAuth.getInstance().signOut();
                // Vous pouvez également effectuer d'autres opérations de nettoyage ou de redirection ici

                // Affichez un message ou effectuez une action après la déconnexion
                Toast.makeText(getActivity(), "Déconnecté avec succès", Toast.LENGTH_SHORT).show();

                // Redirigez l'utilisateur vers l'activité de connexion
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });


        //
        username=rootView.findViewById(R.id.UserName);
        userid=rootView.findViewById(R.id.UserId);
        email=rootView.findViewById(R.id.email);

        //recuperer le email et le nom dans la base et les positionne dans profil
        databaseRef = FirebaseDatabase.getInstance().getReference("données");
        databaseRef.child(UserId.uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email1 = dataSnapshot.child("email").getValue(String.class);
                    username.setText(name);
                    userid.setText(UserId.uid);
                    email.setText(email1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Gérer les erreurs de lecture depuis la base de données
            }
        });


        return rootView;


    }


}
