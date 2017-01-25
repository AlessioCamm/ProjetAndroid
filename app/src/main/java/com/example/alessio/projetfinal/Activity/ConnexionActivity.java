package com.example.alessio.projetfinal.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alessio.projetfinal.BDD.UserAccessDB;
import com.example.alessio.projetfinal.R;

public class ConnexionActivity extends Activity {

    //Composants
    Button connexion;
    EditText email;
    EditText password;
    EditText nom;
    EditText prenom;

    //Variables
    private int count = 0;
    boolean flag_connect = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        //Associations des éléments avec la vue XML
        connexion = (Button) findViewById(R.id.bt_connexion_connect);
        email = (EditText) findViewById(R.id.EditTextMailCo);
        password = (EditText) findViewById(R.id.EditTextPasswordCo);
        nom = (EditText) findViewById(R.id.EditTextNomIns);
        prenom = (EditText) findViewById(R.id.EditTextPrenomIns);
    }

    //Retour au menu
    public void VueRetour(View v){
        switch(v.getId()){
            case R.id.bt_retourCo:
                Intent retour = new Intent(this, MenuActivity.class);
                startActivity(retour);
                break;
        }
    }

    //Aller vers inscription
    public void VueIns(View v){
        switch(v.getId()){
            case R.id.bt_connexion_inscription:
                Intent insc = new Intent(this, InscriptionActivity.class);
                startActivity(insc);
                break;
        }
    }

    public void onClickConnexion(View v) {
        switch (v.getId()) {
            case R.id.bt_connexion_connect:

                UserAccessDB utilisateurdb = new UserAccessDB(this);
                utilisateurdb.openForRead();
                //Vérifie l'existence de l'utilisateur dans la base de données
                count = utilisateurdb.getProfilesCount(email.getText().toString().toLowerCase().trim());
                utilisateurdb.getProfilesCount(email.getText().toString().toLowerCase().trim());
                utilisateurdb.Close();

                //Si l'utilisateur n'existe pas ou est vide, ou mot de passe pas bon
                if(count == 0 || email.getText().toString().toLowerCase().trim().isEmpty() || password.getText().toString().toLowerCase().trim().isEmpty() || password.getText().toString().length() < 4)
                {
                    Toast.makeText(this, "Combinaison mail/mot de passe incorrecte", Toast.LENGTH_SHORT).show();
                    Intent retour = new Intent(this, MenuActivity.class);
                    finish();
                    startActivity(retour);
                    flag_connect = false;
                }
                //Accès aux applications quand c'est bon
                if(flag_connect)
                {
                    Toast.makeText(this, "Utilisateur connecté", Toast.LENGTH_SHORT).show();
                    Intent menu = new Intent(this, PageActivity.class);
                    finish();
                    menu.putExtra("email", email.getText().toString().toLowerCase().trim());
                    startActivity(menu);
                }
                break;
        }
        flag_connect = true;
    }
}
