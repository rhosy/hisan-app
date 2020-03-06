package com.ronggosukowati.hisan.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.material.textfield.TextInputLayout;
import com.ronggosukowati.hisan.R;
import com.ronggosukowati.hisan.model.AlumniData;
import com.ronggosukowati.hisan.model.Cities;
import com.ronggosukowati.hisan.model.CitiesData;
import com.ronggosukowati.hisan.model.Districts;
import com.ronggosukowati.hisan.model.DistrictsData;
import com.ronggosukowati.hisan.model.GeneralResponse;
import com.ronggosukowati.hisan.model.Provincies;
import com.ronggosukowati.hisan.model.ProvinciesData;
import com.ronggosukowati.hisan.model.UserData;
import com.ronggosukowati.hisan.model.Villages;
import com.ronggosukowati.hisan.model.VillagesData;
import com.ronggosukowati.hisan.services.ApiBuilder;
import com.ronggosukowati.hisan.services.BaseApiService;
import com.ronggosukowati.hisan.session.SharePrefmanager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormEditActivity extends AppCompatActivity {

    private TextInputLayout txtInputNik, txtInputNama, txtInputTglLahir, txtInputTelp, txtInputDusun, txtInputThnMasuk, txtInputThnKeluar, txtInputKeterangan;
    private SmartMaterialSpinner spinnerTempatLahir, spinnerProvinsi, spinnerKabupaten, spinnerKecamatan, spinnerKelurahan;
    private Button btnSubmit;
    private ImageView imgPhoto;
    private int idTmptLahir, idProvinsi, idKabupaten, idKecamatan;
    private Long idKelurahan;
    private int choosenYear = 2020;
    private String encodedImage;
    private Bitmap compressImageFile;
    private Uri imageUri = null;
    private Calendar myCalendar;

    private ProgressDialog progressDialog;
    private List<CitiesData> citiesData = new ArrayList<>();
    private List<ProvinciesData> provinciesData = new ArrayList<>();
    private List<CitiesData> citiesDataList = new ArrayList<>();
    private List<DistrictsData> districtsData = new ArrayList<>();
    private List<VillagesData> villagesData = new ArrayList<>();
    private AlumniData alumniData;
    private UserData userData;
    private int idData;
    private static final String BASE_IMAGE_URL = "http://hisan.id/storage/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit);

        getSupportActionBar().setTitle("Edit Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userData = SharePrefmanager.getInstance(this).getUser();
        myCalendar = Calendar.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait ...");

        Intent in = getIntent();
        alumniData = in.getParcelableExtra("datas");


        txtInputNik = findViewById(R.id.text_input_nik);
        txtInputNama = findViewById(R.id.text_input_name);
        txtInputTglLahir = findViewById(R.id.text_input_tgl_lahir);
        txtInputTelp = findViewById(R.id.text_input_telp);
        txtInputDusun = findViewById(R.id.text_input_jalan);
        txtInputThnMasuk = findViewById(R.id.text_input_masuk);
        txtInputThnKeluar = findViewById(R.id.text_input_keluar);
        txtInputKeterangan = findViewById(R.id.text_input_keterangan);
        spinnerTempatLahir = findViewById(R.id.spinner_tempat_lahir);
        spinnerProvinsi = findViewById(R.id.spinner_tempat_provinsi);
        spinnerKabupaten = findViewById(R.id.spinner_tempat_kabupaten);
        spinnerKecamatan = findViewById(R.id.spinner_tempat_kecamatan);
        spinnerKelurahan = findViewById(R.id.spinner_tempat_kelurahan);
        imgPhoto = findViewById(R.id.img_photo);
        btnSubmit = findViewById(R.id.btn_submit);

        if (alumniData != null){
            idData = alumniData.getId();
            txtInputNik.getEditText().setText(String.valueOf(alumniData.getNik()));
            txtInputNama.getEditText().setText(alumniData.getName());
            spinnerTempatLahir.setHint(alumniData.getAlumniCity().getName());
            txtInputTglLahir.getEditText().setText(alumniData.getTglLahir());
            txtInputTelp.getEditText().setText(alumniData.getPhone());
            spinnerProvinsi.setHint(alumniData.getAlumniProvince().getName());
            spinnerKabupaten.setHint(alumniData.getAlumniCity().getName());
            spinnerKecamatan.setHint(alumniData.getAlumniDistrict().getName());
            spinnerKelurahan.setHint(alumniData.getAlumniVillage().getName());
            txtInputDusun.getEditText().setText(alumniData.getAlamat());
            txtInputThnMasuk.getEditText().setText(String.valueOf(alumniData.getTahunMasuk()));
            txtInputThnKeluar.getEditText().setText(String.valueOf(alumniData.getTahunKeluar()));
            txtInputKeterangan.getEditText().setText(alumniData.getKeterangan());
            Glide.with(this).load(BASE_IMAGE_URL+alumniData.getFoto()).into(imgPhoto);

            idTmptLahir = alumniData.getAlumniCity().getId();
            idProvinsi = alumniData.getAlumniProvince().getId();
            idKabupaten = alumniData.getAlumniCity().getId();
            idKecamatan = alumniData.getAlumniDistrict().getId();
            idKelurahan = alumniData.getAlumniVillage().getId();

        }

        //TempatLahir
        getCities();

        //Provincies
        getProvincies();

        getCity(idProvinsi);
        getDistrict(idKabupaten);
        getVillage(idKecamatan);

        txtInputNik.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() < 16){
                    txtInputNik.setError("NIK tidak valid");
                    txtInputNik.setErrorEnabled(true);
                }else {
                    txtInputNik.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtInputTglLahir.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FormEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTanggal = "dd-MM-yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        txtInputTglLahir.getEditText().setText(sdf.format(myCalendar.getTime()));

                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txtInputThnMasuk.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(FormEditActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                txtInputThnMasuk.getEditText().setText(String.valueOf(selectedYear));
                                choosenYear = selectedYear;

                            }

                        }, choosenYear, 0);
                builder.showYearOnly()
                        .setYearRange(1945, Calendar.getInstance().get(Calendar.YEAR))
                        .build()
                        .show();
            }
        });

        txtInputThnKeluar.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(FormEditActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                txtInputThnKeluar.getEditText().setText(String.valueOf(selectedYear));

                            }
                        }, choosenYear, 0);
                builder.showYearOnly()
                        .setYearRange(choosenYear, Calendar.getInstance().get(Calendar.YEAR))
                        .build()
                        .show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = alumniData.getId();
                String nik = txtInputNik.getEditText().getText().toString().trim();
                String name = txtInputNama.getEditText().getText().toString().trim();
                String tglLahir = txtInputTglLahir.getEditText().getText().toString().trim();
                String tlp = txtInputTelp.getEditText().getText().toString().trim();
                String jalan = txtInputDusun.getEditText().getText().toString().trim();
                int thnMasuk = Integer.parseInt(txtInputThnMasuk.getEditText().getText().toString().trim());
                int thnKeluar = Integer.parseInt(txtInputThnKeluar.getEditText().getText().toString().trim());
                String keterangan = txtInputKeterangan.getEditText().getText().toString().trim();

                updateData(id, nik, name, idTmptLahir, tglLahir, tlp, idProvinsi, idKabupaten, idKecamatan, idKelurahan, jalan, thnMasuk, thnKeluar, keterangan);


            }
        });

        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePicker();
            }
        });

    }

    private void getProvincies() {
        BaseApiService apiService = ApiBuilder.call();
        apiService.getProvincies().enqueue(new Callback<Provincies>() {
            @Override
            public void onResponse(Call<Provincies> call, Response<Provincies> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        progressDialog.dismiss();
                        provinciesData = response.body().getProvinciesData();
                        List<String> provinceItem = new ArrayList<>();
                        for (int i = 0; i < provinciesData.size(); i++) {
                            provinceItem.add(provinciesData.get(i).getName());
                        }

                        spinnerProvinsi.setItem(provinceItem);
                        spinnerProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                idProvinsi = Integer.parseInt(provinciesData.get(i).getId());
                                spinnerKabupaten.setHint("Pilih Kabupaten");
                                spinnerKecamatan.setHint("Pilih Kecamatan");
                                spinnerKelurahan.setHint("Pilih Kelurahan");
                                getCity(idProvinsi);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                idProvinsi = alumniData.getAlumniProvince().getId();

                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<Provincies> call, Throwable t) {

            }
        });
    }

    private void getCity(final int idProvinsi) {
        progressDialog.show();
        BaseApiService apiService = ApiBuilder.call();
        apiService.getCity(idProvinsi).enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        progressDialog.dismiss();
                        citiesDataList = response.body().getCitiesData();
                        List<String> cityItem = new ArrayList<>();
                        for (int i = 0; i < citiesDataList.size(); i++) {
                            cityItem.add(citiesDataList.get(i).getName());
                        }

                        spinnerKabupaten.setItem(cityItem);
                        spinnerKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                idKabupaten = Integer.parseInt(citiesDataList.get(i).getId());
                                spinnerKecamatan.setHint("Pilih Kecamatan");
                                spinnerKelurahan.setHint("Pilih Kelurahan");
                                getDistrict(idKabupaten);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                idKabupaten = alumniData.getAlumniCity().getId();

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    private void getDistrict(int idKabupaten) {
        progressDialog.show();
        BaseApiService apiService = ApiBuilder.call();
        apiService.getDistrict(idKabupaten).enqueue(new Callback<Districts>() {
            @Override
            public void onResponse(Call<Districts> call, Response<Districts> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        progressDialog.dismiss();
                        districtsData = response.body().getDistrictsData();
                        List<String> districtItem = new ArrayList<>();
                        for (int i = 0; i < districtsData.size(); i++) {
                            districtItem.add(districtsData.get(i).getName());
                        }

                        spinnerKecamatan.setItem(districtItem);
                        spinnerKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                idKecamatan = Integer.parseInt(districtsData.get(i).getId());
                                spinnerKelurahan.setHint("Pilih Kelurahan");
                                getVillage(idKecamatan);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                idKecamatan = alumniData.getAlumniDistrict().getId();

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<Districts> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    private void getVillage(int idKecamatan) {
        progressDialog.show();
        BaseApiService apiService = ApiBuilder.call();
        apiService.getVillage(idKecamatan).enqueue(new Callback<Villages>() {
            @Override
            public void onResponse(Call<Villages> call, Response<Villages> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        progressDialog.dismiss();
                        villagesData = response.body().getVillagesData();
                        List<String> villageItem = new ArrayList<>();
                        for (int i = 0; i < villagesData.size(); i++) {
                            villageItem.add(villagesData.get(i).getName());
                        }
                        spinnerKelurahan.setItem(villageItem);
                        spinnerKelurahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                idKelurahan = Long.valueOf(villagesData.get(i).getId());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                idKelurahan = alumniData.getAlumniVillage().getId();

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<Villages> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    private void getCities() {
        progressDialog.show();
        BaseApiService apiService = ApiBuilder.call();
        apiService.getCities().enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        //progressDialog.dismiss();
                        citiesData = response.body().getCitiesData();
                        final List<String> citiesItem = new ArrayList<>();
                        for (int i = 0; i < citiesData.size(); i++) {
                            citiesItem.add(citiesData.get(i).getName());

                        }

                        spinnerTempatLahir.setItem(citiesItem);
                        spinnerTempatLahir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                idTmptLahir = Integer.parseInt(citiesData.get(i).getId());

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                idTmptLahir = alumniData.getAlumniCity().getId();

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    private void updateData(int id, String nik, String name, int idTmptLahir, String tglLahir, String tlp, int idProvinsi, int idKabupaten, int idKecamatan, Long idKelurahan, String jalan, int thnMasuk, int thnKeluar, String keterangan) {
        progressDialog.show();
        BaseApiService apiService = ApiBuilder.call();
        apiService.editData(id, nik, name, idTmptLahir, tglLahir, tlp, idProvinsi, idKabupaten, idKecamatan, idKelurahan, jalan, thnMasuk, thnKeluar, keterangan).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body() != null){
                    if (response.body().getStatus()){
                        progressDialog.dismiss();
                        Toasty.success(FormEditActivity.this, "Data telah diubah", Toasty.LENGTH_LONG).show();
                        Intent intent = new Intent(FormEditActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        progressDialog.dismiss();
                        Toasty.error(FormEditActivity.this, "Data gagal diubah", Toasty.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("onFailure", "onFailuer:: "+ t.getMessage() );
                Toasty.error(FormEditActivity.this, "Error connect to server", Toasty.LENGTH_LONG).show();

            }
        });
    }

    private void showImagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(FormEditActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                imgPhoto.setImageURI(imageUri);

                File new_image_file = new File(imageUri.getPath());
                try {
                    compressImageFile = new Compressor(FormEditActivity.this)
                            .setMaxHeight(200)
                            .setMaxWidth(200)
                            .setQuality(10)
                            .compressToBitmap(new_image_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                encodedImage = "data:image/png;base64," + encodeImage(compressImageFile);
                savePhoto();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void savePhoto() {
        progressDialog.show();
        BaseApiService apiService = ApiBuilder.call();
        apiService.updatePhoto(idData, encodedImage).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body() != null){
                    if (response.body().getStatus()){
                        progressDialog.dismiss();
                        Toasty.success(FormEditActivity.this, "Foto telah diubah", Toasty.LENGTH_LONG).show();
                    }else {
                        progressDialog.dismiss();
                        Toasty.error(FormEditActivity.this, "Foto gagal diubah", Toasty.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("onFailure", "onFailure::"+ t.getMessage());

            }
        });
    }

    private String encodeImage(Bitmap selectedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
}
