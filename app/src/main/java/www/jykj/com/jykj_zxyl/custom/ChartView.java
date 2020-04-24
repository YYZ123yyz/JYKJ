package www.jykj.com.jykj_zxyl.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ChartView extends View {

    public int XPoint=60; // 原点的X坐标
    public int YPoint;// 原点的Y坐标260
    public int XScale; // X的刻度长度55
    public int YScale; // Y的刻度长度40
    public int XLength; // X轴的长度380
    public int YLength; // Y轴的长度240
    private int top = 10;//上边缘距离
    private int left = 40;//左边缘距离
    private int right = 10;//右边缘距离
    private int bottom = 80;//下边缘距离
    private String[] YLabel;//y轴的刻度值
    private String[] XLabel;//X轴的刻度值
    private String[][] dateStr;

    public ChartView(Context context) {
        super(context);
    }

    public void setDate(String[] YLabel, String[] XLabel, String[][] dates) {
        this.YLabel = YLabel;
        this.XLabel = XLabel;
        dateStr = dates;
        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        YLength = getHeight() - bottom - top;//整个Y轴的长度
        XLength = getWidth() - right - left;
        YPoint = getHeight() - bottom;
        XScale = (XLength / XLabel.length);//--x轴的刻度平均长度
        YScale = (YLength / YLabel.length);//Y--Y轴的刻度平均长度

        Paint paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setColor(Color.GRAY);
        paint.setTextSize(20);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        //画竖线
        canvas.drawLine(XPoint, YPoint, XPoint, top, paint);
        canvas.drawLine(XPoint - 5, top + 10, XPoint, top, paint);
        canvas.drawLine(XPoint + 5, top + 10, XPoint, top, paint);
        for (int i = 1; i * YScale <= YLength; i++) {//画横刻度
            canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i * YScale, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(YLabel[i - 1], XPoint - 30, YPoint - i * YScale + 7, paint);
            paint.setColor(Color.GRAY);
        }
        //画横线
        canvas.drawLine(XPoint, YPoint, XLength, YPoint, paint);
        canvas.drawLine(XLength - 10, YPoint + 5, XLength, YPoint, paint);
        canvas.drawLine(XLength - 10, YPoint - 5, XLength, YPoint, paint);

        for (int i = 1; i * XScale <= XLength; i++) {//画竖刻度
            canvas.drawLine(XPoint + (i - 1) * XScale, YPoint, XPoint + (i - 1) * XScale, YPoint - 5, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(XLabel[i - 1], XPoint + (i - 1) * XScale - 3, YPoint + 30, paint);
            paint.setColor(Color.GRAY);
        }
        //画数据图
        drawData(paint, canvas, dateStr);
    }

    private void drawData(Paint paint, Canvas canvas, String[][] dates) {

        for (int i = 0; i < dates.length; i++) {
            paint.setColor(Color.BLUE);
            for (int j = 0; j < dates[i].length; j++) {
                canvas.drawCircle((float) XPoint + (j ) * XScale, (float) calcuLations(dates[i][j]), 5, paint);
                paint.setColor(Color.BLACK);
//                canvas.drawText("(" + dates[i][j] + ")", (float) XPoint + (j + 1) * XScale - 5, (float) calcuLations(dates[i][j]) - 20, paint);
                paint.setColor(Color.BLUE);
                if (j + 1 < dates[i].length) {
                    canvas.drawLine((float) XPoint + (j ) * XScale, (float) calcuLations(dates[i][j])
                            , (float) XPoint + (j + 1) * XScale, (float) calcuLations(dates[i][j + 1]), paint);
                }
            }
        }

    }

    private int calcuLations(String y0) //计算y轴坐标
    {
        double y;
        try {
            y = Double.parseDouble(y0);
        } catch (Exception e) {
            return -100;
        }
        return (int) (YPoint - YPoint * (y / Double.parseDouble(YLabel[YLabel.length - 1])));
    }


}
