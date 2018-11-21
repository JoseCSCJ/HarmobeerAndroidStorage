package br.com.opet.tds.harmobeerAndroid.repository;

import android.content.Context;

/**
 * Created by José Carlos em 18/11/2018.
 */

public class Repository {
    private CervejaRepository cervejaRepository;
    private PratoRepository pratoRepository;
    private UsuarioRepository usuarioRepository;

    public Repository(Context context){
        cervejaRepository = new CervejaRepository(context);
        pratoRepository = new PratoRepository(context);
        usuarioRepository = new UsuarioRepository(context);
    }

    public CervejaRepository getCervejaRepository() {
        return cervejaRepository;
    }

    public PratoRepository getPratoRepository() {
        return pratoRepository;
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }
}
