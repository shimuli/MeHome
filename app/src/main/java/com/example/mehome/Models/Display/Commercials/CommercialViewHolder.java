package com.example.mehome.Models.Display.Commercials;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mehome.Models.AddingProperty.CommercialSale.CommercialData;
import com.example.mehome.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommercialViewHolder extends RecyclerView.Adapter<CommercialViewHolder.ImageViewHolder> {
    private Context mContext;
    private List<CommercialData> mUploads;
    private OnItemClickListener mListener;

    public CommercialViewHolder(Context context, List<CommercialData> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.commercial_imageitem, parent, false);
        return new ImageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        CommercialData uploadCurrent = mUploads.get(position);
        holder.textViewTitle.setText(uploadCurrent.getTitle_of_Commercial());
        holder.textViewLocation.setText(uploadCurrent.getCommercial_Location());
        holder.textViewDesc.setText(uploadCurrent.getCommercialDesc());
        holder.textViewPrice.setText(uploadCurrent.getCommercialPrice());
        Picasso.with(mContext)
                .load(uploadCurrent.getImageURL())
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewTitle, textViewLocation, textViewDesc, textViewPrice;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_name);
            textViewLocation = itemView.findViewById(R.id.text_view_location);
            textViewDesc = itemView.findViewById(R.id.text_view_description);
            textViewPrice = itemView.findViewById(R.id.text_view_Price);
            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Do whatever");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onWhatEverClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }

                }
            }
            return false;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
