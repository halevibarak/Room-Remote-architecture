package com.test.halevi.barakapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.halevi.barakapp.R;
import com.test.halevi.barakapp.model.Contact;

import java.util.List;

/**
 * Created by Barak on 24/08/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> ContactCellList;
    private CallInterface mLisenner;


    public ContactAdapter(List<Contact> contactList, CallInterface listenner){
        this.ContactCellList = contactList;
        mLisenner = listenner;
    }
    @Override
    public int getItemCount() {
        return ContactCellList.size();
    }

    public Contact getItem(int position){
        return ContactCellList.get(position);
    }


    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View horizontalItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(horizontalItem);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {

        final Contact contact = getItem(position);

        if (contact != null ){
            holder.nameView.setText(contact.getName());
            holder.phoneView.setText(contact.getPhone());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLisenner.callPhone(contact.getPhone());
            }
        });
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public ContactViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.name_text);
            phoneView = (TextView) itemView.findViewById(R.id.phone_text);

        }


        TextView nameView;
        TextView phoneView;
    }

    public void updateData(List<Contact> contactList){
        this.ContactCellList = contactList;
        notifyDataSetChanged();
    }
}
