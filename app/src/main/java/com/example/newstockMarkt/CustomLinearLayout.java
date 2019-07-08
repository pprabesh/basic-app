package com.example.newstockMarkt;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

public class CustomLinearLayout extends LinearLayoutManager {

    boolean isAutoMeasured;
    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLinearLayout(Context context, int orientation, boolean reverseLayout, boolean isAutoMeasured) {
        super(context, orientation, reverseLayout);
        isAutoMeasured = isAutoMeasured;
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }
}
