package sr.dlamini.flex_it;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.material.internal.ManufacturerUtils;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "FlexIt";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE User" +
                "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FirstName text, LastName text ,IdNumber text" +
                ", Email text ,Password text)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

        String CREATE_FACILITY_TABLE = "CREATE TABLE Facility" +
                "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FacilityName text, Capacity Integer , Price Integer)";
        sqLiteDatabase.execSQL(CREATE_FACILITY_TABLE);

        String CREATE_BOOKING_TABLE = "CREATE TABLE Booking" +
                "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " FacilityName text ,Email text, DateBooked text , TimeIn text,  TimeOut text)";
        sqLiteDatabase.execSQL(CREATE_BOOKING_TABLE);

        String ADD_FAC = "insert into facility values (1,'Parking', 50, 250);";
        String ADD_FAC1 = "insert into facility values (2, 'Mandela', 60, 150);";
        String ADD_FAC2 = "insert into facility values (3, 'Ampithea', 20, 10);";
        String ADD_FAC3 = "insert into facility values (4, 'Nsika building', 50, 250);";
        String ADD_FAC4 = "insert into facility values (5, 'E001', 500, 850);";
        sqLiteDatabase.execSQL(ADD_FAC);
        sqLiteDatabase.execSQL(ADD_FAC1);
        sqLiteDatabase.execSQL(ADD_FAC2);
        sqLiteDatabase.execSQL(ADD_FAC3);
        sqLiteDatabase.execSQL(ADD_FAC4);

        String ADD_ADMIN = "insert into user values (1, 'admin', 'admin', 'admin', 'admin@ufs.ac.za','admin' )";
        sqLiteDatabase.execSQL(ADD_ADMIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS User");
        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS Facility");
        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS Booking");
        this.onCreate(sqLiteDatabase);
    }

    public boolean addUser(String fName, String lName,
                           String IdNumber, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FirstName", fName);
        values.put("LastName", lName);
        values.put("IdNumber", IdNumber);
        values.put("Email", email);
        values.put("Password", password);
        long results = db.insert("User", null, values);
        return results > -1;
    }

    public boolean addBooking(String email, String facilityId, String date, String TimeIn, String TimeOut) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email", email);
        values.put("FacilityName", facilityId);
        values.put("DateBooked", date);
        values.put("TimeIn", TimeIn);
        values.put("TimeOut", TimeOut);
        long results = db.insert("Booking", null, values);
        return results > -1;
    }

    public boolean addFacility(String FacilityName, String Capacity, String Price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FacilityName", FacilityName);
        values.put("Capacity", Capacity);
        values.put("Price", Price);
        long results = db.insert("Facility", null, values);
        return results > -1;
    }

    public Cursor getData(String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + table_name;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getData(String table_name, String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean delete(String table, String where) {
        SQLiteDatabase db = this.getWritableDatabase();
        long results = db.delete(table, where, null);
        return results > -1;
    }
}
