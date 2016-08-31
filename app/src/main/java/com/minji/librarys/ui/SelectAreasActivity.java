package com.minji.librarys.ui;

import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minji.librarys.R;
import com.minji.librarys.base.BaseActivity;
import com.minji.librarys.fragment.FragmentReadingRoom;
import com.minji.librarys.http.OkHttpManger;
import com.minji.librarys.uitls.ToastUtil;
import com.minji.librarys.uitls.ViewsUitls;
import com.minji.librarys.widget.wheel.ArrayWheelAdapter;
import com.minji.librarys.widget.wheel.OnWheelChangedListener;
import com.minji.librarys.widget.wheel.WheelView;

import org.json.JSONArray;
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

public class SelectAreasActivity extends BaseActivity implements View.OnClickListener {


    public String mFloors[] = new String[]{"  河北省  ", "  山西省  ", "  内蒙古  ", "  辽宁省  ", "  吉林省  ", "  黑龙江  ", "  江苏省  "};

    public String mReadingRooms[][] = new String[][]{
            new String[]{"  石家庄  ", "唐山", "秦皇岛", "邯郸", "邢台", "保定", "张家口", "承德", "沧州", "廊坊", "衡水"},
            new String[]{"太原", "大同", "阳泉", "长治", "晋城", "朔州", "晋中", "运城", "忻州", "临汾", "吕梁"},
            new String[]{"呼和浩特", "包头", "乌海", "赤峰", "通辽", "鄂尔多斯", "呼伦贝尔", "巴彦淖尔", "乌兰察布", "兴安", "锡林郭勒", "阿拉善"},
            new String[]{"沈阳", "大连", "鞍山", "抚顺", "本溪", "丹东", "锦州", "营口", "阜新", "辽阳", "盘锦", "铁岭", "朝阳", "葫芦岛"},
            new String[]{"长春", "吉林", "四平", "辽源", "通化", "白山", "松原", "白城", "延边"},
            new String[]{"哈尔滨", "齐齐哈尔", "鸡西", "鹤岗", "双鸭山", "大庆", "伊春", "佳木斯", "七台河", "牡丹江", "黑河", "绥化", "大兴安岭"},
            new String[]{}};


    private TextView mSelectReadingRoomDialog;

    private AlertDialog alertDialog;
    private WheelView mWheelLeft;
    private WheelView mWheelRight;
    private ImageView selectVisibility;

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
                showSelectReadingRoomDialog();

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
                            for (int p = 0; p < arrParent.length(); p++) {
                                JSONObject objParent = arrParent.optJSONObject(p);
                                String floorName = objParent.optString("text");
                                String floorId = objParent.optString("value");
                                JSONArray arrChildren = objParent.optJSONArray("children");
                                for (int c = 0; c < arrChildren.length(); c++) {
                                    JSONObject objChildren = arrParent.optJSONObject(c);
                                    String readingRoomName = objChildren.optString("text");
                                    String readingRoomId = objChildren.optString("value");
                                }
                            }
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
                int leftPosition = mWheelLeft.getCurrentItem();
                String floor = mFloors[leftPosition];
                String readingRoom = mReadingRooms[leftPosition][mWheelRight.getCurrentItem()];
                ToastUtil.showToast(getApplicationContext(), floor + "--" + readingRoom);
                alertDialog.cancel();
                // TODO 使用floor和readingRoom去请求网络数据,然后展示

                if (mSelectReadingRoomDialog.getVisibility() != View.GONE) {
                    System.out.println("++++++++++++++++++++++++++++++");
                    selectVisibility.setVisibility(View.VISIBLE);
                    mSelectReadingRoomDialog.setVisibility(View.GONE);
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fl_select_area_reading_room_content, new FragmentReadingRoom()).commit();

                break;
        }
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
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_select_reading_room);
        LinearLayout linearLayout = (LinearLayout) window.findViewById(R.id.ll_select_area_dialog_add_weel);
        // 创建左右weel控件
        creatLeftRightWeel();
        //设置 LinearLayout 的布局参数
        LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 5);
        paramsLeft.gravity = Gravity.LEFT;
        LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 5);
        paramsRight.gravity = Gravity.RIGHT;
        //将 WheelView 对象放到左侧 LinearLayout 中
        linearLayout.addView(mWheelLeft, paramsLeft);
        //将 WheelView 对象放到 右侧 LinearLayout 中
        linearLayout.addView(mWheelRight, paramsRight);

        //为左侧的 WheelView 设置条目改变监听器
        mWheelLeft.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                //设置右侧的 WheelView 的适配器
                mWheelRight.setAdapter(new ArrayWheelAdapter<String>(mReadingRooms[newValue]));
                mWheelRight.setCurrentItem(mReadingRooms[newValue].length / 2);
            }
        });

        Button mCancel = (Button) window.findViewById(R.id.bt_select_area_dialog_cancel);
        Button mSure = (Button) window.findViewById(R.id.bt_select_area_dialog_make_sure);
        mCancel.setOnClickListener(this);
        mSure.setOnClickListener(this);
    }

    private void creatLeftRightWeel() {
        //创建 WheelView 组件
        mWheelLeft = new WheelView(this);
        //设置 WheelView 组件最多显示 5 个元素
        mWheelLeft.setVisibleItems(5);
        //设置 WheelView 元素是否循环滚动
        mWheelLeft.setCyclic(true);
        //设置 WheelView 适配器
        mWheelLeft.setAdapter(new ArrayWheelAdapter<String>(mFloors));
        //设置右侧的 WheelView
        mWheelRight = new WheelView(this);
        //设置右侧 WheelView 显示个数
        mWheelRight.setVisibleItems(5);
        //设置右侧 WheelView 元素是否循环滚动
        mWheelRight.setCyclic(true);
        //设置右侧 WheelView 的元素适配器
        mWheelRight.setAdapter(new ArrayWheelAdapter<String>(mReadingRooms[0]));
    }
}
