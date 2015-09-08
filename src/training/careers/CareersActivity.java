package training.careers;

import training.careers.CareerFragment.OnSubjectSelectedListener;
import training.careers.FacultiesFragment.OnFacultySelectedListener;
import training.careers.FacultyFragment.OnCareerSelectedListener;
import training.careers.YearsCareerFragment.OnYearCareerSelectedListener;
import training.unrc.R;
import training.util.Request;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;

/**
 * Todas las interfaces que implementa son para comunicar los diferentes
 * fragmentos entre si. Esta clase seria un mediador para los diferentes
 * fragmentos.
 */
public class CareersActivity extends FragmentActivity implements
		OnFacultySelectedListener, OnCareerSelectedListener,
		OnYearCareerSelectedListener, OnSubjectSelectedListener {

	protected static final String CURRENT_POSITION = "position";
	protected static final String FROM_MAIN_CAREERS = "ok";
	protected static final int LOAD_OLD_POSITION = 666;
	private String SHOW_MESSAGE = "training.careers.CareersActivity.SHOW_MESSAGE";
	private static boolean isOnTabet = false;
	private int mCurrentFragment;
	protected static Request request;
	private boolean passFragmentFacultySelect = false;
	private boolean passFragmentSubjectSelect = true;
	private FragmentManager manager;
	private boolean showMessage;
	private SharedPreferences sharedPref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faculty);

		sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		showMessage = sharedPref.getBoolean(SHOW_MESSAGE, true);

		if (showMessage) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.carrer_activity_dialog_attention);
			builder.setMessage(R.string.carrer_activity_dialog_message_careers_not_found);
			builder.setPositiveButton(
					R.string.carrer_activity_dialog_not_show_more,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							showMessage = false;
							SharedPreferences.Editor editor = sharedPref.edit();
							editor.putBoolean(SHOW_MESSAGE, showMessage);
							editor.commit();
						}
					});
			builder.setNegativeButton(R.string.ok_delete_dialog,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();
		}

		manager = getSupportFragmentManager();
		request = new Request(this, getResources());

		// True si esta en small display
		if (findViewById(R.id.fragment_container) != null) {
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			// muestra las facultades.
			FacultiesFragment faculties = new FacultiesFragment();
			// metemos al contenedor de Fragment
			manager.beginTransaction().add(R.id.fragment_container, faculties)
					.commit();
		} else {
			isOnTabet = true;
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			this.mCurrentFragment = R.string.fragment1;
			// Entra por tablet.
			FacultiesFragment faculties = new FacultiesFragment();
			FacultyFragment faculty = new FacultyFragment();

			// metemos al contenedor de Fragment
			manager.beginTransaction()
					.add(R.id.fragment_container_left, faculties).commit();
			manager.beginTransaction()
					.add(R.id.fragment_container_rigth, faculty).commit();
		}

	}

	// Terminar menu. (TAREA)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_careers, menu);
		return true;
	}

	@Override
	public void onFacultySelected(int position) {
		// El usuario selecciona una facultad de la lista de facultades
		// (Faculties) se invoca este metodo.
		FacultiesFragment facultyfragment = (FacultiesFragment) manager
				.findFragmentById(R.id.fragment_container_left);

		// Si el fragmento esta creado retorna true, esto es por que estamos en
		// una tablet
		if (facultyfragment != null) {
			FacultyFragment facultyfragment1 = (FacultyFragment) manager
					.findFragmentById(R.id.fragment_container_rigth);
			facultyfragment1.updateFacultyView(position);
			passFragmentFacultySelect = true;
		} else {
			// crear el fragmento y agregarlo al contenedor
			FacultyFragment newFragment = new FacultyFragment();
			createrOnlyAFragment(newFragment, R.id.fragment_container, position);
			passFragmentFacultySelect = true;
		}
	}

	@Override
	public void onCareerSelected(int position) {
		// Cuando el usuario selecciona una carrera de la lista de carreras se
		// invoca este metodo.
		// Se le muestra los años que provienen de la carrera que selecciono.
		if (passFragmentFacultySelect && passFragmentSubjectSelect) {

			if (isOnTabet) {
				this.mCurrentFragment = R.string.fragment2;

				YearsCareerFragment newFragment = new YearsCareerFragment();
				createrOnlyAFragment(newFragment, R.id.fragment_container_left,
						position);
				// Sin el Bundle.
				CareerFragment newFragment2 = new CareerFragment();
				FragmentTransaction transaction2 = manager.beginTransaction();
				transaction2.replace(R.id.fragment_container_rigth,
						newFragment2);
				transaction2.addToBackStack(null);
				transaction2.commit();
				passFragmentSubjectSelect = false;
			} else {
				// crear el fragmento y agregarlo al contenedor
				YearsCareerFragment newFragment = new YearsCareerFragment();
				createrOnlyAFragment(newFragment, R.id.fragment_container,
						position);
				passFragmentSubjectSelect = true;
			}
		}
	}

	@Override
	public void onYearCareerSelected(int position) {
		// El usuario selecciona una año, de los años que tiene la carrera se
		// invoca este metodo para llevar un seguimiento del uso.
		request.setCurrentYear(position);

		boolean flag = true;
		CareerFragment careerFragment = null;

		try {
			careerFragment = (CareerFragment) manager
					.findFragmentById(R.id.fragment_container_rigth);
		} catch (Exception e) {
			flag = false;
		}
		if (isOnTabet) {
			if (flag) {
				FacultyFragment newFragment = new FacultyFragment();
				Bundle args = new Bundle();
				args.putInt(CareersActivity.CURRENT_POSITION, position);
				newFragment.setArguments(args);
				careerFragment.updateCareerView(position);
				passFragmentSubjectSelect = true;
			}
		} else {
			CareerFragment newFragment = new CareerFragment();
			createrOnlyAFragment(newFragment, R.id.fragment_container, position);
			passFragmentSubjectSelect = true;
		}
	}

	@Override
	public void onSubjectSelected(int position) {
		// El usuario selecciona materia de una carrera en un año
		// correspondiente, se invoca este metodo.
		CareerFragment careerFragment = null;
		boolean flag = true;
		try {
			careerFragment = (CareerFragment) manager
					.findFragmentById(R.id.fragment_container_rigth);

		} catch (Exception e) {
			flag = false;
		}
		// si flag es true es porque ya se viene trabajando en una tablet.
		if (isOnTabet) {
			if (flag) {
				careerFragment = new CareerFragment();
				createrOnlyAFragment(careerFragment,
						R.id.fragment_container_left, LOAD_OLD_POSITION);
			}
			this.mCurrentFragment = R.string.fragment3;
			SubjectFragment newFragment = new SubjectFragment();
			createrOnlyAFragment(newFragment, R.id.fragment_container_rigth,
					position);
		} else {
			SubjectFragment newFragment = new SubjectFragment();
			createrOnlyAFragment(newFragment, R.id.fragment_container, position);
		}
	}

	/*
	 * @param newFragment {@link Fragment} Fragmento a cargar.
	 * 
	 * @param idFragmentContainer {@link int} ID de donde lo carga.
	 * 
	 * @param position {@link int} posicion a salvar en Bundle.
	 */
	private void createrOnlyAFragment(Fragment newFragment,
			int idFragmentContainer, int position) {
		Bundle args = new Bundle();
		args.putInt(CareersActivity.CURRENT_POSITION, position);
		newFragment.setArguments(args);
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(idFragmentContainer, newFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	/*
	 * Cerramos la base de datos que abrimos en onCreate(). Este se llama
	 * despues de finish() o que el sistema la mata la app por baja memoria.
	 */
	@Override
	public void onDestroy() {
		request.finalize();
		manager = null;
		super.onDestroy();
	}

	/*
	 * Atrapa de back button cuando se presiona. funciona solo para tabletas.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (isOnTabet) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				handleTheBackButton();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void handleTheBackButton() {
		int position;
		switch (this.mCurrentFragment) {
		case R.string.fragment1:
			// vuelve al inicio de la app
			this.finish(); // causa el invoque de onDestroy
			break;
		case R.string.fragment2:
			// volver al fragmento 1.
			this.passFragmentFacultySelect = true;
			this.passFragmentSubjectSelect = true;
			this.mCurrentFragment = R.string.fragment1;
			FacultiesFragment faculties = new FacultiesFragment();
			FacultyFragment faculty = new FacultyFragment();
			position = request.getPositionOnFaculties();
			createrOnlyAFragment(faculty, R.id.fragment_container_rigth,
					position);
			// metemos al contenedor de Fragment
			manager.beginTransaction()
					.add(R.id.fragment_container_left, faculties).commit();
			break;
		case R.string.fragment3:
			// volver al fragmento 2.
			this.mCurrentFragment = R.string.fragment2;
			YearsCareerFragment newFragment = new YearsCareerFragment();
			position = request.getPositionOnCareers();
			createrOnlyAFragment(newFragment, R.id.fragment_container_left,
					position);
			CareerFragment newFragment2 = new CareerFragment();
			newFragment2.setTrueBackTrack();
			FragmentTransaction transaction2 = manager.beginTransaction();
			transaction2.replace(R.id.fragment_container_rigth, newFragment2);
			transaction2.addToBackStack(null);
			transaction2.commit();
			break;
		}
	}

	protected static int getLayoutSKD() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.layout.simple_list_item_activated_1
				: android.R.layout.simple_list_item_1;
	}
}