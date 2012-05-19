package com.exia.android;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// classe comprennant les diff�rantes fonctions et methodes permettant la signature du client

public class Signature extends SurfaceView implements SurfaceHolder.Callback {

	float oldcx = 0;
	float oldcy = 0;
	private Bitmap buffer;
	private static Canvas surface;
	private static Paint paint;
	SurfaceHolder holder;

	// constructeur
	public Signature(Context context) {
		super(context);
		holder = getHolder();
		holder.addCallback(this);

	}

	// constructeur
	public Signature(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = getHolder();
		holder.addCallback(this);

	}

	// constructeur
	public Signature(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		holder = getHolder();
		holder.addCallback(this);

	}

	// Permet le trac� de la signature quand l'utilisateur agit sur l'�cran.
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float cx = event.getX();
		float cy = event.getY();

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			break;

		case MotionEvent.ACTION_MOVE:

			paint.setStrokeWidth(5);
			paint.setColor(0xffff0000);
			surface.drawLine(cx, cy, oldcx, oldcy, paint);
			break;

		}

		oldcx = cx;
		oldcy = cy;

		this.invalidate();
		return true;
	}

	// permet de signaler que la surface a �t� redessin�
	@Override
	public void invalidate() {

		if (holder != null) {
			Canvas c = holder.lockCanvas();
			if (c != null) {

				c.drawBitmap(buffer, 0, 0, null);
				holder.unlockCanvasAndPost(c);

			}
		}
	}

	// cr�ation de l'espace et la surface ou la signature va �tre effectu�e
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		buffer = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		surface = new Canvas(buffer);
		paint = new Paint();
		paint.setColor(0xffffffff);
		surface.drawPaint(paint);
	}

	// cr�ation de l'espace et la surface ou la signature va �tre effectu�e
	public void surfaceCreated(SurfaceHolder holder) {
		buffer = Bitmap.createBitmap(this.getWidth(), this.getHeight(),
				Config.ARGB_8888);
		surface = new Canvas(buffer);
		paint = new Paint();
		paint.setColor(0xffffffff);
		surface.drawPaint(paint);
		this.invalidate();

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	// permet de r�inisialiser la surface de dessin de la signature (permet
	// d'�ffacer la signature)
	public void resetSurface() {
		paint.setColor(0xffffffff);
		surface.drawPaint(paint);
		this.invalidate();
	}

	// permet de sauvegarder la signature
	public void saveBitmap(Context context) {

		String filename = String.valueOf(System.currentTimeMillis());
		ContentValues values = new ContentValues();
		values.put(Images.Media.TITLE, filename);
		values.put(Images.Media.DATE_ADDED, System.currentTimeMillis());
		values.put(Images.Media.MIME_TYPE, "image/jpeg");

		Uri uri = context.getContentResolver().insert(
				Images.Media.EXTERNAL_CONTENT_URI, values);

		try {

			OutputStream outStream = context.getContentResolver()
					.openOutputStream(uri);
			buffer.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();
			Log.d("done", "done");

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

}