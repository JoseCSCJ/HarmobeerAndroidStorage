package br.com.opet.tds.harmobeerAndroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import br.com.opet.tds.harmobeerAndroid.R;
import br.com.opet.tds.harmobeerAndroid.model.Usuario;

/**
 * Created by Diego on 22/10/2018.
 */

public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    private int rId;

    public UsuarioAdapter(Context context, int resource,List<Usuario> objects) {
        super(context, resource, objects);
        rId = resource;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent){
        /*View mView = currentView;
        if(mView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = inflater.inflate(rId,null);
        }*/

        Usuario usuario = getItem(position);

        TextView textUsuario = (TextView)super.getView(position,currentView,parent);
        textUsuario.setText(usuario.getUsername());

        return textUsuario;
    }

    @Override
    public View getDropDownView(int position, View currentView, ViewGroup parent) {
        Usuario usuario = getItem(position);
        TextView label = (TextView) super.getDropDownView(position, currentView, parent);
        label.setText(usuario.getUsername());

        return label;
    }
}

