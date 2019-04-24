package com.timsimonhughes.atlas.ui.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.timsimonhughes.atlas.model.POTD;
import com.timsimonhughes.atlas.ui.listeners.POTDOnItemClickListener;
import com.timsimonhughes.atlas.utils.POTDUtils;
import com.timsimonhughes.atlas.R;

import java.util.List;

public class POTDAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<POTD> PODList;
    private POTDOnItemClickListener onItemClickListener;

    public POTDAdapter(Context context, List<POTD> podList) {
        this.mContext = context;
        this.PODList = podList;
    }

    public void setOnItemClickListener(POTDOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_potd, viewGroup, false);
        return new POTDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        POTD potd = PODList.get(position);
        bindPOTD((POTDViewHolder) viewHolder, potd, position);
//        if (potd instanceof POTD) {
//            bindPOTD((POTDViewHolder) viewHolder, potd);
//        }
    }

    private void bindPOTD(final POTDViewHolder holder, final POTD potd, final int position) {

        String formattedDate = POTDUtils.formatDate(potd.getDate());

//        holder.textViewPOTDTitle.setText(potd.getTitle());
        holder.textViewPOTDDate.setText(formattedDate);
        holder.textViewPOTDExplanation.setText(potd.getExplanation());

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(holder.getAdapterPosition(), holder.imageViewPOTD, potd));

//        potd.getDate();
//        potd.getExplanation();
//        potd.getHdurl();
//        potd.getMedia_type();
//        potd.getService_version();
//        potd.getTitle();
//        potd.getUrl();

        Glide.with(mContext)
                .load(potd.getUrl())
                .into(holder.imageViewPOTD);
    }


    @Override
    public int getItemCount() {
        return PODList.size();
    }

    class POTDViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewPOTD;
        TextView textViewPOTDTitle, textViewPOTDDate, textViewPOTDExplanation;

        public POTDViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPOTD = itemView.findViewById(R.id.image_view_potd);
//            textViewPOTDTitle = itemView.findViewById(R.id.text_view_title);
            textViewPOTDDate = itemView.findViewById(R.id.text_view_date);
            textViewPOTDExplanation = itemView.findViewById(R.id.text_view_explanation);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
