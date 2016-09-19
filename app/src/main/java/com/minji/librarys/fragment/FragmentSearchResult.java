package com.minji.librarys.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minji.librarys.R;
import com.minji.librarys.StringsFiled;

/**
 * Created by user on 2016/9/19.
 */
public class FragmentSearchResult extends Fragment {


    private String mTime;
    private String mUserName;
    private String mStates;
    private String mMobile;
    private String mSeatNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mTime = bundle.getString(StringsFiled.SEARCH_RESULT_TIME);
        mUserName = bundle.getString(StringsFiled.SEARCH_RESULT_USERNAME);
        mStates = bundle.getString(StringsFiled.SEARCH_RESULT_STATES);
        mMobile = bundle.getString(StringsFiled.SEARCH_RESULT_MOBILE);
        mSeatNumber = bundle.getString(StringsFiled.SEARCH_RESULT_SEAT_NUMBER);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_search_seat_result, null);

        TextView mobile = (TextView) view.findViewById(R.id.tv_search_seat_result_right_mobile);
        mobile.setText(mMobile);
        TextView time = (TextView) view.findViewById(R.id.tv_search_seat_result_right_time);
        time.setText(mTime);
        TextView seatNumber = (TextView) view.findViewById(R.id.tv_search_seat_result_right_seat_number);
        seatNumber.setText(mSeatNumber);
        TextView states = (TextView) view.findViewById(R.id.tv_search_seat_result_right_states);
        states.setText(mStates);
        TextView userName = (TextView) view.findViewById(R.id.tv_search_seat_result_right_user_name);
        userName.setText(mUserName);

        return view;
    }
}
