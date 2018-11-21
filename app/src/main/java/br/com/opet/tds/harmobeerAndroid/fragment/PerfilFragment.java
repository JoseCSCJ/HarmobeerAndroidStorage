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
import br.com.opet.tds.harmobeerAndroid.model.Usuario;
import br.com.opet.tds.harmobeerAndroid.repository.Repository;

public class PerfilFragment extends Fragment {

    private EditText usernamePer, emailPer, senhaAnt, senhaPer, senhaConf;
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
        View view = inflater.inflate(R.layout.fragment_perfil, null);
        Button mButton = (Button) view.findViewById(R.id.editarUsuario);
        Button sButton = (Button) view.findViewById(R.id.alterarSenha);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository repository = new Repository(getActivity().getApplicationContext());

                Long idUsuario = (Long)getActivity().getIntent().getSerializableExtra("idUsuarioLogado");
                Usuario usuarioAntigo = repository.getUsuarioRepository().retornarUsuario(idUsuario);
                Usuario usuarioNovo = usuarioAntigo;

                usernamePer = getActivity().findViewById(R.id.usernamePer);
                emailPer = getActivity().findViewById(R.id.emailPer);

                if(usernamePer.getText().toString()==null && emailPer.getText().toString()==null){
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Necessário preencher para editar as informações...",
                            Toast.LENGTH_SHORT).show();
                }else {

                    if (usernamePer.getText().toString() != null) {
                        usuarioNovo.setUsername(usernamePer.getText().toString());
                    }
                    if (emailPer.getText().toString() != null) {
                        usuarioNovo.setEmail(emailPer.getText().toString());
                    }
                        repository.getUsuarioRepository().update(usuarioNovo);
                        Toast.makeText(getActivity().getApplicationContext(),
                                "O usuario administrador " + usuarioAntigo.getUsername() + " teve seus dados alterados." +
                                        "Novo Username: " + usuarioNovo.getUsername() +
                                        "Novo Email: " + usuarioNovo.getEmail(),
                                Toast.LENGTH_SHORT).show();
                    }


                }

        });

        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senhaAnt = getActivity().findViewById(R.id.senhaAnt);
                senhaPer = getActivity().findViewById(R.id.senhaPer);
                senhaConf= getActivity().findViewById(R.id.senhaConf);

                Repository repository = new Repository(getActivity().getApplicationContext());

                Long idUsuario = (Long)getActivity().getIntent().getSerializableExtra("idUsuarioLogado");
                Usuario usuarioAntigo = repository.getUsuarioRepository().retornarUsuario(idUsuario);
                Usuario usuarioNovo = usuarioAntigo;


                if(senhaAnt.getText().toString().compareTo(usuarioAntigo.getSenha())==0 &&
                        senhaPer.getText().toString().compareTo(senhaConf.getText().toString())==0){
                    usuarioNovo.setSenha(senhaPer.getText().toString());
                    repository.getUsuarioRepository().update(usuarioNovo);
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Sua senha foi alterada com sucesso.",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Não foi possível alterar a sua senha. Reveja os dados inseridos.",
                            Toast.LENGTH_SHORT).show();
                }



                senhaAnt.setText("");
                senhaPer.setText("");
                senhaConf.setText("");
            }
        });
        return view;
    }



}
