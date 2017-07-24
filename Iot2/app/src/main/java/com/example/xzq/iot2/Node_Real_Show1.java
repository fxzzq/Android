package com.example.xzq.iot2;

/**
 * Created by xzq on 2017/6/26.
 */
        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Paint.Cap;
        import android.graphics.Paint.Style;
        import android.graphics.RectF;
        import android.graphics.SweepGradient;
        import android.util.AttributeSet;
        import android.util.Log;
        import android.view.View;

public class Node_Real_Show1 extends View {
    private Paint linePaint;
    private Paint circlePaint;
    private Paint textPaint;//刻度值
    private Paint centerTextPaint;//中间的温度值
    private Paint indicatorPaint;//指示器
    private RectF mRectF;

    private int defaultValue;
    int mCenter = 0;// 圆的半径
    int mRadius = 0;//半径
    private SweepGradient mSweepGradient,mSweepGradient2;//渲染梯度
    private int scanDegree = 0;//最高温度和最低温度扫描角度
    private int currentScanDegree=0;//当前温度扫过的角度
    private boolean isCanMove;
    private GetDegreeInterface mGetDegreeInterface;
    int minDegrees = 0;//最低温度
    int maxDegree=0;//最高温度
    int currentDegree=0;//当前温度

    public boolean flag=false;
    int screenWidth,screenHeight;

    public Node_Real_Show1(Context context) {
        super(context);
        Log.e("My", "1");
        initPaint();
    }

    public Node_Real_Show1(Context context, AttributeSet attrs) {
        super(context, attrs);
        screenWidth=MeasureUtil.getScreenWidth(context);
        screenHeight=MeasureUtil.getScreenHeight(context);
        Log.e("My", "2  "+screenWidth+"  "+screenHeight);
        initPaint();
    }

    private void initPaint() {
        linePaint = new Paint();
        linePaint.setColor(Color.CYAN);
        linePaint.setStyle(Style.FILL);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(1.0f);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30);

        centerTextPaint = new Paint();
        centerTextPaint.setColor(Color.BLUE);
        centerTextPaint.setTextAlign(Paint.Align.CENTER);
        centerTextPaint.setAntiAlias(true);
        centerTextPaint.setTextSize(40);/*xzq修改 原来为80*/

        circlePaint = new Paint();
        circlePaint.setColor(Color.WHITE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Style.STROKE);
        circlePaint.setStrokeCap(Cap.ROUND);//实现末端圆弧
        circlePaint.setStrokeWidth(30.0f);/*原来为60*/
/*小圆点指示器*/
        indicatorPaint=new Paint();
        indicatorPaint.setColor(0xFFF7F709);/*0xFFF7F709*/
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setStyle(Style.FILL);

        // 着色的共有270度，这里设置了12个颜色均分360度s
        int[] colors = { 0xFFD52B2B, 0xFFf70101, 0xFFFFFFFF, 0xFFFFFFFF,
                0xFF6AE2FD, 0xFF8CD0E5, 0xFFA3CBCB, 0xFFD1C299, 0xFFE5BD7D,
                0xFFAA5555, 0xFFBB4444, 0xFFC43C3C };
        /**/

