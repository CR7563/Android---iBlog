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
	 * drawable�������õ�map
	 */
	private Map<String, SoftReference<Drawable>> drawableMap = null;
	/**
	 * ��view&task��ɢ��
	 */
	private ConcurrentHashMap<String, DrawableDownloaderTask> view2task = null;
	
	/**
	 * д�ļ�ʱ����������
	 */
	private BlockingQueue<SoftReference<Map<String, Object>>> writeQueue = null;
	/**
	 * д�ļ����߳��Ƿ���ִ�еı��
	 */
	private volatile boolean writeFileFlag = false;
	
	public GetHttpImage() {
		drawableMap = new HashMap<String, SoftReference<Drawable>>();
		view2task = new ConcurrentHashMap<String, DrawableDownloaderTask>(100);
		writeQueue = new LinkedBlockingQueue<SoftReference<Map<String, Object>>>(200);
	}
	
	public void getHttpBitmap(String url , View view){
		//��ʼ��һ��Ĭ�ϵ�ͼƬ
		if (view instanceof ImageView) {
			((ImageView) view).setImageResource(R.drawable.ic_launcher);
		} else {
			view.setBackgroundResource(R.drawable.ic_launcher);
		}
		
		String imageName = getImageNameFromUrl(url);//����ͼƬurlȥ������ͼƬ������
		
		if (imageName!=null) {
			//�ӻ����л�ȡ
			System.out.println("��=========="+drawableMap+"  "+imageName);
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
			//���ֻ��ļ�������� ͼƬ
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
			//�����������涼û��ȡ��ͼƬ�����ʱ������
			forceDownload(imgUrl, view);
		}
		
	}
	/**
	 * Description: ǿ������
	 * @param url
	 * @param imageView
	 */
	private void forceDownload(String url, View view) {
		// ��view&task,��������Ϻ�ҳ��ʱ�ȶ�
		String imgName = getImageNameFromUrl(url);
		if (!view2task.containsKey(imgName)) {
			DrawableDownloaderTask task = new DrawableDownloaderTask(view);
			bindView2Task(imgName, task);
			task.execute(url);
		}
	}
	/**
	 * ����ͼƬurlȥ������ͼƬ������
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
	 * Description: view �󶨵�task
	 * @param view
	 * @param task
	 */
	public void bindView2Task(String imageName, DrawableDownloaderTask task) {
		//ֻ��������������û�� �Ű����������ؽ�ȥ
		if (!view2task.containsKey(imageName)) {
			view2task.put(imageName, task);
		}
	}
	
	/**
	 * Description: �첽���������� Copyright (c) �����Ӳ� All Rights Reserved.
	 * 
	 * @version 1.0 2011-12-29 ����04:10:30 mustang created
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
	 * Description: �첽��������
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
	 * Description: ��д�ļ����������
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
