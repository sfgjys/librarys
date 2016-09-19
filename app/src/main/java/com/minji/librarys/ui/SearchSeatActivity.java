package com.minji.librarys.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.minji.librarys.R;
import com.minji.librarys.StringsFiled;
import com.minji.librarys.base.BaseActivity;
import com.minji.librarys.bean.SearchHistory;
import com.minji.librarys.fragment.FragmentSearchHistory;
import com.minji.librarys.fragment.FragmentSearchResult;
import com.minji.librarys.http.OkHttpManger;
import com.minji.librarys.uitls.GsonTools;
import com.minji.librarys.uitls.SharedPreferencesUtil;
import com.minji.librarys.uitls.StringUtils;
import com.minji.librarys.uitls.ToastUtil;
import com.minji.librarys.uitls.ViewsUitls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by user on 2016/8/25.
 */
public class SearchSeatActivity extends BaseActivity implements View.OnClickListener {

    private View view;

    private EditText mInputSearchSeat;
    private ImageView mCleanInputSearch;
    private TextView mSearchButton;

    private String mSearchContent;
    private boolean mHistoryIsEqual;


    @Override
    public void onCreateContent() {

        setBackVisibility(View.VISIBLE);
        setSettingVisibility(View.GONE);
        setTitleVisibility(View.VISIBLE);
        setTitleImageVisibility(View.GONE);

        view = setContent(R.layout.layout_serach_seat);
        initSetContentView();


        // 设置清除输入与输入显示清除的监听
        setCleanAndInputListener();

        showHistorySearchContent();


        mSearchButton.setOnClickListener(this);


    }

    private void showHistorySearchContent() {

        String searchHistory = SharedPreferencesUtil.getString(this, StringsFiled.SEARCH_HISTORY, "");

        if (!StringUtils.isEmpty(searchHistory)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_search_seat_bottom_history_or_search_result, new FragmentSearchHistory()).commit();
        }

    }

    private void setCleanAndInputListener() {
        mCleanInputSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCleanInputSearch.getVisibility() == View.VISIBLE) {
                    mInputSearchSeat.setText("");
                }
            }
        });
        mInputSearchSeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isEmpty(s.toString())) {// 输入为空那么要隐藏清除按钮，显示语音按钮
                    if (mCleanInputSearch.getVisibility() != View.INVISIBLE) {
                        mCleanInputSearch.setVisibility(View.INVISIBLE);
                    }
                } else {// 输入不为空那么要隐藏语音按钮，显示清除按钮
                    if (mCleanInputSearch.getVisibility() == View.INVISIBLE) {
                        mCleanInputSearch.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void initSetContentView() {

        mInputSearchSeat = (EditText) view.findViewById(R.id.et_input_search_seat);
        mCleanInputSearch = (ImageView) view.findViewById(R.id.iv_clean_input_search);
        mSearchButton = (TextView) view.findViewById(R.id.tv_search_seat_search_button);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_search_seat_search_button:
                mSearchContent = mInputSearchSeat.getText().toString().trim();
                if (!StringUtils.isEmpty(mSearchContent)) {
                    if (judgeFormat(mSearchContent)) {
                        requestSearchSeat(mSearchContent);
                    } else {
                        ToastUtil.showToast(this, "查询内容格式有误,请重新输入");
                        mInputSearchSeat.setText("");
                    }
                } else {
                    ToastUtil.showToast(this, "查询内容不可为空");
                    mInputSearchSeat.setText("");
                }
                break;
        }

    }

    public void requestSearchSeat(final String searchContent) {

        setIsInterruptTouch(true);
        setLoadingVisibility(View.VISIBLE);

        OkHttpClient okHttpClient = OkHttpManger.getInstance().getOkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("seat", searchContent).build();
        Request request = new Request.Builder()
                .url("http://192.168.1.40:8080/library-seat/mobile/seat")
                .post(formBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ViewsUitls.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        setIsInterruptTouch(false);
                        setLoadingVisibility(View.GONE);
                        ToastUtil.showToast(SearchSeatActivity.this, "查询座位信息失败!");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                ViewsUitls.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        analysisRequestSeatResult(result,searchContent);
                    }
                });
            }
        });
    }

    private void analysisRequestSeatResult(String result, String searchContent) {
        try {
            JSONObject object = new JSONObject(result);
            if (object.has("result")) {
                if (object.optBoolean("result")) {
                    // 查询到具体信息
                    JSONObject message = object.optJSONObject("message");
                    Bundle bundle = new Bundle();
                    bundle.putString(StringsFiled.SEARCH_RESULT_TIME, message.optString("time"));
                    bundle.putString(StringsFiled.SEARCH_RESULT_USERNAME, message.optString("username"));
                    bundle.putString(StringsFiled.SEARCH_RESULT_STATES, message.optString("states"));
                    bundle.putString(StringsFiled.SEARCH_RESULT_MOBILE, message.optString("mobile"));
                    bundle.putString(StringsFiled.SEARCH_RESULT_SEAT_NUMBER, searchContent);
                    FragmentSearchResult fragmentSearchResult = new FragmentSearchResult();
                    fragmentSearchResult.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_search_seat_bottom_history_or_search_result, fragmentSearchResult).commit();
                    // 查询成功后将查询记录进行保存
                    updateSearchNote(searchContent);

                } else {
                    ToastUtil.showToast(SearchSeatActivity.this, object.optString("message"));
                }
            } else {
                ToastUtil.showToast(SearchSeatActivity.this, "查询座位信息失败!!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setIsInterruptTouch(false);
        setLoadingVisibility(View.GONE);
    }

    private void updateSearchNote(String searchContent) {
        String searchHistory = SharedPreferencesUtil.getString(this, StringsFiled.SEARCH_HISTORY, "");
        if (StringUtils.isEmpty(searchHistory)) {// 第一次查询成功
            List<SearchHistory> list = new ArrayList<>();
            list.add(new SearchHistory(searchContent));
            String gsonString = GsonTools.createGsonString(list);
            SharedPreferencesUtil.saveStirng(this, StringsFiled.SEARCH_HISTORY, gsonString);
        } else {// 已经有查询记录了
            List<SearchHistory> searchHistories = analysisGSONDate(searchHistory);
            // 先声明本次查询在查询记录里没有重复
            mHistoryIsEqual = false;
            for (SearchHistory history : searchHistories) {
                if (searchContent.equals(history.getSearchSeat())) {
                    mHistoryIsEqual = true;// 有重复了
                    return;
                }
            }
            if (!mHistoryIsEqual) {// 没有重复，需要进行写入记录
                searchHistories.add(new SearchHistory(searchContent));
                String gsonString = GsonTools.createGsonString(searchHistories);
                SharedPreferencesUtil.saveStirng(this, StringsFiled.SEARCH_HISTORY, gsonString);
            }
        }
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

    private boolean judgeFormat(String searchContent) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher isNum = pattern.matcher(searchContent);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
