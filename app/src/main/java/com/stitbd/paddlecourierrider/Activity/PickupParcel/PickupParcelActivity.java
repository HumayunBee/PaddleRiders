package com.stitbd.paddlecourierrider.Activity.PickupParcel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stitbd.paddlecourierrider.Activity.ReturnParcel.ReturnActivity;
import com.stitbd.paddlecourierrider.Adaptar.PickupParcelAdaptar.PickUpParcelAdaptar;
import com.stitbd.paddlecourierrider.Model.PickUpParcel.PickupParcel;
import com.stitbd.paddlecourierrider.Network.Api;
import com.stitbd.paddlecourierrider.Network.RetrofitClient;
import com.stitbd.paddlecourierrider.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickupParcelActivity extends AppCompatActivity {

    Api api;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    SwipeRefreshLayout Swip;

 /*   ImageButton Call;

    String phnno= "0123456789";*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_parcel);

        recyclerView = findViewById(R.id.rv_pickup_list);

/*        Call=findViewById(R.id.call);*/



        Swip=findViewById(R.id.swip);
        Swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                datainitialize();
                Swip.setRefreshing(false);
            }
        });
        progressDialog = new ProgressDialog(PickupParcelActivity.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        TextView toolbar = findViewById(R.id.tv_toolbar_title);
        toolbar.setText("Pickup Parcel List");
        ImageView toolbarBack = findViewById(R.id.tv_back);

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


//        Toast.makeText(this, "restart", Toast.LENGTH_SHORT).show();
        datainitialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        datainitialize();

    }


    void datainitialize() {
        //Toast.makeText(PickupParcelActivity.this, "tessting", Toast.LENGTH_SHORT).show();


        progressDialog.show();

        api = RetrofitClient.get(getApplicationContext()).create(Api.class);

        api.getparcel().enqueue(new Callback<PickupParcel>() {
            @Override
            public void onResponse(Call<PickupParcel> call, Response<PickupParcel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {

                    // Toast.makeText(PickupParcelActivity.this, "tessting", Toast.LENGTH_SHORT).show();
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                    PickUpParcelAdaptar adaptar = new PickUpParcelAdaptar(response.body().getParcels(),
                            getApplicationContext(), new PickUpParcelAdaptar.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            datainitialize();
                            // Toast.makeText(PickupParcelActivity.this, "tessting", Toast.LENGTH_SHORT).show();
                        }
                    });
                    recyclerView.setAdapter(adaptar);
                    Log.e("ddd", String.valueOf(response.body().getParcels().size()));
                } else {
                    try {
                        // Log.e("tesstss", response.errorBody().string());
                        try {
                            JSONObject json = new JSONObject(response.errorBody().string().toString());
                            Toast.makeText(PickupParcelActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // String a=response.errorBody().string().toString();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<PickupParcel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}