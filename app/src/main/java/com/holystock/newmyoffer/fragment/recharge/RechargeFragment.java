package com.holystock.newmyoffer.fragment.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.service.recharge.RechargeConformActivity;
import com.holystock.newmyoffer.utils.Helper;
import com.holystock.newmyoffer.views.BorderView;

import java.util.Objects;

public class RechargeFragment extends Fragment {

    private EditText etAmount;

    private BorderView[] views;
    private TextView[] texts;

    private int selectedIndex = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(
                R.layout.fragment_recharge,
                container,
                false
        );

        initViews(root);

        return root;
    }

    private void initViews(View root) {

        etAmount = root.findViewById(R.id.etAmount);

        root.findViewById(R.id.nextBtn2).setOnClickListener(view -> {

            Helper.hideKeyboard(Objects.requireNonNull(getActivity()));

            startActivity(new Intent(getActivity(), RechargeConformActivity.class));


        });

        root.findViewById(R.id.nextBtn).setOnClickListener(view -> {

            Helper.hideKeyboard(Objects.requireNonNull(getActivity()));

            startActivity(new Intent(getActivity(), RechargeConformActivity.class));
        });

        BorderView view1 = root.findViewById(R.id.view1);
        BorderView view2 = root.findViewById(R.id.view2);
        BorderView view3 = root.findViewById(R.id.view3);
        BorderView view4 = root.findViewById(R.id.view4);

        TextView tv1 = root.findViewById(R.id.tv1);
        TextView tv2 = root.findViewById(R.id.tv2);
        TextView tv3 = root.findViewById(R.id.tv3);
        TextView tv4 = root.findViewById(R.id.tv4);

        views = new BorderView[]{
                view1,
                view2,
                view3,
                view4
        };

        texts = new TextView[]{
                tv1,
                tv2,
                tv3,
                tv4
        };

        for (int i = 0; i < views.length; i++) {

            final int index = i;

            views[i].setOnClickListener(v -> {

                String amount = getNumericValue(
                        texts[index].getText().toString()
                );

                etAmount.setText(amount);
                etAmount.setSelection(amount.length());

                selectButton(index);
            });
        }

        etAmount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start,
                                          int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s,
                                      int start,
                                      int before,
                                      int count) {

                String enteredAmount =
                        getNumericValue(s.toString());

                boolean found = false;

                for (int i = 0; i < texts.length; i++) {

                    String buttonAmount =
                            getNumericValue(
                                    texts[i].getText().toString()
                            );

                    if (buttonAmount.equals(enteredAmount)
                            && !enteredAmount.isEmpty()) {

                        selectButton(i);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    clearSelection();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        clearSelection();
    }

    private void selectButton(int index) {

        if (selectedIndex == index) {
            return;
        }

        selectedIndex = index;

        for (int i = 0; i < views.length; i++) {

            boolean selected = i == index;

            views[i].setBackgroundColorCustom(
                    ContextCompat.getColor(requireContext(), selected
                            ? R.color.primaryLight
                            : R.color.white)
            );

            texts[i].setTextColor(
                    requireContext().getColor(
                            R.color.black
                    )
            );
        }

        animateSelection(views[index]);
    }

    private void clearSelection() {

        selectedIndex = -1;

        for (int i = 0; i < views.length; i++) {

            views[i].setBackgroundColorCustom(
                    ContextCompat.getColor(requireContext(), R.color.white)
            );

            texts[i].setTextColor(
                    requireContext().getColor(
                            R.color.black
                    )
            );
        }
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

    private String getNumericValue(String text) {

        return text.replaceAll("[^0-9]", "");
    }
}