package com.test.halevi.barakapp.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Barak on 24/08/2017.
 */

public class ContactDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public ContactDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.top = space;

        if(parent.getChildLayoutPosition(view) == state.getItemCount()-1){
            outRect.bottom = space;
        }
    }
}