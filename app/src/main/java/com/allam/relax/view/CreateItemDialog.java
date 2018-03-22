package com.allam.relax.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.allam.relax.R;

/**
 * Relax
 * Created by Uranus on 22/03/2018 .
 */

public class CreateItemDialog extends DialogFragment {

    private EditText mEditTextName, mEditTextQantity, mEditTextPrice,
            mEditTextMarket, mEditTextNotes;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_new_item, null);

        mEditTextName = view.findViewById(R.id.dialog_item_name);
        mEditTextQantity = view.findViewById(R.id.dialog_item_quantity);
        mEditTextPrice = view.findViewById(R.id.dialog_item_price);
        mEditTextMarket = view.findViewById(R.id.dialog_item_market);
        mEditTextNotes = view.findViewById(R.id.dialog_item_notes);

        builder.setView(view)
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), mEditTextName.getText(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        return builder.create();
    }
}
