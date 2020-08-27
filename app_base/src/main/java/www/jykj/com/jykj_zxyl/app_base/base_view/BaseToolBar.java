package www.jykj.com.jykj_zxyl.app_base.base_view;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import www.jykj.com.jykj_zxyl.app_base.R;


/**
 * Description：TooBar
 *
 * @author: qiuxinhai
 * @date: 2020-07-14 13:03
 */
public class BaseToolBar extends Toolbar {
    /**
     * 左侧Title
     */
    private TextView mTxtLeftTitle;
    /**
     * 左侧image
     */
    private ImageButton mTxtLeftImage;
    /**
     * 右侧的image
     */
    private ImageButton mTextRightImage;

    private ImageButton mTextRightSearch;

    /**
     * 中间Title
     */
    private TextView mTxtMiddleTitle;
    /**
     * 右侧Title
     */
    private TextView mTxtRightTitle;

    public BaseToolBar(Context context) {
        this(context,null);
    }

    public BaseToolBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTxtLeftImage = findViewById(R.id.left_image_id);
        mTxtLeftTitle = findViewById(R.id.txt_left_title);
        mTxtMiddleTitle = findViewById(R.id.txt_main_title);
        mTxtRightTitle = findViewById(R.id.txt_right_title);
        mTextRightImage=findViewById(R.id.right_image_id);
        mTextRightSearch=findViewById(R.id.right_image_search);
    }

    //设置中间title的内容
    public void setMainTitle(String text) {
        this.setTitle(" ");
        mTxtMiddleTitle.setVisibility(View.VISIBLE);
        mTxtMiddleTitle.setText(text);
    }

    //设置中间title的内容文字的颜色
    public void setMainTitleColor(int color) {
        mTxtMiddleTitle.setTextColor(color);
    }

    //设置title左边文字
    public void setLeftTitleText(String text) {
        mTxtLeftTitle.setVisibility(View.VISIBLE);
        mTxtLeftTitle.setText(text);
    }

    //设置title左边文字颜色
    public void setLeftTitleColor(int color) {
        mTxtLeftTitle.setTextColor(color);
    }

    //设置title左边图标
    public void setLeftTitleDrawable(int res) {
        Drawable dwLeft = ContextCompat.getDrawable(getContext(), res);
        mTxtLeftImage.setImageDrawable(dwLeft);
        /*dwLeft.setBounds(0, 0, 60, 60);
        mTxtLeftTitle.setCompoundDrawables(dwLeft, null, null, null);*/
    }
    //设置title右边的图标
    public void setRightTitleDrawable( int res){
        Drawable dwRight = ContextCompat.getDrawable(getContext(), res);
        mTextRightImage.setImageDrawable(dwRight);
    }
    //设置title右边搜索的图标
    public void setRightTitleSearchDrawable(int res){
        Drawable dwRight = ContextCompat.getDrawable(getContext(), res);
        mTextRightSearch.setImageDrawable(dwRight);
    }

    //设置title左边点击事件
    public void setLeftTitleClickListener(OnClickListener onClickListener){
        mTxtLeftImage.setOnClickListener(onClickListener);
        mTxtLeftTitle.setOnClickListener(onClickListener);
    }

    //设置title右边文字
    public void setRightTitleText(String text) {
        mTxtRightTitle.setVisibility(View.VISIBLE);
        mTxtRightTitle.setText(text);
    }

    //设置title右边文字颜色
    public void setRightTitleColor(int color) {
        mTxtRightTitle.setTextColor(color);
    }



    //设置title右边点击事件
    public void setRightTitleClickListener(OnClickListener onClickListener){
        mTxtRightTitle.setOnClickListener(onClickListener);
        mTextRightImage.setOnClickListener(onClickListener);
        mTextRightSearch.setOnClickListener(onClickListener);
    }

    //设置title右边点击事件
    public void setRightSearchTitleClickListener(OnClickListener onClickListener){
        mTxtRightTitle.setOnClickListener(onClickListener);
        mTextRightSearch.setOnClickListener(onClickListener);
    }


}
