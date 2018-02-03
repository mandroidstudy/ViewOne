package android.coolweather.com.viewpreoject.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.coolweather.com.viewpreoject.R;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Mao on 2018/2/3.
 */

public class MyView extends View implements View.OnClickListener{
    private String text;
    private int textSize;
    private int textColor;
    private Paint mPaint;
    private Rect mRect;
    //在代码中使用new关键字创建View会使用
    public MyView(Context context) {
        super(context);
        Log.d("MVE","MyView(Context context）被调用");
    }
    /*
    * 在布局文件中声明View会调用,如果想要在XML布局文件中使用该View就必须实现这个构造方法
    * @param context 上下文
    * @param attrs 保存了View的自定义属性的集合，在xml中为View设置的属性可以通过它获取
    */
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("MVE","MyView(Context context, AttributeSet attrs)被调用");
        mRect=new Rect();
        mPaint=new Paint();
        //获取TypedArray对象，其包含了我们自定义的View属性
        TypedArray typedArray=context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyView,0,0);
        int count=typedArray.getIndexCount();
        for (int i=0;i<count;i++){
            int attr=typedArray.getIndex(i);
            switch (attr){
                case R.styleable.MyView_text:
                    text=typedArray.getString(attr);
                    break;
                case R.styleable.MyView_textSize:
                    textSize=typedArray.getDimensionPixelSize(attr,
                            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,20,getResources().getDisplayMetrics())
                    );
                    break;
                case R.styleable.MyView_textColor:
                    textColor=typedArray.getColor(attr,Color.BLACK);
                    break;
            }
        }
        //TypedArray 是一个公用的资源用完后要释放该实例，以便可以被其他模块复用
        typedArray.recycle();
    }
    /*与第二个构造不同在于可以指定默认样式
     *@param defStyleAttr 是当前theme中包含的指向View的样式资源的属性(0代表此参数无效)
     *
     */
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("MVE","MyView(Context context, AttributeSet attrs, int defStyleAttr)被调用");
    }

    /*
    * 测量
    * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else {
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(text,0,text.length(),mRect);
            int textWidth=mRect.width();
            width=(int)(getPaddingLeft()+textWidth+getPaddingRight());
        }
        if (heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }
        else{
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(text,0,text.length(),mRect);
            int textHeight=mRect.height();
            height=(int)(getPaddingTop()+textHeight+getPaddingBottom());
        }
        setMeasuredDimension(width,height);
    }

    /*
     *绘制
    */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);

        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        mPaint.getTextBounds(text,0,text.length(),mRect);
        int textHeight=mRect.height();
        int textWidth=mRect.width();
        canvas.drawText(text,getWidth()/2-textWidth/2,getHeight()/2,mPaint);
    }

    @Override
    public void onClick(View v) {

    }
}
