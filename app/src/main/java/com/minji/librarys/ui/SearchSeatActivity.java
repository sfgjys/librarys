package com.minji.librarys.ui;

import android.view.View;
import android.widget.GridView;

import com.minji.librarys.R;
import com.minji.librarys.adapter.SerachSeatHistoryAdapter;
import com.minji.librarys.base.BaseActivity;

/**
 * Created by user on 2016/8/25.
 */
public class SearchSeatActivity extends BaseActivity {


    String[] strings = {"654-ASD", "654-ASD", "654-ASD", "654-ASD", "654-ASD", "654-ASD", "654-ASD", "654-ASD", "654-ASD",};


    @Override
    public void onCreateContent() {

        setBackVisibility(View.VISIBLE);
        setSettingVisibility(View.GONE);
        setTitleVisibility(View.VISIBLE);
        setTitleImageVisibility(View.GONE);

        View view = setContent(R.layout.layout_serach_seat);

        GridView viewById = (GridView) view.findViewById(R.id.gv_search_seat_history);

        viewById.setAdapter(new SerachSeatHistoryAdapter(strings));
    }
}
