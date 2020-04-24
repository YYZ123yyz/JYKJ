package www.jykj.com.jykj_zxyl.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideHospitalInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.yslm.CreateUnionActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.DoctorUnionInviteMemberActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.JoinDoctorsUnionActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.UpdateUnionActivity;
import www.jykj.com.jykj_zxyl.activity.myself.UserCenterActivity;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.widget.OnWheelChangedListener;
import www.jykj.com.jykj_zxyl.util.widget.WheelView;
import www.jykj.com.jykj_zxyl.util.widget.adapters.ArrayWheelAdapter;


public class ProvincePicker extends Dialog implements
		android.view.View.OnClickListener, OnWheelChangedListener {
	private JYKJApplication mApp;
	private CreateUnionActivity			mCreateActivity;
	private JoinDoctorsUnionActivity	mJoinUnionActivity;
	private DoctorUnionInviteMemberActivity mDoctorUnionInviteMemberActivity;
	private UpdateUnionActivity mUpdateUnionActivity;
	private UserCenterActivity mUserCenterActivity;
	/**
	 * 所有省
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - 省 value - 市
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

	/**
	 *  市
	 */
	protected List<ProvideBasicsRegion> mCityList = new ArrayList<>();

	/**
	 *  区
	 */
	protected List<ProvideBasicsRegion> mDistList = new ArrayList<>();

	/**
	 * 当前省
	 */
	protected ProvideBasicsRegion mCurrentProviceName;
	/**
	 * 当前市
	 */
	protected ProvideBasicsRegion mCurrentCityName;
	/**
	 * 当前区
	 */
	protected ProvideBasicsRegion mCurrentDistrictName;

	/**
	 * 当前区的邮政编码
	 */
	protected String mCurrentZipCode = "";
	private Context context;
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private Button mBtnConfirm;

	private int	intoActivity;				//入口Activity 1=创建联盟  2=加入联盟  3=邀请加入联盟

	public ProvincePicker(Context context) {
		super(context, R.style.mydialogstyle);
		this.context = context;
		this.mApp = (JYKJApplication) context.getApplicationContext();

		initView(context);

	}

	public void setActivity(Activity activity,int type){
		switch (type)
		{
			case 1:
				mCreateActivity = (CreateUnionActivity)activity;
				intoActivity = type;
				break;
			case 2:
				mJoinUnionActivity = (JoinDoctorsUnionActivity)activity;
				intoActivity = type;
				break;

			case 3:
				mDoctorUnionInviteMemberActivity = (DoctorUnionInviteMemberActivity)activity;
				intoActivity = type;
				break;
			case 4:
				mUpdateUnionActivity  = (UpdateUnionActivity)activity;
				intoActivity = type;
				break;
			case 5:
				mUserCenterActivity  = (UserCenterActivity) activity;
				intoActivity = type;
				break;
		}

	}

	public void initView(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.activity_region, null);

		mViewProvince = (WheelView) view.findViewById(R.id.id_province);
		mViewCity = (WheelView) view.findViewById(R.id.id_city);
		mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
		mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
		setUpListener();
		setUpData(context);
		setContentView(view);
	}

	private void setUpListener() {
		// 添加change事件
		mViewProvince.addChangingListener(this);
		// 添加change事件
		mViewCity.addChangingListener(this);
		// 添加change事减
		mViewDistrict.addChangingListener(this);
		// 添加onclick事件
		mBtnConfirm.setOnClickListener(this);
	}

	private void setUpData(Context context) {
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<ProvideBasicsRegion>(context,mApp.gRegionProvideList
				));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities(context);
		updateAreas(context);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_confirm:
				showSelectedResult(context);
				break;
			default:
				break;
		}

	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities(context);
		} else if (wheel == mViewCity) {
			updateAreas(context);
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistList.get(newValue);
			if (mCurrentCityName == null)
            {
                System.out.println("");
            }
	}
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas(Context context) {
		int pCurrent = mViewCity.getCurrentItem();

		if (mCityList.size() == 0)
			return;
		mCurrentCityName = mCityList.get(pCurrent);

		List<ProvideBasicsRegion> listd = new ArrayList<>();
		if (!mCurrentCityName.getRegion_id().equals("sqb"))
		{
			ProvideBasicsRegion provideBasicsRegion1City = new ProvideBasicsRegion();
			provideBasicsRegion1City.setRegion_name("全部");
			provideBasicsRegion1City.setRegion_parent_id(mCurrentProviceName.getRegion_parent_id());
			provideBasicsRegion1City.setRegion_id("qqb");
			provideBasicsRegion1City.setRegion_level(2);
			listd.add(provideBasicsRegion1City);
		}
		for (int i = 0 ; i < mApp.gRegionDistList.size(); i++)
		{
			if (mApp.gRegionDistList.get(i).getRegion_parent_id().equals( mCurrentCityName.getRegion_id()))
			{
				listd.add(mApp.gRegionDistList.get(i));
			}
		}
		mDistList = listd;
		System.out.println(new Gson().toJson(listd));
        ProvideBasicsRegion provideBasicsRegion = new ProvideBasicsRegion();
        listd.add(provideBasicsRegion);
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<ProvideBasicsRegion>(context,
                listd));
		mViewDistrict.setCurrentItem(0);
		int pAreaCurrent = mViewDistrict.getCurrentItem();
		if (listd.size() >= 1)
			mCurrentDistrictName = listd.get(pAreaCurrent);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities(Context context) {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mApp.gRegionProvideList.get(pCurrent);
		List<ProvideBasicsRegion> list = new ArrayList<>();
		System.out.println("0");
        ProvideBasicsRegion provideBasicsRegion1City = new ProvideBasicsRegion();
        provideBasicsRegion1City.setRegion_name("全部");
        provideBasicsRegion1City.setRegion_parent_id(mCurrentProviceName.getRegion_parent_id());
        provideBasicsRegion1City.setRegion_id("sqb");
        provideBasicsRegion1City.setRegion_level(2);
		list.add(provideBasicsRegion1City);
		for (int i = 0 ; i < mApp.gRegionCityList.size(); i++)
		{
			if (mApp.gRegionCityList.get(i).getRegion_parent_id().equals( mApp.gRegionProvideList.get(pCurrent).getRegion_id()))
			{
				list.add(mApp.gRegionCityList.get(i));
			}
		}
		mCityList = list;
		mViewCity
				.setViewAdapter(new ArrayWheelAdapter<ProvideBasicsRegion>(context, list));
		mViewCity.setCurrentItem(0);
		System.out.println("mList:"+list.size());
		System.out.println("mCityList:"+mCityList.size());
		updateAreas(context);
	}

	private void showSelectedResult(Context context) {
	    if (mCurrentCityName == null)
        {
            System.out.println("");
        }
		switch (intoActivity)
		{
			case 1:
				if (mCurrentDistrictName.getRegion_name() == null)
				{

					mCreateActivity.mChoiceRegionMap.put("provice",mCurrentProviceName);
					mCreateActivity.mChoiceRegionMap.put("city",mCurrentCityName);
				}
				else
				{
					mCreateActivity.mChoiceRegionMap.put("provice",mCurrentProviceName);
					mCreateActivity.mChoiceRegionMap.put("city",mCurrentCityName);
					mCreateActivity.mChoiceRegionMap.put("dist",mCurrentDistrictName);
				}
				mCreateActivity.setRegionText();
				break;
			case 2:
				if (mCurrentDistrictName.getRegion_name() == null)
				{

					mJoinUnionActivity.mChoiceRegionMap.put("provice",mCurrentProviceName);
					mJoinUnionActivity.mChoiceRegionMap.put("city",mCurrentCityName);
				}
				else
				{
					mJoinUnionActivity.mChoiceRegionMap.put("provice",mCurrentProviceName);
					mJoinUnionActivity.mChoiceRegionMap.put("city",mCurrentCityName);
					mJoinUnionActivity.mChoiceRegionMap.put("dist",mCurrentDistrictName);
				}
				mJoinUnionActivity.setRegionText();
				break;
			case 3:
				if (mCurrentDistrictName.getRegion_name() == null)
				{
					mDoctorUnionInviteMemberActivity.mChoiceRegionMap.put("provice",mCurrentProviceName);
					mDoctorUnionInviteMemberActivity.mChoiceRegionMap.put("city",mCurrentCityName);
				}
				else
				{
					mDoctorUnionInviteMemberActivity.mChoiceRegionMap.put("provice",mCurrentProviceName);
					mDoctorUnionInviteMemberActivity.mChoiceRegionMap.put("city",mCurrentCityName);
					mDoctorUnionInviteMemberActivity.mChoiceRegionMap.put("dist",mCurrentDistrictName);
				}
				mDoctorUnionInviteMemberActivity.setRegionText();
				break;
			case 4:
				if (mCurrentDistrictName.getRegion_name() == null)
				{

					mUpdateUnionActivity.mChoiceRegionMap.put("provice",mCurrentProviceName);
					mUpdateUnionActivity.mChoiceRegionMap.put("city",mCurrentCityName);
				}
				else
				{
					mUpdateUnionActivity.mChoiceRegionMap.put("provice",mCurrentProviceName);
					mUpdateUnionActivity.mChoiceRegionMap.put("city",mCurrentCityName);
					mUpdateUnionActivity.mChoiceRegionMap.put("dist",mCurrentDistrictName);
				}
				mUpdateUnionActivity.setRegionText();
				break;

			case 5:
				if (mCurrentDistrictName.getRegion_name() == null)
				{

					mUserCenterActivity.mChoiceRegionMap.put("provice",mCurrentProviceName);
					mUserCenterActivity.mChoiceRegionMap.put("city",mCurrentCityName);
				}
				else
				{
					mUserCenterActivity.mChoiceRegionMap.put("provice",mCurrentProviceName);
					mUserCenterActivity.mChoiceRegionMap.put("city",mCurrentCityName);
					mUserCenterActivity.mChoiceRegionMap.put("dist",mCurrentDistrictName);
				}
				mUserCenterActivity.setRegionText();
				break;

		}

		this.dismiss();
	}


}
