package com.mobileexercicio.cadastrobebidas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nome, codigo, tipo, preco;
    TextView id;
    ListView listaBebidas;
    ArrayList<Bebida> lista = new ArrayList<Bebida>();
    List<Bebida> listaB = new ArrayList<Bebida>();
    BebidaDB bebidaDB = new BebidaDB(this);
    Button btnCadastro,btnSalvar,btnAtualizar,btnDeletar,btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = openOrCreateDatabase("bebidas",
                Context.MODE_PRIVATE,
                null);

        btnCadastro = findViewById(R.id.btnCreate);
        btnSalvar = findViewById(R.id.btnSave);
        btnAtualizar = findViewById(R.id.btnUpdate);
        btnDeletar = findViewById(R.id.btnDelete);
        btnVoltar = findViewById(R.id.btnBack);
        nome = findViewById(R.id.etName);
        preco = findViewById(R.id.etPreco);
        codigo = findViewById(R.id.etCodigo);
        tipo = findViewById(R.id.etTipo);
        id = findViewById(R.id.tvId);
        listaBebidas = findViewById(R.id.listBebidas);

        btnSalvar.setVisibility(View.INVISIBLE);
        btnVoltar.setVisibility(View.INVISIBLE);
        btnAtualizar.setClickable(false);
        btnDeletar.setClickable(false);


        visualizarlista(listaBebidas, nome,preco,id,codigo,tipo);


        btnCadastro.setOnClickListener(view ->{
            cadastrarBebidas(nome,preco,codigo,tipo,id);
        });

    }

    private void cadastrarBebidas(EditText nome, EditText preco, EditText codigo, EditText tipo, TextView id) {

        btnSalvar = findViewById(R.id.btnSave);
        btnAtualizar.setVisibility(View.INVISIBLE);
        btnDeletar.setVisibility(View.INVISIBLE);
        btnSalvar.setVisibility(View.VISIBLE);
        btnVoltar.setVisibility(View.VISIBLE);
        btnCadastro.setVisibility(View.INVISIBLE);
        nome.setVisibility(View.VISIBLE);
        preco.setVisibility(View.VISIBLE);
        codigo.setVisibility(View.VISIBLE);
        tipo.setVisibility(View.VISIBLE);
        id.setVisibility(View.VISIBLE);

        btnSalvar.setOnClickListener(v -> {
            Bebida bebida = new Bebida();
            bebida.setNome(nome.getText().toString());
            bebida.setPreco(Double.parseDouble(preco.getText().toString()));
            bebida.setCodigo(codigo.getText().toString());
            bebida.setTipo(tipo.getText().toString());
            bebidaDB.save(bebida);
            Toast.makeText(getApplicationContext(), "Bebida cadastrada", Toast.LENGTH_SHORT).show();
            Voltar();
            visualizarlista(listaBebidas, nome, preco, id, codigo, tipo);
        });
        btnVoltar.setOnClickListener(v ->{
            Voltar();
        });
    }

    private void visualizarlista(ListView listaBebidas, EditText nome, EditText preco, TextView id, EditText codigo, EditText tipo) {
        //Lista todos os cadastros
        listaB.clear();
        listaB = bebidaDB.findAll();

        ArrayAdapter<Bebida>adapter = new ArrayAdapter<Bebida>(this, android.R.layout.simple_list_item_1,listaB);
        this.listaBebidas.setAdapter(adapter);

        //Define ações ao clicar em algum item da lista
        listaBebidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long l) {
                Bebida bebidaSelecionada = (Bebida) av.getItemAtPosition(position);

                //Definir Visibilidade
                btnCadastro.setVisibility(View.INVISIBLE);
                nome.setVisibility(View.VISIBLE);
                preco.setVisibility(View.VISIBLE);
                codigo.setVisibility(View.VISIBLE);
                tipo.setVisibility(View.VISIBLE);
                id.setVisibility(View.VISIBLE);

                //Definir Clickable
                btnAtualizar.setClickable(true);
                btnDeletar.setClickable(true);
                nome.setClickable(false);
                preco.setClickable(false);
                codigo.setClickable(false);
                tipo.setClickable(false);

                //Preencher Campos
                id.setText(String.valueOf(bebidaSelecionada.getId()));
                nome.setText(bebidaSelecionada.getNome());
                preco.setText(String.valueOf(bebidaSelecionada.getPreco()));
                codigo.setText(bebidaSelecionada.getCodigo());
                tipo.setText(bebidaSelecionada.getTipo());

                //Ações dos botões deletar e Atualizar
                btnAtualizar.setOnClickListener(v ->{
                    atualizarCadastro(bebidaSelecionada, nome,preco,codigo,tipo);
                });

                btnDeletar.setOnClickListener(v->{
                    deletarBebida(bebidaSelecionada);
                });

            }
        });
    }

    private void atualizarCadastro(Bebida bebida, EditText nome, EditText preco, EditText codigo, EditText tipo) {

        BebidaDB bebidaDB1 = new BebidaDB(this);
        btnSalvar = findViewById(R.id.btnSave);
        btnAtualizar.setVisibility(View.INVISIBLE);
        btnDeletar.setVisibility(View.INVISIBLE);
        btnSalvar.setVisibility(View.VISIBLE);
        btnVoltar.setVisibility(View.VISIBLE);
        btnCadastro.setVisibility(View.INVISIBLE);

        btnSalvar.setOnClickListener(v -> {
            bebida.setNome(nome.getText().toString());
            bebida.setPreco(Double.parseDouble(preco.getText().toString()));
            bebida.setCodigo(codigo.getText().toString());
            bebida.setTipo(tipo.getText().toString());
            bebida.setId(bebida.getId());
            bebidaDB1.update(bebida);
            Toast.makeText(getApplicationContext(), "Bebida atualizada", Toast.LENGTH_SHORT).show();
            Voltar();
            visualizarlista(listaBebidas, nome, preco, id, codigo, tipo);
        });

        btnVoltar.setOnClickListener(v ->{
            Voltar();
        });
    }

    private void deletarBebida(Bebida bebida) {

        BebidaDB bebidaDB = new BebidaDB(this);
        bebidaDB.delete(bebida.getId());
        Voltar();
    }

    private void Voltar() {
        btnSalvar.setVisibility(View.INVISIBLE);
        btnVoltar.setVisibility(View.INVISIBLE);
        nome.setVisibility(View.GONE);
        preco.setVisibility(View.GONE);
        tipo.setVisibility(View.GONE);
        id.setVisibility(View.GONE);
        codigo.setVisibility(View.GONE);
        btnDeletar.setVisibility(View.VISIBLE);
        btnCadastro.setVisibility(View.VISIBLE);
        btnAtualizar.setVisibility(View.VISIBLE);
        btnAtualizar.setClickable(false);
        btnDeletar.setClickable(false);

        visualizarlista(listaBebidas, nome,preco,id,codigo,tipo);

    }

}

