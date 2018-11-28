package br.com.opet.tds.harmobeerAndroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.opet.tds.harmobeerAndroid.R;
import br.com.opet.tds.harmobeerAndroid.model.Cerveja;
import br.com.opet.tds.harmobeerAndroid.model.Usuario;
import br.com.opet.tds.harmobeerAndroid.repository.CervejaRepository;
import br.com.opet.tds.harmobeerAndroid.repository.Repository;

public class AtualizarCerv extends AppCompatActivity {

    private EditText nome, estilo, teor;
    private TextView dados;
    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_cerv);
        repository = new Repository(getApplicationContext());

        try{
            Cerveja c = repository.getCervejaRepository().loadCervejaByID(getIntent().getLongExtra("ID", 0));
            dados = findViewById(R.id.dadosCerv);
            dados.setText("Cerveja: " + c.getNome() +" Estilo: " + c.getEstilo() + " Teor " + c.getTeor_alc() + " %");
            nome = findViewById(R.id.nomecerv);
            estilo = findViewById(R.id.estilo);
            teor = findViewById(R.id.teor);
            nome.setText(c.getNome(), TextView.BufferType.EDITABLE);
            estilo.setText(c.getEstilo(), TextView.BufferType.EDITABLE);
            teor.setText (String.valueOf(c.getTeor_alc()), TextView.BufferType.EDITABLE);
        }catch (Throwable t){
            t.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Não foi possível carregar os dados  dessa cerveja",
                    Toast.LENGTH_SHORT).show();

        }
        Button mButton = findViewById(R.id.atualizarCerv);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cerveja c = repository.getCervejaRepository().loadCervejaByID(getIntent().getLongExtra("ID", 0));

                    c.setNome(nome.getText().toString());
                    c.setEstilo(estilo.getText().toString());
                    c.setTeor_alc(Double.parseDouble(teor.getText().toString()));



                    if(!c.getNome().isEmpty() && !c.getEstilo().isEmpty()) {
                        repository.getCervejaRepository().update(c);
                        Toast.makeText(getApplicationContext(),
                                "A cerveja " + c.getNome() + " foi atualizada com sucesso!",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AtualizarCerv.this, MainActivity.class);
                        intent.putExtra("idUsuarioLogado", getIntent().getLongExtra("idUsuarioLogado",0));
                        startActivity(intent);
                    }
                    else{
                        throw new Exception();
                    }

                }catch (Throwable t){
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Não foi possível atualizar essa cerveja",
                            Toast.LENGTH_SHORT).show();
                }
    }

    });

        Button sButton = findViewById(R.id.removerCerv);
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cerveja c = repository.getCervejaRepository().loadCervejaByID(getIntent().getLongExtra("ID", 0));

                        repository.getCervejaRepository().delete(c.getID());
                        Toast.makeText(getApplicationContext(),
                                "A cerveja " + c.getNome() + " foi removida com sucesso!",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AtualizarCerv.this, MainActivity.class);
                        intent.putExtra("idUsuarioLogado", getIntent().getLongExtra("idUsuarioLogado",0));
                        startActivity(intent);


                }catch (Throwable t){
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Não foi possível remover essa cerveja",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}

