package training.bus;

import java.util.LinkedList;
import java.util.List;

import training.unrc.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class DeleteSchedule extends Activity {

	private static final String STATE_SAVE_LIST = "DeleteSchedule.STATE_SAVE_LIST";
	private static final int SELECTED = 1;
	private static final int NO_SELECTED = 0;
	private static final String STATE_SAVE_BUS_LINE = "DeleteSchedule.STATE_SAVE_BUS_LINE";
	private EditText deleteLine;
	private ListView deleteList;
	private List<MySchedule> schedules;
	private List<MySchedule> newList;
	private String[] mNameLine;
	private List<ModelListCheck> list = null;
	private ArrayAdapter<ModelListCheck> adapter;
	private String currentBusline;
	private boolean deleteOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete_my_schedule);
		deleteOk = true;
		deleteLine = (EditText) findViewById(R.id.select_line);
		deleteList = (ListView) findViewById(R.id.delete_list);

		newList = new LinkedList<MySchedule>();
		this.schedules = BusActivity.schedules;
		if (this.schedules == null) {
			this.setResult(RESULT_FIRST_USER);
			this.finishActivity(BusActivity.REQUEST_CODE_DELETE_SCHEDULE);
			this.finish();
		}
		mNameLine = new String[BusActivity.myLoadLineName.size()];

		for (int i = 0; i < mNameLine.length; i++) {
			mNameLine[i] = BusActivity.mNameLine[BusActivity.myLoadLineName
					.get(i)];
		}

		final AlertDialog.Builder builderLine = new AlertDialog.Builder(this);
		deleteLine.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				builderLine.setTitle(getResources().getString(
						R.string.bus_delete_and_add_select_line));
				builderLine.setItems(mNameLine,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int item) {
								currentBusline = mNameLine[item];
								deleteLine.setText(currentBusline);

								newList = BusActivity.getAllScheduleByLine(
										currentBusline, schedules);

								list = toModelListCheck(newList);
								adapter = new InteractiveArrayAdapter(
										DeleteSchedule.this, list);
								deleteList.setAdapter(adapter);

							}
						});
				AlertDialog alert = builderLine.create();
				alert.show();
			}
		});

	}

	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		int[] toCheck = savedInstanceState.getIntArray(STATE_SAVE_LIST);
		String busLine = savedInstanceState.getString(STATE_SAVE_BUS_LINE);
		if (toCheck != null && busLine != null) {
			currentBusline = busLine;
			newList = BusActivity.getAllScheduleByLine(busLine,
					BusActivity.schedules);
			list = toModelListCheck(newList);

			for (int i = 0; i < toCheck.length; i++) {
				boolean bool = (toCheck[i] == SELECTED) ? true : false;
				list.get(i).setSelected(bool);
			}
			adapter = new InteractiveArrayAdapter(DeleteSchedule.this, list);
			deleteList.setAdapter(adapter);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		if (list != null) {
			int[] toSave = new int[list.size()];
			int j = 0;
			for (ModelListCheck i : list) {
				if (i.isSelected())
					toSave[j] = SELECTED;
				else
					toSave[j] = NO_SELECTED;
				j++;
			}
			savedInstanceState.putIntArray(STATE_SAVE_LIST, toSave);
			savedInstanceState.putString(STATE_SAVE_BUS_LINE, currentBusline);
		}
		super.onSaveInstanceState(savedInstanceState);
	}

	// transforma los datos MySchedule a una lista chequeable ModelListChech
	private List<ModelListCheck> toModelListCheck(List<MySchedule> list) {
		List<ModelListCheck> result = new LinkedList<ModelListCheck>();
		for (int i = 0; i < list.size(); i++) {
			ModelListCheck element = new ModelListCheck(list.get(i));
			result.add(element);
		}
		return result;
	}

	public void deleteSchedules(View view) {
		if (list != null && hasCheckElement()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.title_delete_dialog);
			builder.setPositiveButton(R.string.ok_delete_dialog,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							for (ModelListCheck element : list) {
								if (element.isSelected()) {
									element.getMySchedule().setContext(
											getApplicationContext());
									deleteOk = deleteOk
											&& element.getMySchedule().delete();
								}
							}
							if (deleteOk) {
								deleteOk();
							}
						}
					});
			builder.setNegativeButton(R.string.cancel_delete_dialog,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();
		} else
			Toast.makeText(
					this,
					getResources().getString(
							R.string.bus_activity_delete_element_not_found),
					Toast.LENGTH_SHORT).show();
	}

	private void deleteOk() {
		this.setResult(RESULT_OK);
		this.finishActivity(BusActivity.REQUEST_CODE_DELETE_SCHEDULE);
		this.finish();
	}

	public void selectAllSchedules(View view) {
		if (list != null) {
			boolean exists = true;
			for (ModelListCheck element : list) {
				exists = exists && element.isSelected();
			}

			for (ModelListCheck element : list) {
				if (exists)
					element.setSelected(false);
				else
					element.setSelected(true);
			}
			adapter = new InteractiveArrayAdapter(DeleteSchedule.this, list);
			deleteList.setAdapter(adapter);
		}
	}

	// retorna true sii exite al menos un elemento para borrar
	private boolean hasCheckElement() {
		boolean hasElement = false;
		for (ModelListCheck element : list) {
			hasElement = hasElement || element.isSelected();
		}
		return hasElement;
	}

}
