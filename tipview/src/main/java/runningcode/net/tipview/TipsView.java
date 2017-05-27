package runningcode.net.tipview;


import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static runningcode.net.tipview.TriangleView.Direction.DOWN;
import static runningcode.net.tipview.TriangleView.Direction.LEFT;
import static runningcode.net.tipview.TriangleView.Direction.RIGHT;
import static runningcode.net.tipview.TriangleView.Direction.UP;

/**
 * Author： chenchongyu
 * Email: wochenchongyu@126.com
 * Date: 2017/5/11
 * Description:
 */
public class TipsView {
    private final static int TRIANGLE_HEIGHT = 10;
    private final static int TRIANGLE_WIDTH = 6;
    private final static double DEFAULT_WIDTH_RATE = 0.8;
    private Builder bulder;
    private PopupWindow mPopupWindow;
    private TriangleView mTriangleView;
    private FrameLayout contentView;
    private ViewGroup contentBox;

    private int contentViewHeight;//popwindow内容高度
    private int contentViewWidth;//popwindow内容宽度
    private int popWindowY;//popwindow y坐标
    private int popWindowX;
    private int[] locations;
    private TextView vContent;
    private TextView vTitle;
    private ImageView vIcon;

    //可配置参数
    /**
     * popwindow宽度和锚点宽度比例，默认为0.8
     */
    private double widthRate;
    private Context context;
    /**
     * 设置宽高，默认是WRAP_CONTENT
     */
    private int width, height;
    private int radius;
    private TriangleView.Direction mDirection;
    /**
     * 箭头位置策略，目前有两种；
     * 1、默认选项，auto，自适应
     * 2、锚点策略，箭头跟随锚点位置
     */
    private TriangleView.PosStrategy mStrategy;
    private int bgColor;
    /**
     * 默认没有边框，设置颜色之后才生效
     */
    private float borderWidth;
    private float alpha = 1f;
    private RelativeLayout customView;
    private long dismissTime;
    private Margin margin = new Margin(0, 0, 0, 0);

    public TipsView(Builder builder) {
        this.context = builder.context;
        mDirection = builder.mDirection;
        widthRate = DEFAULT_WIDTH_RATE;
        mStrategy = TriangleView.PosStrategy.AUTO_CENTER;
        this.customView = builder.resLayout;
        init();


        this.borderWidth = builder.borderWidth;
        this.width = builder.width;
        this.height = builder.height;
        this.radius = builder.radius;
        this.margin = builder.margin;
        this.dismissTime = builder.mills;

        if (builder.padding != null) {
            setPading(builder.padding);
        }

        if (!TextUtils.isEmpty(builder.title)) {
            setTitle(builder.title);
        }

        if (builder.icon != 0) {
            setIcon(builder.icon);
        }

        if (builder.titleColor != 0) {
            vTitle.setTextColor(builder.titleColor);
        }

        if (!TextUtils.isEmpty(builder.content)) {
            setContent(builder.content);
        }

        if (builder.color != 0) {
            setColor(builder.color);
        }

        if (builder.alpha < 1) {
            setAlpha(builder.alpha);
        }
        if (builder.contentColor != 0) {
            vContent.setTextColor(builder.contentColor);
        }

        if (builder.mStrategy != null)
            this.mStrategy = builder.mStrategy;

        if (builder.widthRate != 0.0) {
            setWidthRate(builder.widthRate);
        }

        if (builder.borderColor != 0) {
            setBorderColor(builder.borderColor);
        }

        if (builder.onClickListener != null) {
            setOnClickListener(builder.onClickListener);
        }


    }

    private void setPading(Padding padding) {
        contentBox.setPadding(padding.left, padding.top, padding.right, padding.bottom);
    }

    private TipsView(Context context, TriangleView.Direction direction) {

    }

    private void init() {
        mTriangleView = new TriangleView(context);
        mTriangleView.setDirection(mDirection);

        mPopupWindow = new PopupWindow(context);

//        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(null);
        setContentView();

    }

