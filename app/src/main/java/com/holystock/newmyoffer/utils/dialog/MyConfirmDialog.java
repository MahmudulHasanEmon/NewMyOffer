package com.holystock.newmyoffer.utils.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.controller.RichTextBuilder;
import com.holystock.newmyoffer.model.Confirm;
import com.holystock.newmyoffer.model.TextSegment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MyConfirmDialog {

    private static Dialog dialog;

    private MyConfirmDialog() {
        // Prevent instantiation
    }

    public static void show(@NonNull Context context,
                            @NonNull Confirm confirm,
                            View.OnClickListener okListener) {

        // Prevent multiple dialogs
        dismiss();

        if (!(context instanceof Activity)) {
            return;
        }

        Activity activity = (Activity) context;

        if (activity.isFinishing()) {
            return;
        }

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm_view);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        if (dialog.getWindow() != null) {

            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));

            dialog.getWindow().setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);

            dialog.getWindow().getAttributes().windowAnimations =
                    R.style.DialogAnimation;

        }
        ArrayList<TextSegment> list = new ArrayList<>();

        list.clear();
        list.add(new TextSegment(confirm.getHeaderTitle()+" ").setTextColor(activity.getColor(R.color.primary)).setStyle(Typeface.BOLD));
        list.add(
                new TextSegment(confirm.getSubTitle())
                        .setTextColor(activity.getColor(R.color.primary))
                        .setStyle(Typeface.NORMAL)
                        .setTextSize(16)
        );

        RichTextBuilder.apply(dialog.findViewById(R.id.title), list);

        dialog.findViewById(R.id.close).setOnClickListener(v -> dismiss());

        TableLayout table = dialog.findViewById(R.id.tableLayout);

        LinkedHashMap<String, String> data = new LinkedHashMap<>();

        data.put("Amount", "1000");
        data.put("Fee", "10");
        data.put("Charge", "5");
        data.put("Total", "1015");
        data.put("Balance", "5000");
        data.put("New Balance", "3985");

        buildTable(table, data);


        try {
            dialog.show();
        } catch (Exception e) {
            dialog = null;
        }
    }

    public static void dismiss() {

        try {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        } catch (Exception ignored) {

        } finally {
            dialog = null;
        }
    }

    public static boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }


    public static void buildTable(TableLayout tableLayout, Map<String, String> tableData) {

        tableLayout.removeAllViews();

        List<Map.Entry<String, String>> list =
                new ArrayList<>(tableData.entrySet());

        int columns = 2;

        for (int i = 0; i < list.size(); i += columns) {

            TableRow row = new TableRow(tableLayout.getContext());

            for (int j = 0; j < columns; j++) {

                int index = i + j;

                if (index < list.size()) {

                    Map.Entry<String, String> item = list.get(index);

                    row.addView(createCell(
                            tableLayout.getContext(),
                            item.getKey(),
                            item.getValue()
                    ));

                } else {

                    View empty = new View(tableLayout.getContext());

                    TableRow.LayoutParams params =
                            new TableRow.LayoutParams(
                                    0,
                                    TableRow.LayoutParams.WRAP_CONTENT,
                                    1f);

                    empty.setLayoutParams(params);

                    row.addView(empty);
                }
            }

            tableLayout.addView(row);
        }
    }

    private static View createCell(Context context,
                                   String key,
                                   String value) {

        LinearLayout layout = new LinearLayout(context);

        layout.setOrientation(LinearLayout.VERTICAL);

        layout.setPadding(32, 20, 32, 20);

        layout.setLayoutParams(
                new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f));

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.WHITE);
        drawable.setStroke(
                1,
                ContextCompat.getColor(context, R.color.greyDivider));

        layout.setBackground(drawable);

        TextView title = new TextView(context);
        title.setPadding(0,7,0,8);
        title.setLineSpacing(4f, 1.2f);

        ArrayList<TextSegment> list = new ArrayList<>();

        list.clear();
        Typeface typeface = ResourcesCompat.getFont(context, R.font.bangla_medium);

        list.add(
                new TextSegment(key)
                        .setTextSize(12)
                        .setTextColor(context.getColor(R.color.grey))
                        .setTypefaceFamily(String.valueOf(typeface))
        );

        list.add(
                new TextSegment("\n"+value)
                        .setTextSize(13)
                        .setTextColor(context.getColor(R.color.black))
                        .setTypefaceFamily(String.valueOf(typeface))
        );

        RichTextBuilder.apply(title, list);



        layout.addView(title);
       // layout.addView(amount);

        return layout;
    }

}