package runningcode.net.tipsview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import runningcode.net.tipview.MutilImageView;
import runningcode.net.tipview.TipsView;
import runningcode.net.tipview.TriangleView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.v_btn).setOnClickListener(this);
        findViewById(R.id.v_btn2).setOnClickListener(this);
        findViewById(R.id.v_btn3).setOnClickListener(this);
        findViewById(R.id.v_btn4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v_btn:
                showUpTips(v);
                break;
            case R.id.v_btn2:
                showDownTips(v);
                break;
            case R.id.v_btn3:
                showLeftTips(v);
                break;
            case R.id.v_btn4:
                showRightTips(v);
                break;
        }
    }

    private void showUpTips(View v) {
        boolean cus = true;
        if (cus)
            withCustomView(v);
        else {
            new TipsView.Builder()
                    .with(this)
                    .color(Color.parseColor("#FFF9F6"))
                    .title("箭头向上 build")
                    .padding(new TipsView.Padding(0, 10, 0, 0))
                    .setTitleColor(Color.parseColor("#000000"))
                    .setContentColor(Color.parseColor("#666666"))
                    .borderColor(Color.parseColor("#FC9153"))
                    .borderWidth(1f)
                    .radius(5)
                    .icon(android.R.drawable.stat_notify_error)
                    .position(TriangleView.PosStrategy.ANCHOR_CENTER)
                    .width(WindowManager.LayoutParams.MATCH_PARENT)
                    .rate(0.95)
                    .content(((TextView) v).getText().toString())
                    .direction(TriangleView.Direction.UP)
                    .setOnClickListener(new TipsView.OnTipClickListener() {
                        @Override
                        public void onClick(TipsView view) {
                            view.dismiss();
                        }
                    })
                    .build()
                    .show(v);
        }

//        startActivity(new Intent(this,S2Activity.class));
    }

    private void withCustomView(View v) {
        RelativeLayout customView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.layout_custom_pop, null);
        MutilImageView mutilImageView = (MutilImageView) customView.findViewById(R.id.bts_tip_imgs);
        mutilImageView.setOrder(MutilImageView.ORDER_REV);
        int[] data = new int[]{android.R.drawable.ic_input_add,
                android.R.drawable.ic_media_ff,
                android.R.drawable.ic_delete
        };
        mutilImageView.setImgs(data, 50, 50, 15);

        new TipsView.Builder()
                .with(this)
                .customView(customView)
                .position(TriangleView.PosStrategy.ANCHOR_CENTER)
                .width(WindowManager.LayoutParams.MATCH_PARENT)
                .direction(TriangleView.Direction.UP)
                .padding(new TipsView.Padding(20, 20, 0, 0))
                .margin(new TipsView.Margin(0, 10, 0, 0))
                .color(Color.parseColor("#FFF9F6"))
                .borderColor(Color.parseColor("#FC9153"))
                .borderWidth(1f)
                .radius(5)
                .setOnClickListener(new TipsView.OnTipClickListener() {
                    @Override
                    public void onClick(TipsView view) {
                        view.dismiss();
                    }
                })
                .build()
                .show(v);
    }

    private void showDownTips(View v) {
        new TipsView.Builder()
                .with(this)
                .position(TriangleView.PosStrategy.ANCHOR_CENTER)
                .width(WindowManager.LayoutParams.MATCH_PARENT)
                .padding(new TipsView.Padding(20, 20, 0, 0))
                .margin(new TipsView.Margin(0, 0, 0, 10))
                .color(Color.parseColor("#F5FAFD"))
                .borderColor(Color.parseColor("#3CA0E6"))
                .borderWidth(1f)
                .title("箭头向下 build")
                .setTitleColor(Color.parseColor("#3CA0E6"))
                .content(((TextView) v).getText().toString())
                .direction(TriangleView.Direction.DOWN)
                .setOnClickListener(new TipsView.OnTipClickListener() {
                    @Override
                    public void onClick(TipsView view) {
                        view.dismiss();
                    }
                })
                .build()
                .show(v);

    }

    private void showLeftTips(View v) {
        RelativeLayout customView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.layout_custom_pop, null);
        MutilImageView mutilImageView = (MutilImageView) customView.findViewById(R.id.bts_tip_imgs);
        int[] data = new int[]{android.R.drawable.ic_media_ff, android.R.drawable.ic_delete};
        mutilImageView.setImgs(data, 50, 50, 0);

        new TipsView.Builder()
                .with(this)
                .customView(customView)
                .direction(TriangleView.Direction.LEFT)
                .setOnClickListener(new TipsView.OnTipClickListener() {
                    @Override
                    public void onClick(TipsView view) {
                        view.dismiss();
                    }
                })
                .build()
                .show(v);
    }

    private void showRightTips(View v) {
        new TipsView.Builder()
                .with(this)
                .color(R.color.colorPrimary)
                .title("箭头向右 build")
                .content(((TextView) v).getText().toString())
                .direction(TriangleView.Direction.RIGHT)
                .setOnClickListener(new TipsView.OnTipClickListener() {
                    @Override
                    public void onClick(TipsView view) {
                        view.dismiss();
                    }
                })
                .build()
                .show(v);
    }
}
