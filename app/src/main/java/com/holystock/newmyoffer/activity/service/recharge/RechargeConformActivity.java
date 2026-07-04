package com.holystock.newmyoffer.activity.service.recharge;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.controller.KeyboardController;
import com.holystock.newmyoffer.utils.appThemes.AppToolbarManager;
import com.holystock.newmyoffer.utils.appThemes.Status;
import com.holystock.newmyoffer.views.BorderView;
import com.holystock.newmyoffer.views.BottomNextButtonView;

public class RechargeConformActivity extends BaseActivity {

    private BorderView[] views;
    private TextView[] texts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_conform);

        new Status(this).setLightStatusBar();
        new AppToolbarManager(this).init();

        BottomNextButtonView buttonView = findViewById(R.id.bottomNextButton);



        new KeyboardController(
                this,
                findViewById(R.id.keyboardRootLayout),
                findViewById(R.id.tvDisplay),
                value -> {

                    buttonView.setButtonClickable(value.length() == 5);

                    if (value.length() == 5){
                        buttonView.setButtonTint(R.color.selectedDark);
                    }else{
                        buttonView.setButtonTint(R.color.unselected);
                    }

                }, null

        ).showKeyboard();

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
                    ContextCompat.getColor(RechargeConformActivity.this, selected
                            ? R.color.primaryLight
                            : R.color.white)
            );

            texts[i].setTextColor(
                    RechargeConformActivity.this.getColor(
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