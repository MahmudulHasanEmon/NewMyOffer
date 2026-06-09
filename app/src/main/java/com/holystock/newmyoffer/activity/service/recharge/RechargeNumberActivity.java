package com.holystock.newmyoffer.activity.service.recharge;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.adapter.RechargeNumberAdapter;
import com.holystock.newmyoffer.model.Contact;
import com.holystock.newmyoffer.utils.appThemes.AppToolbarManager;
import com.holystock.newmyoffer.utils.appThemes.Status;
import com.holystock.newmyoffer.utils.helper.GetContacts;

import java.util.ArrayList;

public class RechargeNumberActivity extends BaseActivity {
    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private ArrayList<Contact> contacts = new ArrayList<>();

    private RelativeLayout noContactLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_number);

        new Status(this).setLightStatusBar();
        new AppToolbarManager(this).init();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_READ_CONTACTS);
        } else {

            contacts = GetContacts.getContacts(getApplicationContext());
            Log.d("GetContacts", GetContacts.findContactByNumber(contacts, "01845416702").getName());

        }

        RechargeNumberAdapter numberAdapter = new RechargeNumberAdapter(
                getApplicationContext(),
                contacts
        );

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerView.setAdapter(numberAdapter);

        EditText search_bar = findViewById(R.id.search_bar);
        noContactLayout = findViewById(R.id.noContactLayout);

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ArrayList<Contact> list =
                        GetContacts.filterContacts(contacts, s.toString());

                if (!list.isEmpty()) {

                    recyclerView.setVisibility(View.VISIBLE);
                    noContactLayout.setVisibility(View.GONE);

                    numberAdapter.setData(list);

                } else {
                    recyclerView.setVisibility(View.GONE);
                    noContactLayout.setVisibility(View.VISIBLE);
                }
            }
        });


    }

}