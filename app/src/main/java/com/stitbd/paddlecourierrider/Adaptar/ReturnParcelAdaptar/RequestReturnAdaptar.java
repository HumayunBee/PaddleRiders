package com.stitbd.paddlecourierrider.Adaptar.ReturnParcelAdaptar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.stitbd.paddlecourierrider.Model.Return.ReturnParcel;
import com.stitbd.paddlecourierrider.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RequestReturnAdaptar extends RecyclerView.Adapter<RequestReturnAdaptar.Viewholders> {


    List<ReturnParcel> returnS = new ArrayList<>();
    Context context;
    Activity activity;

    public RequestReturnAdaptar(List<ReturnParcel> returnS, Context context, Activity activity) {
        this.returnS = returnS;
        this.context = context;
        this.activity = activity;

    }

    @NonNull
    @NotNull
    @Override
    public RequestReturnAdaptar.Viewholders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_parcel, parent, false);
        return new RequestReturnAdaptar.Viewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RequestReturnAdaptar.Viewholders holder, @SuppressLint("RecyclerView") int position) {
        holder.Invoice.setText(String.valueOf(returnS.get(position).getParcelInvoice()));
        holder.CustomerName.setText(String.valueOf(returnS.get(position).getCustomerName()));
        holder.CustomerPhn.setText(String.valueOf(returnS.get(position).getCustomerContactNumber()));
//        holder.CustomerAddress.setText(String.valueOf(returnS.get(position).getMerchantAddress()));
        holder.TotalCollectAmount.setText(String.valueOf(returnS.get(position).getTotalCollectAmount()));
        holder.MerchantName.setText(String.valueOf(returnS.get(position).getMerchantName()));
        holder.ParcelStatus.setText(String.valueOf(returnS.get(position).getParcelStatus()));
        holder.optionMenu.setVisibility(View.GONE);
        holder.CustomerAddress.setText(String.valueOf(returnS.get(position).getMerchantAddress()));

        holder.Merchatnphn.setText(String.valueOf(returnS.get(position).getMerchantContactNumber()));


        holder.Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String call = returnS.get(position).getCustomerContactNumber();
                if (isCallPermissionGranted(context)) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + call));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(callIntent);
                }


            }
        });

        holder.Merchant_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mcall = returnS.get(position).getMerchantContactNumber();
                if (isCallPermissionGranted(context)) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + mcall));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(callIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return returnS.size();
    }

    public class Viewholders extends RecyclerView.ViewHolder {
        TextView Invoice, CustomerName, CustomerPhn, CustomerAddress, TotalCollectAmount, MerchantName, ParcelStatus,
                Merchatnphn;
        ImageView optionMenu;
        ImageButton Call, Merchant_call;

        public Viewholders(@NonNull @NotNull View itemView) {
            super(itemView);
            Invoice = itemView.findViewById(R.id.tv_invoice);
            CustomerName = itemView.findViewById(R.id.tv_customer_name);
            CustomerPhn = itemView.findViewById(R.id.tv_phone);
            CustomerAddress = itemView.findViewById(R.id.tv_address);
            TotalCollectAmount = itemView.findViewById(R.id.tv_amount);
            MerchantName = itemView.findViewById(R.id.tv_merchant_name);
            ParcelStatus = itemView.findViewById(R.id.tv_status);
            optionMenu = itemView.findViewById(R.id.iv_menu);
            Merchatnphn = itemView.findViewById(R.id.tv_merchant_phn);
            Call = itemView.findViewById(R.id.call);
            Merchant_call = itemView.findViewById(R.id.merchant_call);
        }
    }

    public boolean isCallPermissionGranted(Context context1) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(context1, android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }
}
