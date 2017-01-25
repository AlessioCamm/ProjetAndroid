package com.example.alessio.projetfinal.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alessio.projetfinal.Activity.ConnexionActivity;
import com.example.alessio.projetfinal.BDD.User;
import com.example.alessio.projetfinal.BDD.UserAccessDB;
import com.example.alessio.projetfinal.R;

public class InscriptionActivity extends Activity {

    //Composants
    Button inscription;
    EditText email;
    EditText password;
    EditText nom;
    EditText prenom;

    //Variables
    UserAccessDB userDB = new UserAccessDB(this);
    int count =0;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        //Associations des éléments avec XML
        prenom = (EditText)findViewById(R.id.EditTextPrenomIns);
        nom = (EditText)findViewById(R.id.EditTextNomIns);
        email = (EditText)findViewById(R.id.EditTextMailIns);
        password = (EditText)findViewById(R.id.EditTextPasswordIns);
        inscription = (Button)findViewById(R.id.bt_inscription_ins);
    }

    public void onClickInscription(View v) {
        switch (v.getId()) {
            case R.id.bt_inscription_ins:
                //Vérification de doublon
                count = userDB.getProfilesCount(email.getText().toString());
                userDB.getProfilesCount(email.getText().toString());

                if(prenom.getText().toString().length() < 3)//Si prénom vide ou plus petit de 3 caractères, refusé
                {
                    Toast.makeText(this, "Veuillez entrer un prénom correct", Toast.LENGTH_SHORT).show();
                    flag = false;
                }

                else if(nom.getText().toString().length() < 3)//Si nom vide ou plus petit de 3 caractères, refusé
                {
                    Toast.makeText(this, "Veuillez entrer un nom correct", Toast.LENGTH_SHORT).show();
                    flag = false;
                }

                else if (count == 1)//Mail entré existant, donc refusé
                {
                    Toast.makeText(this, "Cette adresse mail est déjà utilisée", Toast.LENGTH_SHORT).show();
                    flag = false;
                }

                else if(email.getText().toString().length() < 10)//Si mail vide ou plus petit que 10 caractères, refusé
                {
                    Toast.makeText(this, "Veuillez entrer un mail correct", Toast.LENGTH_SHORT).show();
                    flag = false;
                }

                else if(password.getText().toString().length() < 4)//Si MDP vide ou plus petit de 4 caractères, refusé
                {
                    Toast.makeText(this, "Veuillez entrer un mot de passe correct", Toast.LENGTH_SHORT).show();
                    flag = false;
                }

                else if(prenom.getText().toString().isEmpty() || nom.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                {
                    Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    flag = false;
                }

                else if(flag)//Verrou, et quand c'est bon..
                {
                    User user1 = new User(email.getText().toString(), password.getText().toString(), nom.getText().toString(), prenom.getText().toString());
                    //ajout de la ligne de l'utilisateur dans la bdd
                    userDB.openForWrite();
                    userDB.insertUser(user1);
                    userDB.Close();
                    Toast.makeText(this, "Vous êtes inscrit(e)", Toast.LENGTH_SHORT).show();
                    finish();

                    Intent connexion = new Intent(this, ConnexionActivity.class);
                    finish();
                    startActivity(connexion);
                }
                break;
        }
        //Verrou placé sur "true" (sinon sur "false")
        flag = true;
    }

    public void VueRetCo(View v){
        switch(v.getId()){
            case R.id.bt_retourConnex:
                //Retour à Connexion
                Intent insc = new Intent(this, ConnexionActivity.class);
                startActivity(insc);
                break;
        }
    }
}
