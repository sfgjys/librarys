package com.minji.librarys.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.minji.librarys.R;
import com.minji.librarys.ui.MyCenterActivity;

/**
 * Created by user on 2016/8/24.
 */
public class FragmentMyCenterUpdateInformation extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.layout_my_cneter_bottom_update_information, null);

        TextView mStudyNumber = (TextView) inflate.findViewById(R.id.tv_update_information_item_study_number);
        TextView mStudyName = (TextView) inflate.findViewById(R.id.tv_update_information_item_study_name);
        EditText mpPoneNumber = (EditText) inflate.findViewById(R.id.et_update_information_item_phone_number);
        EditText mEmailAddress = (EditText) inflate.findViewById(R.id.et_update_information_item_email_address);
        Button mInformationSure = (Button) inflate.findViewById(R.id.bt_update_information_information_make_sure);
        mInformationSure.setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_update_information_information_make_sure:
                MyCenterActivity activity = (MyCenterActivity) getActivity();
                activity.showButtomFragmentNormal();
                break;
        }
    }
}
