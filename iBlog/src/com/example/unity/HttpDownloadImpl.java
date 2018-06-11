package com.example.unity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class HttpDownloadImpl {

	/**
	 * Description:ÏÂÔØÒ»ÕÅÍ¼Æ¬
	 * @param activity
	 * @throws IOException
	 */
	public Drawable downloadImageByUrl(String url) throws IOException {
		Drawable drawable = null;
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		// try {
		if (!TextUtils.isEmpty(url)) {
			String[] urls = url.split("/");
			String srcName = "";
			if (urls.length > 0) {
				srcName = urls[urls.length - 1];
			}
			URL imageurl = new URL(url);
			conn = (HttpURLConnection) imageurl.openConnection();
			conn.setConnectTimeout(2 * 1000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			if (conn.getResponseCode() == 200) {
				inputStream = new BufferedInputStream(conn.getInputStream());
				drawable = Drawable.createFromStream(inputStream, srcName);
			}
		}
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		if (conn != null) {
			conn.disconnect();
		}
		if (inputStream != null) {
			inputStream.close();
		}
		// }
		return drawable;
	}

}
