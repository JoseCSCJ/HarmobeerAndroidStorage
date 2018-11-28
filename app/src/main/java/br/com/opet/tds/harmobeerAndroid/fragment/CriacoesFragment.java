package br.com.opet.tds.harmobeerAndroid.fragment;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.opet.tds.harmobeerAndroid.DAO.CervejaDAO;
import br.com.opet.tds.harmobeerAndroid.DAO.PratoDAO;
import br.com.opet.tds.harmobeerAndroid.R;
import br.com.opet.tds.harmobeerAndroid.activity.AtualizarCerv;
import br.com.opet.tds.harmobeerAndroid.activity.AtualizarPrato;
import br.com.opet.tds.harmobeerAndroid.activity.MainActivity;
import br.com.opet.tds.harmobeerAndroid.adapter.CervejaAdapter;
import br.com.opet.tds.harmobeerAndroid.adapter.PratoAdapter;
import br.com.opet.tds.harmobeerAndroid.adapter.UsuarioAdapter;
import br.com.opet.tds.harmobeerAndroid.model.Cerveja;
import br.com.opet.tds.harmobeerAndroid.model.Prato;
import br.com.opet.tds.harmobeerAndroid.model.Usuario;
import br.com.opet.tds.harmobeerAndroid.repository.Repository;

public class CriacoesFragment extends Fragment {

    private Spinner listaUsuarios;
    private ListView listaCervejas, listaPratos;
    private Repository repository;
    private Cerveja cerveja;
    private Prato prato;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_criacoes, null);
        listaUsuarios = view.findViewById(R.id.viewUser);
        listaCervejas = view.findViewById(R.id.viewCerv);
        listaPratos = view.findViewById(R.id.viewPrato);

                try {
                    loadUsers();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Aconteceu um erro... Tente mais tarde", Toast.LENGTH_SHORT).show();
                }

        return view;


    }

    private void loadUsers() {
        repository = new Repository(getActivity().getApplicationContext());
        final UsuarioAdapter adapter = new UsuarioAdapter(getActivity(),android.R.layout.simple_spinner_item,repository.getUsuarioRepository().getAllUsuarios());
        listaUsuarios.setAdapter(adapter);
        listaUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Usuario usuario = (Usuario) adapterView.getItemAtPosition(i);
                    loadPrato(usuario.getId());
                    loadCerv(usuario.getId());
                }catch (Throwable t){
                    t.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Aconteceu um erro... Tente mais tarde", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

    }

    private void loadCerv(long id){
        repository = new Repository(getActivity().getApplicationContext());
        CervejaAdapter adapter = new CervejaAdapter(getActivity(),R.layout.cerveja_item,repository.getCervejaRepository().getAllCervejasJoin(id));
        listaCervejas.setAdapter(adapter);
        listaCervejas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CervejaDAO.CervejaJoin cervejaJoin = (CervejaDAO.CervejaJoin) adapterView.getItemAtPosition(i);
                callActivityCerv (cervejaJoin.cerveja.getID());
            }


        });

    }

    private void loadPrato(long id){
        repository = new Repository(getActivity().getApplicationContext());
        PratoAdapter adapter = new PratoAdapter(getActivity(),R.layout.prato_item,repository.getPratoRepository().getAllPratosJoin(id));
        listaPratos.setAdapter(adapter);
        listaPratos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PratoDAO.PratoJoin pratoJoin = (PratoDAO.PratoJoin) adapterView.getItemAtPosition(i);
                callActivityPrato (pratoJoin.prato.getId());

                }

            });
        }
    private void callActivityCerv (Long id) {

        Intent atualizar = new Intent(getActivity().getApplicationContext(),AtualizarCerv.class);
        atualizar.putExtra("ID",id);
        atualizar.putExtra("idUsuarioLogado", ((MainActivity)getActivity()).retornaUsuarioLogado());
        startActivity(atualizar);
    }

    private void callActivityPrato (Long id) {
        Intent atualizar = new Intent(getActivity().getApplicationContext(),AtualizarPrato.class);
        atualizar.putExtra("ID",id);
        atualizar.putExtra("idUsuarioLogado", ((MainActivity)getActivity()).retornaUsuarioLogado());
        startActivity(atualizar);
    }


}


