package com.example.unity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.example.activity.R;
import com.example.model.Constant;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

public class GetHttpImage {
	
	/**
	 * drawable的软引用的map
	 */
	private Map<String, SoftReference<Drawable>> drawableMap = null;
	/**
	 * 绑定view&task的散列
	 */
	private ConcurrentHashMap<String, DrawableDownloaderTask> view2task = null;
	
	/**
	 * 写文件时的阻塞队列
	 */
	private BlockingQueue<SoftReference<Map<String, Object>>> writeQueue = null;
	/**
	 * 写文件的线程是否在执行的标记
	 */
	private volatile boolean writeFileFlag = false;
	
	public GetHttpImage() {
		drawableMap = new HashMap<String, SoftReference<Drawable>>();
		view2task = new ConcurrentHashMap<String, DrawableDownloaderTask>(100);
		writeQueue = new LinkedBlockingQueue<SoftReference<Map<String, Object>>>(200);
	}
	
	public void getHttpBitmap(String url , View view){
		//初始化一个默认的图片
		if (view instanceof ImageView) {
			((ImageView) view).setImageResource(R.drawable.ic_launcher);
		} else {
			view.setBackgroundResource(R.drawable.ic_launcher);
		}
		
		String imageName = getImageNameFromUrl(url);//根据图片url去解析出图片的名字
		
		if (imageName!=null) {
			//从缓存中获取
			System.out.println("空=========="+drawableMap+"  "+imageName);
			if (drawableMap.containsKey(imageName)) {
	            SoftReference<Drawable> softReference = drawableMap.get(imageName);
	            Drawable drawable = softReference.get();
	            if (drawable != null&&view != null) {
	            	if (view instanceof ImageView) {
	    				((ImageView) view).setImageDrawable(drawable); 
	    			} else {
	    				view.setBackgroundDrawable(drawable);
	    			}
	            	//Animation animation = AnimationUtils.loadAnimation(context, R.anim.alpha);
	            	//view.startAnimation(animation);
	            	//LayoutAnimationController animationController = new LayoutAnimationController(animation);
	            	return;
	            }
	        }
			//从手机文件里面查找 图片
			Drawable db = ServiceImage.getImageDrawableByUrl(imageName);
			if (db != null && view != null) {
				if (view instanceof ImageView) {
					((ImageView) view).setImageDrawable(db); 
				} else {
					view.setBackgroundDrawable(db);
				}
				//Animation animation = AnimationUtils.loadAnimation(context, R.anim.alpha);
            	//view.startAnimation(animation);
				drawableMap.put(imageName, new SoftReference<Drawable>(db));
				return;
			}
			String imgUrl =url;
			//上面两个里面都没有取到图片，这个时候下载
			forceDownload(imgUrl, view);
		}
		
	}
	/**
	 * Description: 强制下载
	 * @param url
	 * @param imageView
	 */
	private void forceDownload(String url, View view) {
		// 绑定view&task,带下载完毕后画页面时比对
		String imgName = getImageNameFromUrl(url);
		if (!view2task.containsKey(imgName)) {
			DrawableDownloaderTask task = new DrawableDownloaderTask(view);
			bindView2Task(imgName, task);
			task.execute(url);
		}
	}
	/**
	 * 根据图片url去解析出图片的名字
	 * @param imageUrl
	 * @return
	 */
	private String getImageNameFromUrl(String imageUrl){
		String[] urls = null;
		if (!TextUtils.isEmpty(imageUrl)) {
			urls = imageUrl.split("/");
		}
		if (urls != null && urls.length > 0) {
			String imgname = urls[urls.length - 1];
			imgname = imgname.split("\\.")[0];
			return imgname;
		}else {
			return null;
		}
	}
	
	
	/**
	 * Description: view 绑定到task
	 * @param view
	 * @param task
	 */
	public void bindView2Task(String imageName, DrawableDownloaderTask task) {
		//只有下载任务里面没有 才把这个任务加载进去
		if (!view2task.containsKey(imageName)) {
			view2task.put(imageName, task);
		}
	}
	
	/**
	 * Description: 异步的下载任务 Copyright (c) 永新视博 All Rights Reserved.
	 * 
	 * @version 1.0 2011-12-29 下午04:10:30 mustang created
	 */
	class DrawableDownloaderTask extends AsyncTask<String, Void, Drawable> {
		private String url;
		private View view;
		private String imgName = "";

		public DrawableDownloaderTask(View view) {
			this.view = view;
		}

		@Override
		protected Drawable doInBackground(String... params) {
			url = params[0];
			imgName = getImageNameFromUrl(url);
			try {
				return asyncDownloadDrawable(url);
			} catch (IOException e) {
				drawableMap.remove(imgName);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Drawable result) {
			view2task.remove(imgName);
			if (view != null && result != null) {
				if (view instanceof ImageView) {
					((ImageView) view).setImageDrawable(result);
				} else {
					view.setBackgroundDrawable(result);
				}
				//Animation animation = AnimationUtils.loadAnimation(context, R.anim.alpha);
            	//view.startAnimation(animation);
			}
			if (result != null) {
				drawableMap.put(imgName, new SoftReference<Drawable>(result));
			}
		}
	}
	
	/**
	 * Description: 异步下载任务
	 * @param url
	 * @return
	 * @throws IOException 
	 */
	public Drawable asyncDownloadDrawable(String url) throws IOException {
		HttpDownloadImpl httpDownloader = new HttpDownloadImpl();
		Drawable drawable = null;
		if (!TextUtils.isEmpty(url)) {
			drawable = httpDownloader.downloadImageByUrl(url);
			String filename = "";
			filename = getImageNameFromUrl(url);
			if (filename == null) {
				filename = "";
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("filename", filename);
			map.put("drawable", drawable);
			writePoster2File(map);
		}

		return drawable;

	}
	
	/**
	 * Description: 把写文件单独提出来
	 * 
	 * @param map
	 */
	public void writePoster2File(Map<String, Object> map) {
		writeQueue.add(new SoftReference<Map<String, Object>>(map));
		final String dpath = Constant.download_dir;
		if (writeFileFlag == false) {
			new Thread() {
				@Override
				public void run() {
					while (writeQueue.size() > 0) {
						writeFileFlag = true;
						Map<String, Object> map;
						try {
							SoftReference<Map<String, Object>> srf = (SoftReference<Map<String, Object>>) writeQueue.take();
							map = srf.get();
							if (map != null) {
								String filename = (String) map.get("filename");
								Drawable drawable = (Drawable) map.get("drawable");
								BitmapDrawable bd = null;
								if (drawable != null) {
									bd = (BitmapDrawable) drawable;
								}
								Bitmap bm = null;
								if (bd != null) {
									bm = bd.getBitmap();
								}
								if (bm != null) {
									FileOutputStream fos = null;
									try {
										fos = new FileOutputStream(new File(dpath, filename));
										bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									}finally{
										try {
											if(fos != null){
												fos.flush();
												fos.close();
											}
										} catch (IOException e) {
											e.printStackTrace();
										}										
									}
								}
							}
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					writeFileFlag = false;
				}
			}.start();
		}
	}

}
