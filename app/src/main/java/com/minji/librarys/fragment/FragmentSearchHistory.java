package com.minji.librarys.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.minji.librarys.R;
import com.minji.librarys.StringsFiled;
import com.minji.librarys.adapter.SearchSeatHistoryAdapter;
import com.minji.librarys.bean.SearchHistory;
import com.minji.librarys.ui.SearchSeatActivity;
import com.minji.librarys.uitls.SharedPreferencesUtil;
import com.minji.librarys.uitls.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/9/19.
 */
public class FragmentSearchHistory extends Fragment {

    private SearchSeatHistoryAdapter searchSeatHistoryAdapter;
    private SearchSeatActivity searchSeatActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.layout_search_history, null);

        searchSeatActivity = (SearchSeatActivity) getActivity();

        GridView mShowHistory = (GridView) inflate.findViewById(R.id.gv_search_seat_history);

        String searchHistory = SharedPreferencesUtil.getString(getActivity(), StringsFiled.SEARCH_HISTORY, "");
        if (!StringUtils.isEmpty(searchHistory)) {
            List<SearchHistory> searchHistories = analysisGSONDate(searchHistory);
            searchSeatHistoryAdapter = new SearchSeatHistoryAdapter(searchHistories);
            mShowHistory.setAdapter(searchSeatHistoryAdapter);
        }

        mShowHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view;
                searchSeatActivity.requestSearchSeat(textView.getText().toString().trim());
            }
        });
        return inflate;
    }

    private List<SearchHistory> analysisGSONDate(String searchHistory) {
        List<SearchHistory> middleList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(searchHistory);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                middleList.add(new SearchHistory(object.optString("searchSeat")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return middleList;
    }
}
