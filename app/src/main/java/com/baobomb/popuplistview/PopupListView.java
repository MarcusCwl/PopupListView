package com.baobomb.popuplistview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Baobomb on 2015/9/25.
 */
public class PopupListView extends RelativeLayout {
    Context context;
    ListView listView;
    LinearLayout extendView;
    PopupListAdapter popupListAdapter;
    View extendPopupView;
    View extendInnerView;
    Handler handler = new Handler();
    int startY;
    int moveY = 0;
    int heightSpace = 0;

    public PopupListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        popupListAdapter = new PopupListAdapter();
    }

    public void init(ListView customListView) {
        setHeightSpace();
        RelativeLayout.LayoutParams listParams = new RelativeLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams extendsParams = new LinearLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (customListView == null) {
            listView = new ListView(context);
        } else {
            listView = customListView;
        }

        if (extendView == null) {
            extendView = new LinearLayout(context);
            extendView.setOrientation(LinearLayout.VERTICAL);
        }
        listView.setDivider(null);
        listView.setLayoutParams(listParams);
        listView.setAdapter(popupListAdapter);
        listView.setOnItemClickListener(extend);
        extendView.setLayoutParams(extendsParams);
        extendView.setVisibility(GONE);
        this.addView(listView);
        this.addView(extendView);
    }

    public void setItemViews(ArrayList<? extends PopupView> items) {
        popupListAdapter.setItems(items);
    }

    private AdapterView.OnItemClickListener extend = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //TODO GET POSITION AND START ANIMATION
            int height = view.getMeasuredHeight();
            int[] p = new int[2];
            view.getLocationOnScreen(p);
            startY = p[1]-heightSpace;
            moveY = startY;
            zoomIn(i, startY);
        }
    };

    public void zoomIn(int i, int startY) {
        listView.setVisibility(GONE);
        extendPopupView = ((PopupView) popupListAdapter.getItem(i)).getExtendPopupView();
        extendInnerView = ((PopupView) popupListAdapter.getItem(i)).getExtendView();
        extendView.addView(extendPopupView);
        extendPopupView.setY(startY);
        extendInnerView.setVisibility(GONE);
        extendView.addView(extendInnerView);
        extendView.setVisibility(VISIBLE);
        handler.postDelayed(zommInRunnable,100);
    }

    public void zoomOut() {
        extendInnerView.setVisibility(GONE);
        handler.removeCallbacks(zommInRunnable);
        handler.postDelayed(zommOutRunnable, 1);
    }

    public boolean isItemZoomIn() {
        if (extendView.getVisibility() == VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    public Runnable zommInRunnable = new Runnable() {
        @Override
        public void run() {
            if (moveY > 0) {
                moveY-= startY/10;
                extendPopupView.setY(moveY);
                handler.postDelayed(zommInRunnable,1);
            }else{
                extendPopupView.setY(0);
                extendInnerView.setVisibility(VISIBLE);
            }
        }
    };

    public Runnable zommOutRunnable = new Runnable() {
        @Override
        public void run() {
            if (moveY < startY) {
                moveY += (startY)/10;
                extendPopupView.setY(moveY);
                handler.postDelayed(zommOutRunnable,1);
            }else {
                extendPopupView.setY(startY);
                extendView.setVisibility(GONE);
                extendView.removeAllViews();
                extendPopupView = null;
                listView.setVisibility(VISIBLE);
            }
        }
    };

    public void setHeightSpace() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources()
                    .getDisplayMetrics());
        }

        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        this.heightSpace = actionBarHeight+result;
    }
}
