package com.manhdong.contactlist.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.manhdong.contactlist.R;

public class DetailContact extends AppCompatActivity {

    TextView pname, pmail, pphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        pname = (TextView) findViewById(R.id.pName);
        pphone = (TextView) findViewById(R.id.tvPhone);
        pmail = (TextView) findViewById(R.id.tvEmail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pname.setText(bundle.getString("Name"));
        pname.setText(bundle.getString("Phone"));
        pname.setText(bundle.getString("Email"));

    }
}
