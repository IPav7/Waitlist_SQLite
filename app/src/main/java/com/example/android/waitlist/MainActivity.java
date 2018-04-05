package com.example.android.waitlist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.android.waitlist.data.TestUtil;
import com.example.android.waitlist.data.WaitlistContract;
import com.example.android.waitlist.data.WaitlistDBHelper;


public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;

    private EditText mNameEditor;
    private EditText mSizeEditor;

    SQLiteDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView waitlistRecyclerView;

        mNameEditor = findViewById(R.id.person_name_edit_text);
        mSizeEditor = findViewById(R.id.party_count_edit_text);

        // Set local attributes to corresponding views
        waitlistRecyclerView = this.findViewById(R.id.all_guests_list_view);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        WaitlistDBHelper dbHelper = new WaitlistDBHelper(this);

        mDB = dbHelper.getWritableDatabase();

        Cursor guests = getAllGuests();

        // Create an adapter for that cursor to display the data
        mAdapter = new GuestListAdapter(this, guests);

        // Link the adapter to the RecyclerView
        waitlistRecyclerView.setAdapter(mAdapter);

    }


    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitlist(View view) {

        String name = mNameEditor.getText().toString();
        int partySize = Integer.parseInt(mSizeEditor.getText().toString());
        addNewGuest(name, partySize);

        mAdapter.swapCursor(getAllGuests());

        mSizeEditor.clearFocus();
        mSizeEditor.getText().clear();
        mNameEditor.getText().clear();

    }

    private long addNewGuest(String name, int partySize){
        ContentValues contentValues = new ContentValues();
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, name);
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, partySize);
        return mDB.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, contentValues);
    }

    private Cursor getAllGuests(){
        return mDB.query(WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP);
    }


}
