package com.sakura.ydhyfinal.DataSourse;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import androidx.paging.PositionalDataSource;

import com.sakura.ydhyfinal.bean.MyWorks;
import com.sakura.ydhyfinal.bean.TaskBooks;

public class PageDateTaskBooksFactory extends DataSource.Factory {
    public PositionalDataSource<TaskBooks> mPositionalDataSource;


    public PageDateTaskBooksFactory(PositionalDataSource<TaskBooks> positionalDataSource) {
        this.mPositionalDataSource = positionalDataSource;
    }

    @NonNull
    @Override
    public DataSource create() {
        return mPositionalDataSource;
    }
}
