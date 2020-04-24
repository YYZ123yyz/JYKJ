package www.jykj.com.jykj_zxyl.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.HzyqActivity;

public class PaintVisitAdapter extends RecyclerView.Adapter<PaintVisitAdapter.ViewHolder>{

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paint_visit,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.llVisit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(v.getContext(),HzyqActivity.class));
                }
            });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout llVisit;

        public ViewHolder(View view){
            super(view);
            llVisit = view.findViewById(R.id.ll_visit);
        }
    }
}
