package com.example.alessio.projetfinal.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.alessio.projetfinal.R;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void VueUtilisateur(View v){
        switch(v.getId()){
            case R.id.bt_menu_connexionuser:
                //Mène à la vue des applications
                Intent connexionUti = new Intent(this, ConnexionActivity.class);
                startActivity(connexionUti);
                break;
        }
    }


    public void Info(View v){
        switch(v.getId()){
            case R.id.bt_info:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Application réalisée par Alessio Cammarata,\npour l'examen du cours de ''Applications sur Android'' donné par monsieur Scopel.\n \nCette application a pour but de faire office de monitoring pour un automate programmable industriel.")
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                dialog.cancel();
                            }
                        })
                        .create().show();
        }
    }
}
