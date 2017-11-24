package com.example.wjk32.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wjk32.NearbyMapActivity;
import com.example.wjk32.R;

/**
 * Created by wjk32 on 11/17/2017.
 */

public class FragmentHome2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_index_2,null);
        startActivity(new Intent(getActivity(), NearbyMapActivity.class));

        return view;
    }
}
