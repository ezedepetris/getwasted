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
 * Muestra el contenido de una facultad (las carreras que posee).
 * 
 */
public class FacultyFragment extends Fragment {

	private int mCurrentPosition = -1;
	OnCareerSelectedListener mCallback;
	private TextView title;
	private ListView listView;

	/**
	 * Interfaz para comunicarce con la Activity que lo contiene.
	 */
	public interface OnCareerSelectedListener {
		// seleecion de una carrera
		public void onCareerSelected(int position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState
					.getInt(CareersActivity.CURRENT_POSITION);
		}
		return inflater.inflate(R.layout.standar_view, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnCareerSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		listView = (ListView) getView().findViewById(R.id.listView1);
		// Traemos la facultad que se selecciono (posicion en la lista)
		Bundle selectedFaculty = getArguments();
		if (selectedFaculty != null) {
			int position = selectedFaculty.getInt(CareersActivity.CURRENT_POSITION);
			updateFacultyView(position);
		} else if (mCurrentPosition != -1) {
			updateFacultyView(mCurrentPosition);
		}
		// Escuchamos que carrera es seleccionada de la lista de carreras de la
		// Faculty.
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				mCallback.onCareerSelected(position);
			}
		});
	}

	// Actualizo la facultad a mostrar(se selecciona otra facultad).
	public void updateFacultyView(int position) {
		String faculty = CareersActivity.request.getNameOfFaculty(position);
		String[] careersOfFaculty = CareersActivity.request
				.getAllCareersOfFaculty(faculty);
		title = (TextView) getView().findViewById(R.id.title_standar_view);
		title.setText(faculty);
		ListRow[] rowDate = new ListRow[careersOfFaculty.length];
		for (int i = 0; i < careersOfFaculty.length; i++) {
			rowDate[i] = new ListRow(careersOfFaculty[i]);
		}
		ListAdapter adapter = new ListAdapter(getActivity(),
				R.layout.listview_row, rowDate);
		listView.setAdapter(adapter);
		mCurrentPosition = position;
	}
}