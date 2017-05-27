# TipView
![image](https://github.com/chenchongyu/TipView/blob/master/capture/WechatIMG1.jpeg)

简单好用、功能强大的Android提示控件。
调用示例如下，更多示例可以查看demo
```
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
 ```
一句话引用：
```
compile 'com.github.chenchongyu:TipView:v0.0.1'
```