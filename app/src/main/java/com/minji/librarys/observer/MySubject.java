package com.minji.librarys.observer;

/**
 * 获取具体的单例对象
 * */
public class MySubject extends AbstractSubject {

	private static volatile MySubject mySubject = null;

	private MySubject() {
	}

	public static MySubject getInstance() {
		if (mySubject == null) {
			synchronized (MySubject.class) {
				if (mySubject == null) {
					mySubject = new MySubject();
				}
			}
		}
		return mySubject;
	}

	/**
	 * 要想通知所有被通知这 ，其实可以直接调用notifyObservers方法
	 * 不一定需要将notifyObservers方法写在operation中
	 * operation方法只是用于自身这个类
	 * */
	@Override
	public void operation(int mPosition, int distinguishBeNotified, int cancelOrderBid) {
		System.out.println("update self!");
		notifyObservers(mPosition,distinguishBeNotified, cancelOrderBid);
	}

}
