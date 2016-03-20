package me.jgao.restaurant_finder;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by jianxin on 3/20/16.
 */
public class NoKeywordDialogFragment extends DialogFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("\uD83D\uDE39\uD83D\uDE39\uD83D\uDE39\n" + "Please specify some keywords before starting the search.")
                .setPositiveButton(R.string.about_dialog_pos_button, null)
                .setNegativeButton(R.string.about_dialog_neg_button, null);

        return builder.create();
    }
}
