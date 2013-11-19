/**
 * Copyright (c) 2013-2014, Rinc Liu (http://rincliu.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rincliu.library.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class ScrollViewX extends ScrollView{
	private static final long DELAY=100;
	private int currentScroll;
	private Runnable scrollCheckTask;
	
	/**
	 * 
	 * @param context
	 */
	public ScrollViewX(Context context) {
		super(context);
		init();
	}
	
	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public ScrollViewX(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	/**
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ScrollViewX(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		scrollCheckTask = new Runnable() {
			@Override
	        public void run() {
	            int newScroll = getScrollY();
	            if(currentScroll==newScroll){
					if(onScrollListener!=null){
	                    onScrollListener.onScrollStopped();
	                }
				}else{
					if(onScrollListener!=null){
	                    onScrollListener.onScrolling();
	                }
	                currentScroll=getScrollY();
	                postDelayed(scrollCheckTask, DELAY);
	            }
	        }
	    };
	    setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
	            if (event.getAction() == MotionEvent.ACTION_UP) {
	            	currentScroll = getScrollY();
	                postDelayed(scrollCheckTask, DELAY);
	            }
	            return false;
	        }
	    });
	}
	
	public interface OnScrollListener{
		public void onScrollChanged(int x, int y, int oldX, int oldY);
		public void onScrollStopped();
		public void onScrolling();
	}
	
	private OnScrollListener onScrollListener;
	
	/**
	 * 
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener){
		this.onScrollListener=onScrollListener;
	}
	
	@Override
	protected void onScrollChanged(int x, int y, int oldX, int oldY){
		super.onScrollChanged(x, y, oldX, oldY);
		if(onScrollListener!=null){
			onScrollListener.onScrollChanged(x, y, oldX, oldY);
		}
	}
	
	/**
	 * 
	 * @param child
	 * @return
	 */
	public boolean isChildVisible(View child){
		if(child==null){
			return false;
		}
		Rect scrollBounds = new Rect();
		getHitRect(scrollBounds);
		return child.getLocalVisibleRect(scrollBounds);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isAtTop(){
		return getScrollY()<=0;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isAtBottom(){
		return getChildAt(getChildCount()-1).getBottom()+getPaddingBottom()
				==getHeight()+getScrollY();
	}
}