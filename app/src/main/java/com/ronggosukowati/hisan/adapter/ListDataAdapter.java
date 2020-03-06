package com.ronggosukowati.hisan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ronggosukowati.hisan.R;
import com.ronggosukowati.hisan.model.AlumniData;
import com.ronggosukowati.hisan.model.UserData;
import com.ronggosukowati.hisan.session.SharePrefmanager;

import java.util.ArrayList;
import java.util.List;

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ViewHolder> {

    private static final String BASE_IMAGE_URL = "http://hisan.id/storage/images/";
    private UserData userData;

    private List<AlumniData> alumniDataList = new ArrayList<>();
    private Context mContext;
    private DataAlumniCallback dataAlumniCallback;

    public ListDataAdapter(List<AlumniData> alumniDataList, Context mContext, DataAlumniCallback dataAlumniCallback) {
        this.alumniDataList = alumniDataList;
        this.mContext = mContext;
        this.dataAlumniCallback = dataAlumniCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_item, parent, false);
        userData = SharePrefmanager.getInstance(parent.getContext()).getUser();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(alumniDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return alumniDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto, btnMore;
        TextView tvName, tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.thumbnail);
            btnMore = itemView.findViewById(R.id.btn_more);
            tvName = itemView.findViewById(R.id.name);
            tvAddress = itemView.findViewById(R.id.address);
        }

        public void bind(AlumniData alumniData) {

            Glide.with(itemView.getContext()).load(BASE_IMAGE_URL+alumniData.getFoto()).placeholder(R.drawable.ic_user_placeholder).into(imgPhoto);
            tvName.setText(alumniData.getName());
            String address = alumniData.getAlumniVillage().getName()+", " + alumniData.getAlumniCity().getName()+", "+ alumniData.getAlumniDistrict().getName()+", "+ alumniData.getAlumniProvince().getName();
            tvAddress.setText(address);

            btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(view, getAdapterPosition());
                }
            });
        }
    }

    private void showPopupMenu(View view, final int adapterPosition) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        if (userData.getLevel().equalsIgnoreCase("admin")) {
            inflater.inflate(R.menu.menu_popup, popupMenu.getMenu());
        }else {
            inflater.inflate(R.menu.editor_menu, popupMenu.getMenu());
        }
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_edit:
                        dataAlumniCallback.itemEditClicked(adapterPosition);
                        return true;
                    case R.id.action_delete:
                        dataAlumniCallback.itemDeleteClicked(adapterPosition);
                        return true;
                }
                return false;
            }
        });

    }

    public interface DataAlumniCallback{

        void itemEditClicked(int position);

        void itemDeleteClicked(int position);
    }
}
