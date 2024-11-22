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

public class ExcluirDadosActivity extends AppCompatActivity {

    TextView txtnome, txttelefone, txtemail, txtstatus_registro;

    SQLiteDatabase db;

    ImageView imgprimeiro, imganterior, imgproximo, imgultimo;

    Button btexcluirdados;

    int indice, numreg;

    Cursor c;

    DialogInterface.OnClickListener diExcluiRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excluir_dados);


        txtnome = (TextView) findViewById(R.id.txtnome);
        txtemail = (TextView) findViewById(R.id.txtemail);
        txttelefone = (TextView) findViewById(R.id.txttelefone);
        txtstatus_registro = (TextView) findViewById(R.id.txtstatus_registro);

        txtnome.setText("");
        txttelefone.setText("");
        txtemail.setText("");


        imgprimeiro = (ImageView) findViewById(R.id.imgprimeiro);
        imganterior = (ImageView) findViewById(R.id.imganterior);
        imgproximo = (ImageView) findViewById(R.id.imgproximo);
        imgultimo = (ImageView) findViewById(R.id.imgultimo);

        btexcluirdados = (Button) findViewById(R.id.btexcluirdados);

        try {
            db = openOrCreateDatabase("banco_dados", Context.MODE_PRIVATE, null);
            CarregarDados();

            imgprimeiro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c.getCount() > 0) {
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
                    if (c.getCount() > 0) {
                        if (indice > 1) {
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
                    if (c.getCount() > 0) {
                        if (indice != c.getCount()) {

                            indice++;
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
                    if (c.getCount() > 0) {
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
            diExcluiRegistro = new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){

                    db.execSQL("delete from usuarios where numreg = " + numreg);
                    CarregarDados();
                    MostraMensagem("Dados excluidos com sucesso");
                }
            };
            btexcluirdados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c.getCount() > 0) {
                        AlertDialog.Builder dialogo = new AlertDialog.Builder(ExcluirDadosActivity.this);
                        dialogo.setTitle("confirma");
                        dialogo.setMessage("Deseja excluir esses informações");
                        dialogo.setNegativeButton("não", null);
                        dialogo.setPositiveButton("sim", (DialogInterface.OnClickListener) diExcluiRegistro);
                        dialogo.show();
                    } else {
                        MostraMensagem("não existem regitros para excluir");
                    }
                }
            });
        } catch (Exception e) {
            MostraMensagem("Erro :" + e.toString());
        }
    }
        public void CarregarDados(){
        c = db.query("usuarios", new String[]{"numreg","nome","telefone","email"}, null,null,null,null, null);

            txtnome.setText("");
            txttelefone.setText("");
            txtemail.setText("");
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
         public void MostraMensagem(String str){
               AlertDialog.Builder dialogo = new AlertDialog.Builder(ExcluirDadosActivity.this);
               dialogo.setTitle("Aviso");
               dialogo.setMessage(str);
               dialogo.setNeutralButton("ok",null);
               dialogo.show();
    }
}