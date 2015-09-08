package training.map;

import training.unrc.R;
import training.util.MapUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MapActivity extends Activity {

	public static final int PAVILION_1 = 1;
	public static final int PAVILION_2 = 2;
	public static final int PAVILION_3 = 3;
	public static final int MORE_PLACES = 0;

	public static final String SAVE_STATE_PAVILION = "MapActivity.currentPavilion";
	public static final String SAVE_STATE_PLACE = "MapActivity.currentPlace";
	public static final String SAVE_STATE_ITEM = "MapActivity.currentItem";

	public static final int LOAD_DOWNSTAIRS = 0;
	public static final int LOAD_FIRST_FLOOR = 2;
	public static final int LOAD_DEFAULT = 1;

	protected static Bitmap bitmap;
	private static ScaleImageView img;
	private Resources mRes;
	private MapUtil mapUtil;
	private String place;
	private TextView infoPlace;
	private int currentPavilion = -1;
	private String currentPlace = "";
	private int currentItem = -1;
	private int placeInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		img = (ScaleImageView) findViewById(R.id.imageMap);
		infoPlace = (TextView) findViewById(R.id.information_map);
		Typeface font = Typeface.createFromAsset(this.getAssets(),
				"LondonBetween.ttf");
		infoPlace.setTypeface(font);
		initialite();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// borro el mapa que se crea en sd para trabajar.
		mapUtil.deleteFile();
		if (bitmap != null) {
			bitmap.recycle();
			System.gc();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private void initialite() {
		// Loading default map
		infoPlace.setText(getResources().getString(R.string.map_activity_default));
		loadInitialBitmap(R.drawable.general_unrc, img);
		mRes = getResources();
		mapUtil = new MapUtil();
	}

	/* Begin Listener the buttons in UI */

	public void selectedPavilion1(View view) {
		String[] classRoom = mRes.getStringArray(R.array.classroom_pavilion_1);
		showPlace(classRoom, PAVILION_1);
	}

	public void selectedPavilion2(View view) {
		String[] classRoom = mRes.getStringArray(R.array.classroom_pavilion_2);
		showPlace(classRoom, PAVILION_2);

	}

	public void selectedPavilion3(View view) {
		String[] classRoom = mRes.getStringArray(R.array.classroom_pavilion_3);
		showPlace(classRoom, PAVILION_3);
	}

	public void selectedMorePlaces(View view) {
		String[] otherRooms = mRes.getStringArray(R.array.more_places);
		showPlace(otherRooms, MORE_PLACES);
	}

	/* End Listener the buttons in UI */

	/*
	 * @param places {@link String[]}
	 * 
	 * @param selector {@link Integer} key representante de la lista a mostrar.
	 */
	private void showPlace(final String[] places, int selector) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// set title for dialoga and map
		switch (selector) {
		case PAVILION_1:
			builder.setTitle(R.string.msj_dialog_pavilion_1);
			currentPavilion = PAVILION_1;
			break;
		case PAVILION_2:
			builder.setTitle(R.string.msj_dialog_pavilion_2);
			currentPavilion = PAVILION_2;
			break;
		case PAVILION_3:
			builder.setTitle(R.string.msj_dialog_pavilion_3);
			currentPavilion = PAVILION_3;
			break;
		case MORE_PLACES:
			builder.setTitle(R.string.msj_dialog_more_places);
			currentPavilion = MORE_PLACES;
			break;
		default:
			builder.setTitle("");
			currentPavilion = -1;
			break;
		}
		// end set title

		builder.setItems(places, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				currentItem = item;
				currentPlace = places[item];
				drawPositionOnMap(currentPavilion, currentPlace, currentItem);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * @param button
	 *            {@link Integer} boton que se hizo click (ex. pavellon 1 ,
	 *            pavellon 2...)
	 * @param place2
	 *            {@link String} lugar que se quiere buscar ex. aula 11 del
	 *            pavellon 1 pero solo tiene 11.
	 * @param item
	 *            {@link Integer} posicion en el arreglo que se clikeo. ex el
	 *            aula 11 esta en la 3 posicion entonces biene 3
	 */
	public void drawPositionOnMap(int button, String place2, int item) {
		int mapFloorOnDraw = selectFloorBitMap(button, place2, item);
		int itemPoint = item;
		int selectorPoint = button;
		PointF point = mapUtil.getClassRoom(itemPoint, selectorPoint);
		float px = point.x;
		float py = point.y;
		recycleBitMap();
		PointBitMapWorkerTask task = new PointBitMapWorkerTask(this, img, px,
				py, mapFloorOnDraw, button);
		task.execute();
	}

	private void loadInitialBitmap(int resId, ImageView imageView) {
		recycleBitMap();
		BitmapWorkerTask task = new BitmapWorkerTask(imageView, this, resId);
		task.execute();
	}

	private void recycleBitMap() {
		if (bitmap != null) {
			bitmap.recycle();
			System.gc();
		}
	}

	/**
	 * @param button
	 * @param place2
	 * @param item
	 * @return map {@link Integer} indica que drawable se tiene que seleccionar
	 *         (mapa de arriva,abajo o comun)
	 */
	private int selectFloorBitMap(int button, String place2, int item) {
		int map = LOAD_DOWNSTAIRS;
		place = place2;
		placeInfo = LOAD_DOWNSTAIRS;
		if (button == MORE_PLACES) {
			if (mapUtil.belongUpStairs(button, place, mRes))
				map = LOAD_DEFAULT;
			placeInfo = LOAD_DEFAULT;
		} else {
			if (mapUtil.belongUpStairs(button, place, mRes)) {
				map = LOAD_FIRST_FLOOR;
				placeInfo = LOAD_FIRST_FLOOR;
			}
		}
		return map;
	}

	public void setInfoPlace() {
		Resources r = getResources();
		String text;
		switch (placeInfo) {
		case LOAD_DOWNSTAIRS:
			text = r.getString(R.string.map_activity_first_floor) + " | "
					+ r.getString(R.string.map_activity_pavillion) + ": "
					+ currentPavilion + " | "
					+ r.getString(R.string.map_activity_first_classroom) + ": "
					+ place;
			infoPlace.setText(text);
			break;
		case LOAD_DEFAULT:
			text = r.getString(R.string.map_activity_location_of) + ": "
					+ place;
			infoPlace.setText(text);
			break;
		case LOAD_FIRST_FLOOR:
			text = r.getString(R.string.map_activity_second_floor) + " | "
					+ r.getString(R.string.map_activity_pavillion) + ": "
					+ currentPavilion + " | "
					+ r.getString(R.string.map_activity_first_classroom) + ": "
					+ place;
			infoPlace.setText(text);
			break;
		}
	}
}
