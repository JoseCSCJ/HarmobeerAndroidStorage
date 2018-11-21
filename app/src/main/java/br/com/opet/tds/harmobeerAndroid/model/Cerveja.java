package br.com.opet.tds.harmobeerAndroid.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by José Carlos on 18/11//2018.
 */

@Entity(tableName = "cerveja_table",
        foreignKeys = @ForeignKey(entity = Usuario.class,
            parentColumns = "ID",
            childColumns = "usuario_id"))
public class Cerveja {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long ID;
    private String nome;
    private String estilo;
    private double teor_alc;
    @ColumnInfo(name = "usuario_id")
    private long usuarioId;

    public Cerveja(){}
    public Cerveja(long ID, String nome, String estilo, double teor_alc) {
        this.ID = ID;
        this.nome = nome;
        this.estilo = estilo;
        this.teor_alc = teor_alc;
    }

    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstilo() {
        return estilo;
    }
    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public double getTeor_alc() {
        return teor_alc;
    }
    public void setTeor_alc(double teor_alc) {
        this.teor_alc = teor_alc;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
