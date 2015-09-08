package training.careers;

import training.unrc.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Representacion de los años de una carrera.
 * 
 */
public class YearsCareerFragment extends Fragment {

	private final String ARG_POSITION = "position";
	private static int mCurrentPosition = -1;
	OnYearCareerSelectedListener mCallback;
	private TextView title;
	private ListView listView;

	/**
	 * Interfaz para comunicarce con la actividad que contiene
	 */
	public interface OnYearCareerSelectedListener {
		// seleecion de un año de la carrera
		public void onYearCareerSelected(int position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}
		return inflater.inflate(R.layout.standar_view, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnYearCareerSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		listView = (ListView) getView().findViewById(R.id.listView1);
		// Cargamos los años de la carrera que se selecciono
		Bundle careerSelected = getArguments();
		if (careerSelected != null) {
			int position = careerSelected.getInt(ARG_POSITION);
			mCurrentPosition = position;
			updateYearsView(position);
		} else if (mCurrentPosition != -1) {
			updateYearsView(mCurrentPosition);
		}
		// año de carrera seleccionado
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				mCallback.onYearCareerSelected(position);
			}
		});
	}

	// Actualizo los años de la carrera.
	public void updateYearsView(int position) {
		// setea la carrera que se eligio, de la lista de carreras.
		CareersActivity.request.setCurrentCareer(position);
		String careerName = CareersActivity.request.getCurrentCareer();
		String[] yearsOfCareer = CareersActivity.request.getYearsOfCareer();
		ListRow[] rowDate = new ListRow[yearsOfCareer.length];
		for (int i = 0; i < yearsOfCareer.length; i++) {
			rowDate[i] = new ListRow(yearsOfCareer[i]);
		}
		title = (TextView) getView().findViewById(R.id.title_standar_view);
		title.setText(careerName);
		ListAdapter adapter = new ListAdapter(getActivity(),
				R.layout.listview_row, rowDate);
		listView.setAdapter(adapter);
		mCurrentPosition = position;
	}
}