package com.stitbd.paddlecourierrider.Adaptar.DeliveryParcelListAdaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stitbd.paddlecourierrider.Adaptar.listener.DeliveryMenuClickListener;
import com.stitbd.paddlecourierrider.Adaptar.listener.DeliveryRequestMenuClickListner;
import com.stitbd.paddlecourierrider.Model.DeliveryParcel.DeliveryParcel;
import com.stitbd.paddlecourierrider.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RequestDeliveryAdaptar extends RecyclerView.Adapter<RequestDeliveryAdaptar.Viewholders> {

    List<DeliveryParcel> delivery = new ArrayList<>();
    Context context;
    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public RequestDeliveryAdaptar(List<DeliveryParcel> delivery, Context context, OnItemClickListener listener) {
        this.delivery = delivery;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public Viewholders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_parcel, parent, false);
        return new RequestDeliveryAdaptar.Viewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Viewholders holder, int position) {

        holder.Invoice.setText(String.valueOf(delivery.get(position).getParcelInvoice()));
        holder.CustomerName.setText(String.valueOf(delivery.get(position).getCustomerName()));
        holder.CustomerPhn.setText(String.valueOf(delivery.get(position).getCustomerContactNumber()));
        holder.CustomerAddress.setText(String.valueOf(delivery.get(position).getCustomerAddress()));
        holder.TotalCollectAmount.setText( String.valueOf(delivery.get(position).getCollectAmount()));
        holder.MerchantName.setText( String.valueOf(delivery.get(position).getMerchantName()));
        holder.ParcelStatus.setText(String.valueOf(delivery.get(position).getParcelStatus()));
        holder.optionMenu.setOnClickListener(new DeliveryRequestMenuClickListner(this, listener,holder, delivery.get(position),context));
    }

    @Override
    public int getItemCount() {
        return delivery.size();
    }

    public class Viewholders extends RecyclerView.ViewHolder {
        TextView Invoice, CustomerName, CustomerPhn, CustomerAddress, TotalCollectAmount, MerchantName, ParcelStatus;
        ImageView optionMenu;

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
        }

        public ImageView getoption() {
            return this.optionMenu;
        }
    }
}
