package com.minji.librarys.ui;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.minji.librarys.R;
import com.minji.librarys.StringsFiled;
import com.minji.librarys.base.BaseActivity;
import com.minji.librarys.fragment.FragmentReadingRoom;
import com.minji.librarys.http.OkHttpManger;
import com.minji.librarys.uitls.ToastUtil;
import com.minji.librarys.uitls.ViewsUitls;
import com.minji.librarys.widget.WheelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SelectAreasActivity extends BaseActivity implements View.OnClickListener {


    public String mFloorsName[];
    public String mFloorId[];

    public String mReadingRoomName[][];
    public String mReadingRoomId[][];


    private TextView mSelectReadingRoomDialog;

    private AlertDialog alertDialog;
    private ImageView selectVisibility;
    private Window window;
    private WheelView mLeftWheel;
    private WheelView mRightWheel;
    private View viewWheelRight;

    /**
     * 用于判断右边的Wheel是否已经add
     */
    private int whetherAddRightWheel;

    @Override
    public void onCreateContent() {

        // 对标题栏进行设置
        setBackVisibility(View.VISIBLE);
        setSettingVisibility(View.GONE);
        setTitleVisibility(View.VISIBLE);
        setTitleImageVisibility(View.GONE);

        selectVisibility = getSelectVisibility();
        selectVisibility.setOnClickListener(this);

        View content = setContent(R.layout.activity_select_areas);

        mSelectReadingRoomDialog = (TextView) content.findViewById(R.id.tv_select_area_to_dialog_reading_room);
        mSelectReadingRoomDialog.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select_area_to_dialog_reading_room:
                // TODO 请求网络获取选择的数据

                OkHttpClient okHttpClient = OkHttpManger.getInstance().getOkHttpClient();
                RequestBody formBody = new FormBody.Builder().build();
                Request request = new Request.Builder()
                        .url("http://192.168.1.40:8080/library-seat/mobile/floorAndArea")
                        .post(formBody)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("=========================onFailure=============================");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String result = response.body().string().toString();
                        try {
                            JSONArray arrParent = new JSONArray(result);
                            // 先根据arrParent的长度来创建String[]
                            createStringArrays(arrParent);

                            for (int p = 0; p < arrParent.length(); p++) {
                                JSONObject objParent = arrParent.optJSONObject(p);
                                String floorName = objParent.optString("text");
                                mFloorsName[p] = floorName;
                                String floorId = objParent.optString("value");
                                mFloorId[p] = floorId;

                                JSONArray arrChildren = objParent.optJSONArray("children");
                                String[] childrenName = new String[arrChildren.length()];
                                String[] childrenId = new String[arrChildren.length()];
                                for (int c = 0; c < arrChildren.length(); c++) {
                                    JSONObject objChildren = arrChildren.optJSONObject(c);
                                    String readingRoomName = objChildren.optString("text");
                                    childrenName[c] = readingRoomName;
                                    String readingRoomId = objChildren.optString("value");
                                    childrenId[c] = readingRoomId;
                                }
                                mReadingRoomName[p] = childrenName;
                                mReadingRoomId[p] = childrenId;
                            }
                            ViewsUitls.runInMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    showSelectReadingRoomDialog();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

                break;
            case R.id.iv_title_select:
                // TODO 请求网络获取选择的数据
                showSelectReadingRoomDialog();
                break;
            case R.id.bt_select_area_dialog_cancel:
                alertDialog.cancel();
                break;
            case R.id.bt_select_area_dialog_make_sure:

                String[] readingRoomIds = mReadingRoomId[mLeftWheel.getSeletedIndex()];
                if (readingRoomIds.length != 0) {// 不等于0时右边的wheel才有数据
                    String floorId = mFloorId[mLeftWheel.getSeletedIndex()];
                    String readingRoomId = readingRoomIds[mRightWheel.getSeletedIndex()];
                    ToastUtil.showToast(this, floorId + "=-+-=" + readingRoomId);


                    // 将两个ID传递给fragmentReadingRoom
                    Bundle bundle = new Bundle();
                    bundle.putString(StringsFiled.FLOORID, floorId);
                    bundle.putString(StringsFiled.READINGROOMID, readingRoomId);
                    FragmentReadingRoom fragmentReadingRoom = new FragmentReadingRoom();
                    fragmentReadingRoom.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_select_area_reading_room_content, fragmentReadingRoom).commit();


                    // 这个判断是 当中间的选择还在时也就是第一次选择，进入判断，显示右上角的，隐藏中间的------执行前提是第一次选择阅览室后并将两个ID传递给FragmentReadingRoom后(至于座位界面是否显示成功不管)
                    if (mSelectReadingRoomDialog.getVisibility() != View.GONE) {
                        System.out.println("++++++++++++++++++++++++++++++");
                        selectVisibility.setVisibility(View.VISIBLE);
                        mSelectReadingRoomDialog.setVisibility(View.GONE);
                    }
                    alertDialog.cancel();
                } else {
                    ToastUtil.showToast(this, "该楼层内没有阅览室，无法选择");
                }


                break;
        }
    }

    private void createStringArrays(JSONArray arrParent) {
        mFloorsName = null;
        mFloorId = null;
        mFloorsName = new String[arrParent.length()];
        mFloorId = new String[arrParent.length()];

        mReadingRoomName = null;
        mReadingRoomId = null;
        mReadingRoomName = new String[arrParent.length()][];
        mReadingRoomId = new String[arrParent.length()][];
    }

    private void showSelectReadingRoomDialog() {
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(new EditText(ViewsUitls.getContext()));// 为了让对话框内的输入框可以使用？
        WindowManager.LayoutParams attributes = alertDialog.getWindow().getAttributes();// 获取对话框的属性集
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        attributes.width = (int) (d.getWidth() * 0.9);
        alertDialog.show();
        // 设置对话框中自定义内容
        window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_select_reading_room);

        setWindowInWheelView();

        Button mCancel = (Button) window.findViewById(R.id.bt_select_area_dialog_cancel);
        Button mSure = (Button) window.findViewById(R.id.bt_select_area_dialog_make_sure);
        mCancel.setOnClickListener(this);
        mSure.setOnClickListener(this);
    }

    private int WheelHightAndTopIndex = 2;

    private void setWindowInWheelView() {
        final FrameLayout frameLayout = (FrameLayout) window.findViewById(R.id.fl_wheel_right);

        mLeftWheel = (WheelView) window.findViewById(R.id.wheel_left);
        mLeftWheel.setOffset(WheelHightAndTopIndex);// 既是偏差也是高度
        mLeftWheel.setItems(Arrays.asList(mFloorsName));
        mLeftWheel.setSeletion(0);
        mLeftWheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("logleftWheel-onSelected", "selectedIndex: " + selectedIndex + ", item: " + item);
                if (selectedIndex != whetherAddRightWheel) {
                    frameLayout.removeAllViews();
                    getRightWheel(selectedIndex);
                    frameLayout.addView(viewWheelRight);
                    whetherAddRightWheel = selectedIndex;
                }
            }
        });

        // 为了第一次弹出对话框时右边的wheel存在
        frameLayout.removeAllViews();
        getRightWheel(WheelHightAndTopIndex);// 此处的参数1是模拟leftWheel滑动到顶部时selectedIndex的值
        frameLayout.addView(viewWheelRight);
        whetherAddRightWheel = WheelHightAndTopIndex;// 此处的参数1是模拟leftWheel滑动到顶部时selectedIndex的值


    }

    private void getRightWheel(int selectedIndex) {
        viewWheelRight = ViewsUitls.inflate(R.layout.wheel_right);
        mRightWheel = (WheelView) viewWheelRight.findViewById(R.id.wheel_right);
        mRightWheel.setOffset(WheelHightAndTopIndex);// 既是偏差也是高度
        mRightWheel.setItems(Arrays.asList(mReadingRoomName[selectedIndex - WheelHightAndTopIndex]));// 这里获取数据时的角标需要减去TopIndex
        mRightWheel.setSeletion(0);
    }

}
