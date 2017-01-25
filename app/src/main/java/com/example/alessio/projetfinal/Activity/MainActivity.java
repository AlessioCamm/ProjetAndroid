//-------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------- ASSERVISSEMENT DES NIVEAUX DE LIQUIDE -----------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------
package com.example.alessio.projetfinal.Activity;

import android.app.Activity;
import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alessio.projetfinal.R;

public class MainActivity extends Activity {

    private Button bt_main_ConnexS7;
    private ReadTaskS7 readS7;
    private WriteTaskS7 writeS7;
    private NetworkInfo network;
    private ConnectivityManager connexStatus;
    private TextView textViewIP;
    private TextView projValeur;
    private TextView valve1;
    private TextView valve2;
    private TextView valve3;
    private TextView valve4;
    private TextView valveAuto;
    private TextView valveDistant;
    private TextView TextViewAdresse;
    private TextView TextViewA1;
    private TextView TextViewRack;
    private TextView TextViewR1;
    private TextView TextViewSlot;
    private TextView TextViewS1;

    private EditText EditTextAdresse;
    private EditText EditTextRack;
    private EditText EditTextSlot;

    //Button bt_main_SU;

    //String email;
    //String nom;
    //String prenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewIP = (TextView)findViewById(R.id.textViewIP);
        projValeur = (TextView)findViewById(R.id.projValeur);

        valve1 = (TextView)findViewById(R.id.valve1);
        valve2 = (TextView)findViewById(R.id.valve2);
        valve3 = (TextView)findViewById(R.id.valve3);
        valve4 = (TextView)findViewById(R.id.valve4);
        valveAuto = (TextView)findViewById(R.id.valveAuto);
        valveDistant = (TextView)findViewById(R.id.valveDistant);

        TextViewAdresse = (TextView)findViewById(R.id.TextViewAdresse);
        TextViewA1 = (TextView)findViewById(R.id.TextViewA1);
        TextViewRack = (TextView)findViewById(R.id.TextViewRack);
        TextViewR1 = (TextView)findViewById(R.id.TextViewR1);
        TextViewSlot = (TextView)findViewById(R.id.TextViewSlot);
        TextViewS1 = (TextView)findViewById(R.id.TextViewS1);

        EditTextAdresse = (EditText)findViewById(R.id.EditTextAdresse);
        EditTextRack = (EditText)findViewById(R.id.EditTextRack);
        EditTextSlot = (EditText)findViewById(R.id.EditTextSlot);

        bt_main_ConnexS7 = (Button)findViewById(R.id.bt_main_ConnexS7);
        connexStatus = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        network = connexStatus.getActiveNetworkInfo();

        //bt_main_SU = (Button) findViewById(R.id.bt_main_SU);

        //Récupération de l'email dans la liste des applications
       /* Intent intent = getIntent();
        email = intent.getStringExtra("email");
        nom = intent.getStringExtra("nom");
        prenom = intent.getStringExtra("prenom");

        if(email.equals("android"))
        {
            //Quand Super Utilisateur est connecté
            bt_main_SU.setVisibility(View.VISIBLE);
        }
        else
        {

        }*/

    }

    public void onMainClickManager(View v) {
        switch(v.getId())
        {

            case R.id.bt_main_ConnexS7:
                if (network != null && network.isConnectedOrConnecting()) {
                    if (bt_main_ConnexS7.getText().equals("Se connecter")) {
                        bt_main_ConnexS7.setText("Se déconnecter");

                        TextViewAdresse.setText(EditTextAdresse.getText().toString());
                        TextViewRack.setText(EditTextRack.getText().toString());
                        TextViewSlot.setText(EditTextSlot.getText().toString());

                        readS7 = new ReadTaskS7( v, bt_main_ConnexS7, textViewIP, projValeur, valve1, valve2, valve3, valve4, valveAuto, valveDistant, TextViewA1, TextViewAdresse, TextViewR1, TextViewRack, TextViewS1, TextViewSlot, EditTextAdresse,EditTextRack,EditTextSlot);

                        readS7.Start(TextViewAdresse.getText().toString(), TextViewRack.getText().toString(), TextViewSlot.getText().toString());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        writeS7 = new WriteTaskS7();
                        writeS7.Start(TextViewAdresse.getText().toString(), TextViewRack.getText().toString(), TextViewSlot.getText().toString());
                    } else {
                        readS7.Stop();
                        bt_main_ConnexS7.setText("Se connecter");

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
                else {
                    Toast.makeText(this, "Connexion au réseau impossible, merci de vérifier", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