        mCenter = screenWidth / 4;/*xzq修改，原来是一半 现在是4分之一*/
        mRadius = screenWidth/ 4 - 100;
        // 渐变色
        mSweepGradient = new SweepGradient(mCenter, mCenter, colors, null);
        // 构建圆的外切矩形
        mRectF = new RectF(mCenter - mRadius, mCenter - mRadius, mCenter
                + mRadius, mCenter + mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO 自动生成的方法存根
        super.onDraw(canvas);
        circlePaint.setShader(null);

        canvas.drawArc(mRectF, 90, 315, false, circlePaint);//宁广健：从第一象限x轴正半轴开始顺时针绘制，刻画空心线

        Log.e("scan--1-->", scanDegree+"");
        //重新赋值了，所以手指滑动没有显示
        scanDegree=(getMaxDegree()-getMinDegree())*3;
        // 设置画笔渐变色
        circlePaint.setShader(mSweepGradient);
        canvas.drawArc(mRectF, 90+(getMinDegree()-0)*9, (float) scanDegree, false, circlePaint);/*宁广健修改2：刻画动画*/

        int insideIndicator=mRadius-60;//离圆环的距离
        currentScanDegree=(getCurrentDegree()-0)*3;/*xzq修改3*/
        canvas.drawText((currentScanDegree/9+0)+"%", mCenter, mCenter, centerTextPaint);//全温度盘时，把currentScanDegree改为scanDegree，中间的值就会跟着改变了
        Log.e("指示角度----》", currentScanDegree+"");
        /*小圆点指示器的位置*/
        if (currentScanDegree<=90) {//第三象限/*xzq修改：从y轴负半轴为0度，开始顺时针旋转*/
            canvas.drawCircle((float)(mCenter-insideIndicator*Math.sin(Math.PI*(currentScanDegree+0)/180)),(float)(mCenter+insideIndicator*Math.cos(Math.PI*(currentScanDegree+0)/180)), 10, indicatorPaint);
        }else if(90<currentScanDegree&&currentScanDegree<=180) {//第二象限
            canvas.drawCircle((float)(mCenter-insideIndicator*Math.cos(Math.PI*(currentScanDegree-90)/180)),(float)(mCenter-insideIndicator*Math.sin(Math.PI*(currentScanDegree-90)/180)), 10, indicatorPaint);
        }else if (180<currentScanDegree&&currentScanDegree<=270) {//第一象限
            canvas.drawCircle((float)(mCenter+insideIndicator*Math.sin(Math.PI*(currentScanDegree-180)/180)),(float)(mCenter-insideIndicator*Math.cos(Math.PI*(currentScanDegree-180)/180)), 10, indicatorPaint);
        }else if(270<currentScanDegree&&currentScanDegree<=315){//第四象限
            canvas.drawCircle((float)(mCenter+insideIndicator*Math.cos(Math.PI*(currentScanDegree-270)/180)),(float)(mCenter+insideIndicator*Math.sin(Math.PI*(currentScanDegree-270)/180)), 10, indicatorPaint);
        }
/*刻度线*/
        for (int i = 0; i < 120; i++) {
            if (i <= 45 || i >= 60) {
                canvas.drawLine(mCenter, mCenter - mRadius - 15, mCenter,
                        mCenter - mRadius + 15, linePaint);
            }
            canvas.rotate(3, mCenter, mCenter);/*xzq修改：从Y轴正半轴为0刻度顺时针旋转*/
        }

        // x代表文字的x轴距离圆心x轴的距离 因为刚好是45度，所以文字x轴值和y值相等
        int x = 0;
        // 三角形的斜边
        int c = mRadius + 60 / 2 + 20;// 40代表这个字距离圆外边的距离/*xzq修改原来为40*/
        // 因为是每45度写一次文字，故根据到圆心的位置，利用三角形的公式，可以算出文字的坐标值
        x = (int) Math.sqrt((c * c / 2));

//		if (getMinDegree() == 0) {
//			minDegrees = 10;
//		}/*xzq修改4*/
        canvas.drawText("0", mCenter, mCenter +c,
                textPaint);
        canvas.drawText("5", mCenter - x, mCenter + x, textPaint);
        canvas.drawText("10", mCenter - c, mCenter,
                textPaint);
        canvas.drawText("15", mCenter - x, mCenter - x,
                textPaint);
        canvas.drawText("20", mCenter, mCenter - c,
                textPaint);
        canvas.drawText( "25", mCenter + x, mCenter - x,
                textPaint);
        canvas.drawText( "30", mCenter + c, mCenter,
                textPaint);
        canvas.drawText( "35", mCenter + x, mCenter + x,
                textPaint);

    }


    /**
     * 设置最低温度
     *
     * @param degree
     */
    public void setMinDegree(int degree) {
        this.minDegrees = degree;
    }

    public int getMinDegree() {
        return minDegrees;
    }

    /**
     * 获取当前温度值接口
     *
     */
    public interface GetDegreeInterface {
        void getActualDegree(int degree);
    }

    public void setGetDegreeInterface(GetDegreeInterface arg) {
        this.mGetDegreeInterface = arg;
    }

    /**
     * 设置最高温度值
     */
    public  void setMaxDegree( int degree){
        this.maxDegree=degree;
    }

    public int getMaxDegree() {
        return maxDegree;
    }

    /**
     * 设置当前温度
     * @param currentDegree
     */
    public void setCurrentDegree(int currentDegree) {
        this.currentDegree = currentDegree;
    }

    public int getCurrentDegree() {
        return currentDegree;
    }

    // 因为自定义的空间的高度设置的是wrap_content,所以我们必须要重写onMeasure方法去测量高度，否则布局界面看不到
    // 其他控件(被覆盖)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
        //setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 测量宽度
     *
     * @param widthMeasureSpec
     * @return
     */
    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        // 默认宽高;
        defaultValue=screenWidth;

        switch (mode) {
            case MeasureSpec.AT_MOST:
                // 最大值模式 当控件的layout_Width或layout_height属性指定为wrap_content时
                Log.e("cmos---->", "size " + size + " screenWidth " + screenWidth);
                size = Math.min(defaultValue, size);
                break;
            case MeasureSpec.EXACTLY:
                // 精确值模式
                // 当控件的android:layout_width=”100dp”或android:layout_height=”match_parent”时

                break;
            default:
                size = defaultValue;
                break;
        }
        defaultValue = size;
        return size;
    }

    /**
     * 测量高度
     *
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        switch (mode) {
            case MeasureSpec.AT_MOST:
                // 最大值模式 当控件的layout_Width或layout_height属性指定为wrap_content时
                Log.e("cmos---->", "size " + size + " screenHeight " + screenHeight);
                size = Math.min(screenHeight/2, size);
                break;
            case MeasureSpec.EXACTLY:
                // 精确值模式
                // 当控件的android:layout_width=”100dp”或android:layout_height=”match_parent”时

                break;
            default:
                size = defaultValue;
                break;
        }
        return size;
    }

}
