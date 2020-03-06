package com.ronggosukowati.hisan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ronggosukowati.hisan.R;
import com.ronggosukowati.hisan.model.AlumniData;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    private CircleImageView photoDetail;
    private TextView nameDetail, tvNik, tvFullname, tvTmptLahir, tvTglLahir, tvTlp, tvProvinsi, tvKabupaten, tvKecamatan,
            tvKelurahan, tvJln, tvThnMsk, tvThnKeluar, tvKeterangan;

    private AlumniData alumniData;

    private static final String BASE_IMAGE_URL = "http://hisan.id/storage/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setTitle("Biodata");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        photoDetail = findViewById(R.id.img_photo_detail);
        nameDetail = findViewById(R.id.name_detail);
        tvNik = findViewById(R.id.tv_nik_detail);
        tvFullname = findViewById(R.id.tv_fullname_detail);
        tvTmptLahir = findViewById(R.id.tv_tmpt_lahir);
        tvTglLahir = findViewById(R.id.tv_tgl_lahir);
        tvTlp = findViewById(R.id.tv_tlp);
        tvProvinsi = findViewById(R.id.tv_provinsi);
        tvKabupaten = findViewById(R.id.tv_kabupaten);
        tvKecamatan = findViewById(R.id.tv_kecamatan);
        tvKelurahan = findViewById(R.id.tv_kelurahan);
        tvJln = findViewById(R.id.tv_jalan);
        tvThnMsk = findViewById(R.id.tv_thn_msk);
        tvThnKeluar = findViewById(R.id.tv_thn_keluar);
        tvKeterangan = findViewById(R.id.tv_keterangan);

        Intent in = getIntent();
        alumniData = in.getParcelableExtra("detail");

        if (alumniData != null){
            Glide.with(this).load(BASE_IMAGE_URL+alumniData.getFoto()).into(photoDetail);
            nameDetail.setText(alumniData.getName());
            tvNik.setText(String.valueOf(alumniData.getNik()));
            tvFullname.setText(alumniData.getName());
            tvTmptLahir.setText(alumniData.getAlumniCity().getName());
            tvTglLahir.setText(alumniData.getTglLahir());
            tvTlp.setText(alumniData.getPhone());
            tvProvinsi.setText(alumniData.getAlumniProvince().getName());
            tvKabupaten.setText(alumniData.getAlumniCity().getName());
            tvKecamatan.setText(alumniData.getAlumniDistrict().getName());
            tvKelurahan.setText(alumniData.getAlumniVillage().getName());
            tvJln.setText(alumniData.getAlamat());
            tvThnMsk.setText(String.valueOf(alumniData.getTahunMasuk()));
            tvThnKeluar.setText(String.valueOf(alumniData.getTahunKeluar()));
            tvKeterangan.setText(alumniData.getKeterangan());
        }

    }
}
