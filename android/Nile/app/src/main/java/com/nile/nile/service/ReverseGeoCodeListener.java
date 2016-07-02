package com.nile.nile.service;

import com.here.android.mpa.search.Address;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.ResultListener;

/**
 * Created by Felix on 02.07.16.
 */
public class ReverseGeoCodeListener implements ResultListener<Address> {

    @Override
    public void onCompleted(Address address, ErrorCode errorCode) {
        if (errorCode != ErrorCode.NONE) {

        } else {

        }
    }
}
