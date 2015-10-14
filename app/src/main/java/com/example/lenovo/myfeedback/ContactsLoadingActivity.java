package com.example.lenovo.myfeedback;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class ContactsLoadingActivity extends AppCompatActivity {

    ListView contactList;
    private ArrayAdapter adapter;
    TextView name;
    String current_user;
    String current_phone;

    TreeMap<String, String> map;
    ArrayList<String> names;
    ArrayList<String> phones;
    Button logout_button;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_loading_page);
        current_user = MyApplication.pref.getString(MyApplication.NAME, MyApplication.NULL_STRING);
        current_phone = MyApplication.pref.getString(MyApplication.PHONE, MyApplication.NULL_STRING);

        name = (TextView)findViewById(R.id.welcome_user);
        name.setText("Hi, " +current_user+ " (" + current_phone + ")");

        logout_button = (Button)findViewById(R.id.log_out);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = MyApplication.pref.edit();
                editor.remove(MyApplication.NAME);
                editor.remove(MyApplication.PHONE);
                editor.remove(MyApplication.EMAIL);
                editor.commit();

                intent = new Intent(ContactsLoadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        readContacts();
        mapToArray();
        MyAdapter adapter = new MyAdapter(names, phones);
        contactList = (ListView)findViewById(R.id.contactsList);
        contactList.setAdapter(adapter);
        setMessegeSendingOnClick();
    }


    private void setMessegeSendingOnClick(){
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView nameText = (TextView)view.findViewById(R.id.contact_name);
                TextView phoneText = (TextView)view.findViewById(R.id.contact_phone);
                String recipient_name = nameText.getText().toString();
                String recipient_phone = phoneText.getText().toString();

                AlertDialog diaBox = feedbackConfirmOption(recipient_name, recipient_phone);
                diaBox.show();
            }
        });
    }


    private AlertDialog feedbackConfirmOption(final String recipient_name, String recipient_phone)
    {
        String msg = "http://random3%1$_link.com";

        AlertDialog sendConfirm = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Feedback")
                .setMessage("Do you want feedback from "+recipient_name)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SmsManager.getDefault().sendTextMessage(current_phone, null,
                                "Hi "+recipient_name+","+ current_user +" is asking for feedback. Get back to him. Download myFeedback. link: "+ R.string.app_link,
                                null, null);
                        Toast.makeText(getBaseContext(), "Your Request Forwarded", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return sendConfirm;

    }

    private void mapToArray(){

        names = new ArrayList<String>();
        phones = new ArrayList<String>();

        //iterating through the tree map
        for(TreeMap.Entry<String, String> entry: map.entrySet()){
            names.add(entry.getKey());
            phones.add(entry.getValue());
        }
    }


/*
    private LoaderManager.LoaderCallbacks<Cursor> contactsLoader =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    String[] projections = new String[]  {
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };

                    CursorLoader cursorLoader = new CursorLoader(ContactsLoadingActivity.this,
                            ContactsContract.Contacts.CONTENT_URI,
                            projections, null, null, null);
                    return cursorLoader;
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    adapter.swapCursor(data);
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    adapter.swapCursor(null);
                }
            };
    */

    public void readContacts() {

        map = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.toLowerCase().compareTo(t1.toLowerCase());
            }
        });


        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) == 1) {

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        map.put(name,phone);
                        break;
                    }
                    pCur.close();
                }
            }
        }
    }




}


