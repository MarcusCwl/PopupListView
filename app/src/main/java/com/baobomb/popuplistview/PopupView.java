package com.baobomb.popuplistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Baobomb on 2015/9/25.
 */
public abstract class PopupView {
    Context context;
    LayoutInflater layoutInflater;
    int resId;
    View view;
    View extendView;

    public PopupView(Context context, int resId) {
        layoutInflater = LayoutInflater.from(context);
        this.resId = resId;
        view = layoutInflater.inflate(resId, null);
        setViewsElements(view);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        extendView = setExtendView(extendView);
        extendView.setLayoutParams(layoutParams);
    }

    public View getPopupView() {
        return view;
    }

    public View getExtendPopupView() {
        View extendPopupView = layoutInflater.inflate(resId, null);
        setViewsElements(extendPopupView);
        return extendPopupView;
    }

    public View getExtendView() {
        return extendView;
    }

    public abstract void setViewsElements(View view);

    public abstract View setExtendView(View view);
}
