package com.baobomb.popuplistview;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class DemoActivity extends Activity {

    PopupListView popupListView;
    ArrayList<PopupView> popupViews;
    int actionBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        popupViews = new ArrayList<>();
        TypedValue tv = new TypedValue();
        popupListView = (PopupListView) findViewById(R.id.popupListView);
        for (int i = 0; i < 10; i++) {
            PopupView popupView = new PopupView(this, R.layout.popup_view_item) {
                @Override
                public void setViewsElements(View view) {
                    TextView textView = (TextView) view.findViewById(R.id.title);
                    textView.setText("123456789");
                }

                @Override
                public View setExtendView(View view) {
                    View extendView;
                    if (view == null) {
                        extendView = LayoutInflater.from(getApplicationContext()).inflate(R
                                .layout.extend_view, null);
                    } else {
                        extendView = view;
                    }
                    return extendView;
                }
            };
            popupViews.add(popupView);
        }
        popupListView.init(null);
        popupListView.setItemViews(popupViews);
    }

    @Override
    public void onBackPressed() {
        if (popupListView.isItemZoomIn()) {
            popupListView.zoomOut();
        } else {
            super.onBackPressed();
        }
    }
}
