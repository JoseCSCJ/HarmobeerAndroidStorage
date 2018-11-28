package br.com.opet.tds.harmobeerAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.opet.tds.harmobeerAndroid.R;
import br.com.opet.tds.harmobeerAndroid.fragment.CervFragment;
import br.com.opet.tds.harmobeerAndroid.fragment.PerfilFragment;
import br.com.opet.tds.harmobeerAndroid.fragment.PratoFragment;
import br.com.opet.tds.harmobeerAndroid.fragment.UsuarioFragment;
import br.com.opet.tds.harmobeerAndroid.fragment.CriacoesFragment;
import br.com.opet.tds.harmobeerAndroid.model.Cerveja;
import br.com.opet.tds.harmobeerAndroid.model.Prato;
import br.com.opet.tds.harmobeerAndroid.model.Usuario;
import br.com.opet.tds.harmobeerAndroid.repository.Repository;

public class MainActivity extends AppCompatActivity {
     private TextView mTextMessage;
     private Repository repository;
     private Cerveja cerveja;
     private Prato prato;
     private Usuario usuario;
     private CervFragment cervFragment;
     private PratoFragment pratoFragment;
     private UsuarioFragment usuarioFragment;
     private PerfilFragment perfilFragment;
     private EditText nome, estilo, teor, nomeprato, username, email, senha, usernamePer, emailPer, senhaAnt, senhaPer, senhaConf;
     private Spinner listaUsuarios;
    private ListView listaCervejas, listaPratos;
    private Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_cerv:
                    mTextMessage.setText(R.string.title_cerv);
                    fragment = new CervFragment();
                    return loadFragment(fragment);
                case R.id.navigation_prato:
                    mTextMessage.setText(R.string.title_prato);
                    fragment = new PratoFragment();
                    return loadFragment(fragment);
                case R.id.navigation_admin:
                    mTextMessage.setText(R.string.title_admin);
                    fragment = new UsuarioFragment();
                    return loadFragment(fragment);
                case R.id.navigation_criac:
                    mTextMessage.setText(R.string.title_criac);
                    fragment = new CriacoesFragment();
                    return loadFragment(fragment);
                case R.id.navigation_perf:
                    mTextMessage.setText(R.string.title_perf);
                    fragment = new PerfilFragment();
                    return loadFragment(fragment);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = new Repository(getApplicationContext());
        cerveja = new Cerveja();
        prato = new Prato();
        usuario = new Usuario();

        long idUsuarioLogado = (Long) getIntent().getSerializableExtra("idUsuarioLogado");
        Usuario usuarioLogado = repository.getUsuarioRepository().retornarUsuario(idUsuarioLogado);

        System.out.println("O usuario " + usuarioLogado.getUsername()+" logou...");

        nome = findViewById(R.id.nomecerv);
        estilo = findViewById(R.id.estilo);
        teor = findViewById(R.id.teor);

        nomeprato = findViewById(R.id.nomeprato);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);

        listaUsuarios = findViewById(R.id.viewUser);
        listaCervejas = findViewById(R.id.viewCerv);
        listaPratos = findViewById(R.id.viewPrato);

        usernamePer = findViewById(R.id.usernamePer);
        emailPer = findViewById(R.id.emailPer);

        senhaAnt = findViewById(R.id.senhaAnt);
        senhaPer = findViewById(R.id.senhaPer);
        senhaConf= findViewById(R.id.senhaConf);

        cervFragment = new CervFragment();
        pratoFragment = new PratoFragment();
        usuarioFragment = new UsuarioFragment();
        perfilFragment = new PerfilFragment();

        mTextMessage = findViewById(R.id.message);
        mTextMessage.setText(R.string.title_cerv);
        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new CervFragment());

        Button mButton = findViewById(R.id.deslogar);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                } catch (Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Não foi possível sair",
                            Toast.LENGTH_SHORT).show();

                }


            }


        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    public long retornaUsuarioLogado(){
        return (Long) getIntent().getSerializableExtra("idUsuarioLogado");
    }


}


