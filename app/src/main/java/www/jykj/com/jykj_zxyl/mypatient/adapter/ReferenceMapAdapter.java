package www.jykj.com.jykj_zxyl.mypatient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.constant.RegexConstants;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.custom.RefrecenmapBean;

public class ReferenceMapAdapter extends BaseQuickAdapter<RefrecenmapBean, BaseViewHolder> {
    private Context mContext;

    public ReferenceMapAdapter(int layoutResId, @Nullable List<RefrecenmapBean> data, Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void convert(BaseViewHolder helper, RefrecenmapBean item) {
        EditText etHigh = helper.getView(R.id.et_high);
        EditText etLow = helper.getView(R.id.et_low);
        EditText etThre = helper.getView(R.id.et_thre);


        helper.addOnClickListener(R.id.et_high);
        helper.addOnClickListener(R.id.et_low);
        helper.addOnClickListener(R.id.et_thre);

        etHigh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 0) {
                    myListen.onHighInput(helper.getPosition(), "0");
                } else {
                    if (Double.parseDouble(s.toString()) > 300) {
                        ToastUtils.showShort("低压不能大于300");
                    } else {
                        if (myListen!=null) {
                            myListen.onHighInput(helper.getPosition(), s.toString());
                        }
                    }
                }
            }
        });
        etHigh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    KeyboardUtils.showSoftInput();
                    LogUtils.e("有焦点了  " + etHigh.getText().toString().length());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            etHigh.setSelection(etHigh.getText().toString().length());
                        }
                    }, 50);


                } else {

                }
            }
        });
        etHigh.setOnTouchListener(new View.OnTouchListener() {
            int flag = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flag++;
                if (flag == 2) {
                    flag = 0;

                    myListen.onHighClick(helper.getPosition());
                }
                return false;
            }
        });


        etLow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 0) {
                    myListen.onLowInput(helper.getPosition(), "0");
                } else {
                    if (Double.parseDouble(s.toString()) > 200) {
                        ToastUtils.showShort("低压不能大于200");
                    } else {
                        if (myListen != null) {
                            myListen.onLowInput(helper.getPosition(), s.toString());
                        }
                    }
                }


            }
        });
        etLow.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    LogUtils.e("有焦点了  " + etLow.getText().toString().length());
                    KeyboardUtils.showSoftInput();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            etLow.setSelection(etLow.getText().toString().length());
                        }
                    }, 50);


                } else {

                }
            }
        });
        etLow.setOnTouchListener(new View.OnTouchListener() {
            int flag = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flag++;
                if (flag == 2) {
                    flag = 0;
                    myListen.onLowClick(helper.getPosition());
                }
                return false;
            }
        });


        etThre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 0) {
                    myListen.onThreInput(helper.getPosition(), "0");
                } else {
                    if (Double.parseDouble(s.toString()) > 50) {
                        ToastUtils.showShort("阀值不能大于50");
                    } else {
                        if (myListen != null) {
                            myListen.onThreInput(helper.getPosition(), s.toString());
                        }
                    }
                }
            }
        });
        etThre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    KeyboardUtils.showSoftInput();
                    LogUtils.e("有焦点了  " + etThre.getText().toString().length());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            etThre.setSelection(etThre.getText().toString().length());
                        }
                    }, 50);


                } else {

                }
            }
        });
        etThre.setOnTouchListener(new View.OnTouchListener() {
            int flag = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flag++;
                if (flag == 2) {
                    flag = 0;
                    myListen.onThreClick(helper.getPosition());
                }
                return false;
            }
        });


        helper.setIsRecyclable(false);
        if (item.isClick()) {
            if (item.getClickNum() == 0 || item.getClickNum() == 1) {
                etHigh.requestFocus();
            } else if (item.getClickNum() == 2) {
                etLow.requestFocus();
            } else {
                etThre.requestFocus();
            }

            etHigh.setBackground(mContext.getResources().getDrawable(R.drawable.bg_round_999999_2));
            etLow.setBackground(mContext.getResources().getDrawable(R.drawable.bg_round_999999_2));
            etThre.setBackground(mContext.getResources().getDrawable(R.drawable.bg_round_999999_2));

        } else {
            etHigh.setBackground(null);
            etLow.setBackground(null);
            etThre.setBackground(null);
        }

        helper.setText(R.id.tv_age, item.getAgeStart() + "-" + item.getAgeEnd())
                .setText(R.id.et_high, String.valueOf(item.getHighNum()))
                .setText(R.id.et_low, String.valueOf(item.getLowNum()))
                .setText(R.id.et_thre, String.valueOf(item.getGradeFloatingValue()));
    }

    private onEditClick myListen;

    public void setOnEditClick(onEditClick listen) {
        this.myListen = listen;
    }

    public interface onEditClick {
        void onHighClick(int posit);

        void onLowClick(int posit);

        void onThreClick(int posit);

        void onHighInput(int pos, String high);

        void onLowInput(int pos, String low);

        void onThreInput(int pos, String thre);
    }

}
