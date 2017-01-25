package com.example.alessio.projetfinal.Activity;//-------ASSERVISSEMENT DE NIVEAU DE LIQUIDE-------

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

public class ReadTaskS7 {

    //Variables
    private static final int MESSAGE_PRE_EXECUTE = 1;
    private static final int MESSAGE_PROGRESS_UPDATE = 2;
    private static final int MESSAGE_POST_EXECUTE = 3;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private Button bt_main_ConnexS7;
    private View vi_main_ui;
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

    private AutomateS7 plcS7;
    private Thread readThread;

    private S7Client comS7;
    private String[] param = new String[10];
    private byte[] datasPLC = new byte[512];

    public ReadTaskS7(View v, Button b, TextView y, TextView z, TextView j, TextView l, TextView m, TextView n, TextView o, TextView p, TextView q1, TextView q, TextView r1, TextView r,
                      TextView s1, TextView s, EditText c, EditText d, EditText e) {
        vi_main_ui = v;
        bt_main_ConnexS7 = b;
        textViewIP = y;
        projValeur = z;
        valve1 = j;
        valve2 = l;
        valve3 = m;
        valve4 = n;
        valveAuto = o;
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

        //Niveau de l'eau----------------------------------------------------------
        projValeur.setText("Niveau de l'eau : " + S7.GetWordAt(datasPLC, 16) + "");

        //Valve 1-------------------------------
        if(S7.GetBitAt(datasPLC, 0, 1) == false){
            valve1.setText("Valve 1 ouverte");
            valve1.setTextColor(Color.rgb(94,255,0));
        }
        if(S7.GetBitAt(datasPLC, 0, 1) == true){
            valve1.setText("Valve 1 fermée");
            valve1.setTextColor(Color.rgb(255,111,00));
        }

        //Valve 2-------------------------------
        if(S7.GetBitAt(datasPLC, 0, 2) == false){
            valve2.setText("Valve 2 ouverte");
            valve2.setTextColor(Color.rgb(94,255,0));
        }
        if(S7.GetBitAt(datasPLC, 0, 2) == true){
            valve2.setText("Valve 2 fermée");
            valve2.setTextColor(Color.rgb(255,111,00));
        }

        //Valve 3-------------------------------
        if(S7.GetBitAt(datasPLC, 0, 3) == false){
            valve3.setText("Valve 3 ouverte");
            valve3.setTextColor(Color.rgb(94,255,0));
        }
        if(S7.GetBitAt(datasPLC, 0, 3) == true){
            valve3.setText("Valve 3 fermée");
            valve3.setTextColor(Color.rgb(255,111,00));
        }

        //Valve 4-------------------------------
        if(S7.GetBitAt(datasPLC, 0, 4) == false){
            valve4.setText("Valve 4 ouverte");
            valve4.setTextColor(Color.rgb(94,255,0));
        }
        if(S7.GetBitAt(datasPLC, 0, 4) == true){
            valve4.setText("Valve 4 fermée");
            valve4.setTextColor(Color.rgb(255,111,00));
        }

        //Bouton manu/auto----------------------
        if(S7.GetBitAt(datasPLC, 0, 5) == true){
            valveAuto.setText("Automatique");
        }
        if(S7.GetBitAt(datasPLC, 0, 5) == false){
            valveAuto.setText("Manuelle");
        }

        //Bouton locale/distant-----------------
        if(S7.GetBitAt(datasPLC, 0, 6) == true){
            valveDistant.setText("Distant");
        }
        if(S7.GetBitAt(datasPLC, 0, 6) == false){
            valveDistant.setText("Locale");
        }

    }

    private void downloadOnPostExecute(){
        Toast.makeText(vi_main_ui.getContext(), "Déconnecté du Wifi", Toast.LENGTH_SHORT).show();
        textViewIP.setText("Etat : déconnecté");

        projValeur.setText("Niveau de l'eau non connecté");
        valve1.setText("Valve 1 non connectée");
        valve1.setTextColor(Color.rgb(255,255,255));
        valve2.setText("Valve 2 non connectée");
        valve2.setTextColor(Color.rgb(255,255,255));
        valve3.setText("Valve 3 non connectée");
        valve3.setTextColor(Color.rgb(255,255,255));
        valve4.setText("Valve 4 non connectée");
        valve4.setTextColor(Color.rgb(255,255,255));
        valveAuto.setText("Manu/auto non connecté");
        valveDistant.setText("Locale/Distant non connecté");
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