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

  /*  @Query("SELECT filme_table.ID,filme_table.titulo,filme_table.ano_producao,filme_table.avaliacao, genero_table.ID as genero_ID ,genero_table.nome as genero_nome from filme_table INNER JOIN genero_table ON filme_table.genero_id = genero_table.ID ORDER BY avaliacao DESC")
    List<FilmeJoin> loadFilmesJoin();

    @Query("SELECT titulo from filme_table")
    List<String> loadFilmesNames(); */

    static class PratoJoin{
        @Embedded
        public Prato prato;
        @Embedded(prefix = "usuario_")
        public Usuario usuario;
    }
}