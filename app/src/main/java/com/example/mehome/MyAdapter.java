package com.example.mehome;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.List;

public class MyAdapter extends   RecyclerView.Adapter<MyAdapter.ViewHolder> {
private List<TheData>list_data;
private Context ct;


public MyAdapter(List<TheData> list_data, Context ct) {
        this.list_data = list_data;
        this.ct = ct;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data,parent,false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheData ld=list_data.get(position);
        holder.tvprice.setText(ld.getmPrice());
        holder.tvname.setText(ld.getmName());
        holder.tvdesc.setText(ld.getmDesc());
        holder.tvloaction.setText(ld.getmLoaction());

        Picasso.with(ct)
        .load(ld.getmImgUrl())
        .into(holder.imageView);
        }

@Override
public int getItemCount() {
        return list_data.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder{
    private ImageView imageView;
    private TextView tvname, tvprice, tvloaction, tvdesc;
    public ViewHolder(View itemView) {
        super(itemView);
        imageView=(ImageView)itemView.findViewById(R.id.img_view);
        tvname=(TextView)itemView.findViewById(R.id.uName);
        tvloaction=itemView.findViewById(R.id.uLoc);
        tvdesc =itemView.findViewById(R.id.uDesc);
        tvprice =itemView.findViewById(R.id.uPrice);
    }
}
}

