package training.bus;

import java.util.List;

import training.unrc.R;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyScheduleColumn extends View {

	private Context context;
	private ViewGroup parent;

	public MyScheduleColumn(Context context, ViewGroup parent) {
		super(context);
		this.context = context;
		this.parent = parent;
	}

	/**
	 * dibuja una linea
	 * 
	 * @return {@link View} linea seperadora
	 * */
	public View getDrawLine() {
		View view = new View(context);
		LayoutInflater inflater = ((BusActivity) context).getLayoutInflater();
		view = inflater.inflate(R.layout.separate_line, parent, false);
		return view;
	}

	/*
	 * arma el representante de un horario
	 * 
	 * @param schedule
	 * 
	 * @return una View con un horario (hora+dia+place)
	 */
	private View getSchudele(MySchedule schedule) {

		View view = new View(context);
		LayoutInflater inflater = ((BusActivity) context).getLayoutInflater();
		view = inflater.inflate(R.layout.schedule_line, parent, false);

		TextView textView1 = (TextView) view
				.findViewById(R.id.schedule_line_time);
		TextView textView2 = (TextView) view
				.findViewById(R.id.schedule_line_day);
		TextView textView3 = (TextView) view
				.findViewById(R.id.schedule_line_place);

		textView1.setText(schedule.getTime() + " ");
		textView2.setText(schedule.getDay() + " ");
		textView3.setText(schedule.getPlace() + " ");
		return view;
	}

	/**
	 * crea una columna representante de una linea (nombre linea+horarios)
	 * 
	 * @param myScheduleByLine
	 *            {@link List} todos los horarios de una linea
	 * @return {@link View} representa una columna con todos los horarios
	 *         cargados de una linea
	 */
	public View getBusline(List<MySchedule> myScheduleByLine) {

		View view = new View(context);
		LayoutInflater inflater = ((BusActivity) context).getLayoutInflater();
		view = inflater.inflate(R.layout.line_bus, parent, false);

		// referencia a una columna, que es insertada en la tablade horarios
		LinearLayout linearLayout = (LinearLayout) view
				.findViewById(R.id.bus_schedule);

		TextView title = new TextView(this.getContext());
		title.setBackgroundResource(R.drawable.box_my_schedules);
		title.setTextColor(getResources().getColor(R.color.gray_title));
		title.setText(myScheduleByLine.get(0).getLine());
		title.setGravity(Gravity.CENTER);
		title.setTextSize(22);
		linearLayout.addView(title);
		View view2;
		for (int i = 0; i < myScheduleByLine.size(); i++) {
			view2 = getSchudele(myScheduleByLine.get(i));
			if (i % 2 == 0) {
				view2.setBackgroundColor(getResources().getColor(
						R.color.white));
			}
			linearLayout.addView(view2);
		}

		return view;
	}

}
