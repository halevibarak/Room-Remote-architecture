package com.test.halevi.barakapp.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.test.halevi.barakapp.R;
import com.test.halevi.barakapp.adapter.CallInterface;
import com.test.halevi.barakapp.adapter.ContactAdapter;
import com.test.halevi.barakapp.adapter.ContactDecoration;
import com.test.halevi.barakapp.model.Contact;
import com.test.halevi.barakapp.sqlite.DatabaseHandler;

import java.util.List;
/**
 * Created by Barak on 24/08/2017.
 */
public class MainActivity extends AppCompatActivity implements CallInterface {

    private ContactAdapter contactsAdapter;
    private DatabaseHandler mDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataBase = new DatabaseHandler(this);
        final TextView waitView = (TextView) findViewById(R.id.wait_text);
        if (getDataCount() == 0){
            waitView.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    if (getDataCount() > 0){
                        waitView.setText(getString(R.string.noData));
                        contactsAdapter.updateData(getBindingData());
                    }
                }
            }, 3000);
        }
        contactsAdapter = new ContactAdapter(getBindingData(),this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contact_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new ContactDecoration(dpToPx(10)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(contactsAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private List<Contact> getBindingData(){
        return mDataBase.getAllContacts();
    }

    private int getDataCount(){
        return mDataBase.getContactsCount();
    }

    public static int dpToPx(int dp){
        return (int)(dp * Resources.getSystem().getDisplayMetrics().density);
    }
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAdapterPosition();
            mDataBase.deleteContact(contactsAdapter.getItem(position));
            contactsAdapter.updateData(mDataBase.getAllContacts());
            contactsAdapter.notifyDataSetChanged();

        }
    };

    @Override
    public void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }
}
