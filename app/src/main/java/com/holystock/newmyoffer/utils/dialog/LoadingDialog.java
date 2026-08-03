package com.holystock.newmyoffer.utils.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.holystock.newmyoffer.R;

import java.lang.ref.WeakReference;

public class LoadingDialog {

    private final WeakReference<Activity> activityRef;
    private AlertDialog dialog;

    public LoadingDialog(Activity activity) {
        this.activityRef = new WeakReference<>(activity);
    }

    /**
     * Displays the loading dialog.
     * Safe to call from any thread.
     */
    public void start() {
        Activity activity = activityRef.get();
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }

        activity.runOnUiThread(() -> {
            if (dialog != null && dialog.isShowing()) {
                return; // Prevent duplicate dialogs
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.loading_dialog, null);

            builder.setView(dialogView);
            builder.setCancelable(false);

            dialog = builder.create();

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            dialog.show();
        });
    }

    /**
     * Dismisses the loading dialog safely.
     * Safe to call from any thread.
     */
    public void dismiss() {
        Activity activity = activityRef.get();

        Runnable dismissRunnable = () -> {
            if (dialog != null && dialog.isShowing()) {
                try {
                    dialog.dismiss();
                } catch (Exception ignored) {
                    // Handle edge cases where window manager has detached
                } finally {
                    dialog = null;
                }
            }
        };

        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.runOnUiThread(dismissRunnable);
        } else {
            dismissRunnable.run();
        }
    }

    /**
     * Checks if the dialog is currently visible.
     */
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }
}
