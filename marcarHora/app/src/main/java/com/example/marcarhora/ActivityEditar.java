package com.example.marcarhora;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityEditar extends AppCompatActivity {

    EditText txtcorte, txtbarba, txtsombrancelha, txtlimpesa, txtquimico, txtdia, txthora;
    TextView txtstatus_registro;
    SQLiteDatabase db;
    ImageView imgprimeiro, imganterior, imgproximo, imgultimo;
    Button btalterardados;
    int indice, numreg;
    Cursor c;
    DialogInterface.OnClickListener diAlteraInformacoes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        // Inicializar os EditTexts
        txtcorte = findViewById(R.id.txtcorte);
        txtbarba = findViewById(R.id.txtbarba);
        txtsombrancelha = findViewById(R.id.txtsombrancelha);
        txtlimpesa = findViewById(R.id.txtlimpesa);
        txtquimico = findViewById(R.id.txtquimico);
        txtdia = findViewById(R.id.txtdia);
        txthora = findViewById(R.id.txthora);

        // Inicializar TextView
        txtstatus_registro = findViewById(R.id.txtstatus_registro);

        // Inicializar ImageViews
        imgprimeiro = findViewById(R.id.imgprimeiro);
        imganterior = findViewById(R.id.imganterior);
        imgproximo = findViewById(R.id.imgproximo);
        imgultimo = findViewById(R.id.imgultimo);

        // Inicializar Button
        btalterardados = findViewById(R.id.btalterardados);

        try {
            db = openOrCreateDatabase("banco_Reserva2", Context.MODE_PRIVATE, null);

            // Inicializar o Cursor
            carregarDados();

            // Configurar os cliques dos ImageViews para navegar pelos registros
            imgprimeiro.setOnClickListener(view -> irParaPrimeiroRegistro());
            imganterior.setOnClickListener(view -> irParaRegistroAnterior());
            imgproximo.setOnClickListener(view -> irParaProximoRegistro());
            imgultimo.setOnClickListener(view -> irParaUltimoRegistro());

            // Configurar o diálogo de confirmação e alteração de registro
            diAlteraInformacoes = (dialog, which) -> {
                String corte = txtcorte.getText().toString();
                String barba = txtbarba.getText().toString();
                String sombrancelha = txtsombrancelha.getText().toString();
                String limpesa = txtlimpesa.getText().toString();
                String quimico = txtquimico.getText().toString();
                String dia = txtdia.getText().toString();
                String hora = txthora.getText().toString();

                try {
                    db.execSQL("UPDATE usuarios SET corte = '" + corte + "'," +
                            " barba ='" + barba + "', sombrancelha = '" + sombrancelha + "'," +
                            " limpesa = '" + limpesa + "', quimico = '" + quimico + "'," +
                            " dia = '" + dia + "', hora = '" + hora + "' WHERE numreg = " + numreg);
                    MostraMensagem("Dados alterados com sucesso.");
                } catch (Exception e) {
                    MostraMensagem("Erro: " + e.toString());
                }
            };

            btalterardados.setOnClickListener(view -> {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(ActivityEditar.this);
                dialogo.setTitle("Confirmação");
                dialogo.setMessage("Deseja alterar as informações?");
                dialogo.setNegativeButton("Não", null);
                dialogo.setPositiveButton("Sim", diAlteraInformacoes);
                dialogo.show();
            });

        } catch (Exception e) {
            MostraMensagem("Erro: " + e.toString());
        }
    }

    private void carregarDados() {
        c = db.query("usuarios", new String[]{"numreg", "corte", "barba", "sombrancelha", "limpesa", "quimico", "dia", "hora"}, null, null, null, null, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            indice = 1;
            atualizarEditTexts();
        } else {
            txtstatus_registro.setText("Nenhum Registro");
        }
    }

    private void atualizarEditTexts() {
        if (c != null && !c.isAfterLast()) {
            numreg = c.getInt(0);
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
            atualizarEditTexts();
        }
    }

    private void irParaRegistroAnterior() {
        if (c != null && c.getCount() > 0 && indice > 1) {
            indice--;
            c.moveToPrevious();
            atualizarEditTexts();
        }
    }

    private void irParaProximoRegistro() {
        if (c != null && c.getCount() > 0 && indice < c.getCount()) {
            indice++;
            c.moveToNext();
            atualizarEditTexts();
        }
    }

    private void irParaUltimoRegistro() {
        if (c != null && c.getCount() > 0) {
            c.moveToLast();
            indice = c.getCount();
            atualizarEditTexts();
        }
    }

    public void MostraMensagem(String str) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(ActivityEditar.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }
}
