//-------------------------------------------------------------------------------------------------------------------------------------
//------------------------------------CONDITIONNEMENT DES COMPRIMES--------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------
package com.example.alessio.projetfinal.Activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alessio.projetfinal.R;

public class TroisActivity extends Activity {

    //Composants
    private Button bt_main_Compri;
    private ReadCompS7 readcompS7;
    private WriteTaskS7 writeS7;
    private NetworkInfo network;
    private ConnectivityManager connexStatus;
    private TextView textViewIP;
    private TextView projValeur;
    private TextView ArrBout;
    private TextView SelecServ;
    private TextView BtnQ1;
    private TextView BtnQ2;
    private TextView BtnQ3;
    private TextView valvePilu;
    private TextView valveCont;
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

    // Button bt_main_SU;

    //Variables
    //String email;
    //String nom;
    //String prenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trois);

        textViewIP = (TextView)findViewById(R.id.textViewIP);
        projValeur = (TextView)findViewById(R.id.projValeur);

        ArrBout = (TextView)findViewById(R.id.ArrBout);
        SelecServ = (TextView)findViewById(R.id.SelecServ);
        BtnQ1 = (TextView)findViewById(R.id.BtnQ1);
        BtnQ2 = (TextView)findViewById(R.id.BtnQ2);
        BtnQ3 = (TextView)findViewById(R.id.BtnQ3);
        valvePilu = (TextView)findViewById(R.id.valvePilu);
        valveCont = (TextView)findViewById(R.id.valveCont);
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

        bt_main_Compri = (Button)findViewById(R.id.bt_main_Compri);
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

    public void onMainClic(View v) {
        switch(v.getId())
        {

            case R.id.bt_main_Compri:
                if (network != null && network.isConnectedOrConnecting()) {
                    if (bt_main_Compri.getText().equals("Se connecter")) {
                        bt_main_Compri.setText("Se déconnecter");

                        TextViewAdresse.setText(EditTextAdresse.getText().toString());
                        TextViewRack.setText(EditTextRack.getText().toString());
                        TextViewSlot.setText(EditTextSlot.getText().toString());

                        readcompS7 = new ReadCompS7( v, bt_main_Compri, textViewIP, projValeur, ArrBout, SelecServ, BtnQ1, BtnQ2, BtnQ3, valvePilu, valveCont, valveDistant, TextViewA1, TextViewAdresse, TextViewR1, TextViewRack, TextViewS1, TextViewSlot, EditTextAdresse,EditTextRack,EditTextSlot);

                        readcompS7.Start(TextViewAdresse.getText().toString(), TextViewRack.getText().toString(), TextViewSlot.getText().toString());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        writeS7 = new WriteTaskS7();
                        writeS7.Start(TextViewAdresse.getText().toString(), TextViewRack.getText().toString(), TextViewSlot.getText().toString());
                    } else {
                        readcompS7.Stop();
                        bt_main_Compri.setText("Se connecter");

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
