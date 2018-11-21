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

  /*  @Query("SELECT filme_table.ID,filme_table.titulo,filme_table.ano_producao,filme_table.avaliacao, genero_table.ID as genero_ID ,genero_table.nome as genero_nome from filme_table INNER JOIN genero_table ON filme_table.genero_id = genero_table.ID ORDER BY avaliacao DESC")
    List<FilmeJoin> loadFilmesJoin();

    @Query("SELECT titulo from filme_table")
    List<String> loadFilmesNames(); */

    static class CervejaJoin{
        @Embedded
        public Cerveja cerveja;
        @Embedded(prefix = "usuario_")
        public Usuario usuario;
    }
}
