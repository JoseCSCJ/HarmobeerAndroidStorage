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
        Button mButton = view.findViewById(R.id.cadastrarUsuario);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    username = getActivity().findViewById(R.id.username);
                    email = getActivity().findViewById(R.id.email);
                    senha = getActivity().findViewById(R.id.senha);

                    Usuario usuario = new Usuario();

                    usuario.setUsername(username.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());


                    Repository repository = new Repository(getActivity().getApplicationContext());
                    if (usuario.getEmail().contains("@") &&
                            !usuario.getSenha().isEmpty() &&
                            !usuario.getUsername().isEmpty() &&
                            usuario.getSenha().length()>4) {
                        repository.getUsuarioRepository().insert(usuario);
                    }
                    else{
                        throw new Exception();
                    }
                    Toast.makeText(getActivity().getApplicationContext(),
                            "O usuario administrador " + usuario.getUsername() + " foi adicionado com sucesso! " +
                                    "Passe os dados para que o novo administrador possa fazer seu primeiro login",
                            Toast.LENGTH_SHORT).show();
                }catch (Throwable t){
                    t.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Não foi possível adicionar esse administrador. Reveja os dados inseridos.",
                            Toast.LENGTH_SHORT).show();
                }

                username.setText("");
                email.setText("");
                senha.setText("");
            }
        });
        return view;
    }



}
