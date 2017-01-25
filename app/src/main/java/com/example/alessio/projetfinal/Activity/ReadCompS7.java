package com.example.alessio.projetfinal.Activity; //-------CONDITIONNEMENT DES COMPRIMES-------

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.atomic.AtomicBoolean;


import SimaticS7.S7;
import SimaticS7.S7Client;
import SimaticS7.S7OrderCode;

public class ReadCompS7 {
    private static final int MESSAGE_PRE_EXECUTE = 1;
    private static final int MESSAGE_PROGRESS_UPDATE = 2;
    private static final int MESSAGE_POST_EXECUTE = 3;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private Button bt_main_Compri;
    private View vi_main_ui;
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

    private AutomateS7 plcS7;
    private Thread readThread;

    private S7Client comS7;
    private String[] param = new String[10];
    private byte[] datasPLC = new byte[512];

    public ReadCompS7(View v, Button b, TextView y, TextView z,TextView a, TextView j,  TextView m, TextView n, TextView i, TextView f, TextView o, TextView p, TextView q1, TextView q, TextView r1, TextView r,
                      TextView s1, TextView s, EditText c, EditText d, EditText e) {
        vi_main_ui = v;
        bt_main_Compri = b;
        textViewIP = y;
        projValeur = z;
        ArrBout = a;
        SelecServ = j;
        BtnQ1 = m;
        BtnQ2 = n;
        BtnQ3 = i;
        valvePilu = f;
        valveCont = o;
        valveDistant = p;
        TextViewA1 = q1;
        TextViewAdresse = q;
        TextViewR1 = r1;
        TextViewRack = r;
        TextViewS1 = s1;
        TextViewSlot = s;

        EditTextAdresse = c;
        EditTextRack = d;
        EditTextRack = e;

        comS7 = new S7Client();
        plcS7 = new AutomateS7();

        readThread = new Thread(plcS7);
    }

    //VOIR WRITETASKS7
    public void Stop() {
        isRunning.set(false);
        comS7.Disconnect();
        readThread.interrupt();
    }

    public void Start(String a, String r, String s) {
        if (!readThread.isAlive()) {
            param[0] = a;
            param[1] = r;
            param[2] = s;

            readThread.start();
            isRunning.set(true);
        }
    }

    private void downloadOnPreExecute (int t){
        Toast.makeText(vi_main_ui.getContext(), "Connecté en WiFi", Toast.LENGTH_SHORT).show();
        textViewIP.setText("Etat : connecté à PLC " + String.valueOf(t));
        TextViewA1.setText("Connecté à IP");
        TextViewAdresse.setTextColor(Color.rgb(94,255,0));
        TextViewR1.setText("Connecté à rack n°");
        TextViewRack.setTextColor(Color.rgb(94,255,0));
        TextViewS1.setText("Connecté à slot n°");
        TextViewSlot.setTextColor(Color.rgb(94,255,0));
    }

    private void downloadOnProgressUpdate (int progress){ //Voir les données en temps réel

        //Nombre de bouteilles----------------------------------------------------------
        projValeur.setText("Nombre de bouteilles : " + S7.GetWordAt(datasPLC, 16) + "");
        if(S7.GetBitAt(datasPLC, 1,2) == false){
            projValeur.setTextColor(Color.rgb(255,255,255));
        }
        if(S7.GetBitAt(datasPLC, 1,2) == true){
            projValeur.setText("Remise à zéro du compteur");
            projValeur.setTextColor(Color.rgb(255,0,0));
        }

        //Arrivée des flacons-------------------
        if(S7.GetBitAt(datasPLC, 1,3) == false){
            ArrBout.setText("Arrivée coupée, flacons vides non disponibles");
            ArrBout.setTextColor(Color.rgb(255,00,00));
        }
        if(S7.GetBitAt(datasPLC, 1,3) == true){
            ArrBout.setText("Flacons vides disponibles");
            ArrBout.setTextColor(Color.rgb(94,255,0));
        }

        //Selecteur en service-------------------
        if(S7.GetBitAt(datasPLC, 0, 0) == false){
            SelecServ.setText("Selecteur en service non activé");
            SelecServ.setTextColor(Color.rgb(255,00,00));
        }
        if(S7.GetBitAt(datasPLC, 0, 0) == true){
            SelecServ.setText("Selecteur en service activé");
            SelecServ.setTextColor(Color.rgb(94,255,0));
        }

        //Bouton I2------------------------------
        if(S7.GetBitAt(datasPLC, 4,3) == false){
            BtnQ1.setText("Bouton I2 non activé");
            BtnQ1.setTextColor(Color.rgb(255,111,00));
        }
        if(S7.GetBitAt(datasPLC, 4,3) == true){
            BtnQ1.setText("Bouton I2 activé, 5 comprimés demandés en tube");
            BtnQ1.setTextColor(Color.rgb(94,255,0));

        }

        //Bouton I3------------------------------
        if(S7.GetBitAt(datasPLC, 4,4) == false){
            BtnQ2.setText("Bouton I3 non activé");
            BtnQ2.setTextColor(Color.rgb(255,111,00));
        }
        if(S7.GetBitAt(datasPLC, 4,4) == true){
            BtnQ2.setText("Bouton I3 activé, 10 comprimés demandés en tube");
            BtnQ2.setTextColor(Color.rgb(94,255,0));
        }

        //Bouton I4------------------------------
        if(S7.GetBitAt(datasPLC, 4,5) == false){
            BtnQ3.setText("Bouton I4 non activé");
            BtnQ3.setTextColor(Color.rgb(255,111,00));
        }
        if(S7.GetBitAt(datasPLC, 4,5) == true){
            BtnQ3.setText("Bouton I4 activé, 15 comprimés demandés en tube");
            BtnQ3.setTextColor(Color.rgb(94,255,0));
        }

        //Distributeur de pilules---------------
        if(S7.GetBitAt(datasPLC, 4,0) == false){
            valvePilu.setText("Distributeur de pilules à l'arrêt");
        }
        if(S7.GetBitAt(datasPLC, 4,0) == true){
            valvePilu.setText("Distributeur de pilules en fonction");
        }

        //Contacteur moteur de bande------------
        if(S7.GetBitAt(datasPLC, 4,1) == false){
            valveCont.setText("Moteur bande à l'arrêt");
        }
        if(S7.GetBitAt(datasPLC, 4,1) == true){
            valveCont.setText("Moteur bande en fonction");
        }

        //Contacteur moteur de bande------------
        if(S7.GetBitAt(datasPLC, 1,6) == false){
            valveDistant.setText("Local");
        }
        if(S7.GetBitAt(datasPLC, 1,6) == true){
            valveDistant.setText("Distant");
        }


    }

