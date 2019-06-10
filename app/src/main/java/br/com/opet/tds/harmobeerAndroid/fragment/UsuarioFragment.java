package br.com.opet.tds.harmobeerAndroid.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.auth.AuthCredential;

import com.google.firebase.auth.FirebaseAuth;

import br.com.opet.tds.harmobeerAndroid.R;
import br.com.opet.tds.harmobeerAndroid.activity.LoginActivity;
import br.com.opet.tds.harmobeerAndroid.util.GlideV4Engine;
import br.com.opet.tds.harmobeerAndroid.util.TimestampUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;


import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class UsuarioFragment extends Fragment {

    private EditText username, email, senha, senhaUsu;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;
    private AuthCredential credential;
    private boolean usuarioCriado;
    private List<Uri> mSelected;
    private ImageView img;
    private FirebaseStorage fs;
    private StorageReference sr;
    private String uri;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        usuarioCriado = false;
        fs= FirebaseStorage.getInstance();
        sr = fs.getReference();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario, null);
        Button mButton = view.findViewById(R.id.cadastrarUsuario);
        img = view.findViewById(R.id.imageUser);
        Picasso.get()
                .load("https://firebasestorage.googleapis.com/v0/b/harmobeer.appspot.com/o/Link-Select_1-512.png?alt=media&token=ce417839-7732-464e-a9ce-489db83f9779")
                .into(img);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    username = getActivity().findViewById(R.id.username);
                    email = getActivity().findViewById(R.id.email);
                    senha = getActivity().findViewById(R.id.senha);


                    final String sUsername = username.getText().toString();
                    final String sEmail = email.getText().toString();
                    final String sSenha = senha.getText().toString();

                    if (mSelected == null) {
                       uri = null;
                    } else {
                        sr = sr.child(TimestampUtil.getTimestamp() + ".png");
                        sr.putFile(mSelected.get(0)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                uri = sr.getDownloadUrl().toString();
                            }
                        });
                    }

                    final String url = uri;


                    if (!sUsername.isEmpty() && !sEmail.isEmpty() && sSenha.length() >= 6 && sEmail.contains("@") && sEmail.contains(".")) {
                        criarUsuario(sEmail, sUsername, url);
                        mAuth.createUserWithEmailAndPassword(sEmail, sSenha)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                "O novo usuario administrador " + sUsername + " foi adicionado com sucesso! " +
                                                        "Passe os dados para que o novo administrador possa fazer seu primeiro login",
                                                Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();
                                        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                "Houve uma falha no processamento desse novo usuário. Reveja os dados inseridos ou aguarde antes de tentar novamente.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Houve uma falha no processamento desse novo usuário. Reveja os dados inseridos ou aguarde antes de tentar novamente.",
                                Toast.LENGTH_SHORT).show();
                    }


                } catch (Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Não foi possível adicionar esse administrador. Reveja os dados inseridos.",
                            Toast.LENGTH_SHORT).show();
                }


            }

        });
        /*img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(UsuarioFragment.this);
                rxPermissions
                        .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    Matisse.from(UsuarioFragment.this)
                                            .choose(MimeType.ofImage())
                                            .countable(false)
                                            .maxSelectable(1)
                                            .thumbnailScale(0.9f)
                                            .imageEngine(new GlideV4Engine())
                                            .forResult(1234)
                                    ;
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });*/


        return view;
    }


    private void criarUsuario(String sEmail, String sUsername, String url) {

        String dwldUrl;
        if (url == null){
            dwldUrl = "https://firebasestorage.googleapis.com/v0/b/harmobeer.appspot.com/o/Link-Select_1-512.png?alt=media&token=ce417839-7732-464e-a9ce-489db83f9779";
        }else{
            dwldUrl = url;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("email", sEmail);
        data.put("username", sUsername);
        data.put("link", dwldUrl);

        db.collection("usuario")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("Adicionado ao banco de dados!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Houve uma falha na adição desse usuário no banco de dados. Será necessário adicioná-lo manualmente",
                                Toast.LENGTH_SHORT).show();
                        System.out.println("Erro ao adicionar novo usuário no banco de dados");
                    }
                });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected.get(0));
            Picasso.get().load(mSelected.get(0)).into(img);

        }
    }
}
