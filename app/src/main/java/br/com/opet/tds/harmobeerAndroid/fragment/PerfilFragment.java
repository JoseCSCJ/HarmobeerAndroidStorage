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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.opet.tds.harmobeerAndroid.R;
import br.com.opet.tds.harmobeerAndroid.activity.MainActivity;
import br.com.opet.tds.harmobeerAndroid.util.GlideV4Engine;
import br.com.opet.tds.harmobeerAndroid.util.TimestampUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class PerfilFragment extends Fragment {


    private EditText usernamePer, senhaAnt, senhaPer, senhaConf;
    private TextView emailPer;
    private ImageView img;
    private ProgressBar pgr;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage fs;
    private StorageReference st;
    private List<Uri> mSelected;
    private boolean trocarImg = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        fs = FirebaseStorage.getInstance();
        st = fs.getReference();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, null);
        usernamePer = view.findViewById(R.id.usernamePer);
        emailPer = view.findViewById(R.id.emailPer);
        emailPer.setText(mUser.getEmail());
        img = view.findViewById(R.id.imageUser);

        final String idUsuario = (String) getActivity().getIntent().getSerializableExtra("idUsuarioLogado");


        db.collection("usuario").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> objeto = document.getData();
                        if (objeto.get("email").toString().compareTo(idUsuario) == 0) {
                            usernamePer.setText(objeto.get("username").toString());
                            String url = document.get("link").toString();
                            Picasso.get().load(url).into(img);

                        }
                    }

                }

            }
        });


        Button mButton = view.findViewById(R.id.editarUsuario);
        Button sButton = view.findViewById(R.id.alterarSenha);
        Button iButton = view.findViewById(R.id.alterarImagem);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    emailPer.setText(mUser.getEmail());

                    if (usernamePer.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Necessário preencher para editar as informações...",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        db.collection("usuario").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Map<String, Object> objeto = document.getData();
                                        if (objeto.get("email").toString().compareTo(idUsuario) == 0) {
                                            db.collection("usuario").document(document.getId())
                                                    .update("username", usernamePer.getText().toString())
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(getActivity().getApplicationContext(),
                                                                    "O seu username foi alterado para " + usernamePer.getText().toString(),
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getActivity().getApplicationContext(),
                                                                    "Não foi possível alterar seu username",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                            ;

                                        }
                                        ;

                                    }
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Não foi possivel recuperar os dados.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }


                } catch (Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Não foi possível efetuar as alterações",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    senhaAnt = getActivity().findViewById(R.id.senhaAnt);
                    senhaPer = getActivity().findViewById(R.id.senhaPer);
                    senhaConf = getActivity().findViewById(R.id.senhaConf);

                    AuthCredential credential = EmailAuthProvider.getCredential(mUser.getEmail(), senhaAnt.getText().toString());

                    mUser.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if (senhaPer.getText().toString().equals(senhaConf.getText().toString()) &&
                                    senhaPer.getText().toString().length() > 4) {
                                mUser.updatePassword(senhaPer.getText().toString())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity().getApplicationContext(),
                                                        "Sua senha foi alterada com sucesso.",
                                                        Toast.LENGTH_SHORT).show();
                                                senhaAnt.setText("");
                                                senhaPer.setText("");
                                                senhaConf.setText("");

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity().getApplicationContext(),
                                                        "Não foi possível alterar sua senha, aguarde antes de tentar novamente",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            } else {
                                System.out.println(senhaPer.getText().toString().equals(senhaConf.getText().toString()));
                                System.out.println(senhaPer.getText().toString().length());
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Não foi possível alterar a sua senha. Reveja os dados inseridos.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Não foi possível alterar a sua senha. A senha atual não foi inserida corretamente",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Não foi possível alterar a sua senha.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        iButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (trocarImg) {
                    db.collection("usuario").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> objeto = document.getData();
                                    final String id = document.getId();
                                    if (objeto.get("email").toString().compareTo(idUsuario) == 0) {
                                        st = st.child(TimestampUtil.getTimestamp() + ".png");
                                        st.putFile(mSelected.get(0)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                st.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        db.collection("usuario").document(id)
                                                                .update("link", uri.toString())
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Toast.makeText(getActivity().getApplicationContext(),
                                                                                "A sua imagem foi alterada",
                                                                                Toast.LENGTH_SHORT).show();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(getActivity().getApplicationContext(),
                                                                                "Não foi possível alterar sua imagem",
                                                                                Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                });
                                            }

                                            ;
                                        });

                                    }
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Necessário escolher nova imagem para alterar a já existente!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(PerfilFragment.this);
                rxPermissions
                        .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    Matisse.from(PerfilFragment.this)
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
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {

            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected.get(0));
            Picasso.get().load(mSelected.get(0)).into(img);
            trocarImg = true;

        }
    }


}
