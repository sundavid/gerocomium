package com.swt.geracomium;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swt.geracomium.entity.User;



/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_profile, container, false);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_profile,null);
        ((TextView)v.findViewById(R.id.name)).setText(getString(R.string.name) + ": " + User.getUser().name);
        ((TextView)v.findViewById(R.id.phone_number)).setText(getString(R.string.phone_number) + ": " + User.getUser().phone_number);
        ((TextView)v.findViewById(R.id.age)).setText(getString(R.string.age) + ": " + User.getUser().age);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

}
