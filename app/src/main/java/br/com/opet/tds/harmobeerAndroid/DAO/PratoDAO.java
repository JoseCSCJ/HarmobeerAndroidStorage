package br.com.opet.tds.harmobeerAndroid.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.opet.tds.harmobeerAndroid.model.Prato;
import br.com.opet.tds.harmobeerAndroid.model.Usuario;

/**
 * Created by José Carlos on 18/11/2018.
 */

@Dao
public interface PratoDAO {

    @Insert
    void insert(Prato prato);

    @Update
    void update(Prato prato);

    @Query("SELECT * FROM prato_table WHERE prato_table.ID == :id")
    Prato loadPratoByID(Long id);

    @Query("DELETE FROM prato_table where prato_table.ID == :id")
    void delete(long id);

    @Query("SELECT * from prato_table ORDER BY nome ASC")
    List<Prato> loadPratos();

    @Query("SELECT prato_table.ID," +
            " prato_table.usuario_id," +
            " prato_table.nome," +
            " usuario_table.id as usuario_id," +
            " usuario_table.username as username " +
            "from prato_table " +
            "INNER JOIN usuario_table " +
            "ON prato_table.usuario_id = usuario_table.ID " +
            "WHERE usuario_table.ID = :id"+
            " ORDER BY nome ASC")
    List<PratoJoin> loadPratosJoin(long id);


    static class PratoJoin{
        @Embedded
        public Prato prato;
        @Embedded(prefix = "usuario_")
        public Usuario usuario;
    }
}