    private void downloadOnPostExecute(){
        Toast.makeText(vi_main_ui.getContext(), "Déconnecté du Wifi", Toast.LENGTH_SHORT).show();
        textViewIP.setText("Etat : déconnecté");
        projValeur.setText("Nombre de bouteilles non connecté");
        ArrBout.setText("Arrivée des flacons non connecté");
        ArrBout.setTextColor(Color.rgb(255,255,255));
        SelecServ.setText("Selecteur en service non connecté");
        SelecServ.setTextColor(Color.rgb(255,255,255));
        BtnQ1.setText("Bouton I2 non connecté");
        BtnQ1.setTextColor(Color.rgb(255,255,255));
        BtnQ2.setText("Bouton I3 non connecté");
        BtnQ2.setTextColor(Color.rgb(255,255,255));
        BtnQ3.setText("Bouton I4 non connecté");
        BtnQ3.setTextColor(Color.rgb(255,255,255));
        valvePilu.setText("Distributeur de pilules non connecté");
        valveCont.setText("Moteur bande non connecté");
        valveDistant.setText("Local/Distant non connecté");
        TextViewA1.setText("IP déconnectée");
        TextViewAdresse.setText("Info IP indisponible, non connecté");
        TextViewAdresse.setTextColor(Color.rgb(255,255,255));
        TextViewR1.setText("Rack déconnecté");
        TextViewRack.setText("Info du rack indisponible, non connecté");
        TextViewRack.setTextColor(Color.rgb(255,255,255));
        TextViewS1.setText("Slot déconnecté");
        TextViewSlot.setText("Info du slot indisponible, non connecté");
        TextViewSlot.setTextColor(Color.rgb(255,255,255));

    }

    private Handler monHandler = new Handler(){
        @Override
        public void handleMessage (Message msg){
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_PRE_EXECUTE:
                    downloadOnPreExecute(msg.arg1);
                    break;
                case MESSAGE_PROGRESS_UPDATE:
                    downloadOnProgressUpdate(msg.arg1);
                    break;
                case MESSAGE_POST_EXECUTE:
                    downloadOnPostExecute();
                    break;
                default:
                    break;
            }
        }
    };

    private class AutomateS7 implements Runnable{
        @Override
        public void run(){
            try{
                comS7.SetConnectionType(S7.S7_BASIC);
                Integer res = comS7.ConnectTo(param[0], Integer.valueOf(param[1]), Integer.valueOf(param[2]));

                S7OrderCode OrderCode = new S7OrderCode();
                Integer result = comS7.GetOrderCode(OrderCode);
                int numCPU=-1;
                if(res.equals(0) && result.equals(0)){
                    numCPU = Integer.valueOf(OrderCode.Code().toString().substring(5,8));
                }
                else numCPU=0000;

                sendPreExecuteMessage(numCPU);

                while(isRunning.get()){
                    if(res.equals(0)){
                        int retInfo = comS7.ReadArea(S7.S7AreaDB,5,0,20,datasPLC);
                        int data=0;
                        int dataB=0;
                        if (retInfo ==0){
                            data = S7.GetWordAt(datasPLC, 0);

                            sendProgressMessage(data);
                        }
                        Log.i("Variable A.P.I. -> ", String.valueOf(data));
                        Log.i("Niveau de l'eau ", S7.GetWordAt(datasPLC, 16)+"");
                        Log.i("Mode auto ", S7.GetBitAt(datasPLC, 0,5)+"");
                        Log.i("Valve 1 ouverte ", S7.GetBitAt(datasPLC, 0,1)+"");
                        Log.i("Valve 2 ouverte ", S7.GetBitAt(datasPLC, 0,2)+"");
                        Log.i("Valve 3 ouverte ", S7.GetBitAt(datasPLC, 0,3)+"");
                        Log.i("Valve 4 ouverte ", S7.GetBitAt(datasPLC, 0,4)+"");
                        Log.i("Local distant ", S7.GetBitAt(datasPLC, 0,6)+"");
                    }
                    try{
                        Thread.sleep(500);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                sendPostExecuteMessage();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        private void sendPostExecuteMessage(){
            Message postExecuteMsg = new Message();
            postExecuteMsg.what = MESSAGE_POST_EXECUTE;
            monHandler.sendMessage(postExecuteMsg);
        }

        private void sendPreExecuteMessage(int v) {
            Message preExecuteMsg = new Message();
            preExecuteMsg.what = MESSAGE_PRE_EXECUTE;
            preExecuteMsg.arg1 = v;
            monHandler.sendMessage(preExecuteMsg);
        }

        private void sendProgressMessage(int i) {
            Message progressMsg = new Message();
            progressMsg.what = MESSAGE_PROGRESS_UPDATE;
            progressMsg.arg1 = i;
            monHandler.sendMessage(progressMsg);
        }
    }
}