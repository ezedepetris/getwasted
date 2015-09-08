package training.map;

import training.unrc.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class MapPoint {

	private Canvas canvas;
	private Context context;
	Bitmap icon;
	double px;
	double py;
	int currentPavilion;
	int floorMap;

	public MapPoint(Context context, int floorMap, int currentPavilion) {
		canvas = new Canvas();
		this.context = context;
		this.currentPavilion = currentPavilion;
		this.floorMap = floorMap;
	}

	public void drawPoint(Bitmap bitmap, float px, float py) {
		canvas.setBitmap(bitmap);
		loadMap();
		Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.red_pin);
		canvas.drawBitmap(icon, px - icon.getWidth() / 2,
				py - icon.getHeight(), null);
	}

	private void loadMap() {
		float px = 0, py = 0;
		int floor = 0;
		boolean hasDraw = true;
		switch (currentPavilion) {

		case (MapActivity.PAVILION_1):
			px = 1176;
			py = 1146;
			floor = R.drawable.pavilion_one_up;
			if (floorMap == MapActivity.LOAD_DOWNSTAIRS)
				floor = R.drawable.pavilion_one_down;
			break;
		case (MapActivity.PAVILION_2):
			px = 215;
			py = 834;
			floor = R.drawable.pavillion_two_three_up;
			if (floorMap == MapActivity.LOAD_DOWNSTAIRS)
				floor = R.drawable.pavillion_two_three_down;
			break;
		case (MapActivity.PAVILION_3):
			px = 215;
			py = 834;
			floor = R.drawable.pavillion_two_three_up;
			if (floorMap == MapActivity.LOAD_DOWNSTAIRS)
				floor = R.drawable.pavillion_two_three_down;
			break;
		default:
			px = 215;
			py = 834;
			if (floorMap == MapActivity.LOAD_DEFAULT)
				hasDraw = false;
			floor = R.drawable.pavillion_two_three_down;
			break;
		}

		if (hasDraw) {
			Bitmap map = BitmapFactory.decodeResource(context.getResources(),
					floor);
			canvas.drawBitmap(map, px, py, null);
		}

		if (hasDraw && currentPavilion == MapActivity.MORE_PLACES) {
			Bitmap map = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.pavilion_one_down);
			canvas.drawBitmap(map, 1176, 1146, null);
		}
	}
}
