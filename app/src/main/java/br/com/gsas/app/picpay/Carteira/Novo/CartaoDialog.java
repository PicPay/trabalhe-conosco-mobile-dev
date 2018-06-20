package br.com.gsas.app.picpay.Carteira.Novo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import br.com.gsas.app.picpay.R;

public class CartaoDialog extends DialogFragment{

    private final int REQUEST_CODE = 1;
    private final int RESULT_CODE = 200;

    private FrameLayout visa;
    private FrameLayout master;
    private FrameLayout america;
    private FrameLayout elo;
    private FrameLayout hiper;

    private DialogListener listener;

    public interface DialogListener {

        void onFinishDialog(String inputText);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_cartao,null);

        visa = view.findViewById(R.id.visa_cartao);
        master = view.findViewById(R.id.master_cartao);
        america = view.findViewById(R.id.america_cartao);
        elo = view.findViewById(R.id.elo_cartao);
        hiper = view.findViewById(R.id.hipe_cartao);

        visa.setOnClickListener(clickCartao());
        master.setOnClickListener(clickCartao());
        america.setOnClickListener(clickCartao());
        elo.setOnClickListener(clickCartao());
        hiper.setOnClickListener(clickCartao());
        builder.setView(view);

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DialogListener) {
            listener = (DialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private View.OnClickListener clickCartao() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = view.getId();

                Intent intent = new Intent();

                switch (id){

                    case R.id.visa_cartao:

                        listener.onFinishDialog("Visa");
                        getDialog().dismiss();
                        break;

                    case  R.id.master_cartao:

                        listener.onFinishDialog("Mastercar");
                        getDialog().dismiss();
                        break;

                    case R.id.america_cartao:

                        listener.onFinishDialog("American Express");
                        getDialog().dismiss();
                        break;

                    case R.id.elo_cartao:

                        listener.onFinishDialog("Elo");
                        getDialog().dismiss();
                        break;

                    case R.id.hipe_cartao:

                        listener.onFinishDialog("Hipercard");
                        getDialog().dismiss();
                        break;

                }
            }
        };
    }
}
