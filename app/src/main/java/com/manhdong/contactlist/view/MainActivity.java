package com.manhdong.contactlist.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.manhdong.contactlist.R;
import com.manhdong.contactlist.model.Contact;
import com.manhdong.contactlist.model.Email;
import com.manhdong.contactlist.model.Phone;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<String> names;
    List<Contact> contacts;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ContentResolver cr = getContentResolver();
                Cursor cursor = cr.query(ContactsContract.Data.CONTENT_URI,
                        null,
                        ContactsContract.Data.RAW_CONTACT_ID + "=?",
                        new String[]{contacts.get(position).get_id()+""},
                        null,null);

                ArrayList<String> phonelist = new ArrayList<String>();
                ArrayList<String> emaillist = new ArrayList<String>();

                if (cursor!= null){
                    if (cursor.moveToFirst()){
                        do{
                            String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
                            switch (mimeType){
                                case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                                    Phone phone = new Phone();
                                    String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    phone.setPhoneNum(phoneNum);
                                    int phoneType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                                    phone.setType(phoneType);
                                    //Chuyển từ in sang String
                                    contacts.get(position).addPhoneNum(phone);
                                    phonelist.add(phoneNum);

                                    break;
                                case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:

                                    Email email = new Email();
                                    String emailInfo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                                    email.setAddress(emailInfo);
                                    email.setType(cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)));
                                    contacts.get(position).addEmail(email);
                                    emaillist.add(emailInfo);

                                    break;
                            }

                        }while (cursor.moveToNext());


                    }
                    cursor.close();
                }

                Intent intent = new Intent(MainActivity.this, DetailContact.class);
                Bundle bundle = new Bundle();
            //    bundle.putBundle("Contact", contacts.get(position)));
                Contact detail = contacts.get(position);
                bundle.putString("Name", detail.getPersonName());
                bundle.putStringArrayList("Phone", phonelist);
                bundle.putStringArrayList("Email", emaillist);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }

    private void initData() {
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.RawContacts._ID, ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY},
                null,null,null,null);
        contacts = new ArrayList<>();
        names = new ArrayList<>();
        if(cursor !=null){
            if(cursor.moveToFirst()){
                do{
                    Contact contact = new Contact();
                    contact.set_id(cursor.getLong(cursor.getColumnIndex(ContactsContract.RawContacts._ID)));
                    contact.setPersonName(cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY)));
                    contacts.add(contact);
                    names.add(contact.getPersonName());

                }while (cursor.moveToNext());

            }

            cursor.close();
        }

    }
}
