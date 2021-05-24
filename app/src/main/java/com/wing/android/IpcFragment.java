package com.wing.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.wing.android.aidl.BookManagerActivity;
import com.wing.android.bindpool.BinderPoolActivity;
import com.wing.android.messenger.MessengerActivity;
import com.wning.demo.R;
import com.wning.demo.databinding.ActivityIpcBinding;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 进程间通信
 */
public class IpcFragment extends Fragment implements View.OnClickListener {

    private ActivityIpcBinding mainBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainBinding = ActivityIpcBinding.inflate(inflater,container,false);
        View view = mainBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mainBinding.messengerBtn.setOnClickListener(this);
        mainBinding.aidlBookManagerBtn.setOnClickListener(this);
        mainBinding.binderPoolBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messenger_btn:
                startActivity(new Intent(getActivity(), MessengerActivity.class));
                break;
            case R.id.aidl_book_manager_btn:
                startActivity(new Intent(getActivity(), BookManagerActivity.class));
                break;
            case R.id.binder_pool_btn:
                startActivity(new Intent(getActivity(), BinderPoolActivity.class));
                break;
        }
    }
}