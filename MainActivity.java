package com.example.databarang;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editNamaBarang, editJumlahBarang;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNamaBarang = findViewById(R.id.editNamaBarang);
        editJumlahBarang = findViewById(R.id.editJumlahBarang);
        Button buttonSimpan = findViewById(R.id.buttonSimpan);
        Button buttonLihat = findViewById(R.id.buttonLihat);

        // Membuat database lokal
        database = openOrCreateDatabase("DataBarangDB", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS barang (id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, jumlah INTEGER)");

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanBarang();
            }
        });

        buttonLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lihatDataBarang();
            }
        });
    }

    private void simpanBarang() {
        String nama = editNamaBarang.getText().toString();
        String jumlah = editJumlahBarang.getText().toString();

        if (nama.isEmpty() || jumlah.isEmpty()) {
            Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("nama", nama);
        values.put("jumlah", Integer.parseInt(jumlah));

        long result = database.insert("barang", null, values);
        if (result != -1) {
            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
            editNamaBarang.setText("");
            editJumlahBarang.setText("");
        } else {
            Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
        }
    }

    private void lihatDataBarang() {
        Cursor cursor = database.rawQuery("SELECT * FROM barang", null);
        StringBuilder data = new StringBuilder();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nama = cursor.getString(1);
            int jumlah = cursor.getInt(2);
            data.append(id).append(". ").append(nama).append(" - ").append(jumlah).append("\n");
        }

        if (data.length() > 0) {
            Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Belum ada data", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}
