package com.sakura.ydhyfinal.DataSourse;


import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import androidx.paging.PositionalDataSource;

import com.sakura.ydhyfinal.bean.MyWorks;


public class BookdetailListDataSourceFactory extends DataSource.Factory {

    public PositionalDataSource<MyWorks> mPositionalDataSource;


    public BookdetailListDataSourceFactory(PositionalDataSource<MyWorks> positionalDataSource) {
        this.mPositionalDataSource = positionalDataSource;
    }

    @NonNull
    @Override
    public DataSource create() {
        return mPositionalDataSource;
    }
}
