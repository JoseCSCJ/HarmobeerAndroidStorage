package br.com.opet.tds.harmobeerAndroid.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by José Carlos on 18/11/2018.
 */
@Entity(tableName = "prato_table",
        foreignKeys = @ForeignKey(entity = Usuario.class,
                parentColumns = "ID",
                childColumns = "usuario_id"))
public class Prato {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long id;

    private String nome;



    @ColumnInfo(name = "usuario_id")
    private long usuarioId;

    public Prato() {
    }
    public Prato(long id, String nome ) {
        this.id = id;
        this.nome = nome;
    }

    public long getId() {
        return id;
    }
    public void setId(long Id) {
        this.id = Id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
