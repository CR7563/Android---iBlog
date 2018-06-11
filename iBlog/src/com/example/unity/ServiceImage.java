package com.example.unity;

import java.io.File;
import java.util.Calendar;

import com.example.model.Constant;



import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class ServiceImage {

	/**
	 * Description: 修改图片时间 文件夹路径格式：有sdcard情况
	 * /mnt/sdcard/com.com.stv.supervod.activity/files
	 * 无sdcard情况是：/date/date/com.com.stv.supervod.activity/files
	 * @param act
	 */
	public static void deleteTimeoutImage(Activity act) {
		long now = Calendar.getInstance().getTimeInMillis();
		File dir = new File(Constant.download_dir);
		String[] filesnames = dir.list();
		if (filesnames != null && filesnames.length > 0) {
			for (String name : filesnames) {
				if (!TextUtils.isEmpty(name)) { 
					File file = new File(dir.getAbsoluteFile(), name);
					// 文件已经过期，需要删除
					if (file != null && file.isFile() && now - file.lastModified() > Constant.interval_day) {
						file.delete();
					}
				}
			}
		}

	}

	/**
	 * Description: 根据文件名字取出本地文件，如果返回为null需要把文件的URL单独扔到一个list中，
	 * 对list起一个单独下载的线程,刷新页面中没有海报的数据。
	 *
	 * @param imgName
	 * @param act
	 * @return
	 */
	public static Drawable getImageDrawableByName(String imgName) {
		String path = Constant.download_dir;
		if (!TextUtils.isEmpty(imgName)) { 
			File file = new File(path, imgName);
			if (file.isFile()) {
				Drawable da = Drawable.createFromPath(path + "/" + imgName);
				if( da != null){
					// 修改文件的最后访问时间
					file.setLastModified(System.currentTimeMillis());
					return da;
				}
			}else{
				return null;
			}
		}
		return null;
	}
	/**
	 * Description: 通过URL获取图片文件   修改直接传输 图片名字
	 * 
	 * 
	 * @param url
	 * @return
	 */
	public static Drawable getImageDrawableByUrl(String url) {		
		Drawable da = getImageDrawableByName(url);
		return da;
	}

}
