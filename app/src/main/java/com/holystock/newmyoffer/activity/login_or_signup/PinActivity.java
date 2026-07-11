package com.holystock.newmyoffer.activity.login_or_signup;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.holystock.newmyoffer.MainActivity;
import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.controller.KeyboardController;
import com.holystock.newmyoffer.controller.RichTextBuilder;
import com.holystock.newmyoffer.model.TextSegment;
import com.holystock.newmyoffer.utils.appThemes.Status;
import com.holystock.newmyoffer.views.BottomNextButtonView;

import java.util.ArrayList;

public class PinActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        new Status(this).setLightStatusBar();
        BottomNextButtonView buttonView = findViewById(R.id.bottomNextButton);


        new KeyboardController(
                this,
                findViewById(R.id.keyboardRootLayout),
                findViewById(R.id.tvDisplay),
                value -> {

                    buttonView.setButtonClickable(value.length() == 5);

                    if (value.length() == 5) {
                        buttonView.setOnClickListener(v -> startActivity(new Intent(PinActivity.this, MainActivity.class)));
                    }

                }, null
        ).showKeyboard();


        TextView tvNumber = findViewById(R.id.tvNumber);
        ArrayList<TextSegment> list = new ArrayList<>();

        list.add(
                new TextSegment("একাউন্ট নাম্বার")
                        .setTextSize(14)
                        .setTextColor(getColor(R.color.greyDark))
        );

        list.add(
                new TextSegment("\n+8801775185654")
                        .setTextSize(15)
                        .setTextColor(getColor(R.color.black))
                        .setTextStyle(Typeface.BOLD)        );

        RichTextBuilder.apply(tvNumber, list);



    }
}