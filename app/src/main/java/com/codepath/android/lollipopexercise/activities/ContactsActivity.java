package com.codepath.android.lollipopexercise.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.android.lollipopexercise.R;
import com.codepath.android.lollipopexercise.adapters.ContactsAdapter;
import com.codepath.android.lollipopexercise.models.Contact;

import java.util.List;


public class ContactsActivity extends AppCompatActivity {
    private ContactsAdapter mAdapter;
    private List<Contact> mContacts;
    private RecyclerView rvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Find RecyclerView and bind to adapter
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        // allows for optimizations
        rvContacts.setHasFixedSize(true);

        // Define 2 column grid layout
        final GridLayoutManager layout = new GridLayoutManager(ContactsActivity.this, 2);

        // Unlike ListView, you have to explicitly give a LayoutManager to the RecyclerView to position items on the screen.
        // There are three LayoutManager provided at the moment: GridLayoutManager, StaggeredGridLayoutManager and LinearLayoutManager.
        rvContacts.setLayoutManager(layout);

        // get data
        mContacts = Contact.getContacts();

        // Create an adapter
        mAdapter = new ContactsAdapter(ContactsActivity.this, mContacts);

        // Bind adapter to list
        rvContacts.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.miAdd:
                addContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void removeContact(int pos) {
        this.mContacts.remove(pos);
        mAdapter.notifyDataSetChanged();
    }

    private void addContact() {
        final int pos = 0;
        Contact conatact = Contact.getRandomContact(this);
        this.mContacts.add(pos, conatact);
        mAdapter.notifyDataSetChanged();
        rvContacts.scrollToPosition(pos);

        Snackbar.make(rvContacts, R.string.contact_added, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeContact(pos);
                    }
                })
                .setActionTextColor(ContextCompat.getColor(ContactsActivity.this, R.color.accent))
                .show();
    }
}
