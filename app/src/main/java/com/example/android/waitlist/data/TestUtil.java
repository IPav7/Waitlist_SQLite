package com.example.android.waitlist.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavin on 05.04.2018.
 */

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null)
            return;

        List<ContentValues> values = new ArrayList<>();

        ContentValues contentValues = new ContentValues();
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, "John");
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, 12);
        values.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, "Tim");
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, 2);
        values.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, "Jessica");
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, 99);
        values.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, "Larry");
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, 1);
        values.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, "Kim");
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, 45);
        values.add(contentValues);

        try {
            db.beginTransaction();

            db.delete(WaitlistContract.WaitlistEntry.TABLE_NAME, null, null);

            for (ContentValues cv :
                    values) {
                db.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            Log.i("ERROR", "SQLITE ERROR");
        }
        finally {
            db.endTransaction();
        }
    }

}
