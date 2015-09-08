package training.careers;

import training.unrc.R;
import android.app.Activity;
import android.content.res.Resources;
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
 * Muestra todas las materias de una carrera, en forma de lista.
 * 
 */
public class CareerFragment extends Fragment {

	public static final String ARG_POSITION = "position";
	private static int mCurrentPosition = -1;
	OnSubjectSelectedListener mCallback;
	private TextView title;
	private ListView listView;
	private boolean backTrack = false;

	/**
	 * Interfaz para comunicarce con la Activity que lo contiene.
	 */
	public interface OnSubjectSelectedListener {
		// seleecion de una materia.
		public void onSubjectSelected(int position);
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
			mCallback = (OnSubjectSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		listView = (ListView) getView().findViewById(R.id.listView1);
		title = (TextView) getView().findViewById(R.id.title_standar_view);
		title.setText(R.string.title_career_fragment);
		// Traigo la carrera que selecciono (posicion en la lista)
		Bundle selectedCareer = getArguments();
		if (selectedCareer != null) {
			int position = selectedCareer.getInt(ARG_POSITION);

			if (position != CareersActivity.LOAD_OLD_POSITION)
				mCurrentPosition = position;
			updateCareerView(position);

		} else if (mCurrentPosition != -1 && backTrack) {
			updateCareerView(mCurrentPosition);
		}
		// Escuchamos que materia es seleccionada de la lista de materias de la
		// Carrera.
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				mCallback.onSubjectSelected(position);
			}
		});
	}

	public void setTrueBackTrack() {
		backTrack = true;
	}

	@Override
	public void onStop() {
		super.onStop();
		backTrack = true;
	}

	// Actualizo la Carrera a mostrar(se cambian las materia que esta
	// mostrando).
	public void updateCareerView(int position) {
		String careerName = CareersActivity.request.getCurrentCareer();
		String[] subjectOfCareer = CareersActivity.request.getSubjectOfCareer();
		if (position == CareersActivity.LOAD_OLD_POSITION)
			position = mCurrentPosition;

		Resources r = getResources();
		title.setText(careerName + " :  " + r.getString(R.string.year_title)
				+ " " + (position + 1));

		ListRow[] rowDate = new ListRow[subjectOfCareer.length];
		for (int i = 0; i < subjectOfCareer.length; i++) {
			rowDate[i] = new ListRow(subjectOfCareer[i]);
		}
		ListAdapter adapter = new ListAdapter(getActivity(),
				R.layout.listview_row, rowDate);
		listView.setAdapter(adapter);
		mCurrentPosition = position;
	}
}