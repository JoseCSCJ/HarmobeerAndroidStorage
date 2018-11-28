package br.com.opet.tds.harmobeerAndroid.repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.opet.tds.harmobeerAndroid.DAO.PratoDAO;
import br.com.opet.tds.harmobeerAndroid.database.HarmobeerDatabase;
import br.com.opet.tds.harmobeerAndroid.model.Prato;

/**
 * Created by José Carlos on 18/11/2018.
 */

public class PratoRepository {
    private PratoDAO mPratoDAO;
    private List<Prato> mPratos;
    private List<PratoDAO.PratoJoin> mPratoJoin;

    public PratoRepository(Context context){
        HarmobeerDatabase db = HarmobeerDatabase.getDatabase(context);
        mPratoDAO = db.pratoDAO();
    }

    public List<Prato> getAllPratos(){
        mPratos = mPratoDAO.loadPratos();
        return mPratos;
    }

    public List<PratoDAO.PratoJoin> getAllPratosJoin(long id){
        mPratoJoin = mPratoDAO.loadPratosJoin(id);
        return mPratoJoin;
    }

    public Prato loadPratoByID(long ID) {
        return mPratoDAO.loadPratoByID(ID);
    }

    public void insert(Prato prato){
        mPratoDAO.insert(prato);
    }
    public void update(Prato prato) {mPratoDAO.update(prato);}
    public void delete(long id) {mPratoDAO.delete(id);}
}
