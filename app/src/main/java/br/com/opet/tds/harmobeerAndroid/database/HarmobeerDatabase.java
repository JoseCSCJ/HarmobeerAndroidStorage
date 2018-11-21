package br.com.opet.tds.harmobeerAndroid.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.opet.tds.harmobeerAndroid.DAO.CervejaDAO;
import br.com.opet.tds.harmobeerAndroid.DAO.PratoDAO;
import br.com.opet.tds.harmobeerAndroid.DAO.UsuarioDAO;
import br.com.opet.tds.harmobeerAndroid.model.Cerveja;
import br.com.opet.tds.harmobeerAndroid.model.Prato;
import br.com.opet.tds.harmobeerAndroid.model.Usuario;


/**
 * Created by José Carlos on 18/11/2018.
 */
@Database(entities = {Usuario.class,Prato.class, Cerveja.class},version = 1)
public abstract class HarmobeerDatabase extends RoomDatabase {
    private static volatile HarmobeerDatabase INSTANCE;
    public abstract CervejaDAO cervejaDAO();
    public abstract PratoDAO pratoDAO();
    public abstract UsuarioDAO usuarioDAO();

    public static HarmobeerDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (HarmobeerDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),HarmobeerDatabase.class,"harmobeer_database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
