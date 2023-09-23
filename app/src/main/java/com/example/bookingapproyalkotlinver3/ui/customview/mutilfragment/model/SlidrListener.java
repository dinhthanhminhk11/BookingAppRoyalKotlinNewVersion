package com.example.bookingapproyalkotlinver3.ui.customview.mutilfragment.model;

public interface SlidrListener {
    void onSlideStateChanged(int state);

    void onSlideChange(float percent);

    void onSlideOpened();

    boolean onSlideClosed();
}

