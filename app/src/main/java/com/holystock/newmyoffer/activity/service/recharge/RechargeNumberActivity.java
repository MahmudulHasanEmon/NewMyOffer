package com.holystock.newmyoffer.activity.service.recharge;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.adapter.RechargeNumberAdapter;
import com.holystock.newmyoffer.controller.RechargeSheetController;
import com.holystock.newmyoffer.controller.RichTextBuilder;
import com.holystock.newmyoffer.model.Contact;
import com.holystock.newmyoffer.model.TextSegment;
import com.holystock.newmyoffer.utils.Controller;
import com.holystock.newmyoffer.utils.appThemes.AppToolbarManager;
import com.holystock.newmyoffer.utils.appThemes.Status;
import com.holystock.newmyoffer.utils.helper.GetContacts;
import com.holystock.newmyoffer.views.BorderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RechargeNumberActivity extends BaseActivity {
    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private RecyclerView recyclerView;
    private EditText searchBar;
    private RelativeLayout noContactLayout;
    private BorderView nextBtn;
    private TextView tvNoTitle;
    private RechargeNumberAdapter numberAdapter;
    private ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_number);

        new Status(this).setLightStatusBar();
        new AppToolbarManager(this).init();

        initViews();
        setupRecyclerView();
        setupSearch();

        checkContactPermission();
    }

    private void initViews() {

        recyclerView =
                findViewById(R.id.recyclerView);

        searchBar =
                findViewById(R.id.search_bar);

        noContactLayout =
                findViewById(R.id.noContactLayout);

        nextBtn =
                findViewById(R.id.nextBtn);

        tvNoTitle =
                findViewById(R.id.tvNoTitle);
    }

    private void setupRecyclerView() {

        numberAdapter =
                new RechargeNumberAdapter(
                        this,
                        new ArrayList<>()
                );

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerView.setAdapter(numberAdapter);

        numberAdapter.setOnItemClickListener(
                (phone, contact) -> request(phone, contact.getName())
        );
    }

    private void checkContactPermission() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED) {

            loadContacts();

        } else {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.READ_CONTACTS
                    },
                    REQUEST_CODE_READ_CONTACTS
            );
        }
    }

    private void loadContacts() {

        contacts = GetContacts.getContacts(this);

        numberAdapter.setData(contacts);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );

        if (requestCode == REQUEST_CODE_READ_CONTACTS
                && grantResults.length > 0
                && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {

            loadContacts();

        } else {

            Toast.makeText(
                    this,
                    "Contact permission denied",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void setupSearch() {

        searchBar.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after
                    ) {

                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count
                    ) {

                        filterContacts(
                                s.toString().trim()
                        );
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s
                    ) {

                    }
                }
        );
    }

    private void filterContacts(
            String keyword
    ) {

        ArrayList<Contact> list =
                GetContacts.filterContacts(
                        contacts,
                        keyword
                );

        numberAdapter.setData(list);

        boolean hasData =
                list != null
                        && !list.isEmpty();

        recyclerView.setVisibility(
                hasData
                        ? View.VISIBLE
                        : View.GONE
        );

        noContactLayout.setVisibility(
                hasData
                        ? View.GONE
                        : View.VISIBLE
        );

        if (!hasData) {

            validate(keyword);

        } else {

            nextBtn.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void validate(
            String number
    ) {

        ArrayList<TextSegment> list = new ArrayList<>();

        if (!GetContacts.isBangladeshiMobile(number)) {

            nextBtn.setVisibility(View.GONE);

            list.clear();
            list.add(new TextSegment("আপনি যে কন্ট্যাক্ট খুঁজছেন, তা পাওয়া যায়নি").setTextColor(Color.GRAY));

            RichTextBuilder.apply(tvNoTitle, list);

            return;
        }

        nextBtn.setVisibility(View.VISIBLE);

        list.clear();
        list.add(new TextSegment(number).setTextColor(Color.BLACK).setTextSize(16));
        list.add(new TextSegment(" -নাম্বারে মোবাইল রিচার্জ করুন").setTextColor(Color.GRAY));

        RichTextBuilder.apply(tvNoTitle, list);


        nextBtn.setOnClickListener(
                v -> request(number, number)
        );
    }

    private void request(
            String phone,
            String name
    ) {

        Controller.hideKeyboard(this);

        new RechargeSheetController(this)
                .addItem("Airtel", R.drawable.airtel)
                .addItem("Banglalink", R.drawable.banglalink)
                .addItem("Grameenphone", R.drawable.grameenphone)
                .addItem("Robi", R.drawable.robi)
                .addItem("Ryze", R.drawable.ryze)
                .addItem("Skitto", R.drawable.skitto)
                .addItem("Teletalk", R.drawable.teletalk)
                .onItemSelected((title, icon, position) -> {
                    Intent intent = new Intent(this, RechargeOfferActivity.class);

                    intent.putExtra(RechargeOfferActivity.EXTRA_NUMBER, phone);
                    intent.putExtra(RechargeOfferActivity.EXTRA_OPERATOR, title);
                    intent.putExtra(RechargeOfferActivity.EXTRA_NAME, name);
                    intent.putExtra(RechargeOfferActivity.EXTRA_ICON, icon);

                    startActivity(intent);
                })
                .show();
    }


}