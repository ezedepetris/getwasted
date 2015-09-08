package training.bus;

import training.unrc.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Se lanza luego de apretar un numero de linea en la grilla. Representa un
 * dialog con dos botones: 1) el horario del colectivo selecionado 2) el
 * recorrido del colectivo seleccionado
 */
public class PathAndSchedulesButtons extends Activity {
	private Bundle args;
	private TextView lineName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.path_and_schedules_buttons);
		args = getIntent().getBundleExtra(BusActivity.BUNDLE);
		int position = args.getInt(BusActivity.LINE_BUS_POSITION);
		lineName = (TextView) findViewById(R.id.path_and_schedule_line_name);
		lineName.setText(BusActivity.mNameLine[position]);
	}	

	/*
	 * boton de horario
	 */
	public void schedule(View view) {
		Intent intent = new Intent(PathAndSchedulesButtons.this,
				BusSchedules.class);
		intent.putExtra(BusActivity.BUNDLE, args);
		startActivity(intent);
	}

	/*
	 * boton de recorrido
	 */
	public void path(View view) {
		Intent intent = new Intent(PathAndSchedulesButtons.this, PathBus.class);
		intent.putExtra(BusActivity.BUNDLE, args);
		startActivity(intent);

	}

}
