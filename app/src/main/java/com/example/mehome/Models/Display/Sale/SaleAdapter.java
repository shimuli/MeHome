package com.example.mehome.Models.Display.Sale;

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

import com.example.mehome.Models.AddingProperty.HolidayHouses.HolidayData;
import com.example.mehome.Models.AddingProperty.SaleHouses.SaleData;
import com.example.mehome.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ImageViewHolder> {
    private Context mContext;
    private List<HolidayData> mUploads;
    private OnItemClickListener mListener;

    public SaleAdapter(Context context, List<HolidayData> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.saleimageitems, parent, false);
        return new ImageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        HolidayData uploadCurrent = mUploads.get(position);
        holder.textViewTitle.setText(uploadCurrent.getTitle_of_Holiday_House());
        holder.textViewLocation.setText(uploadCurrent.getHoldiay_House_Location());
        holder.textViewDesc.setText(uploadCurrent.getHoliday_HouseDesc());
        holder.textViewPrice.setText(uploadCurrent.getHoldiay_HousePrice());
        holder.textViewBed.setText(uploadCurrent.getHoliday_Bedroom_No());
        holder.textViewPhone.setText(uploadCurrent.getOwnerNumber());
        holder.textViewType.setText(uploadCurrent.getTypes());
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
        public TextView textViewTitle, textViewLocation, textViewDesc, textViewPrice,textViewType, textViewBed, textViewPhone;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_name_comm);
            textViewLocation = itemView.findViewById(R.id.text_view_location_comm);
            textViewDesc = itemView.findViewById(R.id.text_view_description_comm);
            textViewPrice = itemView.findViewById(R.id.text_view_Price_comm);
            imageView = itemView.findViewById(R.id.image_view_upload_comm);
            textViewBed = itemView.findViewById(R.id.text_view_Bedrooms_comm);
            textViewPhone =itemView.findViewById(R.id.text_view_phone_comm);
            textViewType = itemView.findViewById(R.id.text_view_type_comm);


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
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");
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

