package com.example.unity;

import java.io.File;
import java.util.Calendar;

import com.example.model.Constant;



import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class ServiceImage {

	/**
	 * Description: �޸�ͼƬʱ�� �ļ���·����ʽ����sdcard���
	 * /mnt/sdcard/com.com.stv.supervod.activity/files
	 * ��sdcard����ǣ�/date/date/com.com.stv.supervod.activity/files
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
					// �ļ��Ѿ����ڣ���Ҫɾ��
					if (file != null && file.isFile() && now - file.lastModified() > Constant.interval_day) {
						file.delete();
					}
				}
			}
		}

	}

	/**
	 * Description: �����ļ�����ȡ�������ļ����������Ϊnull��Ҫ���ļ���URL�����ӵ�һ��list�У�
	 * ��list��һ���������ص��߳�,ˢ��ҳ����û�к��������ݡ�
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
					// �޸��ļ���������ʱ��
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
	 * Description: ͨ��URL��ȡͼƬ�ļ�   �޸�ֱ�Ӵ��� ͼƬ����
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
