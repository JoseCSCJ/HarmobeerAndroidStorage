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
import br.com.opet.tds.harmobeerAndroid.model.Cerveja;
import br.com.opet.tds.harmobeerAndroid.repository.Repository;

public class CervFragment extends Fragment {

    private EditText nome, estilo,  teor;

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
        Button mButton = view.findViewById(R.id.cadastrarCerv);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    nome = getActivity().findViewById(R.id.nomecerv);
                    estilo = getActivity().findViewById(R.id.estilo);
                    teor = getActivity().findViewById(R.id.teor);

                    Cerveja cerveja = new Cerveja();

                    Long idUsuario = (Long) getActivity().getIntent().getSerializableExtra("idUsuarioLogado");

                    cerveja.setNome(nome.getText().toString());
                    cerveja.setEstilo(estilo.getText().toString());
                    cerveja.setTeor_alc(Long.parseLong(teor.getText().toString()));
                    cerveja.setUsuarioId(idUsuario);

                    Repository repository = new Repository(getActivity().getApplicationContext());
                    if(!cerveja.getNome().isEmpty() && !cerveja.getEstilo().isEmpty()) {
                        repository.getCervejaRepository().insert(cerveja);
                        Toast.makeText(getActivity().getApplicationContext(),
                                "A cerveja " + cerveja.getNome() + " foi adicionada com sucesso!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        throw new Exception();
                    }

                }catch (Throwable t){
                    t.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Não foi possível adicionar uma nova cerveja",
                            Toast.LENGTH_SHORT).show();
                }
                nome.setText("");
                estilo.setText("");
                teor.setText("");
            }
        });
        return view;
    }



}
