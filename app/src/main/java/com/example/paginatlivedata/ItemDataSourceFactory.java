package com.example.paginatlivedata;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class ItemDataSourceFactory extends DataSource.Factory {

    // Create mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer,Item>> itemLiveDataSource = new MutableLiveData<>();


    @Override
    public DataSource<Integer,Item> create() {

        //getting our data source object
//        ItemDataSource itemDataSource = new ItemDataSource();
        ItemDataSource itemDataSource = new ItemDataSource();

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);

        //returning the datasource
        return itemDataSource;
    }

    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, Item>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }

}
