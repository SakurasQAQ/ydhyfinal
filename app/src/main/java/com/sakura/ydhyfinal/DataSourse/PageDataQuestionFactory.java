package com.sakura.ydhyfinal.DataSourse;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import androidx.paging.PositionalDataSource;

import com.sakura.ydhyfinal.bean.MyWorks;
import com.sakura.ydhyfinal.bean.StoryQues;

public class PageDataQuestionFactory extends DataSource.Factory{
    public PositionalDataSource<StoryQues> mPositionalDataSource;

    public PageDataQuestionFactory(PositionalDataSource<StoryQues> positionalDataSource){
        this.mPositionalDataSource = positionalDataSource;
    }

    @NonNull
    @Override
    public DataSource create() {
        return mPositionalDataSource;
    }
}
