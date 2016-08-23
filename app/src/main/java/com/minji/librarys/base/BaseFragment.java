package com.minji.librarys.base;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minji.librarys.base.ContentPage.ResultState;
import com.minji.librarys.uitls.ViewsUitls;

import java.util.List;

/**
 * T为数据在集合中的Bean类型
 *
 */
public abstract class BaseFragment<T> extends DialogFragment {

	private ContentPage contentPage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		contentPage = new ContentPage(ViewsUitls.getContext()) {

			@Override
			public View onCreateSuccessView() {
				// 注意是调用谁的方法
				return BaseFragment.this.onCreateSuccessView();
			}

			@Override
			public ResultState onLoad() {
				return BaseFragment.this.onLoad();
			}

		};

		onSubClassOnCreateView();

		return contentPage;
	}

	protected abstract void onSubClassOnCreateView();

	public ContentPage getContetnPage() {
		return contentPage;
	}

	public void showPageHM(ResultState resultState) {
		contentPage.showPageHM(resultState);
	}

	/**
	 * 返回加载成功对应的界面
	 *
	 * @return
	 */
	protected abstract View onCreateSuccessView();

	/**
	 * 加载网络数据的方法 在此抽象方法中子类最后必须调用父类的chat()方法
	 *
	 * @return
	 */
	protected abstract ResultState onLoad();

	/**
	 * 这个方法是创建了Fragment后立即调用并开启的方法。如果不调用就一直是加载界面显示，且是没有网络请求的
	 * 给子类调用间接调用到ContentPage的网络请求，了加载网络数据，并更新界面
	 */
	public void loadDataAndRefresh() {
		if (contentPage != null) {
			contentPage.loadDataAndRefresh();
		}
	}

	/**
	 * 被子类用来调用网络请求的结果，并返回数据给ContentPage中给其进行判断
	 * ResultState这个返回结果是一个枚举，每个枚举都是int类型，且在Contentpage中onLoad()的返回结果赋值给了private
	 * int state;
	 * */
	public ResultState chat(List<T> data2) {
		if (data2 == null) {
			// 网络请求后承载数据的集和为空说明网络请求失败
			return ResultState.STATE_ERROR;
		} else {
			if (data2.size() == 0) {
				// 网络请求后承载数据的集为和不为空但是没有数据说明网络请求成功，但不用显示数据
				return ResultState.STATE_EMPTY;
			} else {
				// 成功且有数据
				return ResultState.STATE_SUCCESS;
			}
		}
	}

}
