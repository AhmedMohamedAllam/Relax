package com.allam.relax.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.allam.relax.R;
import com.allam.relax.controller.Validation;
import com.allam.relax.controller.interfaces.OnCompleteListener;
import com.allam.relax.model.Item;

/**
 * Relax
 * Created by Uranus on 22/03/2018 .
 */

public class ItemDetailDialog extends DialogFragment{

    private EditText mEditTextName, mEditTextQuantity, mEditTextPrice,
            mEditTextMarket, mEditTextNotes;
    private Button mDoneButton, mCancelButton;
    public OnCompleteListener<Item> mCompleteListener;
    private static final String ITEM_TAG = "item";

    public static ItemDetailDialog getInstance(Item item){
        ItemDetailDialog dialogFragment = new ItemDetailDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEM_TAG, item);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_new_item, null);

        mEditTextName = view.findViewById(R.id.dialog_item_name);
        mEditTextQuantity = view.findViewById(R.id.dialog_item_quantity);
        mEditTextPrice = view.findViewById(R.id.dialog_item_price);
        mEditTextMarket = view.findViewById(R.id.dialog_item_market);
        mEditTextNotes = view.findViewById(R.id.dialog_item_notes);
        mDoneButton = view.findViewById(R.id.done_dialog_item_button);
        mCancelButton = view.findViewById(R.id.cancel_dialog_item_button);


        if (getArguments() != null && getArguments().get(ITEM_TAG) != null) {
            Item item = (Item) getArguments().get(ITEM_TAG);
            mEditTextName.setText(item.getName());
            mEditTextQuantity.setText(item.getQuantity());
            mEditTextPrice.setText(item.getPrice());
            mEditTextMarket.setText(item.getMarket());
            mEditTextNotes.setText(item.getNote());
        }


        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validation.isEditTextNull(getActivity(), mEditTextName)){
                    return;
                }
                Item createdItem = new Item(
                        mEditTextName.getText().toString(),
                        mEditTextQuantity.getText().toString(),
                        mEditTextPrice.getText().toString(),
                        mEditTextMarket.getText().toString(),
                        mEditTextNotes.getText().toString()
                );

                if (mCompleteListener != null){
                    mCompleteListener.OnComplete(createdItem, null);
                    String successMessage = String.format(getString(R.string.add_item_successfully),createdItem.getName());
                    Toast.makeText(getActivity(), successMessage, Toast.LENGTH_SHORT).show();
                }
                getDialog().dismiss();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
