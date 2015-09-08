package training.bus;

import training.unrc.R;
import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Despues de cliquear un horario esta activity es la que manejas los horarios
 * que se muestran (incluye si van los horarios de sab-dom)
 */
public class BusSchedules extends Activity {

	private static final int BUTTON_ONE = 10;
	private static final int BUTTON_TWO = 11;
	private static final String STATE_SAVE_SCHEDULES = "BusSchedules.mCurrentShedules";
	private static final String STATE_SAVE_BUTTON = "BusSchedules.mCurrentButton";
	private int positionOnGrid;
	private int imageLoading;
	private ImageView imageView;
	// posicion del horario que se eligio en la grilla
	private int mCurrentSchedules;
	// si es lun-vier o sab-dom
	private int mCurrentButton;

	protected Integer[] schedules = { R.drawable.line_1_red,
			R.drawable.line_1_green, R.drawable.line_2_red_mon_fri,
			R.drawable.line_2_mon_fri, R.drawable.line_3, R.drawable.line_4,
			R.drawable.line_5_mon_fri, R.drawable.line_6, R.drawable.line_7,
			R.drawable.line_8_red, R.drawable.line_8_green,
			R.drawable.line_9_red, R.drawable.line_9_green, R.drawable.line_10,
			R.drawable.line_11, R.drawable.line_12, R.drawable.line_13_mon_fri,
			R.drawable.line_14, R.drawable.line_15, R.drawable.line_16,
			R.drawable.line_17, R.drawable.line_18_mon_fri, R.drawable.line_a,
			R.drawable.line_h_mon_fri };

	protected Integer[] specialSchedules = { null, null,
			R.drawable.line_2_red_sat_sun, R.drawable.line_2_sat_sun, null,
			null, R.drawable.line_5_sat_sun, null, null, null, null, null,
			null, null, null, null, R.drawable.line_13_sat_sun, null, null,
			null, null, R.drawable.line_18_sat_sun, null,
			R.drawable.line_h_sat_sun };

	/*
	 * conjunto de posiciones de botones en la grilla que tienen habilitado los
	 * horarios especiales ** linea 2 posicion en grilla : 2 y 3 linea 5
	 * posicion en grilla : 6 linea 13 posicion en grilla : 16
	 */
	private int[] shedulesSpecialEnabled = { 2, 3, 6, 16, 21, 23 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus_schedules);
		Bundle args = getIntent().getBundleExtra(BusActivity.BUNDLE);
		positionOnGrid = args.getInt(BusActivity.LINE_BUS_POSITION);
		imageView = (ImageView) findViewById(R.id.schudeles_bus);
		imageLoading = selectImage(positionOnGrid);
		imageView.setImageResource(imageLoading);
		mCurrentSchedules = positionOnGrid;
		mCurrentButton = BUTTON_ONE;
		markButton(BUTTON_ONE);
		enableSpecialSchedules(mCurrentSchedules);
	}

	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mCurrentSchedules = savedInstanceState.getInt(STATE_SAVE_SCHEDULES);
		mCurrentButton = savedInstanceState.getInt(STATE_SAVE_BUTTON);
		enableSpecialSchedules(mCurrentSchedules);
		markButton(mCurrentButton);
		if (mCurrentButton == BUTTON_TWO)
			imageLoading = selectSpecialImage(mCurrentSchedules);
		else
			imageLoading = selectImage(mCurrentSchedules);
		imageView.setImageResource(imageLoading);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt(STATE_SAVE_BUTTON, mCurrentButton);
		savedInstanceState.putInt(STATE_SAVE_SCHEDULES, mCurrentSchedules);
		super.onSaveInstanceState(savedInstanceState);
	}

	/*
	 * Listener boton lun-viernes
	 */
	public void specialSchedules(View view) {
		markButton(BUTTON_TWO);
		mCurrentButton = BUTTON_TWO;
		imageLoading = selectSpecialImage(positionOnGrid);
		imageView.setImageResource(imageLoading);
	}

	/*
	 * Listener boton sab-dom-feriado
	 */
	public void commonSchedules(View view) {
		markButton(BUTTON_ONE);
		mCurrentButton = BUTTON_ONE;
		imageLoading = selectImage(positionOnGrid);
		imageView.setImageResource(imageLoading);
	}

	private int selectImage(int positionOnGrid) {
		return schedules[positionOnGrid];
	}

	private int selectSpecialImage(int positionOnGrid) {
		return specialSchedules[positionOnGrid];
	}

	private void markButton(int index) {
		Button one = (Button) findViewById(R.id.button_common_schedules);
		Button two = (Button) findViewById(R.id.button_special_schedules);
		switch (index) {
		case BUTTON_ONE:
			one.setBackgroundColor(getResources().getColor(
					R.color.button_bar_selected));
			one.setTextColor(getResources().getColor(R.color.white));
			two.setBackgroundColor(color.darker_gray);
			two.setTextColor(getResources().getColor(R.color.black));
			break;
		case BUTTON_TWO:
			two.setBackgroundColor(getResources().getColor(
					R.color.button_bar_selected));
			two.setTextColor(getResources().getColor(R.color.white));
			one.setBackgroundColor(color.darker_gray);
			one.setTextColor(getResources().getColor(R.color.black));
			break;
		}
	}

	/*
	 * @param index representa el boton que se elijio en la grilla. Desabilita
	 * al el boton de sab.dom y fer si no posee.
	 */
	private void enableSpecialSchedules(int index) {
		boolean belongs = false;
		for (int i = 0; i < shedulesSpecialEnabled.length; i++) {
			if (shedulesSpecialEnabled[i] == index)
				belongs = true;
		}
		if (!belongs)
			// tengo que bloquear el boton de feriado,sabado y domingo
			findViewById(R.id.button_special_schedules).setVisibility(
					Button.INVISIBLE);
	}
}
