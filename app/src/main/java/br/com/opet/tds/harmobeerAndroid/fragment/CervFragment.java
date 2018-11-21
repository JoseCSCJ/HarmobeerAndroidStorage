package br.com.opet.tds.harmobeerAndroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.opet.tds.harmobeerAndroid.R;
import br.com.opet.tds.harmobeerAndroid.model.Cerveja;
import br.com.opet.tds.harmobeerAndroid.repository.Repository;

public class CervFragment extends Fragment {

    private EditText nome, estilo,  teor;
    private Repository repository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cerv, null);
        Button mButton = (Button) view.findViewById(R.id.cadastrarCerv);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome = getActivity().findViewById(R.id.nomecerv);
                estilo = getActivity().findViewById(R.id.estilo);
                teor = getActivity().findViewById(R.id.teor);

                Cerveja cerveja = new Cerveja();

                Long idUsuario = (Long)getActivity().getIntent().getSerializableExtra("idUsuarioLogado");

                cerveja.setNome(nome.getText().toString());
                cerveja.setEstilo(estilo.getText().toString());
                cerveja.setTeor_alc(Long.parseLong(teor.getText().toString()));
                cerveja.setUsuarioId(idUsuario);

                Repository repository = new Repository(getActivity().getApplicationContext());
                repository.getCervejaRepository().insert(cerveja);

                Toast.makeText(getActivity().getApplicationContext(),
                        "A cerveja "+ cerveja.getNome() + " foi adicionada com sucesso!",
                        Toast.LENGTH_SHORT).show();

                nome.setText("");
                estilo.setText("");
                teor.setText("");
            }
        });
        return view;
    }



}
