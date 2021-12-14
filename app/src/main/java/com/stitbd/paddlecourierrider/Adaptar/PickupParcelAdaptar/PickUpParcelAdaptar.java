package com.stitbd.paddlecourierrider.Adaptar.PickupParcelAdaptar;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.stitbd.paddlecourierrider.Adaptar.listener.PercelMenuClickListener;
import com.stitbd.paddlecourierrider.Model.PickUpParcel.Parcel;
import com.stitbd.paddlecourierrider.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PickUpParcelAdaptar extends RecyclerView.Adapter<PickUpParcelAdaptar.Viewholders> {

    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    List<Parcel> pickup = new ArrayList<>();
    Context context;
    Activity activity;

    public PickUpParcelAdaptar(List<Parcel> pickup, Context context, OnItemClickListener listener,Activity activity) {
        this.pickup = pickup;
        this.context = context;
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public PickUpParcelAdaptar.Viewholders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_parcel, parent, false);
        return new PickUpParcelAdaptar.Viewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PickUpParcelAdaptar.Viewholders holder, @SuppressLint("RecyclerView") int position) {

        holder.Invoice.setText(String.valueOf(pickup.get(position).getParcelInvoice()));
        holder.CustomerName.setText(String.valueOf(pickup.get(position).getCustomerName()));
        holder.CustomerPhn.setText(String.valueOf(pickup.get(position).getCustomerContactNumber()));
        holder.CustomerAddress.setText(String.valueOf(pickup.get(position).getCustomerAddress()));
        holder.TotalCollectAmount.setText( String.valueOf(pickup.get(position).getTotalCollectAmount()));
        holder.MerchantName.setText(String.valueOf(pickup.get(position).getMerchantName()));
        holder.ParcelStatus.setText(String.valueOf(pickup.get(position).getParcelStatus()));
        holder.optionMenu.setOnClickListener(new PercelMenuClickListener(this, holder,
                pickup.get(position), listener));
        holder.Merchatnphn.setText(String.valueOf(pickup.get(position).getMerchantContactNumber()));


        holder.Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String call = pickup.get(position).getCustomerContactNumber();
                if (isCallPermissionGranted(context)){
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+call));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(callIntent);
                }


            }
        });

        holder.Merchant_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mcall = pickup.get(position).getMerchantContactNumber();
                if (isCallPermissionGranted(context)){
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+mcall));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(callIntent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return pickup.size();
    }

    public class Viewholders extends RecyclerView.ViewHolder {

        TextView Invoice, CustomerName, CustomerPhn, CustomerAddress,
                TotalCollectAmount, MerchantName, ParcelStatus,Merchatnphn;
        ImageView optionMenu;
        ImageButton Call,Merchant_call;

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
            Merchatnphn=itemView.findViewById(R.id.tv_merchant_phn);
            Call=itemView.findViewById(R.id.call);
            Merchant_call=itemView.findViewById(R.id.merchant_call);


        }

        public ImageView getoption() {
            return this.optionMenu;
        }
    }

    public  boolean isCallPermissionGranted(Context context1) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(context1,android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }
}
