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

public class UsuarioFragment extends Fragment {

    private EditText username, email, senha;
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
        View view = inflater.inflate(R.layout.fragment_usuario, null);
        Button mButton = (Button) view.findViewById(R.id.cadastrarUsuario);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = getActivity().findViewById(R.id.username);
                email = getActivity().findViewById(R.id.email);
                senha= getActivity().findViewById(R.id.senha);

                Usuario usuario = new Usuario();

                Long idUsuario = (Long)getActivity().getIntent().getSerializableExtra("idUsuarioLogado");

                usuario.setUsername(username.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());


                Repository repository = new Repository(getActivity().getApplicationContext());
                repository.getUsuarioRepository().insert(usuario);

                Toast.makeText(getActivity().getApplicationContext(),
                        "O usuario administrador"+ usuario.getUsername() + " foi adicionado com sucesso! " +
                                "Passe esses dados para que o novo administrador possa fazer seu primeiro login",
                        Toast.LENGTH_SHORT).show();

                username.setText("");
                email.setText("");
                senha.setText("");
            }
        });
        return view;
    }



}
