package br.com.opet.tds.harmobeerAndroid.repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.opet.tds.harmobeerAndroid.DAO.CervejaDAO;
import br.com.opet.tds.harmobeerAndroid.database.HarmobeerDatabase;
import br.com.opet.tds.harmobeerAndroid.model.Cerveja;

/**
 * Created by José Carlos em 18/11/2018.
 */

public class CervejaRepository {
    private CervejaDAO mCervejaDAO;
    private List<Cerveja> mCervejas;
    private List<CervejaDAO.CervejaJoin> mCervejasJoin;

    public CervejaRepository(Context context) {
        HarmobeerDatabase db = HarmobeerDatabase.getDatabase(context);
        mCervejaDAO = db.cervejaDAO();
    }

    public List<Cerveja> getAllCervejas() {
        mCervejas = mCervejaDAO.loadCervejas();
        return mCervejas;
    }

    public List<CervejaDAO.CervejaJoin> getAllCervejasJoin(long id){
        mCervejasJoin = mCervejaDAO.loadCervejasJoin(id);
        return mCervejasJoin;
    }


    public Cerveja loadCervejaByID(long ID) {
        return mCervejaDAO.loadCervejasByID(ID);
    }

    public void insert(Cerveja cerveja) {
        new insertAsyncTask(mCervejaDAO).execute(cerveja);
    }

    public void delete(long id) {
        mCervejaDAO.delete(id);
    }

    public void update(Cerveja cerveja) {
        mCervejaDAO.update(cerveja);
    }

    private static class insertAsyncTask extends AsyncTask<Cerveja, Void, Void> {

        private CervejaDAO mAsyncTaskDAO;

        insertAsyncTask(CervejaDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Cerveja... params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }
}
