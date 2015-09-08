package training.map;

import java.lang.ref.WeakReference;

import training.unrc.R;
import training.util.MapUtil;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

class PointBitMapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
	private final WeakReference<ImageView> imageViewReference;
	private MapPoint mapPoint;
	private float px;
	private float py;
	private MapUtil mapUtil;
	Context context;
	ProgressDialog dialog;

	public PointBitMapWorkerTask(Context context, ImageView imageView,
			float px, float py, int selectFloorMap, int currentPavilion) {
		// Use a WeakReference to ensure the ImageView can be garbage collected
		imageViewReference = new WeakReference<ImageView>(imageView);
		this.px = px;
		this.py = py;
		mapPoint = new MapPoint(context, selectFloorMap, currentPavilion);
		mapUtil = new MapUtil();
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		Resources r = context.getResources();
		dialog = ProgressDialog
				.show(context,
						r.getString(R.string.map_activity_bitmap_worker_task_wait_plis),
						r.getString(R.string.map_activity_point_bitmap_worker_task_draw_position)
								+ "...");
		dialog.setIcon(R.drawable.red_pin);
		super.onPreExecute();
	}

	// Decode image in background.
	@Override
	protected Bitmap doInBackground(Integer... params) {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		Bitmap bitmap = null;
		return mapUtil.readMutableBitmap(bitmap);

	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (imageViewReference != null && bitmap != null) {
			final ImageView imageView = imageViewReference.get();
			if (imageView != null) {
				mapPoint.drawPoint(bitmap, px, py);
				MapActivity.bitmap = bitmap;
				imageView.setImageBitmap(bitmap);
				((MapActivity) context).setInfoPlace();
				dialog.dismiss();
			}
		}
	}

}