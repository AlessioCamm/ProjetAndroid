package com.example.alessio.projetfinal.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alessio.projetfinal.R;

public class PageActivity extends Activity {

    TextView textViewStatut;
    Button bt_main_Liste;

    //Variables
    String email;
    String nom;
    String prenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        textViewStatut = (TextView) findViewById(R.id.textViewStatut);
        bt_main_Liste = (Button) findViewById(R.id.bt_main_Liste);

        //Récupération de l'email dans la liste des applications
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        nom = intent.getStringExtra("nom");
        prenom = intent.getStringExtra("prenom");

        if(email.equals("android"))
        {
            //Quand Super Utilisateur est connecté
            bt_main_Liste.setVisibility(View.VISIBLE);
            textViewStatut.setText("Super utilisateur connecté");
            textViewStatut.setTextColor(Color.rgb(254,145,71));
        }
        else
        {
            textViewStatut.setText(email + " connecté");
        }
    }

    public void VueUne(View v){
        switch(v.getId()){
            case R.id.bt_main_REG:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Souhaitez-vous vérifier l'asservissement de niveau de liquide ?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick (DialogInterface dialog, int which){
                                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                dialog.cancel();
                            }
                        })
                        .create().show();
        }
    }

    public void VueDeux(View v){
        switch(v.getId()){
            case R.id.bt_main_COMP:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Souhaitez-vous vérifier le conditionnement des comprimés ?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick (DialogInterface dialog, int which){
                                Intent intent = new Intent (getApplicationContext(), TroisActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                dialog.cancel();
                            }
                        })
                        .create().show();
        }
    }

    public void VueUsers(View v){
        switch(v.getId()){
            case R.id.bt_main_Liste:
                //Retour au menu
                Intent liste = new Intent(this, ListeActivity.class);
                startActivity(liste);
                break;
        }
    }

    public void VueRetour(View v){
        switch(v.getId()){
            case R.id.bt_retour:
                //Retour au menu
                Intent retour = new Intent(this, MenuActivity.class);
                startActivity(retour);
                break;
        }
    }

}
