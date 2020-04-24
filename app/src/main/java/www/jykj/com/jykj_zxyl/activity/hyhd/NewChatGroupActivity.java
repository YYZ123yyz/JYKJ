package www.jykj.com.jykj_zxyl.activity.hyhd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseTitleBar;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.adapter.DorcerFriendExpandableListViewAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.fragment.FragmentYHHD;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.NestedExpandaleListView;

/**
 * 建群
 */
public class NewChatGroupActivity extends AppCompatActivity {


    private             Context                             mContext;
    private             NewChatGroupActivity                mActivity;
    private             Handler                             mHandler;
    private             JYKJApplication                     mApp;

    private     NestedExpandaleListView                 mYSHY;                                  //医生好友
    private     DorcerFriendExpandableListViewAdapter mDorcerFriendExpandableListViewAdapter;   //医生好友适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newchatgroup);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBar(mActivity);
        initLayout();

    }

    /**
     * 初始化布局
     */
    private void initLayout() {
//        mYSHY = (NestedExpandaleListView)this.findViewById(R.id.rv_fragmethyhd_yshyInfo);
//        mDorcerFriendExpandableListViewAdapter = new DorcerFriendExpandableListViewAdapter(mInteractDoctorUnionInfo,mContext,mApp);
//        mYSHY.setAdapter(mDorcerFriendExpandableListViewAdapter);
//        mYSHY.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                mInteractDoctorUnionInfo.get(groupPosition).setClick(!mInteractDoctorUnionInfo.get(groupPosition).isClick());
//                mDorcerFriendExpandableListViewAdapter.setDate(mInteractDoctorUnionInfo);
//                mDorcerFriendExpandableListViewAdapter.notifyDataSetInvalidated();
//                mDorcerFriendExpandableListViewAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });
//        mYSHY.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                Intent intent = new Intent();
//                intent.setClass(mContext,ChatActivity.class);
//                intent.putExtra("userCode",mInteractDoctorUnionInfo.get(i).getDoctorUnionPersonnelList().get(i1).getDoctorCode());
//                intent.putExtra("userName",mInteractDoctorUnionInfo.get(i).getDoctorUnionPersonnelList().get(i1).getDoctorUserName());
//                startActivity(intent);
//                return false;
//            }
//        });

    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }



}
