package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import entity.patientInfo.MsgPushModel;
import entity.patientInfo.ProvideMsgPushModel;
import www.jykj.com.jykj_zxyl.R;

public class ChooseMsgAdapter extends BaseAdapter {
    private List<MsgPushModel> list;
    private Context mContext;

    public ChooseMsgAdapter(Context mContext, List<MsgPushModel> list){
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView){
            convertView = View.inflate(mContext, R.layout.item_choose_msg,null);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.tv_msg);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(list.get(position).getMsgContent());
        return convertView;
    }

    class ViewHolder{
        TextView  textView;
    }


}
