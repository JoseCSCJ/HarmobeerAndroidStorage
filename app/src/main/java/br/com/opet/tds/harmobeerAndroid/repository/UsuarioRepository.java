package br.com.opet.tds.harmobeerAndroid.repository;


import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.opet.tds.harmobeerAndroid.DAO.UsuarioDAO;

import br.com.opet.tds.harmobeerAndroid.database.HarmobeerDatabase;

import br.com.opet.tds.harmobeerAndroid.model.Usuario;


/**
 * Created by José Carlos on 18/11/2018.
 */

public class UsuarioRepository {
    private UsuarioDAO mUsuarioDAO;
    private List<Usuario> mUsuarios;

    public UsuarioRepository(Context context){
        HarmobeerDatabase db = HarmobeerDatabase.getDatabase(context);
        mUsuarioDAO = db.usuarioDAO();
    }

    public List<Usuario> getAllUsuarios(){
        mUsuarios = mUsuarioDAO.loadUsuario();
        return mUsuarios;
    }

    /*public Usuario loadUsuarioByID(long ID) {
        return mUsuarioDAO.loadUsuarioByID(ID);
    }*/

    public void insert(Usuario usuario){
        mUsuarioDAO.insert(usuario);
    }
    public void update(Usuario usuario) {mUsuarioDAO.update(usuario);}
    public Usuario logar(String email, String senha){
        return mUsuarioDAO.logar(email, senha);
    }
    public Usuario retornarUsuario(long id){
        return mUsuarioDAO.retornarUsuario(id);
    }
}
