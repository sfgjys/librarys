package com.minji.librarys.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.minji.librarys.R;
import com.minji.librarys.StringsFiled;
import com.minji.librarys.ui.IntegralAndOrderActivity;
import com.minji.librarys.ui.MyCenterActivity;
import com.minji.librarys.uitls.ViewsUitls;

/**
 * Created by user on 2016/8/24.
 */
public class FragmentMyCenterNormal extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.layout_my_cneter_bottom_normal, null);

        RelativeLayout myInformation = (RelativeLayout) inflate.findViewById(R.id.my_center_bottom_part_normal_my_information);
        myInformation.setOnClickListener(this);
        RelativeLayout meMessage = (RelativeLayout) inflate.findViewById(R.id.my_center_bottom_part_normal_me_message);
        meMessage.setOnClickListener(this);
        RelativeLayout myIntegral = (RelativeLayout) inflate.findViewById(R.id.my_center_bottom_part_normal_my_integral);
        myIntegral.setOnClickListener(this);
        RelativeLayout myOrder = (RelativeLayout) inflate.findViewById(R.id.my_center_bottom_part_normal_my_order);
        myOrder.setOnClickListener(this);

        return inflate;
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(ViewsUitls.getContext(), IntegralAndOrderActivity.class);

        switch (v.getId()) {
            case R.id.my_center_bottom_part_normal_my_information:
                MyCenterActivity activity = (MyCenterActivity) getActivity();
                activity.showButtomFragmentUpdate();
                break;
            case R.id.my_center_bottom_part_normal_me_message:

                break;
            case R.id.my_center_bottom_part_normal_my_integral:
                intent.putExtra(StringsFiled.ACTIVITY_TITLE, "积分明细");
                getActivity().startActivity(intent);
                break;
            case R.id.my_center_bottom_part_normal_my_order:
                intent.putExtra(StringsFiled.ACTIVITY_TITLE, "我的预约");
                getActivity().startActivity(intent);
                break;
        }

    }
}
