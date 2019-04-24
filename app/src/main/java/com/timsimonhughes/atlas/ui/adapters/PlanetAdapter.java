package com.timsimonhughes.atlas.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.timsimonhughes.atlas.R;
import com.timsimonhughes.atlas.model.Planet;
import com.timsimonhughes.atlas.ui.listeners.PlanetItemClickListener;
import com.timsimonhughes.atlas.utils.ImageUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class PlanetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Planet> plannetList;
    private Context context;
    private PlanetItemClickListener planetItemClickListener;

    public PlanetAdapter(List<Planet> planets, Context context) {
        this.plannetList = planets;
        this.context = context;
    }

    public void setPlanetItemClickListener(PlanetItemClickListener planetItemClickListener) {
        this.planetItemClickListener = planetItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_planets, parent, false);
        return new PlanetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Planet planet = plannetList.get(position);
        bindMission((PlanetViewHolder) holder, planet, position);
    }

    private void bindMission(PlanetViewHolder holder, Planet planet, int position) {
        String missionImageUrl = planet.getImage_url();
        String missionTitle = planet.getTitle();

        ViewCompat.setTransitionName(holder.imageViewMission, context.getResources().getString(R.string.transition_name));

        ImageUtils.loadImage(context, missionImageUrl, holder.imageViewMission);
        holder.itemView.setOnClickListener(v -> planetItemClickListener.onItemClick(holder.getAdapterPosition(), holder.imageViewMission, planet));
    }

    @Override
    public int getItemCount() {
        return plannetList.size();
    }

    class PlanetViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewMission;

        public PlanetViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewMission = itemView.findViewById(R.id.image_view_mission);

        }
    }

}
