package www.jykj.com.jykj_zxyl.fragment.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import entity.EducationBean;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.EducationAdapter;
import www.jykj.com.jykj_zxyl.base.BaseLazyFragment;

public class HomeEducationFragment extends BaseLazyFragment {

    private RecyclerView mRecycleview;

    @Override
    protected void onLazyLoadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_education;
    }

    @Override
    protected void initView(View view) {
        mRecycleview=  view.findViewById(R.id.m_recycleview);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<EducationBean> educationBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            EducationBean educationBean = new EducationBean();
            educationBean.setNum(String.valueOf(i));
            educationBeans.add(educationBean);
        }

        EducationAdapter educationAdapter = new EducationAdapter(R.layout.item_education, educationBeans);
        mRecycleview.setAdapter(educationAdapter);
    }
}
