package br.com.gsas.app.picpay.Util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import br.com.gsas.app.picpay.R;

public class DialogAceitar extends DialogFragment {

    AlertDialog dialog;
    private String text;
    private TextView text_dialog;


    public static DialogAceitar newInstance(String text){

        DialogAceitar dialogAceitar = new DialogAceitar();

        Bundle args = new Bundle();
        args.putString("Text", text);

        dialogAceitar.setArguments(args);

        return dialogAceitar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        if(getArguments() != null){

            text = (String) getArguments().getString("Text");
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_text,null);

        text_dialog = view.findViewById(R.id.text_dialog);
        text_dialog.setText(text);

        builder.setView(view)

                .setPositiveButton(getString(R.string.bot_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getDialog().dismiss();
                    }
                });

        return builder.create();

    }
}
