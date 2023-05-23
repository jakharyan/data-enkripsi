package com.example.berbagidataenkripsi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Base64

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Membuat tempat simpan
        val tempatData = getPreferences(Context.MODE_PRIVATE)
        // Membuat fungsi simpan
        findViewById<Button>(R.id.btn_simpan).setOnClickListener{
            // Menyimpan nilai dari XML ke kotlin
            var nama = findViewById<EditText>(R.id.et_nama).text.toString()
            // Menguji bila nilai nama kosong
            if (nama.isEmpty()){
                // Membuat pesan dan menampilkan
                Toast.makeText(
                    this,
                    "Masukan Nama Anda",
                    Toast.LENGTH_SHORT
                ).show()
                // Kembali ke fungsi simpan
                return@setOnClickListener
            }
            // Membuat tempat simpanan untuk
            // Nilai variable yang dienkripsi
            val simpanNama = enkripsiData(nama)
            // Merubah nilai yang disimpan
            val simpanData = tempatData.edit()
            // Memindahkan nilai ke vairable preference
            simpanData.putString("nama", simpanNama)
            // Simpan nilai preference
            simpanData.apply()
            Toast.makeText(
                this,
                "Nama Anda Tersimpan",
                Toast.LENGTH_SHORT
            ).show()
            // Membuat fungsi baru
            kosongkanText()
        }

        findViewById<Button>(R.id.btn_panggil).setOnClickListener {
            var panggilNama = tempatData.getString("nama",null)
            val panggilHasil = deskripsiData(panggilNama.toString())
            findViewById<TextView>(R.id.tv_hasil).text = "$panggilHasil \n $panggilNama "
        }
    }
    private fun enkripsiData(namaEnkripsi: String): String {
        val enkripsi = android.util.Base64.encode(
            namaEnkripsi.toByteArray(),
            android.util.Base64.DEFAULT
        )
        return String(enkripsi)
    }
    private fun deskripsiData(namaEnkripsi: String): String {
        val enkripsi = android.util.Base64.decode(
            namaEnkripsi.toByteArray(),
            android.util.Base64.DEFAULT
        )
        return String(enkripsi)
    }
    private fun kosongkanText() {
        findViewById<EditText>(R.id.et_nama).setText("")
        findViewById<TextView>(R.id.tv_hasil).setText("")
    }
}

