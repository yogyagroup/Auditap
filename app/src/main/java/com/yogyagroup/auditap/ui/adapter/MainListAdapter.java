package com.yogyagroup.auditap.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yogyagroup.auditap.R;
import com.yogyagroup.auditap.model.Receipt;
import com.yogyagroup.auditap.ui.activity.PhotoActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListViewHolder> {
    private final String TAG = MainListAdapter.class.getSimpleName();
    private ArrayList<Receipt> receipts;
    private Context context;

    public MainListAdapter(ArrayList<Receipt> receipts, Context context) {
        this.receipts = receipts;
        this.context = context;
    }

    @Override
    public MainListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(final MainListViewHolder holder, final int position) {
        final Receipt receipt = receipts.get(position);

        holder.viewCustomerName.setText(receipt.getName());

        holder.viewMotorBrand.setText(receipt.getBrand() + " " + receipt.getColor());

        holder.viewSalesDetail.setText(receipt.getLeasing() + "    " + receipt.getDate() + "  DP: " + receipt.getDp_po());

        if (receipt.getMemo() == 0) {
            holder.imageCircle.setVisibility(View.GONE);
        } else {
            holder.imageCircle.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PhotoActivity.class);
                intent.putExtra("receipt_id", receipt.getId());
                intent.putExtra("customer_name", receipt.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    class MainListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_customer_name)
        TextView viewCustomerName;
        @BindView(R.id.view_motor_brand)
        TextView viewMotorBrand;
        @BindView(R.id.image_circle)
        ImageView imageCircle;
        @BindView(R.id.view_sales_detail)
        TextView viewSalesDetail;

        public MainListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
