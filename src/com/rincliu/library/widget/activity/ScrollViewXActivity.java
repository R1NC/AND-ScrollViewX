package com.rincliu.library.widget.activity;

import com.rincliu.library.widget.ScrollViewX;
import com.rincliu.library.widget.ScrollViewX.OnScrollListener;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class ScrollViewXActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ScrollViewX svx = new ScrollViewX(this);
        setContentView(svx);
        TextView tv = new TextView(this);
        tv.setHeight(3000);
        svx.addView(tv);
        svx.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldX, int oldY) {
                android.util.Log.d("@", "x:" + oldX + "->" + x + ", y:" + oldY + "->" + y);
            }

            @Override
            public void onScrollStopped() {
                if (svx.isAtTop()) {
                    Toast.makeText(ScrollViewXActivity.this, "Stopped at top", Toast.LENGTH_SHORT).show();
                } else if (svx.isAtBottom()) {
                    Toast.makeText(ScrollViewXActivity.this, "Stopped at bottom", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ScrollViewXActivity.this, "Stopped", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScrolling() {
                android.util.Log.d("@", "scrolling...");
            }
        });
    }
}
