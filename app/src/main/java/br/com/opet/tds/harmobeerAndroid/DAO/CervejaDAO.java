package br.com.opet.tds.harmobeerAndroid.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.opet.tds.harmobeerAndroid.model.Cerveja;
import br.com.opet.tds.harmobeerAndroid.model.Usuario;

/**
 * Created by José Carlos on 18/11/2018.
 */

@Dao
public interface CervejaDAO {

    @Insert
    void insert(Cerveja cerveja);

    @Update
    void update(Cerveja cerveja);

    @Query("SELECT * FROM cerveja_table WHERE cerveja_table.ID == :id")
    Cerveja loadCervejasByID(Long id);

    @Query("DELETE FROM cerveja_table where cerveja_table.ID == :id")
    void delete(long id);

    @Query("SELECT * from cerveja_table ORDER BY nome ASC")
    List<Cerveja> loadCervejas();

    @Query("SELECT cerveja_table.ID," +
            " cerveja_table.usuario_id," +
            " cerveja_table.nome," +
            " cerveja_table.estilo," +
            " cerveja_table.teor_alc," +
            " usuario_table.ID as usuario_id," +
            " usuario_table.username as username " +
            "from cerveja_table " +
            "INNER JOIN usuario_table " +
            "ON cerveja_table.usuario_id = usuario_table.ID " +
            "WHERE usuario_table.ID=:id "+
            "ORDER BY nome ASC")
    List<CervejaJoin> loadCervejasJoin(long id);


    static class CervejaJoin{
        @Embedded
        public Cerveja cerveja;
        @Embedded(prefix = "usuario_")
        public Usuario usuario;
    }
}
