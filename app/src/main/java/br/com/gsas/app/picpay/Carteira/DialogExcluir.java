package br.com.gsas.app.picpay.Carteira;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import br.com.gsas.app.picpay.Domain.Cartao;
import br.com.gsas.app.picpay.R;

public class DialogExcluir extends DialogFragment {

    AlertDialog dialog;
    private Cartao cartao;
    private TextView text_temos;

    private DialogListener listener;

    public interface DialogListener {

        void onExcluir(Cartao cartao);
    }


    public static DialogExcluir newInstance(Cartao cartao, DialogListener listener){

        DialogExcluir dialogExcluir = new DialogExcluir();

        dialogExcluir.listener = listener;
        Bundle args = new Bundle();
        args.putSerializable("Cartao", cartao);

        dialogExcluir.setArguments(args);

        return dialogExcluir;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        if(getArguments() != null){

            cartao = (Cartao) getArguments().getSerializable("Cartao");
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_excluir,null);

        text_temos = view.findViewById(R.id.text_termos);

        String msg = getString(R.string.excluir_termos);
        String number = cartao.getCard_nunber();

        msg = msg.replace("XXXX", number.substring(number.length() - 4, number.length()));
        text_temos.setText(msg);

        builder.setView(view)

                .setPositiveButton(getString(R.string.dialog_excluir), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        listener.onExcluir(cartao);
                    }
                })
                .setNegativeButton(getString(R.string.dialog_cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getDialog().dismiss();
                    }
                });

        return builder.create();

    }
}
