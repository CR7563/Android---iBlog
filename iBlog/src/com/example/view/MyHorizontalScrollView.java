package com.example.view;

import com.example.activity.MainActivity;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class MyHorizontalScrollView extends HorizontalScrollView{

	private int leftWidth;
	
	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setLeftWidth(int leftWidth){
		this.leftWidth=leftWidth;
	}
	 
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//如果滑块在零的位置，且点击事件在菜单范围内，则不在上层消耗点击事件
		int offset=computeHorizontalScrollOffset();
		if(offset==0){
			if(ev.getRawX()<leftWidth){				
				return false;
			}
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_UP:
			if(offset <= leftWidth/2 && offset > 0){
				Runnable runnable = new Runnable(){
					public void run(){
						MyHorizontalScrollView.this.smoothScrollTo(0, 0);
					}
				};
				Handler handler = new Handler();
				handler.post(runnable);
			}
			else if(offset > leftWidth/2 && offset < leftWidth){
				Runnable runnable = new Runnable(){
					public void run(){
						MyHorizontalScrollView.this.smoothScrollTo(leftWidth, 0);
					}
				};
				Handler handler = new Handler();
				handler.post(runnable);
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		if(l==0){
			MainActivity.flagMove=true;
		}else if(l==leftWidth){
			MainActivity.flagMove=false;
		}
	}
	
}
