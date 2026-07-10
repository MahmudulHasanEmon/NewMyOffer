package com.holystock.newmyoffer.utils.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.holystock.newmyoffer.R;

public final class MyDialog {

    private static Dialog dialog;

    private MyDialog() {
        // Prevent instantiation
    }

    public static void show(@NonNull Context context,
                            @NonNull String title,
                            @NonNull String message,
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
        dialog.setContentView(R.layout.my_dialog_view);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        if (dialog.getWindow() != null) {

            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));

            dialog.getWindow().setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);

            dialog.getWindow().getAttributes().windowAnimations =
                    R.style.DialogAnimation;
        }

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        txtTitle.setText(title);
        txtMessage.setText(message);

        btnOk.setOnClickListener(v -> {

            dismiss();

            if (okListener != null) {
                okListener.onClick(v);
            }
        });

        btnCancel.setOnClickListener(v -> dismiss());

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

}