package com.byethost12.kitm.mobiliaplikacija;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mokytojas on 2018-02-07.
 */

public class DatabaseSQLite extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION   = 1;
    private static final String DATABASE_NAME   = "db";

    private static final String TABLE_USERS     = "users";
    private static final String USER_ID         = "id";
    private static final String USER_LEVEL      = "userlevel";
    private static final String USER_NAME       = "name";
    private static final String USER_PASSWORD   = "password";
    private static final String USER_EMAIL      = "email";

    private static final String TABLE_POKEMONS      = "pokemonai";
    private static final String POKEMON_ID          = "id";
    private static final String POKEMON_NAME        = "name";
    private static final String POKEMON_CP          = "cp";
    private static final String POKEMON_ABILITIES   = "abilities";
    private static final String POKEMON_TYPE        = "type";
    private static final String POKEMON_WEIGHT      = "weight";
    private static final String POKEMON_HEIGHT      = "height";

    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USER_ID + " INTEGER PRIMARY KEY,"
                + USER_LEVEL + " TEXT,"
                + USER_NAME + " TEXT,"
                + USER_PASSWORD + " TEXT,"
                + USER_EMAIL + ")";

        String CREATE_POKEMONS_TABLE = "CREATE TABLE " + TABLE_POKEMONS + "("
                + POKEMON_ID + " INTEGER PRIMARY KEY,"
                + POKEMON_NAME + " TEXT,"
                + POKEMON_CP + " TEXT,"
                + POKEMON_ABILITIES + " TEXT,"
                + POKEMON_TYPE + " TEXT,"
                + POKEMON_WEIGHT + " REAL,"
                + POKEMON_HEIGHT + ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_POKEMONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_LEVEL,      user.getUserlevel());
        values.put(USER_NAME,       user.getUsernameForRegister());
        values.put(USER_PASSWORD,   user.getPasswordForRegister());
        values.put(USER_EMAIL,      user.getEmailForRegister());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);

        // Closing database connection
        db.close();
    }

    User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{
                        USER_ID,
                        USER_LEVEL,
                        USER_NAME,
                        USER_PASSWORD,
                        USER_EMAIL
                },
                USER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();

                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUserlevel(cursor.getString(1));
                user.setUsernameForRegister(cursor.getString(2));
                user.setPasswordForRegister(cursor.getString(3));
                user.setEmailForRegister(cursor.getString(4));

                // adding user to list
                users.add(user);
            } while (cursor.moveToNext());
        }

        // return users list
        return users;
    }

    void addPokemon(Pokemonas pokemonas) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(POKEMON_NAME,        pokemonas.getName());
        values.put(POKEMON_CP,          pokemonas.getCp());
        values.put(POKEMON_ABILITIES,   pokemonas.getAbilities());
        values.put(POKEMON_TYPE,        pokemonas.getType());
        values.put(POKEMON_WEIGHT,      pokemonas.getWeight());
        values.put(POKEMON_HEIGHT,      pokemonas.getHeight());

        // Inserting Row
        db.insert(TABLE_POKEMONS, null, values);

        // Closing database connection
        db.close();
    }

    public List<Pokemonas> getAllPokemonai() {
        List<Pokemonas> pokemonai = new ArrayList<Pokemonas>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_POKEMONS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Pokemonas pokemonas = new Pokemonas();

                pokemonas.setId(Integer.parseInt(cursor.getString(0)));
                pokemonas.setName(cursor.getString(1));
                pokemonas.setCp(cursor.getString(2));
                pokemonas.setAbilities(cursor.getString(3));
                pokemonas.setType(cursor.getString(4));
                pokemonas.setWeight(cursor.getDouble(5));
                pokemonas.setHeight(cursor.getDouble(6));

                // adding user to list
                pokemonai.add(pokemonas);
            } while (cursor.moveToNext());
        }

        // return pokemonaiSQLite list
        return pokemonai;
    }

    public List<Pokemonas> getPokemonByName(String name) {
        List<Pokemonas> pokemonai = new ArrayList<Pokemonas>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM pokemonai WHERE name LIKE '%"+name+"%'", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Pokemonas pokemonas = new Pokemonas();

                pokemonas.setId(Integer.parseInt(cursor.getString(0)));
                pokemonas.setName(cursor.getString(1));
                pokemonas.setCp(cursor.getString(2));
                pokemonas.setAbilities(cursor.getString(3));
                pokemonas.setType(cursor.getString(4));
                pokemonas.setWeight(cursor.getDouble(5));
                pokemonas.setHeight(cursor.getDouble(6));

                // adding user to list
                pokemonai.add(pokemonas);
            } while (cursor.moveToNext());
        }

        // return pokemonaiSQLite list
        return pokemonai;

    }

    public Pokemonas getPokemonas(int id) {
        Pokemonas pokemonas = new Pokemonas();

        List<Pokemonas> pokemonai = new ArrayList<Pokemonas>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM pokemonai WHERE id = " + id + "", null);
        if (cursor.moveToFirst()) {
            do {
                pokemonas.setId(Integer.parseInt(cursor.getString(0)));
                pokemonas.setName(cursor.getString(1));
                pokemonas.setCp(cursor.getString(2));
                pokemonas.setAbilities(cursor.getString(3));
                pokemonas.setType(cursor.getString(4));
                pokemonas.setWeight(cursor.getDouble(5));
                pokemonas.setHeight(cursor.getDouble(6));

                pokemonai.add(pokemonas);
            } while (cursor.moveToNext());
        }
        return pokemonai.get(0);
    }


    public boolean isValidUser(String username, String password){
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE "
                        + USER_NAME + "='" + username + "'AND " +
                        USER_PASSWORD + "='" + password + "'" , null);
        if (c.getCount() > 0)
            return true;
        return false;
    }

    public void updatePokemon(Pokemonas pokemon){
        ContentValues cv = new ContentValues();
        cv.put(POKEMON_NAME,pokemon.getName());
        cv.put(POKEMON_CP,pokemon.getCp());
        cv.put(POKEMON_ABILITIES,pokemon.getAbilities());
        cv.put(POKEMON_TYPE,pokemon.getType());
        cv.put(POKEMON_WEIGHT,pokemon.getWeight());
        cv.put(POKEMON_HEIGHT,pokemon.getHeight());

        getReadableDatabase().update(TABLE_POKEMONS, cv, " id = "+pokemon.getId(), null);
    }
}
