package com.holystock.newmyoffer.activity.service.recharge;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.utils.appThemes.AppToolbarManager;
import com.holystock.newmyoffer.utils.appThemes.Status;

public class RechargeOfferActivity extends BaseActivity {

    public static final String EXTRA_NUMBER = "number";
    public static final String EXTRA_OPERATOR = "operator";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_ICON = "icon";

    private String operator;
    private String phone;
    private String name;
    private int icon;

    private TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_offer);

        new Status(this).setLightStatusBar();
        new AppToolbarManager(this).init();

        tvDisplay = findViewById(R.id.tvDisplay);

        if (getIntent() == null) {
            finish();
            return;
        }

        // Receive safely
        phone = getIntent().getStringExtra(EXTRA_NUMBER);
        operator = getIntent().getStringExtra(EXTRA_OPERATOR);
        name = getIntent().getStringExtra(EXTRA_NAME);
        icon = getIntent().getIntExtra(EXTRA_ICON, R.drawable.backspace_24dp);

        if (phone == null) phone = "";
        if (operator == null) operator = "";
        if (name == null) name = "";

        bindData();
    }

    private void bindData() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(name).append("\n");
        sb.append("Phone: ").append(phone).append("\n");
        sb.append("Operator: ").append(operator).append("\n");
        sb.append("Icon ID: ").append(icon);

        tvDisplay.setText(sb.toString());
        Drawable drawable = ContextCompat.getDrawable(this, icon);

        if (drawable != null) {

            int size = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    50,
                    getResources().getDisplayMetrics()
            );

            drawable.setBounds(0, 0, size, size);

            tvDisplay.setCompoundDrawables(
                    drawable,
                    null,
                    null,
                    null
            );
        }

        tvDisplay.setCompoundDrawablePadding(20);


    }
}