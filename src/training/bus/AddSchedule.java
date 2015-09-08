package training.bus;

import java.util.Calendar;

import training.unrc.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddSchedule extends FragmentActivity {

	private static EditText addLine;
	private static EditText addTime;
	private EditText addPlace;
	private String currentBusline;

	private String[] DAY = { "Lun-Vie", "Sab-Dom" };
	private String currentDay;
	private EditText addDay;

	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		private String timePiker;

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hour, int minute) {
			// Do something with the time chosen by the user
			String hourString = hour + "";
			String minuteString = minute + "";
			if (hour < 10)
				hourString = "0" + hour;
			if (minute < 10)
				minuteString = "0" + minute;

			timePiker = hourString + ":" + minuteString;
			addTime.setText(timePiker);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_my_schedule);

		addTime = (EditText) findViewById(R.id.add_schedule);
		addLine = (EditText) findViewById(R.id.add_line);
		addPlace = (EditText) findViewById(R.id.add_place);
		addDay = (EditText) findViewById(R.id.add_day);

		final AlertDialog.Builder builderLine = new AlertDialog.Builder(this);
		addLine.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				builderLine.setTitle(getResources().getString(
						R.string.bus_delete_and_add_select_line));
				builderLine.setItems(BusActivity.mNameLine,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								currentBusline = BusActivity.mNameLine[item];
								addLine.setText(currentBusline);
							}
						});
				AlertDialog alert = builderLine.create();
				alert.show();
			}
		});

		addTime.setOnClickListener(new OnClickListener() {
			DialogFragment newFragment;

			@Override
			public void onClick(View v) {
				newFragment = new TimePickerFragment();
				newFragment.show(getSupportFragmentManager(), "timePicker");
			}
		});

		final AlertDialog.Builder builderDay = new AlertDialog.Builder(this);
		addDay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				builderDay.setTitle(getResources().getString(
						R.string.bus_delete_and_add_select_line));
				builderDay.setItems(DAY, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int item) {
						currentDay = DAY[item];
						addDay.setText(currentDay);
					}
				});
				AlertDialog alert = builderDay.create();
				alert.show();
			}
		});
	}// end onCreate

	public void saveLineSchedule(View view) {
		String line = addLine.getText().toString();
		String time = addTime.getText().toString();
		String day = addDay.getText().toString();
		String place = addPlace.getText().toString();
        place= place.replaceAll("(\\r|\\n)", "");
		place.trim();
		if (place.length() > 12)
			place = place.substring(0, 12);

		if (line.compareTo("") != 0 && time.compareTo("") != 0
				&& day.compareTo("") != 0) {
			MySchedule mySchedule = new MySchedule(line, time, day, place, this);
			try {
				mySchedule.save();
				Toast.makeText(this,
						getResources().getString(R.string.message_save),
						Toast.LENGTH_SHORT).show();
				this.setResult(RESULT_OK);
				this.finishActivity(BusActivity.REQUEST_CODE_ADD_SCHEDULE);
				this.finish();
			} catch (ExceptionExistSchedule e) {
				Toast.makeText(this,
						getResources().getString(R.string.message_exception),
						Toast.LENGTH_SHORT).show();
			}

		} else {

			Toast.makeText(this,
					getResources().getString(R.string.message_fill_fields),
					Toast.LENGTH_SHORT).show();
		}
	}

}
