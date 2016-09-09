package com.minji.librarys.ui;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minji.librarys.R;
import com.minji.librarys.StringsFiled;
import com.minji.librarys.base.BaseActivity;
import com.minji.librarys.http.OkHttpManger;
import com.minji.librarys.uitls.SharedPreferencesUtil;
import com.minji.librarys.uitls.ViewsUitls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by user on 2016/8/23.
 */
public class MyCentersActivity extends BaseActivity implements View.OnClickListener {

    private TextView studentNumber;
    private TextView allIntegral;

    @Override
    public void onCreateContent() {

        setBackVisibility(View.VISIBLE);
        setSettingVisibility(View.GONE);
        setTitleVisibility(View.VISIBLE);
        setTitleImageVisibility(View.GONE);

        View view = setContent(R.layout.layout_my_center);
        studentNumber = (TextView) view.findViewById(R.id.tv_my_center_top_part_student_number);
        allIntegral = (TextView) view.findViewById(R.id.tv_my_center_middle_part_all_integral);

        requestStudentNumberAndIntegral();

        initBottomView();


    }

    private void initBottomView() {
        RelativeLayout myInformation = (RelativeLayout) findViewById(R.id.my_center_bottom_part_normal_my_information);
        myInformation.setOnClickListener(this);
        RelativeLayout meMessage = (RelativeLayout) findViewById(R.id.my_center_bottom_part_normal_me_message);
        meMessage.setOnClickListener(this);
        RelativeLayout myIntegral = (RelativeLayout) findViewById(R.id.my_center_bottom_part_normal_my_integral);
        myIntegral.setOnClickListener(this);
        RelativeLayout myOrder = (RelativeLayout) findViewById(R.id.my_center_bottom_part_normal_my_order);
        myOrder.setOnClickListener(this);
    }

    private void requestStudentNumberAndIntegral() {
        String mUserId = SharedPreferencesUtil.getString(this, StringsFiled.USERID, "");
        OkHttpClient okHttpClient = OkHttpManger.getInstance().getOkHttpClient();
        RequestBody meInformationFormBody = new FormBody.Builder()
                .add("userid", mUserId).build();
        Request meInformationRequest = new Request.Builder()
                .url("http://192.168.1.40:8080/library-seat/mobile/data")
                .post(meInformationFormBody)
                .build();

        RequestBody allPointFormBody = new FormBody.Builder()
                .add("userid", mUserId).build();
        Request allPointRequest = new Request.Builder()
                .url("http://192.168.1.40:8080/library-seat/mobile/myPointAndYesterdayPointInfo")
                .post(allPointFormBody)
                .build();
        okHttpClient.newCall(meInformationRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("meInformationRequest---onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultString = response.body().string();
                System.out.println(resultString);
                ViewsUitls.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(resultString);
                            studentNumber.setText("学号 : " + object.optString("studentId"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        okHttpClient.newCall(allPointRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("allPointRequest---onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultString = response.body().string();
                System.out.println(resultString);
                ViewsUitls.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(resultString);
                            allIntegral.setText(object.optInt("myPoint") + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.my_center_bottom_part_normal_my_information:
                skipFourActivity("我的资料");
                break;
            case R.id.my_center_bottom_part_normal_me_message:
                skipFourActivity("系统消息");
                break;
            case R.id.my_center_bottom_part_normal_my_integral:
                skipFourActivity("积分明细");
                break;
            case R.id.my_center_bottom_part_normal_my_order:
                skipFourActivity("我的预约");
                break;
        }
    }


    private void skipFourActivity(String title) {
        Intent intent = new Intent(ViewsUitls.getContext(), IntegralAndOrderActivity.class);
        intent.putExtra(StringsFiled.ACTIVITY_TITLE, title);
        startActivity(intent);
    }
}
