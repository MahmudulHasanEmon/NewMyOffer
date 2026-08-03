package com.holystock.newmyoffer.activity.service.recharge;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.controller.KeyboardController;
import com.holystock.newmyoffer.data.model.Confirm;
import com.holystock.newmyoffer.data.model.Contact;
import com.holystock.newmyoffer.utils.appThemes.Status;
import com.holystock.newmyoffer.utils.dialog.MyConfirmDialog;
import com.holystock.newmyoffer.views.BorderView;
import com.holystock.newmyoffer.views.BottomNextButtonView;

import java.util.ArrayList;
import java.util.List;

public class RechargeConfirmActivity extends BaseActivity {

    private BorderView[] views;
    private TextView[] texts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_confirm);

        new Status(this).setLightStatusBar();

        BottomNextButtonView buttonView = findViewById(R.id.bottomNextButton);
        buttonView.setButtonClickable(false);

        KeyboardController keyboardController = new KeyboardController(
                this,
                findViewById(R.id.keyboardRootLayout),
                findViewById(R.id.tvDisplay),
                value -> {

                    boolean isValid = value.length() == 5;
                    buttonView.setButtonClickable(isValid);

                },
                null
        );

        keyboardController.showKeyboard();

        buttonView.setOnClickListener(v -> {

            if (!buttonView.isButtonClickable()) {
                return;
            }

            Contact contact = new Contact("MD. Mahmudul Hasan Emon", new ArrayList<>(List.of("01845416702")), "hjhjhj");

            Confirm.Body body = new Confirm.Body(10, 10, 10, 10, 10, 10, "jhgh");

            Confirm confirm = new Confirm("recharge", "Mobile Recharge", "to Confirm", contact, body);

            MyConfirmDialog.show(
                    this,
                    confirm,
                    vv -> Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
            );

        });


        BorderView view1 = findViewById(R.id.view1);
        BorderView view2 = findViewById(R.id.view2);

        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);

        views = new BorderView[]{
                view1,
                view2
        };

        texts = new TextView[]{
                tv1,
                tv2,
        };


        for (int i = 0; i < views.length; i++) {

            final int index = i;

            selectButton(0);

            views[i].setOnClickListener(v -> {
                selectButton(index);
            });
        }

    }

    private void selectButton(int index) {

        for (int i = 0; i < views.length; i++) {

            boolean selected = i == index;

            views[i].setBackgroundColorCustom(
                    ContextCompat.getColor(RechargeConfirmActivity.this, selected
                            ? R.color.primaryLight
                            : R.color.white)
            );

            texts[i].setTextColor(
                    RechargeConfirmActivity.this.getColor(
                            R.color.black
                    )
            );
        }

        animateSelection(views[index]);
    }

    private void animateSelection(View view) {

        view.animate()
                .cancel();

        view.setScaleX(1f);
        view.setScaleY(1f);

        view.animate()
                .scaleX(1.08f)
                .scaleY(1.08f)
                .setDuration(120)
                .withEndAction(() ->
                        view.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(120)
                                .start()
                )
                .start();

    }


}