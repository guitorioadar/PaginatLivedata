package com.example.paginatlivedata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import com.example.paginatlivedata.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemKeyDataSource extends ItemKeyedDataSource<Integer,Item> {

    private  final String TAG = "ItemDataSource";

    //the size of a page that we want
    public static final int ITEM_SIZE = 25;

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    //we need to fetch from stackoverflow
    private static final String SITE_NAME = "stackoverflow";

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Item> callback) {
        RetrofitClient.getInstance()
                .getApi().getAnswers(FIRST_PAGE, ITEM_SIZE, SITE_NAME)
                .enqueue(new Callback<StackoverflowApiResponse>() {
                    @Override
                    public void onResponse(Call<StackoverflowApiResponse> call, Response<StackoverflowApiResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                callback.onResult(response.body().items, 0, response.body().items.size());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<StackoverflowApiResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: loadInitial: "+t.getMessage());
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Item> callback) {
        RetrofitClient.getInstance()
                .getApi().getAnswers(FIRST_PAGE, ITEM_SIZE, SITE_NAME)
                .enqueue(new Callback<StackoverflowApiResponse>() {
                    @Override
                    public void onResponse(Call<StackoverflowApiResponse> call, Response<StackoverflowApiResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                callback.onResult(response.body().items);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<StackoverflowApiResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: loadInitial: "+t.getMessage());
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Item> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull Item item) {
        return item.questionId;
    }
}
