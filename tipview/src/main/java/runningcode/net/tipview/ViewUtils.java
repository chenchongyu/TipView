package runningcode.net.tipview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Author： chenchongyu
 * Email: wochenchongyu@126.com
 * Date: 2017/5/12
 * Description:
 */

public class ViewUtils {
    public static Point getScreenWH(Context context) {
        Point point = new Point();
        WindowManager wm = ((Activity) context).getWindowManager();
        wm.getDefaultDisplay().getSize(point);

        return point;
    }

    private static int dp2px(Context context, float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
