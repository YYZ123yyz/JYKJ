package www.jykj.com.jykj_zxyl.fragment.hzgl;



import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.jykj.com.jykj_zxyl.R;

public class BloodRulesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blood_rules, container, false);
        return v;
    }
    //定义一个方法进行接收
    public static BloodRulesFragment create(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        BloodRulesFragment fragment01 = new BloodRulesFragment();
        fragment01.setArguments(bundle);
        return fragment01;
    }
}
