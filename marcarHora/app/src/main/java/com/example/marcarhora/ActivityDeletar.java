package com.example.marcarhora;

import android.os.Bundle;

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


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class ActivityDeletar extends AppCompatActivity {

    TextView txtcorte, txtquimico, txtbarba, txtsombrancelha, txtlimpesa, txtdia, txthora, txtstatus_registro;
    SQLiteDatabase db;
    ImageView imgprimeiro, imganterior, imgproximo, imgultimo;
    int indice, numreg;
    Cursor c;

    Button btexcluirdados;

    DialogInterface.OnClickListener diExcluiRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletar);

        // Inicializar TextViews
        txtcorte = findViewById(R.id.txtcorte);
        txtbarba = findViewById(R.id.txtbarba);
        txtsombrancelha = findViewById(R.id.txtsombrancelha);
        txtlimpesa = findViewById(R.id.txtlimpesa);
        txtquimico = findViewById(R.id.txtquimico);
        txtdia = findViewById(R.id.txtdia);
        txthora = findViewById(R.id.txthora);
        txtstatus_registro = findViewById(R.id.txtstatus_registro);

        // Inicializar Button
        btexcluirdados = findViewById(R.id.btexcluirdados);

        // Inicializar ImageViews
        imgprimeiro = findViewById(R.id.imgprimeiro);
        imganterior = findViewById(R.id.imganterior);
        imgproximo = findViewById(R.id.imgproximo);
        imgultimo = findViewById(R.id.imgultimo);

        // Limpar TextViews
        txtcorte.setText("");
        txtbarba.setText("");
        txtsombrancelha.setText("");
        txtlimpesa.setText("");
        txtquimico.setText("");
        txtdia.setText("");
        txthora.setText("");

        try {
            db = openOrCreateDatabase("banco_Reserva2", Context.MODE_PRIVATE, null);

            // Inicializar o Cursor com os dados da tabela
            CarregarDados();

            // Configurar os cliques dos ImageViews para navegar pelos registros
            imgprimeiro.setOnClickListener(view -> irParaPrimeiroRegistro());
            imganterior.setOnClickListener(view -> irParaRegistroAnterior());
            imgproximo.setOnClickListener(view -> irParaProximoRegistro());
            imgultimo.setOnClickListener(view -> irParaUltimoRegistro());

            // Configurar o diálogo de confirmação e exclusão de registro
            diExcluiRegistro = (dialog, which) -> {
                if (c != null && c.moveToPosition(indice - 1)) {
                    numreg = c.getInt(0); // Atualizar o numreg baseado na posição atual do cursor
                    db.execSQL("DELETE FROM usuarios WHERE numreg = " + numreg);
                    CarregarDados();
                    MostraMensagem("Dados excluídos com sucesso");
                } else {
                    MostraMensagem("Erro ao excluir registro");
                }
            };

            btexcluirdados.setOnClickListener(view -> {
                if (c != null && c.getCount() > 0) {
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(ActivityDeletar.this);
                    dialogo.setTitle("Confirmação");
                    dialogo.setMessage("Deseja excluir essas informações?");
                    dialogo.setNegativeButton("Não", null);
                    dialogo.setPositiveButton("Sim", diExcluiRegistro);
                    dialogo.show();
                } else {
                    MostraMensagem("Não existem registros para excluir");
                }
            });

        } catch (Exception e) {
            MostraMensagem("Erro: " + e.toString());
        }
    }

    public void CarregarDados() {
        c = db.query("usuarios", new String[]{"numreg", "corte", "barba", "sombrancelha", "limpesa", "quimico", "dia", "hora"}, null, null, null, null, null);

        // Limpar TextViews
        txtcorte.setText("");
        txtbarba.setText("");
        txtsombrancelha.setText("");
        txtlimpesa.setText("");
        txtquimico.setText("");
        txtdia.setText("");
        txthora.setText("");

        if (c.getCount() > 0) {
            c.moveToFirst();
            indice = 1;
            atualizarTextViews();
        } else {
            txtstatus_registro.setText("Nenhum Registro");
        }
    }

    private void atualizarTextViews() {
        if (c != null && !c.isAfterLast()) {
            txtcorte.setText(c.getString(1));
            txtbarba.setText(c.getString(2));
            txtsombrancelha.setText(c.getString(3));
            txtlimpesa.setText(c.getString(4));
            txtquimico.setText(c.getString(5));
            txtdia.setText(c.getString(6));
            txthora.setText(c.getString(7));
            txtstatus_registro.setText(indice + "/" + c.getCount());
        }
    }

    private void irParaPrimeiroRegistro() {
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            indice = 1;
            atualizarTextViews();
        }
    }

    private void irParaRegistroAnterior() {
        if (c != null && c.getCount() > 0 && indice > 1) {
            indice--;
            c.moveToPrevious();
            atualizarTextViews();
        }
    }

    private void irParaProximoRegistro() {
        if (c != null && c.getCount() > 0 && indice < c.getCount()) {
            indice++;
            c.moveToNext();
            atualizarTextViews();
        }
    }

    private void irParaUltimoRegistro() {
        if (c != null && c.getCount() > 0) {
            c.moveToLast();
            indice = c.getCount();
            atualizarTextViews();
        }
    }

    public void MostraMensagem(String str) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(ActivityDeletar.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }
}
