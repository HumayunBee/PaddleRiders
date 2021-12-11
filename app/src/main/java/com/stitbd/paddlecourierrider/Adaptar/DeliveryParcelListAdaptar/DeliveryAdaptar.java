package com.stitbd.paddlecourierrider.Adaptar.DeliveryParcelListAdaptar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stitbd.paddlecourierrider.Adaptar.listener.DeliveryMenuClickListener;
import com.stitbd.paddlecourierrider.Model.DeliveryParcel.DeliveryParcel;
import com.stitbd.paddlecourierrider.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAdaptar extends RecyclerView.Adapter<DeliveryAdaptar.Viewholders> {

    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    List<DeliveryParcel> delivery = new ArrayList<>();
    Context context;

    public DeliveryAdaptar(List<DeliveryParcel> delivery, Context context, OnItemClickListener listener) {
        this.delivery = delivery;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public DeliveryAdaptar.Viewholders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_parcel, parent, false);
        return new DeliveryAdaptar.Viewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DeliveryAdaptar.Viewholders holder, @SuppressLint("RecyclerView") int position) {

        holder.Invoice.setText(String.valueOf(delivery.get(position).getParcelInvoice()));
        holder.CustomerName.setText(String.valueOf(delivery.get(position).getCustomerName()));
        holder.CustomerPhn.setText(String.valueOf(delivery.get(position).getCustomerContactNumber()));
        holder.CustomerAddress.setText(String.valueOf(delivery.get(position).getCustomerAddress()));
        holder.TotalCollectAmount.setText(String.valueOf(delivery.get(position).getCollectAmount()));
        holder.MerchantName.setText(String.valueOf(delivery.get(position).getMerchantName()));
        holder.ParcelStatus.setText(String.valueOf(delivery.get(position).getParcelStatus()));
        holder.optionMenu.setOnClickListener(new DeliveryMenuClickListener(this,
                listener, holder, delivery.get(position)));
        holder.Mercahntphn.setText(String.valueOf(delivery.get(position).getMerchantContactNumber()));


        holder.Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String call = delivery.get(position).getCustomerContactNumber();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(call));
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return delivery.size();
    }

    public class Viewholders extends RecyclerView.ViewHolder {

        TextView Invoice, CustomerName, CustomerPhn, CustomerAddress,
                TotalCollectAmount, MerchantName, ParcelStatus, Mercahntphn;
        ImageView optionMenu;
        Button Call;

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
/*            Call=itemView.findViewById(R.id.call);*/
            Mercahntphn = itemView.findViewById(R.id.tv_merchant_phn);
        }

        public ImageView getoption() {
            return this.optionMenu;
        }
    }
}
