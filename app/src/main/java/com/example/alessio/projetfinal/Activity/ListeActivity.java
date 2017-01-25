package com.example.alessio.projetfinal.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.alessio.projetfinal.BDD.User;
import com.example.alessio.projetfinal.BDD.UserAccessDB;
import com.example.alessio.projetfinal.R;

import java.util.ArrayList;

public class ListeActivity extends Activity {

    ListView ListViewUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        ListViewUsers = (ListView) findViewById(R.id.ListViewUsers);

        //Mise en place de la base de données et de la lecture
        UserAccessDB userDB = new UserAccessDB(this);

        userDB.openForRead();
        ArrayList<User> tab_user = userDB.getAllUser();
        userDB.Close();

        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tab_user);
        ListViewUsers.setAdapter(adapter);
    }
}
