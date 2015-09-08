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
 * Fragmento de Facultades. Muestra las facultades disponibles
 * 
 */
public class FacultiesFragment extends Fragment {

	private TextView title;
	private ListView listView;
	private OnFacultySelectedListener mCallback;

	/**
	 * Interfaz para comunicarce con la Activity que lo contiene. Comunica la
	 * facultad selecciona.
	 * 
	 */
	public interface OnFacultySelectedListener {
		// seleccion de una facultad
		public void onFacultySelected(int position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		return inflater.inflate(R.layout.standar_view, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (OnFacultySelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		// We need to use a different list item layout for devices older than
		// Honeycomb
		listView = (ListView) getView().findViewById(R.id.listView1);
		// When in two-pane layout, set the listview to highlight the selected
		// list item
		// (We do this during onStart because at the point the listview is
		// available.)
		if (getFragmentManager().findFragmentById(R.id.fragment_container_left) != null) {
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		updateFacultiesView();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// Se llama con la posicion de la carrera selecionada.
				mCallback.onFacultySelected(position);
			}
		});
	}

	public void updateFacultiesView() {
		String[] faculties = CareersActivity.request.getAllFaculties();
		title = (TextView) getView().findViewById(R.id.title_standar_view);
		title.setText(R.string.faculties_names);
		ListRow[] rowDate = new ListRow[faculties.length];
		for (int i = 0; i < faculties.length; i++) {
			rowDate[i] = new ListRow(faculties[i]);
		}
		ListAdapter adapter = new ListAdapter(getActivity(),
				R.layout.listview_row, rowDate);
		listView.setAdapter(adapter);
	}
}
