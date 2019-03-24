package com.example.closet;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface ItemClickListner {

    void onClick(View view, int position, boolean isLongClick);
}