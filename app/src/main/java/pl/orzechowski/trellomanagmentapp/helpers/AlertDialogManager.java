package pl.orzechowski.trellomanagmentapp.helpers;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.base.BaseActivity;

public class AlertDialogManager {

    private static AlertDialogManager INSTANCE;
    private AlertDialog dialog;
    private BaseActivity activity;
    private Context context;

    private AlertDialogManager(Context context) {
        this.context = context;
    }

    public static AlertDialogManager get() {
        return INSTANCE;
    }

    public static AlertDialogManager init(Context context) {
        return INSTANCE = new AlertDialogManager(context);
    }

    public void show(Context context, String message) {
        dismiss();

        if (context == null) return;

        dialog = new AlertDialog.Builder(context, R.style.AlertsDialogTheme)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.ok), getDefaultClick())
                .setCancelable(false)
                .show();
    }

    public void show(String message) {
        dismiss();

        if (context == null) return;

        dialog = new AlertDialog.Builder(context, R.style.AlertsDialogTheme)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.ok), getDefaultClick())
                .setCancelable(false)
                .show();
    }

    public void show(Context context, String title, String message) {
        dismiss();

        if (context == null) return;

        dialog = new AlertDialog.Builder(context, R.style.AlertsDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.ok), getDefaultClick())
                .setCancelable(false)
                .show();
    }

    public void show(Context context, String message, DialogInterface.OnClickListener clickListener) {
        dismiss();

        if (context == null) return;

        dialog = new AlertDialog.Builder(context, R.style.AlertsDialogTheme)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.ok), clickListener)
                .setNegativeButton(context.getString(R.string.cancel), getDefaultClick())
                .setCancelable(false)
                .show();
    }

    public void show(String title, String message, String positiveButtonText, DialogInterface.OnClickListener clickListener) {
        dismiss();

        if (context == null) return;

        dialog = new AlertDialog.Builder(context, R.style.AlertsDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, clickListener)
                .setNegativeButton(context.getString(R.string.cancel), getDefaultClick())
                .setCancelable(false)
                .show();
    }

    public void show(Context context, String title, String message,
                     String positiveButtonText, DialogInterface.OnClickListener clickPositiveListener,
                     String negativeButtonText) {
        dismiss();

        if (context == null) return;

        dialog = new AlertDialog.Builder(context, R.style.AlertsDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, clickPositiveListener)
                .setNegativeButton(negativeButtonText, getDefaultClick())
                .setCancelable(false)
                .show();
    }

    public void show(Context context, String title, String message,
                     String positiveButtonText, DialogInterface.OnClickListener clickPositiveListener,
                     String negativeButtonText, DialogInterface.OnClickListener clickNegativeListener) {
        dismiss();

        if (context == null) return;

        dialog = new AlertDialog.Builder(context, R.style.AlertsDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, clickPositiveListener)
                .setNegativeButton(negativeButtonText, clickNegativeListener)
                .setCancelable(false)
                .show();
    }

    private DialogInterface.OnClickListener getDefaultClick() {
        return (dialogInterface, i) -> {
            if (activity != null) {
                activity.hideBottomNavigationView();
            }
        };
    }

    public void dismiss() {
        if (dialog != null && this.dialog.isShowing()) dialog.dismiss();

        dialog = null;

    }
}
