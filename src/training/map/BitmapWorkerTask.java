package training.map;

import java.lang.ref.WeakReference;

import training.unrc.R;
import training.util.MapUtil;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
	private final WeakReference<ImageView> imageViewReference;
	// private int data = 0;
	private BitmapFactory.Options options;
	private Context contex;
	private int map;
	private MapUtil mapUtil;
	ProgressDialog dialog;

	public BitmapWorkerTask(ImageView imageView, Context context, int id) {
		// Use a WeakReference to ensure the ImageView can be garbage collected
		imageViewReference = new WeakReference<ImageView>(imageView);
		this.contex = context;
		map = id;
		mapUtil = new MapUtil();
	}

	@Override
	protected void onPreExecute() {
		Resources r = contex.getResources();
		dialog = ProgressDialog
				.show(contex,
						r.getString(R.string.map_activity_bitmap_worker_task_wait_plis),
						r.getString(R.string.map_activity_bitmap_worker_task_loading)
								+ "...");
		super.onPreExecute();
	}

	// Decode image in background.
	@Override
	protected Bitmap doInBackground(Integer... params) {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		Bitmap bm = BitmapFactory.decodeResource(contex.getResources(), map,
				options);
		return mapUtil.convertToMutable(bm);
	}

	// Once complete, see if ImageView is still around and set bitmap.
	// invoked on the UI thread after the background computation finishes. The
	// result of the background computation is passed to this step as a
	// parameter. bitmap es el resultadao de doInbackground
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (imageViewReference != null && bitmap != null) {
			final ImageView imageView = imageViewReference.get();
			if (imageView != null) {
				dialog.dismiss();
				imageView.setImageBitmap(bitmap);
				MapActivity.bitmap = bitmap;
			}
		}
	}
}
