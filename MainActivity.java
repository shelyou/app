package com.example.databarang;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {
    EditText etNamaBarang, etHarga, etStok;
    Button btnSimpan, btnLihat;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNamaBarang = findViewById(R.id.etNamaBarang);
        etHarga = findViewById(R.id.etHarga);
        etStok = findViewById(R.id.etStok);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnLihat = findViewById(R.id.btnLihat);

        // Membuat atau membuka database
        db = openOrCreateDatabase("DataBarang.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Barang (id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, harga INTEGER, stok INTEGER)");

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNamaBarang.getText().toString();
                int harga = Integer.parseInt(etHarga.getText().toString());
                int stok = Integer.parseInt(etStok.getText().toString());
                db.execSQL("INSERT INTO Barang (nama, harga, stok) VALUES ('" + nama + "', " + harga + ", " + stok + ")");
                Toast.makeText(MainActivity.this, "Data Disimpan!", Toast.LENGTH_SHORT).show();
            }
        });

        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lihat Data Barang (bisa dialihkan ke activity baru)
            }
        });
    }
}
