package com.example.paginatlivedata;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private final String TAG = "MainFragment";
    
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeToRefresh;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);

        swipeToRefresh = view.findViewById(R.id.swipe_down_to_refresh_list);



        //setting up recyclerview
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        //getting our ItemViewModel
        ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        //creating the Adapter
        final ItemAdapter adapter = new ItemAdapter(getActivity());


        //observing the itemPagedList from view model
        itemViewModel.itemPagedList.observe(this, new Observer<PagedList<Item>>() {
            @Override
            public void onChanged(@Nullable PagedList<Item> items) {

                //in case of any changes
                //submitting the items to adapter
                adapter.submitList(items);
            }
        });

        //setting the adapter
        recyclerView.setAdapter(adapter);

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Log.d(TAG, "onRefresh: swiped ");

                ItemViewModel itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);

                //observing the itemPagedList from view model
                itemViewModel.itemPagedList.observe(getActivity(), new Observer<PagedList<Item>>() {
                    @Override
                    public void onChanged(@Nullable PagedList<Item> items) {

                        Log.d(TAG, "onChanged: swiped Result ");

                        //in case of any changes
                        //submitting the items to adapter
                        adapter.submitList(items);

                        //setting the adapter
                        recyclerView.setAdapter(adapter);

                        swipeToRefresh.setRefreshing(false);
                    }
                });

            }
        });




        return view;
    }

}
