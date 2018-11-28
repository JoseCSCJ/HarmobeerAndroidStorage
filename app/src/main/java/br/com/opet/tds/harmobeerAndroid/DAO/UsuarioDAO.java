package br.com.opet.tds.harmobeerAndroid.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.opet.tds.harmobeerAndroid.model.Usuario;

/**
 * Created by José Carlos em 18/11/2018.
 */

@Dao
public interface UsuarioDAO {
    @Insert
    void insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Query("SELECT * from usuario_table ORDER BY username ASC")
    List<Usuario> loadUsuario();



    @Query("SELECT id, email, senha, username from usuario_table WHERE email like :email AND senha like :senha")
    Usuario logar(String email, String senha);

    @Query("SELECT id, email, senha, username from usuario_table WHERE id = :id")
    Usuario retornarUsuario(long id);


}


