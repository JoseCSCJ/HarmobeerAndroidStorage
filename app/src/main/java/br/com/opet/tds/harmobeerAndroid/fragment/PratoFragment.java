package br.com.opet.tds.harmobeerAndroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.opet.tds.harmobeerAndroid.R;
import br.com.opet.tds.harmobeerAndroid.model.Prato;
import br.com.opet.tds.harmobeerAndroid.repository.Repository;

public class PratoFragment extends Fragment {

    private EditText nomeprato;
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
        View view = inflater.inflate(R.layout.fragment_prato, null);
        Button mButton = (Button) view.findViewById(R.id.cadastrarPrato);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeprato = getActivity().findViewById(R.id.nomeprato);

                Prato prato = new Prato();

                Long idUsuario = (Long)getActivity().getIntent().getSerializableExtra("idUsuarioLogado");

                prato.setNome(nomeprato.getText().toString());
                prato.setUsuarioId(idUsuario);

                Repository repository = new Repository(getActivity().getApplicationContext());
                repository.getPratoRepository().insert(prato);

                Toast.makeText(getActivity().getApplicationContext(),
                        "O prato "+ prato.getNome() + " foi adicionado com sucesso!",
                        Toast.LENGTH_SHORT).show();

                nomeprato.setText("");

            }
        });
        return view;
    }



}
