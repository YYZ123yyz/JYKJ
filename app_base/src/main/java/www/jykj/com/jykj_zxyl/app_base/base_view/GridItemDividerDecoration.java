package www.jykj.com.jykj_zxyl.app_base.base_view;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-24 11:50
 */
public class GridItemDividerDecoration extends RecyclerView.ItemDecoration{
    private  int mRedundant;
    public static  final  int VERTICAL =1;
    public static final int Horizon =0;
    private Drawable mDivider;
    private int mThickness;
    private int mOratation;


    public GridItemDividerDecoration(Drawable divider, int orantation) {
        this.mDivider =divider;
        this.mOratation =orantation;
        setThickness();
    }

    public GridItemDividerDecoration(Drawable divider, int orantation,int thick) {//亲测横向纵向都可以的
        this.mDivider =divider;
        this.mOratation =orantation;
        setThickness(thick);
    }

    private void setThickness(){
        if (mOratation==VERTICAL){
            mThickness= mDivider.getIntrinsicHeight();
        }else if (mOratation==Horizon){
            mThickness=mDivider.getIntrinsicWidth();
        }
    }

    private void setThickness(int thickness){//用于自己设置分割线宽度
        mThickness = thickness;
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int spancount= ((GridLayoutManager)parent.getLayoutManager()).getSpanCount();
        mRedundant = parent.getChildCount()%spancount;
        mRedundant = mRedundant==0?spancount:mRedundant;//最后一排的item个数
        final int underlineNum = parent.getChildCount()-mRedundant;//下划线的child的个数
        final Drawable divider = mDivider;
        final int thickness = mThickness;
        for (int i = 0;i<underlineNum;i++) {//给非最后一排的item画边边
            View child = parent.getChildAt(i);
            if (mOratation == VERTICAL) {
                divider.setBounds(child.getLeft(), child.getBottom(), child.getRight(), child.getBottom() + thickness);
            }
            if (mOratation == Horizon) {
                divider.setBounds(child.getRight(),child.getTop(), child.getRight() + thickness, child.getBottom());
            }
            divider.draw(c);
        }

    }


}
