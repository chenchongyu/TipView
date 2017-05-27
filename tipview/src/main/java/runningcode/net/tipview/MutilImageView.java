package runningcode.net.tipview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Author： chenchongyu
 * Email: wochenchongyu@126.com
 * Date: 2017/5/22
 * Description:
 */

public class MutilImageView extends RelativeLayout {

    public static final int ORDER_POS = 0;//正序
    public static final int ORDER_REV = 1;//倒序
    private int order;

    public MutilImageView(Context context) {
        super(context, null);
    }

    public MutilImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOrder(int order) {
        this.order = order;
    }


    public void setImgs(int[] res, int width, int height, int keepOutWidth) {
        int margin = 0;
        int[] colors = new int[]{Color.YELLOW, Color.RED, Color.GRAY};
        for (int i = 0; i < res.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(res[i]);
            imageView.setBackgroundColor(colors[i]);
            LayoutParams params = new LayoutParams(width, height);
            if (order == ORDER_POS) {
                params.addRule(ALIGN_PARENT_LEFT);
                if (i > 0)
                    params.setMargins(margin, 0, 0, 0);
            } else {
                params.addRule(ALIGN_PARENT_RIGHT);
                if (i > 0)
                    params.setMargins(0, 0, margin, 0);
            }

            imageView.setLayoutParams(params);
            addView(imageView);
            margin = margin + width - keepOutWidth;
        }

    }
}
