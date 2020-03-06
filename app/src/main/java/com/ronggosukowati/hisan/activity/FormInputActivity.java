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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.material.textfield.TextInputLayout;
import com.ronggosukowati.hisan.R;
import com.ronggosukowati.hisan.model.CitiesData;
import com.ronggosukowati.hisan.model.Cities;
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
import java.util.List;

import es.dmoral.toasty.Toasty;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormInputActivity extends AppCompatActivity {

    private TextInputLayout txtInputNik, txtInputNama, txtInputTglLahir, txtInputTelp, txtInputDusun, txtInputThnMasuk, txtInputThnKeluar, txtInputKeterangan;
    private SmartMaterialSpinner spinnerTempatLahir, spinnerProvinsi, spinnerKabupaten, spinnerKecamatan, spinnerKelurahan;
    private Button btnSubmit;
    private ImageView imgPhoto;
    private String idTmptLahir, idProvinsi, idKabupaten, idKecamatan, idKelurahan, token;
    private int choosenYear = 1998;
    private String encodedImage;
    private Bitmap compressImageFile;
    private Uri imageUri = null;
    private Calendar myCalendar;
    private UserData userData;

    private ProgressDialog progressDialog;
    private List<CitiesData> citiesData = new ArrayList<>();
    private List<ProvinciesData> provinciesData = new ArrayList<>();
    private List<CitiesData> citiesDataList = new ArrayList<>();
    private List<DistrictsData> districtsData = new ArrayList<>();
    private List<VillagesData> villagesData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_input);

        getSupportActionBar().setTitle("Input Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myCalendar = Calendar.getInstance();
        userData = SharePrefmanager.getInstance(this).getUser();
        token = userData.getApiToken();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait ...");

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
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePicker();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nik = txtInputNik.getEditText().getText().toString().trim();
                String name = txtInputNama.getEditText().getText().toString().trim();
                String tglLahir = txtInputTglLahir.getEditText().getText().toString().trim();
                String tlp = txtInputTelp.getEditText().getText().toString().trim();
                String jalan = txtInputDusun.getEditText().getText().toString().trim();
                String thnMasuk = txtInputThnMasuk.getEditText().getText().toString().trim();
                String thnKeluar = txtInputThnKeluar.getEditText().getText().toString().trim();
                String keterangan = txtInputKeterangan.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(nik)) {
                    txtInputNik.setError("Nik is required");
                    txtInputNik.requestFocus();
                } else if (TextUtils.isEmpty(name)) {
                    txtInputNama.setError("Name is required");
                    txtInputNama.requestFocus();
                } else if (TextUtils.isEmpty(tglLahir)) {
                    txtInputTglLahir.setError("Tgl Lahir is required");
                    txtInputTglLahir.requestFocus();
                } else if (TextUtils.isEmpty(tlp)) {
                    txtInputTelp.setError("Telp is required");
                    txtInputTelp.requestFocus();
                } else if (TextUtils.isEmpty(jalan)) {
                    txtInputDusun.setError("Field is required");
                    txtInputDusun.requestFocus();
                } else if (TextUtils.isEmpty(thnMasuk)) {
                    txtInputThnMasuk.setError("Thn Masuk is required");
                    txtInputThnMasuk.requestFocus();
                } else if (TextUtils.isEmpty(thnKeluar)) {
                    txtInputThnKeluar.setError("Thn Keluar is required");
                    txtInputThnKeluar.requestFocus();
                } else if (idTmptLahir == null) {
                    Toasty.error(FormInputActivity.this, "Tempat lahir masih kosong", Toasty.LENGTH_LONG).show();
                } else if (idProvinsi == null) {
                    Toasty.error(FormInputActivity.this, "Provinsi masih kosong", Toasty.LENGTH_LONG).show();
                } else if (idKabupaten == null) {
                    Toasty.error(FormInputActivity.this, "Kabupaten masih kosong", Toasty.LENGTH_LONG).show();
                } else if (idKecamatan == null) {
                    Toasty.error(FormInputActivity.this, "Kabupaten masih kosong", Toasty.LENGTH_LONG).show();
                } else if (idKelurahan == null) {
                    Toasty.error(FormInputActivity.this, "Kabupaten masih kosong", Toasty.LENGTH_LONG).show();
                } else if (encodedImage == null){
                    Toasty.error(FormInputActivity.this, "Foto masih kosong", Toasty.LENGTH_LONG).show();
                } else{
                    submitData(nik, name, idTmptLahir, tglLahir, tlp, idProvinsi, idKabupaten, idKecamatan, idKelurahan, jalan, thnMasuk, thnKeluar, keterangan);
                }

            }
        });

        txtInputTglLahir.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FormInputActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        txtInputThnMasuk.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(FormInputActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                txtInputThnMasuk.getEditText().setText(String.valueOf(selectedYear));
                                choosenYear = selectedYear;

                            }

                        }, choosenYear, 0);
                builder.showYearOnly()
                        .setYearRange(1970, Calendar.getInstance().get(Calendar.YEAR))
                        .build()
                        .show();
            }
        });

        txtInputThnKeluar.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(FormInputActivity.this,
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

        //TempatLahit
        getCities();

        //Provincies
        getProvincies();
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
                                idProvinsi = provinciesData.get(i).getId();
                                getCity(idProvinsi);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

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

    private void getCity(String idProvinsi) {
        progressDialog.show();
        BaseApiService apiService = ApiBuilder.call();
        apiService.getCity(Integer.valueOf(idProvinsi)).enqueue(new Callback<Cities>() {
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
                                idKabupaten = citiesDataList.get(i).getId();
                                getDistrict(idKabupaten);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

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

    private void getDistrict(String idKabupaten) {
        progressDialog.show();
        BaseApiService apiService = ApiBuilder.call();
        apiService.getDistrict(Integer.valueOf(idKabupaten)).enqueue(new Callback<Districts>() {
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
                                idKecamatan = districtsData.get(i).getId();
                                getVillage(idKecamatan);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

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

    private void getVillage(String idKecamatan) {
        progressDialog.show();
        BaseApiService apiService = ApiBuilder.call();
        apiService.getVillage(Integer.valueOf(idKecamatan)).enqueue(new Callback<Villages>() {
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
                                idKelurahan = villagesData.get(i).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

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
                        citiesData = response.body().getCitiesData();
                        List<String> citiesItem = new ArrayList<>();
                        for (int i = 0; i < citiesData.size(); i++) {
                            citiesItem.add(citiesData.get(i).getName());
                        }

                        spinnerTempatLahir.setItem(citiesItem);
                        spinnerTempatLahir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                idTmptLahir = citiesData.get(i).getId();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

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

    private void submitData(String nik, String name, String idTmptLahir, String tglLahir,
                            String tlp, String idProvinsi, String idKabupaten, String idKecamatan,
                            String idKelurahan, String jalan, String thnMasuk, String thnKeluar, String keterangan) {
        progressDialog.show();
        BaseApiService apiService = ApiBuilder.call();
        apiService.addData(nik, name, idTmptLahir, tglLahir, tlp, idProvinsi, idKabupaten, idKecamatan, idKelurahan,
                jalan, thnMasuk, thnKeluar, keterangan, userData.getApiToken(), encodedImage).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body() != null){
                    if (response.body().getStatus()){
                        progressDialog.dismiss();
                        Toasty.success(FormInputActivity.this, response.body().getMessage(), Toasty.LENGTH_LONG).show();
                        Intent intent = new Intent(FormInputActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        progressDialog.dismiss();
                        Toasty.error(FormInputActivity.this, response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("onFailure", "onFailure" + t.getMessage());
                Toasty.error(FormInputActivity.this, "Can not connect to server. Check your internet connection", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void showImagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(FormInputActivity.this);
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
                    compressImageFile = new Compressor(FormInputActivity.this)
                            .setMaxHeight(200)
                            .setMaxWidth(200)
                            .setQuality(10)
                            .compressToBitmap(new_image_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                encodedImage = "data:image/jpeg;base64," + encodeImage(compressImageFile);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private String encodeImage(Bitmap selectedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
}
