package com.stitbd.paddlecourierrider.Activity.DeliveryParcel;

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

import com.stitbd.paddlecourierrider.Activity.CollectionParcel.CollectionParcelActivity;
import com.stitbd.paddlecourierrider.Adaptar.DeliveryParcelListAdaptar.RequestDeliveryAdaptar;
import com.stitbd.paddlecourierrider.Model.DeliveryParcel.DeliveryContainer;
import com.stitbd.paddlecourierrider.Model.DeliveryParcel.DeliveryParcel;
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

public class RequestDeliveryActivity extends AppCompatActivity {

    Api api;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_delivery);


        recyclerView = findViewById(R.id.rv_pickup_list);
        SwipeRefreshLayout Swip = findViewById(R.id.swip);
        Swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                datainitialization();
                Swip.setRefreshing(false);
            }
        });

        TextView toolbar = findViewById(R.id.tv_toolbar_title);
        toolbar.setText("Request Delivery Parcel");
        ImageView toolbarBack = findViewById(R.id.tv_back);

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        datainitialization();

    }

    public void datainitialization() {
        progressDialog = new ProgressDialog(RequestDeliveryActivity.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);

        api = RetrofitClient.get(getApplicationContext()).create(Api.class);

        api.getDeliveryparcel().enqueue(new Callback<DeliveryContainer>() {
            @Override
            public void onResponse(Call<DeliveryContainer> call, Response<DeliveryContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();

                    List<DeliveryParcel> parcelInfos = new ArrayList<>();
                    for (int i = 0; i < response.body().getParcels().size(); i++) {
                        DeliveryParcel orderInfo = response.body().getParcels().get(i);
                        if (orderInfo.getStatus() == 17) {
                            parcelInfos.add(orderInfo);
                        }
                    }
                    if (parcelInfos.size() > 0) {
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL, false));
                        RequestDeliveryAdaptar adaptar = new RequestDeliveryAdaptar(parcelInfos, getApplicationContext(), new RequestDeliveryAdaptar.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                datainitialization();
                            }
                        });
                        recyclerView.setAdapter(adaptar);
                    }
                } else {
                    try {
                        // Log.e("tesstss", response.errorBody().string());
                        try {
                            JSONObject json = new JSONObject(response.errorBody().string().toString());
                            Toast.makeText(RequestDeliveryActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<DeliveryContainer> call, Throwable t) {

            }
        });
    }
}