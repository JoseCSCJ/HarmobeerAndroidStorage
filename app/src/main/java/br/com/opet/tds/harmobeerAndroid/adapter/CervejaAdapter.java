package br.com.opet.tds.harmobeerAndroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.opet.tds.harmobeerAndroid.DAO.CervejaDAO;
import br.com.opet.tds.harmobeerAndroid.R;



/**
 * Created by Diego on 24/09/2018.
 */

public class CervejaAdapter extends ArrayAdapter<CervejaDAO.CervejaJoin> {
    private int rId;

    public CervejaAdapter(@NonNull Context context, int resource, @NonNull List<CervejaDAO.CervejaJoin> objects) {
        super(context, resource, objects);
        rId = resource;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent){
        View mView = currentView;
        CervejaHolder ch;

        if(mView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = inflater.inflate(rId,null);
            ch = new CervejaHolder();
            ch.textNome =  (TextView) mView.findViewById(R.id.textNomeCerveja);
            ch.textEstilo = (TextView) mView.findViewById(R.id.textEstiloCerveja);
            ch.textTeorAlc = mView.findViewById(R.id.textTeorAlc);
            mView.setTag(ch);
        }else{
            ch = (CervejaHolder) mView.getTag();
        }

        CervejaDAO.CervejaJoin cervejaJoin = getItem(position);

        ch.textNome.setText(cervejaJoin.cerveja.getNome().toUpperCase());
        ch.textEstilo.setText(cervejaJoin.cerveja.getEstilo().toUpperCase());
        ch.textTeorAlc.setText(cervejaJoin.cerveja.getTeor_alc() + " %");

        return mView;


    }
    static class CervejaHolder{
        TextView textNome;
        TextView textEstilo;
        TextView textTeorAlc;
    }
}


