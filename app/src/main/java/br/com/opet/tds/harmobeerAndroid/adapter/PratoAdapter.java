package br.com.opet.tds.harmobeerAndroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.opet.tds.harmobeerAndroid.DAO.PratoDAO;
import br.com.opet.tds.harmobeerAndroid.R;
import br.com.opet.tds.harmobeerAndroid.model.Prato;


/**
 * Created by Diego on 24/09/2018.
 */

public class PratoAdapter extends ArrayAdapter<PratoDAO.PratoJoin> {
    private int rId;

    public PratoAdapter(@NonNull Context context, int resource, @NonNull List<PratoDAO.PratoJoin> objects) {
        super(context, resource, objects);
        rId = resource;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent){
        View mView = currentView;

        if(mView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = inflater.inflate(rId,null);
        }

        PratoDAO.PratoJoin pratoJoin = getItem(position);

        TextView textNome = mView.findViewById(R.id.textNomePrato);
        //TextView textUsuario = mView.findViewById(R.id.textUser);


        textNome.setText(pratoJoin.prato.getNome().toUpperCase());
        //textUsuario.setText("Criado por " + pratoJoin.usuario.getUsername().toUpperCase());

        return mView;
    }
}
