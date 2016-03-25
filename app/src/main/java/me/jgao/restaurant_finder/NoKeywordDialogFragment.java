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
        builder.setMessage("\uD83D\uDE39\uD83D\uDE39\uD83D\uDE39\n" + "No keyword to specify?")
                .setPositiveButton(R.string.about_dialog_pos_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity) getActivity()).startSearch();
                    }
                })
                .setNegativeButton(R.string.about_dialog_neg_button, null);

        return builder.create();
    }
}
