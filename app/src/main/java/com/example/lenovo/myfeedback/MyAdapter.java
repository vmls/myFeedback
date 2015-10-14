package com.example.lenovo.myfeedback;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 10/13/2015.
 */
public class MyAdapter extends BaseAdapter {

    private ArrayList<String> names;
    private ArrayList<String> phones;

    public MyAdapter(ArrayList<String> names, ArrayList<String> phones){
        this.names = names;
        this.phones = phones;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else holder = (ViewHolder)convertView.getTag();
        holder.name.setText(names.get(position).toString());
        holder.phone.setText(phones.get(position).toString());
        return convertView;
    }

    private static class ViewHolder{
        private TextView name;
        private TextView phone;
        private View parent;

        private ViewHolder(View parent){
            this.parent = parent;
            name = (TextView)parent.findViewById(R.id.contact_name);
            phone = (TextView)parent.findViewById(R.id.contact_phone);
        }
    }
}
