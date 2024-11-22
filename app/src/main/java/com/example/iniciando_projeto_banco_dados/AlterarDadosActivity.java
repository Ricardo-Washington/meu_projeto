package com.example.iniciando_projeto_banco_dados;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AlterarDadosActivity extends AppCompatActivity {


    EditText txtnome, txttelefone, txtemail;

    TextView txtstatus_registro;

    SQLiteDatabase db;

    ImageView imgprimeiro, imganterior, imgproximo, imgultimo;

    Button btalterardados;

    int indice, numreg;

    Cursor c;

    DialogInterface.OnCancelListener diAlteraInformacoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alterar_dados);

        txtnome = (EditText) findViewById(R.id.txtnome);
        txtemail = (EditText) findViewById(R.id.txtemail);
        txttelefone = (EditText) findViewById(R.id.txttelefone);

        txtstatus_registro = (TextView) findViewById(R.id.txtstatus_registro);

        imgprimeiro = (ImageView) findViewById(R.id.imgprimeiro);
        imganterior = (ImageView) findViewById(R.id.imganterior);
        imgproximo = (ImageView) findViewById(R.id.imgproximo);
        imgultimo = (ImageView) findViewById(R.id.imgultimo);

        btalterardados = (Button) findViewById(R.id.btalterardados);

        try {
            db = openOrCreateDatabase("banco_dados", Context.MODE_PRIVATE,null);

            c = db.query("usuarios", new String[]{"numreg" , "nome", "telefone", "email"}, null,null,null,null,null);

            if (c.getCount() > 0){

                c.moveToFirst();
                indice = 1;
                numreg = c.getInt(0);
                txtnome.setText(c.getString(1));
                txttelefone.setText(c.getString(2));
                txtemail.setText(c.getString(3));

                txtstatus_registro.setText(indice + "/" + c.getCount());
            }
            else {
                txtstatus_registro.setText("Nenhum Registro");
            }
        }
        catch (Exception e){

        }
        imgprimeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c.getCount() > 0){
                    c.moveToFirst();
                    indice = 1;
                    numreg = c.getInt(0);
                    txtnome.setText(c.getString(1));
                    txttelefone.setText(c.getString(2));
                    txtemail.setText(c.getString(3));
                    txtstatus_registro.setText(indice + "/" + c.getCount());
                }
            }
        });
        imganterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c.getCount() > 0){
                    if(indice > 1){
                        indice--;
                        c.moveToPrevious();
                        numreg = c.getInt(0);
                        txtnome.setText(c.getString(1));
                        txttelefone.setText(c.getString(2));
                        txtemail.setText(c.getString(3));
                        txtstatus_registro.setText(indice + "/" + c.getCount());
                    }
                }
            }
        });
        imgproximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c.getCount() > 0){
                    if (indice != c.getCount()){

                        indice ++;
                        c.moveToNext();
                        numreg = c.getInt(0);
                        txtnome.setText(c.getString(1));
                        txttelefone.setText(c.getString(2));
                        txtemail.setText(c.getString(3));
                        txtstatus_registro.setText(indice + "/" + c.getCount());
                    }
                }
            }
        });
        imgultimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c.getCount() > 0){
                    c.moveToLast();
                    indice = c.getCount();
                        numreg = c.getInt(0);
                        txtnome.setText(c.getString(1));
                        txttelefone.setText(c.getString(2));
                        txtemail.setText(c.getString(3));
                        txtstatus_registro.setText(indice + "/" + c.getCount());
                }
            }
        });
        diAlteraInformacoes = new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                String nome = txtnome.getText().toString();
                String telefone = txttelefone.getText().toString();
                String email = txtemail.getText().toString();
                try {
                    db.execSQL("update usuarios set nome = '" + nome +"',"+"telefone ='"+telefone+"',email = '" + email + "'where numreg = " + numreg);
                    MostraMensagem("Dados alterados com sucesso.");
                }
                catch (Exception e){
                MostraMensagem("Erro:" + e.toString());
                }
            }
        };
        btalterardados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(AlterarDadosActivity.this);
                dialogo.setTitle("confirma");
                dialogo.setMessage("Deseja alterar as informações");
                dialogo.setNegativeButton("não", null);
                dialogo.setPositiveButton("sim", (DialogInterface.OnClickListener) diAlteraInformacoes);
                dialogo.show();
            }
        });
    }
    public void MostraMensagem(String str){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(AlterarDadosActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("ok",null);
        dialogo.show();
    }
}