    private void setContentView() {

        contentView = new FrameLayout(context);
        contentView.setLayoutParams(new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        if (this.customView != null) {
            contentBox = customView;
        } else {
            contentBox = new RelativeLayout(context);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            params.setMargins((int) borderWidth, (int) borderWidth, (int) borderWidth, (int) borderWidth);
            contentBox.setLayoutParams(params);
            contentBox.setPadding((int) borderWidth, (int) borderWidth, (int) borderWidth, (int) borderWidth);

            vTitle = new TextView(context);
            vTitle.setId(android.R.id.title);

            vContent = new TextView(context);

            vIcon = new ImageView(context);
            vIcon.setId(android.R.id.icon);

            RelativeLayout.LayoutParams imgLp = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            imgLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            imgLp.addRule(RelativeLayout.CENTER_VERTICAL);
//        imgLp.rightMargin = 20;
            vIcon.setLayoutParams(imgLp);
            contentBox.addView(vIcon);

            RelativeLayout.LayoutParams titleLp = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            titleLp.addRule(RelativeLayout.RIGHT_OF, vIcon.getId());
            vTitle.setLayoutParams(titleLp);
            contentBox.addView(vTitle);

            RelativeLayout.LayoutParams contentLp = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            contentLp.addRule(RelativeLayout.RIGHT_OF, vIcon.getId());
            contentLp.addRule(RelativeLayout.BELOW, vTitle.getId());
            vContent.setLayoutParams(contentLp);

            contentBox.addView(vContent);
        }

        contentView.addView(contentBox);
        mPopupWindow.setContentView(contentView);

    }

    public void setIcon(int icon) {
        vIcon.setImageResource(icon);
    }

    public void setContent(String content) {
        vContent.setText(content);
    }

    public void setTitle(String title) {
        vTitle.setText(title);
    }

    public void setColor(int color) {
        bgColor = color;
        contentBox.setBackgroundColor(bgColor);

        mTriangleView.setColor(bgColor);
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        contentBox.setAlpha((int) (255 * alpha));
        mTriangleView.setAreaAlpha((int) (255 * alpha));
//        alphaView.setAlpha(alpha);
    }

    public void setBorderColor(int borderColor) {
        int color = borderColor;
        mTriangleView.setBorderColor(color);
        mTriangleView.setBorderWidth(borderWidth);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setStroke((int) borderWidth, color);
//        contentBox0.setBackgroundDrawable(drawable);
        if (bgColor != 0)
            drawable.setColor(bgColor);
        if (alpha < 1) {
            drawable.setAlpha((int) (255 * alpha));
//            drawable.set
        }
        contentBox.setBackgroundDrawable(drawable);

    }

    public void setWidthRate(double rate) {
        this.widthRate = rate;
    }

    public void setOnClickListener(final OnTipClickListener onClickListener) {
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(TipsView.this);
            }
        });
    }

    public void show(View anchor) {

        measurePopWindow(anchor);
        assemble(anchor);

        mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
        mPopupWindow.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.NO_GRAVITY, popWindowX, popWindowY);

        if (dismissTime > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPopupWindow.dismiss();
                }
            }, dismissTime);
        }
    }


    public void dismiss() {
        mPopupWindow.dismiss();
    }

    private void assemble(View anchor) {

        switch (mDirection) {
            case UP:
                setVerticalUpLayout(anchor);
                break;
            case DOWN:
                setVerticalDownLayout(anchor);
                break;
            case LEFT:
                setHorizontalLeftLayout(anchor);
                break;
            case RIGHT:
                setHorizontalRightLayout(anchor);
                break;
        }
    }

    private int getXOffset(View anchor) {
        int anchorWidth = anchor.getMeasuredWidth();
        if (mDirection == LEFT) {
            Point point = ViewUtils.getScreenWH(context);
            if (point.x - anchorWidth < contentViewHeight) {
                //如果pop和anchor的宽度超过屏幕，则设置pop的宽度为point.x - anchorwidth-三角号宽度
                mPopupWindow.setWidth(point.x - anchorWidth - dp2px(TRIANGLE_HEIGHT));
            }
            return locations[0] + anchorWidth + margin.left;
        } else if (mDirection == RIGHT) {
            return (int) (locations[0] - contentViewWidth - dp2px(TRIANGLE_WIDTH) - margin.right + borderWidth);
        } else {
            //上下方向 锚点x-10% （rate=0.8为例）
            if (width == MATCH_PARENT) {
                //让整个弹窗居中
                return (int) (ViewUtils.getScreenWH(context).x * (1 - widthRate) / 2);
            } else
                return locations[0] + (int) (anchorWidth * (1 - widthRate) / 2);
        }
    }

    boolean measure = false;

    private int getYOffset(final View anchor) {

        if (mDirection == DOWN) {
            //当内容为多行的时候，contentView.measure测量的高度不准确，需要减去一个行高；
            if (!measure) {
                contentBox.post(new Runnable() {
                    @Override
                    public void run() {
                        int contentHeight = contentBox.getMeasuredHeight();//getTextViewHeight(vTitle) + getTextViewHeight(vContent);
//                        Log.i("tips","conviewHeignt:"+contentHeight+"  con:"+contentBox.getMeasuredHeight());

                        popWindowY = (int) (locations[1] - contentHeight - dp2px(TRIANGLE_WIDTH) - borderWidth - margin.bottom);

                        FrameLayout.LayoutParams posParams = (FrameLayout.LayoutParams) mTriangleView.getLayoutParams();

                        if (mStrategy == TriangleView.PosStrategy.ANCHOR_CENTER)
                            posParams.setMargins(locations[0] + (anchor.getMeasuredWidth() - dp2px(TRIANGLE_HEIGHT)) / 2 - popWindowX, contentHeight, 0, 0);
                        else {
                            posParams.gravity = Gravity.CENTER_HORIZONTAL;
                            posParams.setMargins(0, contentHeight, 0, 0);
                        }

                        mPopupWindow.dismiss();
                        mPopupWindow.showAtLocation(((Activity) context).getWindow().getDecorView(),
                                Gravity.NO_GRAVITY, popWindowX, popWindowY);
                    }
                });
            }
            return -anchor.getMeasuredHeight() - contentViewHeight - dp2px(TRIANGLE_HEIGHT);
        } else if (mDirection == UP) {
            return locations[1] + anchor.getMeasuredHeight() + margin.top;
        } else {
            return locations[1] - (contentViewHeight / 2 - anchor.getMeasuredHeight() / 2);
        }

    }

    private int getTextViewHeight(TextView textView) {
        return textView.getLineCount() * textView.getLineHeight();
    }

    //测量、设置popwindow和内容区的宽高，测量popwindow的xy坐标
    private void measurePopWindow(View anchor) {
        if (height != 0)
            mPopupWindow.setHeight(height);
        else
            mPopupWindow.setHeight(WRAP_CONTENT);

        if (width == MATCH_PARENT) {
            mPopupWindow.setWidth((int) (ViewUtils.getScreenWH(context).x * widthRate));
        } else if (width > 0)
            mPopupWindow.setWidth(width);
        else {
            mPopupWindow.setWidth(WRAP_CONTENT);

            if (mDirection == UP || mDirection == DOWN)
                mPopupWindow.setWidth((int) (anchor.getWidth() * widthRate));
        }


        locations = new int[2];
        anchor.getLocationInWindow(locations);

        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        contentView.measure(spec, spec);
        //计算popwindow的高度
        contentViewHeight = contentView.getMeasuredHeight();

        contentViewWidth = contentView.getMeasuredWidth();

        popWindowY = getYOffset(anchor);
        popWindowX = getXOffset(anchor);
    }


    /**
     * 箭头向上布局
     */
    private void setVerticalUpLayout(View view) {
        mDirection = UP;
        FrameLayout.LayoutParams posParams = new FrameLayout.LayoutParams(dp2px(TRIANGLE_HEIGHT), dp2px(TRIANGLE_WIDTH));
        //因为当match parent的时候，整个布局向左偏移了popWindowX，所以这个地方如果在margin的时候会多，要减去
        if (mStrategy == TriangleView.PosStrategy.ANCHOR_CENTER)
            posParams.setMargins(view.getLeft() + (view.getWidth() - dp2px(TRIANGLE_HEIGHT)) / 2 - popWindowX, 0, 0, 0);
        else
            posParams.gravity = Gravity.CENTER_HORIZONTAL;

        if (contentBox != null) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) contentBox.getLayoutParams();
            params.setMargins(0, (int) (dp2px(TRIANGLE_WIDTH) - borderWidth), 0, 0);
        }

        contentView.addView(mTriangleView, posParams);
    }

    /**
     * 箭头向下布局
     */
    private void setVerticalDownLayout(View view) {
        mDirection = DOWN;
//        contentView.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams posParams = new FrameLayout.LayoutParams(dp2px(TRIANGLE_HEIGHT), dp2px(TRIANGLE_WIDTH));

        if (mStrategy == TriangleView.PosStrategy.ANCHOR_CENTER)
            posParams.setMargins(view.getLeft() + (view.getWidth() - dp2px(TRIANGLE_WIDTH)) / 2 - popWindowX, contentBox.getMeasuredHeight(), 0, 0);
        else
            posParams.gravity = Gravity.CENTER_HORIZONTAL;
//            posParams.setMargins(contentViewWidth/2,contentBox.getMeasuredHeight(),0,0);

        if (contentBox != null) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) contentBox.getLayoutParams();
            params.setMargins(0, (int) borderWidth, 0, 0);
        }

        contentView.addView(mTriangleView, posParams);

    }

    /**
     * 箭头向左布局
     */
    private void setHorizontalLeftLayout(View view) {
        mDirection = LEFT;

//        contentView.setOrientation(LinearLayout.HORIZONTAL);
        FrameLayout.LayoutParams posParams = new FrameLayout.LayoutParams(dp2px(TRIANGLE_WIDTH), dp2px(TRIANGLE_HEIGHT));
        //箭头Y坐标 = view的y+（view的高度-箭头高度）/2
        //marginTop = 箭头Y-popwindow y

        if (mStrategy == TriangleView.PosStrategy.ANCHOR_CENTER) {
            int top = locations[1] + (view.getMeasuredHeight() - dp2px(TRIANGLE_HEIGHT)) / 2;
            posParams.setMargins(0, top - popWindowY, 0, 0);
        } else
            posParams.gravity = Gravity.CENTER_VERTICAL;

        if (contentBox != null) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) contentBox.getLayoutParams();
            params.setMargins((int) (dp2px(TRIANGLE_WIDTH) - borderWidth), 0, 0, 0);
        }

        contentView.addView(mTriangleView, posParams);
    }

    /**
     * 箭头向右布局
     */
    private void setHorizontalRightLayout(View view) {
        mDirection = RIGHT;

        FrameLayout.LayoutParams posParams = new FrameLayout.LayoutParams(dp2px(TRIANGLE_WIDTH), dp2px(TRIANGLE_HEIGHT));

        if (mStrategy == TriangleView.PosStrategy.ANCHOR_CENTER) {
            int top = locations[1] + (view.getMeasuredHeight() - dp2px(TRIANGLE_HEIGHT)) / 2;
            posParams.setMargins(0, top - popWindowY, 0, 0);
        } else {
            posParams.gravity = Gravity.CENTER_VERTICAL;
            posParams.setMargins((int) (contentViewWidth - borderWidth), 0, 0, 0);
        }

        if (contentBox != null) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) contentBox.getLayoutParams();
            params.setMargins((int) (-borderWidth), 0, 0, 0);
        }

        contentView.addView(mTriangleView, posParams);
    }


    public static class Builder {
        private double widthRate;
        private Context context;
        private TriangleView.Direction mDirection;
        private OnTipClickListener onClickListener;
        private TriangleView.PosStrategy mStrategy;
        private String title, content;
        private int color, titleColor, contentColor, icon, borderColor;
        private int width, height;
        private float borderWidth = 1f, alpha = 1f;
        private int radius;
        private Padding padding;
        public Margin margin = new Margin(0, 0, 0, 0);
        private RelativeLayout resLayout;
        private long mills;

        public Builder with(Context context) {
            this.context = context;
            return this;
        }

        public Builder customView(RelativeLayout resLayout) {
            this.resLayout = resLayout;
            return this;
        }

        public Builder position(TriangleView.PosStrategy mStrategy) {
            this.mStrategy = mStrategy;
            return this;
        }

        public Builder padding(Padding padding) {
            this.padding = padding;
            return this;
        }

        public Builder margin(Margin margin) {
            this.margin = margin;
            return this;
        }

        public Builder dismissDelay(long s) {
            this.mills = s;
            return this;
        }

        public Builder borderWidth(float width) {
            this.borderWidth = width;
            return this;
        }

        public Builder alpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        public Builder rate(double rate) {
            this.widthRate = rate;
            return this;
        }

        public Builder radius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder icon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Builder borderColor(int color) {
            this.borderColor = color;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder direction(TriangleView.Direction direction) {
            this.mDirection = direction;
            return this;
        }

        public Builder setOnClickListener(OnTipClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder setContentColor(int contentColor) {
            this.contentColor = contentColor;
            return this;
        }

        public TipsView build() {
            return new TipsView(this);
        }
    }

    public static class Padding {
        public int left, right, top, bottom;

        public Padding() {
        }

        public Padding(int left, int top, int right, int bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }
    }

    public static class Margin {
        public int left, right, top, bottom;

        public Margin(int left, int top, int right, int bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }
    }

    public interface OnTipClickListener {
        void onClick(TipsView view);
    }

    private int dp2px(float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }


}
