package com.stitbd.paddlecourierrider.Activity.PickupParcel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stitbd.paddlecourierrider.Activity.PaidAmount.PaidAmountActivity;
import com.stitbd.paddlecourierrider.Adaptar.PickupParcelAdaptar.PickUpParcelAdaptar;
import com.stitbd.paddlecourierrider.Adaptar.PickupParcelAdaptar.RequestParcelAdaptar;
import com.stitbd.paddlecourierrider.Model.PickUpParcel.Parcel;
import com.stitbd.paddlecourierrider.Model.PickUpParcel.PickupParcel;
import com.stitbd.paddlecourierrider.Network.Api;
import com.stitbd.paddlecourierrider.Network.RetrofitClient;
import com.stitbd.paddlecourierrider.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptPickActivity extends AppCompatActivity {

    Api api;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_pick);

        recyclerView = findViewById(R.id.rv_pickup_list);

        SwipeRefreshLayout Swip = findViewById(R.id.swip);
        Swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                datainitialize();
                Swip.setRefreshing(false);
            }
        });

        TextView toolbar = findViewById(R.id.tv_toolbar_title);
        toolbar.setText("Accept Pickup Parcel");
        ImageView toolbarBack = findViewById(R.id.tv_back);

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(AcceptPickActivity.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);

        api = RetrofitClient.get(getApplicationContext()).create(Api.class);
        datainitialize();


    }

    public void datainitialize() {
        api.getparcel().enqueue(new Callback<PickupParcel>() {
            @Override
            public void onResponse(Call<PickupParcel> call, Response<PickupParcel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();

                    List<Parcel> parcelInfos = new ArrayList<>();
                    for (int i = 0; i < response.body().getParcels().size(); i++) {
                        Parcel orderInfo = response.body().getParcels().get(i);
                        if (orderInfo.getStatus() == 8) {
                            parcelInfos.add(orderInfo);
                        }
                    }
                    if (parcelInfos.size() > 0) {
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL, false));
                        RequestParcelAdaptar adaptar = new RequestParcelAdaptar(parcelInfos, getApplicationContext(),
                                new RequestParcelAdaptar.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {

                                    }
                                },AcceptPickActivity.this);
                        recyclerView.setAdapter(adaptar);
                    }
                } else {
                    try {
                        // Log.e("tesstss", response.errorBody().string());
                        try {
                            JSONObject json = new JSONObject(response.errorBody().string().toString());
                            Toast.makeText(AcceptPickActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
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

            }
        });
    }
}