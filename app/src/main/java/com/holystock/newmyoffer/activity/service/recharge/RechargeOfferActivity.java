package com.holystock.newmyoffer.activity.service.recharge;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.adapter.ViewPagerAdapter;
import com.holystock.newmyoffer.controller.RechargeSheetController;
import com.holystock.newmyoffer.fragment.recharge.InternetFragment;
import com.holystock.newmyoffer.fragment.recharge.RechargeFragment;
import com.holystock.newmyoffer.utils.Helper;
import com.holystock.newmyoffer.utils.appThemes.AppToolbarManager;
import com.holystock.newmyoffer.utils.appThemes.Status;
import com.holystock.newmyoffer.views.BorderView;

public class RechargeOfferActivity extends BaseActivity {

    public static final String EXTRA_NUMBER = "number";
    public static final String EXTRA_OPERATOR = "operator";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_ICON = "icon";
    public static final String EXTRA_IMG = "image";
    public static final String EXTRA_LETTER = "letter";
    public static final String EXTRA_COLOR = "color";

    private String operator;
    private String phone;
    private String name;
    private int icon;

    private TextView tvName;
    private TextView tvNumber;
    private TextView tvLetter;

    private ImageView imgOperator;
    private ImageView ivPhoto;

    private BorderView photoLayout, operatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_offer);

        new Status(this).setLightStatusBar();
        new AppToolbarManager(this).init();

        initViews();
        receiveData();
        bindData();
        setTabLayout();
    }

    private void initViews() {

        tvName = findViewById(R.id.tvName);
        tvNumber = findViewById(R.id.tvNumber);
        tvLetter = findViewById(R.id.tvLetter);

        imgOperator = findViewById(R.id.imgOperator);
        ivPhoto = findViewById(R.id.ivPhoto);

        operatorLayout = findViewById(R.id.operatorLayout);
        photoLayout = findViewById(R.id.photoLayout);
    }

    private void setTabLayout() {

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );

        /*pagerAdapter.addFragment(new RechargeFragment(hashMap, rechageInfo), "অ্যামাউন্ট");
        pagerAdapter.addFragment(new InternetFragment(), "ইন্টারনেট");
        pagerAdapter.addFragment(new CallrateFragment(), "কলরেট");
        pagerAdapter.addFragment(new MinuteFragment(), "মিনিট");
        pagerAdapter.addFragment(new BundleFragment(), "বান্ডেল");
        if (!otherList.isEmpty()) pagerAdapter.addFragment(new OtherFragment(), "আরো");*/

        pagerAdapter.addFragment(new RechargeFragment(), "অ্যামাউন্ট");
        pagerAdapter.addFragment(new InternetFragment(), "ইন্টারনেট");
        pagerAdapter.addFragment(new InternetFragment(), "কলরেট");
        pagerAdapter.addFragment(new InternetFragment(), "মিনিট");
        pagerAdapter.addFragment(new InternetFragment(), "বান্ডেল");
        pagerAdapter.addFragment(new InternetFragment(), "আরো");

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.setPageTransformer(true, new Helper.DepthPageTransformer());

    }

    private void receiveData() {

        if (getIntent() == null) {
            finish();
            return;
        }

        phone = getIntent().getStringExtra(EXTRA_NUMBER);
        operator = getIntent().getStringExtra(EXTRA_OPERATOR);
        name = getIntent().getStringExtra(EXTRA_NAME);

        icon = getIntent().getIntExtra(
                EXTRA_ICON,
                R.drawable.backspace_24dp
        );

        if (phone == null) phone = "";
        if (operator == null) operator = "";
        if (name == null) name = "";
    }

    private void bindData() {

        tvName.setText(name);
        tvNumber.setText(phone);

        if (icon != 0) {
            imgOperator.setImageResource(icon);
        }

        String image = getIntent().getStringExtra(EXTRA_IMG);

        if (image != null && !image.trim().isEmpty()) {

            Glide.with(this)
                    .load(image)
                    .circleCrop()
                    .placeholder(R.drawable.bank_transfer)
                    .error(R.drawable.bank_transfer)
                    .into(ivPhoto);

            ivPhoto.setVisibility(View.VISIBLE);
            photoLayout.setVisibility(View.INVISIBLE);

        }
        else {

            ivPhoto.setVisibility(View.INVISIBLE);
            photoLayout.setVisibility(View.VISIBLE);

            String letter = getIntent().getStringExtra(EXTRA_LETTER);

            if (letter == null || letter.trim().isEmpty()) {

                if (!name.trim().isEmpty()) {
                    letter = String.valueOf(
                            Character.toUpperCase(
                                    name.trim().charAt(0)
                            )
                    );
                } else {
                    letter = "?";
                }
            }

            int color = getIntent().getIntExtra(
                    EXTRA_COLOR,
                    -1
            );

            if (color == -1) {

                int index = Math.abs(
                        name.hashCode()
                ) % Helper.lightColors.length;

                color = Helper.lightColors[index];
            }

            tvLetter.setText(letter);
            photoLayout.setBackgroundColorCustom(color);
        }

        operatorLayout.setOnClickListener(v -> {
            new RechargeSheetController(this)
                    .addItem("Airtel", R.drawable.airtel)
                    .addItem("Banglalink", R.drawable.banglalink)
                    .addItem("Grameenphone", R.drawable.grameenphone)
                    .addItem("Robi", R.drawable.robi)
                    .addItem("Ryze", R.drawable.ryze)
                    .addItem("Skitto", R.drawable.skitto)
                    .addItem("Teletalk", R.drawable.teletalk)
                    .onItemSelected((title, icon1, position) -> {
                        imgOperator.setImageResource(icon1);
                        operator = title;
                    })
                    .show();
        });

    }
}