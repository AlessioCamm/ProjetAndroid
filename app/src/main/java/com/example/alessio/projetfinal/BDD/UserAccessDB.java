package com.example.alessio.projetfinal.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class UserAccessDB {

    //initialisation des variables
    private static final int VERSION = 1;
    private static final String NAME_DB = "User.db";
    private static final String TABLE_USER = "table_user";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_EMAIL = "EMAIL";
    private static final int NUM_COL_EMAIL = 1;
    private static final String COL_PASSWORD = "PASSWORD";
    private static final int NUM_COL_PASSWORD = 2;
    private static final String COL_NOM = "NOM";
    private static final int NUM_COL_NOM = 3;
    private static final String COL_PRENOM = "PRENOM";
    private static final int NUM_COL_PRENOM = 4;


    private SQLiteDatabase db;
    private UserBddSqlite userdb;

    public UserAccessDB(Context c) {
        userdb = new UserBddSqlite(c, NAME_DB, null, VERSION);
    }

    public void openForWrite() {
        db = userdb.getWritableDatabase();
    }

    public void openForRead() {
        db = userdb.getReadableDatabase();
    }

    public void Close() {
        db.close();
    }

    public long insertUser(User u) {
        ContentValues content = new ContentValues();
        content.put(COL_EMAIL, u.getEmail());
        content.put(COL_PASSWORD, u.getPassword());
        content.put(COL_NOM, u.getNom());
        content.put(COL_PRENOM, u.getPrenom());


        return db.insert(TABLE_USER, null, content);
    }

    public int updateUser(int i, User u) {
        ContentValues content = new ContentValues();
        content.put(COL_EMAIL, u.getEmail());
        content.put(COL_PASSWORD, u.getPassword());
        content.put(COL_NOM, u.getNom());
        content.put(COL_PRENOM, u.getPrenom());

        return db.update(TABLE_USER, content, COL_ID + " = " + i, null);
    }

    public int removeUser(String email) {
        return db.delete(TABLE_USER, COL_EMAIL + " = " + email, null);
    }

    public ArrayList<User> getAllUser() {
        Cursor c = db.query(TABLE_USER, new String[]{COL_ID, COL_EMAIL, COL_PASSWORD, COL_NOM, COL_PRENOM},
                null, null, null, null, null);

        ArrayList<User> tabUser = new ArrayList<User>();

        if (c.getCount() == 0) {
            c.close();
            return null;
        }

        while (c.moveToNext()) {
            User user1 = new User();
            user1.setId(c.getInt(NUM_COL_ID));
            user1.setEmail(c.getString(NUM_COL_EMAIL));
            user1.setPassword(c.getString(NUM_COL_PASSWORD));
            user1.setNom(c.getString(NUM_COL_NOM));
            user1.setPrenom(c.getString(NUM_COL_PRENOM));


            tabUser.add(user1);
        }
        c.close();
        return tabUser;
    }

    //Vérifie l'existence de l'utilisateur dans la base de données
    public int getProfilesCount(String email) {
        openForRead();
        Cursor c = db.query(TABLE_USER, new String[]{COL_ID, COL_EMAIL}, COL_EMAIL + " LIKE '" + email + "'", null, null, null, null, null);

        int cnt = c.getCount();
        c.close();
        return cnt;

    }
}
