package com.example.alessio.projetfinal.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UserBddSqlite extends SQLiteOpenHelper {

    private static final String TABLE_USER = "table_user";
    private static final String COL_ID = "ID";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_PASSWORD = "PASSWORD";
    private static final String COL_NOM = "NOM";
    private static final String COL_PRENOM = "PRENOM";

    private static final String CREATE_DB = "CREATE TABLE " +
            TABLE_USER + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_EMAIL + " TEXT NOT NULL, " +
            COL_PASSWORD + " TEXT NOT NULL, " +
            COL_NOM + " TEXT NOT NULL, " +
            COL_PRENOM + " TEXT NOT NULL);" ;

    public UserBddSqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Super utilisateur implémenté en dur
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
        db.execSQL("INSERT INTO table_user (EMAIL, NOM, PRENOM, PASSWORD) VALUES ('android', 'super', 'utilisateur', 'android3')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_USER);
        onCreate(db);
    }
}
