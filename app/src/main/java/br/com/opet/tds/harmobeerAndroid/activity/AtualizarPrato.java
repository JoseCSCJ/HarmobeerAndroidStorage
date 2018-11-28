package br.com.opet.tds.harmobeerAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.opet.tds.harmobeerAndroid.R;
import br.com.opet.tds.harmobeerAndroid.model.Prato;
import br.com.opet.tds.harmobeerAndroid.repository.Repository;

public class AtualizarPrato extends AppCompatActivity {

    private EditText nome, estilo, teor;
    private TextView dados;
    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_prato);
        try {
            repository = new Repository(getApplicationContext());
            Prato c = repository.getPratoRepository().loadPratoByID(getIntent().getLongExtra("ID", 0));
            dados = findViewById(R.id.dadosPrato);
            dados.setText("Prato: " + c.getNome());

            nome = findViewById(R.id.nomeprato);

            nome.setText(c.getNome(), TextView.BufferType.EDITABLE);
        }catch(Throwable t){
            t.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Não foi possível carregar os dados  desse prato",
                    Toast.LENGTH_SHORT).show();
        }
        Button mButton = findViewById(R.id.atualizarPrato);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Prato c = repository.getPratoRepository().loadPratoByID(getIntent().getLongExtra("ID", 0));

                    c.setNome(nome.getText().toString());
                    if(!c.getNome().isEmpty()) {
                        repository.getPratoRepository().update(c);
                        Toast.makeText(getApplicationContext(),
                                "O prato " + c.getNome() + " foi atualizado com sucesso!",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AtualizarPrato.this, MainActivity.class);
                        intent.putExtra("idUsuarioLogado", getIntent().getLongExtra("idUsuarioLogado",0));
                        startActivity(intent);
                    }
                    else{
                        throw new Exception();
                    }

                }catch (Throwable t){
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Não foi possível atualizar esse prato",
                            Toast.LENGTH_SHORT).show();
                }
    }

    });
        Button sButton = findViewById(R.id.removerPrato);
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Prato c = repository.getPratoRepository().loadPratoByID(getIntent().getLongExtra("ID", 0));
                    long idPrato = c.getId();
                    repository.getPratoRepository().delete(idPrato);
                        Toast.makeText(getApplicationContext(),
                                "O prato " + c.getNome() + " foi deletado com sucesso!",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AtualizarPrato.this, MainActivity.class);
                        intent.putExtra("idUsuarioLogado", getIntent().getLongExtra("idUsuarioLogado",0));
                        startActivity(intent);
                    }
                catch (Throwable t){
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Não foi possível remover esse prato",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}

