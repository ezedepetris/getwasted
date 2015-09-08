package training.bus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import training.unrc.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BusActivity extends Activity {

	protected static final String LINE_BUS_POSITION = "BusActivity.LINE_BUS";
	protected static final String BUNDLE = "BusActivity.BUNDLE";
	private static final int BUTTON_ONE = 100;
	private static final int BUTTON_TWO = 111;

	private static final String STATE_SAVE_BUTTON = "BusActivity.STATE_SAVE_BUTTON";
	protected static final int REQUEST_CODE_ADD_SCHEDULE = 10;
	protected static final int REQUEST_CODE_DELETE_SCHEDULE = 11;
	private int mCurrentButton;

	protected static List<MySchedule> schedules;
	private LinearLayout busGrid;
	private LinearLayout mySchedule;
	private GridView gridview;
	private LinearLayout barButtonLayout;
	// arreglo que representa las posiciones de las lineas cargadas en la db
	protected static List<Integer> myLoadLineName;

	// references to our images numbers
	private Integer[] mThumbIds = { R.drawable.red_bus_1,
			R.drawable.green_bus_1, R.drawable.red_bus_2, R.drawable.bus_2,
			R.drawable.bus_3, R.drawable.bus_4, R.drawable.bus_5,
			R.drawable.bus_6, R.drawable.bus_7, R.drawable.red_bus_8,
			R.drawable.green_bus_8, R.drawable.red_bus_9,
			R.drawable.green_bus_9, R.drawable.bus_10, R.drawable.bus_11,
			R.drawable.bus_12, R.drawable.bus_13, R.drawable.bus_14,
			R.drawable.bus_15, R.drawable.bus_16, R.drawable.bus_17,
			R.drawable.bus_18, R.drawable.bus_a, R.drawable.bus_h };

	protected static String[] mNameLine = { "Linea 1 Rojo", "Linea 1 Verde",
			"Linea 2 Rojo", "Linea 2 ", "Linea 3", "Linea 4", "Linea 5",
			"Linea 6", "Linea 7", "Linea 8 Rojo", "Linea 8 Verde",
			"Linea 9 Rojo", "Linea 9 Verde", "Linea 10", "Linea 11",
			"Linea 12", "Linea 13", "Linea 14", "Linea 15", "Linea 16",
			"Linea 17", "Linea 18", "Linea a", "Linea holmberg" };

	/*
	 * adaptador que maneja y crea la grilla con los numeros de lineas
	 */
	public class MyAdapter extends BaseAdapter {

		private Context mContext;

		public MyAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return mThumbIds.length;
		}

		@Override
		public Object getItem(int arg0) {
			return mThumbIds[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View grid;

			if (convertView == null) {
				grid = new View(mContext);
				LayoutInflater inflater = getLayoutInflater();
				grid = inflater.inflate(R.layout.element_grid, parent, false);
			} else {
				grid = (View) convertView;
			}
			ImageView imageView = (ImageView) grid
					.findViewById(R.id.image_part);
			TextView textView = (TextView) grid.findViewById(R.id.text_part);
			imageView.setImageResource(mThumbIds[position]);
			imageView.setAdjustViewBounds(true);
			// textView.setText(mNameLine[position]);
			textView.setGravity(Gravity.CENTER);
			return grid;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_bar_bus);
		mCurrentButton = BUTTON_ONE;
		// layout padre de action_bar_bus
		barButtonLayout = (LinearLayout) findViewById(R.id.action_bar_bus_layout);
		busGrid = (LinearLayout) View.inflate(this, R.layout.bus_grid_view,
				null);
		barButtonLayout.addView(busGrid);
		mySchedule = null;
		myLoadLineName = new ArrayList<Integer>();
		gridview = (GridView) findViewById(R.id.bus_grid_view);
		gridview.setAdapter(new MyAdapter(this));
		markButton(BUTTON_ONE);

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent intent = new Intent(BusActivity.this, PathAndSchedulesButtons.class);
				Bundle args = new Bundle();
				args.putInt(LINE_BUS_POSITION, position);
				intent.putExtra(BUNDLE, args);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(intent);
			}
		});
	}

	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mCurrentButton = savedInstanceState.getInt(STATE_SAVE_BUTTON);
		markButton(mCurrentButton);
		if (mCurrentButton == BUTTON_TWO) {
			if (mySchedule == null) {
				barButtonLayout.removeView(busGrid);
				mySchedule = (LinearLayout) View.inflate(this,
						R.layout.my_schedules, null);
				barButtonLayout.addView(mySchedule);
				loadMySchedules();
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt(STATE_SAVE_BUTTON, mCurrentButton);
		super.onSaveInstanceState(savedInstanceState);
	}

	// boton de horarios comunes
	public void schedules(View view) {
		markButton(BUTTON_ONE);
		mCurrentButton = BUTTON_ONE;
		if (mySchedule != null) {
			barButtonLayout.removeView(mySchedule);
			mySchedule = (LinearLayout) View.inflate(this,
					R.layout.my_schedules, null);
			barButtonLayout.addView(busGrid);
			mySchedule = null;
		}
	}

	/* Checks if external storage is available for read and write */
	private boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	// boton de mis horarios
	public void mySchedules(View view) {
		if (isExternalStorageWritable()) {

			markButton(BUTTON_TWO);
			mCurrentButton = BUTTON_TWO;
			if (mySchedule == null) {
				barButtonLayout.removeView(busGrid);
				mySchedule = (LinearLayout) View.inflate(this,
						R.layout.my_schedules, null);
				barButtonLayout.addView(mySchedule);
				loadMySchedules();
			}
		} else {
			Toast.makeText(this,
					getResources().getString(R.string.not_found_sd),
					Toast.LENGTH_LONG).show();
		}

	}

	private void markButton(int index) {
		Button one = (Button) findViewById(R.id.button_schedules);
		Button two = (Button) findViewById(R.id.button_my_schedules);
		switch (index) {
		case BUTTON_ONE:
			one.setBackgroundColor(getResources().getColor(
					R.color.gray_map_background));
			one.setTextColor(getResources().getColor(
					R.color.gray_button_bar_map));
			two.setBackgroundColor(getResources().getColor(
					R.color.button_bar_selected));
			two.setTextColor(getResources().getColor(
					R.color.gray_light_button_bar_map));
			break;
		case BUTTON_TWO:
			two.setBackgroundColor(getResources().getColor(
					R.color.gray_map_background));
			two.setTextColor(getResources().getColor(
					R.color.gray_button_bar_map));
			one.setBackgroundColor(getResources().getColor(
					R.color.button_bar_selected));
			one.setTextColor(getResources().getColor(
					R.color.gray_light_button_bar_map));
			break;
		}
	}

	// cargo mis horarios almacenados en la base de datos
	private void loadMySchedules() {
		// null si no hay horarios cargados.
		schedules = MySchedule.getAllMySchedules(this);

		LinearLayout contentLineBus = (LinearLayout) findViewById(R.id.content_line_bus);
		// borra todas las columnas,para no repetir
		if (contentLineBus.getChildCount() > 0) {
			contentLineBus.removeAllViews();
		}
		// null ssi no existen datos en la db
		if (schedules != null) {
			MyScheduleColumn myScheduleColumn = new MyScheduleColumn(this,
					contentLineBus);
			myLoadLineName.clear();
			findViewById(R.id.myShedulesHelp).setVisibility(View.GONE);
			for (int i = 0; i < mNameLine.length; i++) {

				List<MySchedule> myScheduleByLine = getAllScheduleByLine(
						mNameLine[i], schedules);
				if (!myScheduleByLine.isEmpty()) {
					myLoadLineName.add(i);
					contentLineBus.addView(myScheduleColumn
							.getBusline(myScheduleByLine));
					contentLineBus.addView(myScheduleColumn.getDrawLine());
				}
			}
		} else
			findViewById(R.id.myShedulesHelp).setVisibility(View.VISIBLE);
	}

	// siempre retorna una lista con los horarios ordenados crecientemente
	protected static List<MySchedule> getAllScheduleByLine(String line,
			List<MySchedule> schedules) {
		MySchedule newSchedule;
		List<MySchedule> newList = new LinkedList<MySchedule>();

		for (int i = 0; i < schedules.size(); i++) {
			newSchedule = schedules.get(i);
			if (newSchedule.getLine().compareTo(line) == 0) {
				((LinkedList<MySchedule>) newList).addFirst(newSchedule);
			}
		}
		return newList;
	}

	// agrega horarios
	public void addSchedule(View view) {
		Intent intent = new Intent(this, AddSchedule.class);
		startActivityForResult(intent, REQUEST_CODE_ADD_SCHEDULE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Resources r = getResources();
		if (requestCode == REQUEST_CODE_ADD_SCHEDULE) {
			if (resultCode == RESULT_OK)
				loadMySchedules();
		}
		if (requestCode == REQUEST_CODE_DELETE_SCHEDULE) {
			if (resultCode == RESULT_OK) {
				loadMySchedules();
				Toast.makeText(this,
						r.getString(R.string.bus_activity_delete_ok),
						Toast.LENGTH_LONG).show();
			}
			if (resultCode == RESULT_FIRST_USER) {
				Toast.makeText(
						this,
						r.getString(R.string.bus_activity_delete_element_not_found),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	// elimina horarios
	public void deleteSchedule(View view) {
		Intent intent = new Intent(this, DeleteSchedule.class);
		startActivityForResult(intent, REQUEST_CODE_DELETE_SCHEDULE);
	}

}