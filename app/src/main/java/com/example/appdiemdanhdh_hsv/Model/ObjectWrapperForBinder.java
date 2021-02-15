package com.example.appdiemdanhdh_hsv.Model;

import android.os.Binder;

public class ObjectWrapperForBinder extends Binder {
    private final Object mData;

    public ObjectWrapperForBinder(Object data) {
        mData = data;
    }

    public Object getData() {
        return mData;
    }
}
