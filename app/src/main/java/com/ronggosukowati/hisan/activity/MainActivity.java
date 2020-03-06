package com.ronggosukowati.hisan.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ronggosukowati.hisan.R;
import com.ronggosukowati.hisan.adapter.ListDataAdapter;
import com.ronggosukowati.hisan.model.Alumni;
import com.ronggosukowati.hisan.model.AlumniData;
import com.ronggosukowati.hisan.model.GeneralResponse;
import com.ronggosukowati.hisan.model.UserData;
import com.ronggosukowati.hisan.services.ApiBuilder;
import com.ronggosukowati.hisan.services.BaseApiService;
import com.ronggosukowati.hisan.session.SharePrefmanager;
import com.ronggosukowati.hisan.utils.ItemClickSupport;
import com.ronggosukowati.hisan.utils.MyDividerItemDecoration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ListDataAdapter.DataAlumniCallback {

    private RecyclerView rvData;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private TextView tvInfo;

    private List<AlumniData> alumniDataList;
    private ListDataAdapter dataAdapter;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Data Alumni");
        setSupportActionBar(toolbar);

        checkPermissions();

        userData = SharePrefmanager.getInstance(this).getUser();
        rvData = findViewById(R.id.rv_data);
        progressBar = findViewById(R.id.progressBar);
        tvInfo = findViewById(R.id.tv_info);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Deleting data ...");

        showListData();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FormInputActivity.class));
            }
        });
    }

    private void showListData() {
        BaseApiService apiService = ApiBuilder.call();
        apiService.getData(userData.getApiToken()).enqueue(new Callback<Alumni>() {
            @Override
            public void onResponse(Call<Alumni> call, Response<Alumni> response) {
                if (response.body() != null){
                    if (response.body().getStatus()){
                        progressBar.setVisibility(View.GONE);
                        alumniDataList = response.body().getAlumniData();
                        if (alumniDataList.size() > 0){
                            tvInfo.setVisibility(View.GONE);
                            rvData.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            rvData.setItemAnimator(new DefaultItemAnimator());
                            rvData.addItemDecoration(new MyDividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL, 36));
                            dataAdapter = new ListDataAdapter(alumniDataList, MainActivity.this, MainActivity.this);
                            rvData.setAdapter(dataAdapter);
                        }else {
                            progressBar.setVisibility(View.GONE);
                            tvInfo.setVisibility(View.VISIBLE);
                        }
                    }else {
                        progressBar.setVisibility(View.GONE);
                        SharePrefmanager.getInstance(MainActivity.this).logout();
                    }
                }

                ItemClickSupport.addTo(rvData).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        showSelectedItem(position);
                    }
                });
            }

            @Override
            public void onFailure(Call<Alumni> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("onFailure", "onFailuer:: "+ t.getMessage());

            }
        });
    }

    private void showSelectedItem(int position) {
        Intent detail = new Intent(MainActivity.this, DetailActivity.class);
        detail.putExtra("detail", alumniDataList.get(position));
        startActivity(detail);
    }


    private void checkPermissions() {
        Dexter.withActivity(MainActivity.this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()){

                    Log.d("TAG", "Permission Granted");
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                token.continuePermissionRequest();
            }
        }).onSameThread().check();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharePrefmanager.getInstance(MainActivity.this).logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemEditClicked(int position) {

        Intent intent = new Intent(MainActivity.this, FormEditActivity.class);
        intent.putExtra("datas", alumniDataList.get(position));
        startActivity(intent);

    }

    @Override
    public void itemDeleteClicked(final int position) {
            progressDialog.show();
            BaseApiService apiService = ApiBuilder.call();
            apiService.deleteData(alumniDataList.get(position).getId()).enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                    if (response.body().getStatus()){
                        progressDialog.dismiss();
                        alumniDataList.remove(position);
                        dataAdapter.notifyDataSetChanged();
                        Toasty.success(MainActivity.this, "Data telah dihapus", Toasty.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("onFailure", "onFailure" + t.getMessage());
                    Toasty.error(MainActivity.this, "Can not connect to server. Check your internet connection", Toast.LENGTH_SHORT).show();


                }
            });
    }
}
