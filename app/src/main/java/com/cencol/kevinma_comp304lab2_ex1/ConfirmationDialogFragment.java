package com.cencol.kevinma_comp304lab2_ex1;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ConfirmationDialogFragment extends DialogFragment {

    // Static Factory method to create a new instance
    static ConfirmationDialogFragment newInstance(String title, String message) {
        ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String msg = getArguments().getString("message");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(getResources().getString(R.string.pos_actn_lbl),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((BaseActivity) getActivity()).doPositiveClick();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.neg_actn_lbl),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((BaseActivity) getActivity()).doNegativeClick();
                            }
                        })
                .create();
    }
}