package me.jgao.restaurant_finder;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by jianxin on 3/19/16.
 */
public class AboutDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("\uD83D\uDE3B\n" + "Made by:\n" + "Jianxin Gao" + "\nChang Xue" + "\nfor CMPE277.")
                .setPositiveButton(R.string.about_dialog_pos_button, null)
                .setNegativeButton(R.string.about_dialog_neg_button, null);

        return builder.create();
    }
}
