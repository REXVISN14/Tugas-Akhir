package com.nikolasgilang_202102362.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DaftarActivity extends AppCompatActivity {
    EditText hp, nama, jeniskelamin, alamat, email;
    Button simpan, tampil, edit, hapus;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        hp = findViewById(R.id.edthp);
        nama = findViewById(R.id.edtnama);
        jeniskelamin = findViewById(R.id.edtjk);
        alamat = findViewById(R.id.edtalamat);
        email = findViewById(R.id.edtemail);
        simpan = findViewById(R.id.btnsimpan);
        tampil = findViewById(R.id.btntampil);
        edit = findViewById(R.id.btnedit);
        hapus = findViewById(R.id.btnhapus);

        DB = new DBHelper(this);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = hp.getText().toString();
                String nm = nama.getText().toString();
                String jk = jeniskelamin.getText().toString();
                String al = alamat.getText().toString();
                String em = email.getText().toString();

                if (TextUtils.isEmpty(n) || TextUtils.isEmpty(nm) || TextUtils.isEmpty(jk) || TextUtils.isEmpty(al) || TextUtils.isEmpty(em))
                    Toast.makeText(DaftarActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                else {
                    Boolean ceknimMHS= DB.ceknim(n);
                    if (ceknimMHS == false){
                        Boolean insert = DB.insertDataMHS(n,nm,jk,al,em);
                        if (insert == true){
                            Toast.makeText(DaftarActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(DaftarActivity.this,"Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(DaftarActivity.this,"Data Member Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.tampilDataMHS();
                if (res.getCount()==0){
                    Toast.makeText(DaftarActivity.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer =  new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Nomor Telepon : "+res.getString(0)+"\n");
                    buffer.append("Nama : "+res.getString(1)+"\n");
                    buffer.append("Jenis Kelamin : "+res.getString(2)+"\n");
                    buffer.append("Alamat : "+res.getString(3)+"\n");
                    buffer.append("Email : "+res.getString(4)+"\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(DaftarActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Data Member");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kb = hp.getText().toString();
                Boolean cekHapusData = DB.hapusDataMHS(kb);
                if (cekHapusData == true)
                    Toast.makeText(DaftarActivity.this, "Data Berhasil Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(DaftarActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n_ = hp.getText().toString();
                String nm_ = nama.getText().toString();
                String jk_ = jeniskelamin.getText().toString();
                String al_ = alamat.getText().toString();
                String em_ = email.getText().toString();

                if (TextUtils.isEmpty(n_) || TextUtils.isEmpty(nm_) || TextUtils.isEmpty(jk_) || TextUtils.isEmpty(al_) || TextUtils.isEmpty(em_))
                    Toast.makeText(DaftarActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                else {
                    Boolean edit = DB.editDataMHS(n_,nm_,jk_,al_,em_);
                    if (edit == false){
                        Toast.makeText(DaftarActivity.this, "Data berhasil diedit", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(DaftarActivity.this,"Data gagal diedit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}