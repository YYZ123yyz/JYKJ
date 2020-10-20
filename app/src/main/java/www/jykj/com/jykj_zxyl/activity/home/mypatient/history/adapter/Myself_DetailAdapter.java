package www.jykj.com.jykj_zxyl.activity.home.mypatient.history.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import yyz_exploit.Utils.MyImageView;

public class Myself_DetailAdapter extends RecyclerView.Adapter<Myself_DetailAdapter.ViewHolder>  {
    private List<String> datas;
    private Context mContext;

    public Myself_DetailAdapter(List<String> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Myself_DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_myself_detail,viewGroup,false);
       ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String s = datas.get(i);
//        Uri uri = Uri.parse(s);
        Glide.with(mContext).load("http://114.215.137.171:8040/patientImage/patientlogo/df90285cf3be47e2b74f24837f0b4652/Patientlogo_20200902183522.jpg").into(viewHolder.item_img);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_img=itemView.findViewById(R.id.item_img);
        }
    }

}
