package training.careers;

import training.unrc.R;
import training.util.ProcessingString;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * fragmento que representa una materia
 * 
 */
public class SubjectFragment extends Fragment {

	public static final String ARG_POSITION = "position";
	private int mCurrentPosition = -1;
	private TextView titleSubjectName;
	private TextView subtitleSubjectName;
	private TextView descriptionSubject;
	private TextView descriptionCorrelativeSelected;
	private String[] correltivesSubject;
	private TextView linkWeb;
	private LinearLayout linerarLayout;
	private Typeface font;
	private int rememberPosition;
	private View vOld;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}
		vOld = null;
		rememberPosition = -1;
		return inflater.inflate(R.layout.subject_view, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		Bundle careerSelected = getArguments();
		if (careerSelected != null) {
			int position = careerSelected.getInt(ARG_POSITION);
			updateSubjectView(position);
		} else if (mCurrentPosition != -1) {
			updateSubjectView(mCurrentPosition);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (linerarLayout != null) {
			linerarLayout.removeAllViews();
		}
	}

	/*
	 * actualizar la caracteristica de la materia que se seleciono de la materia
	 * correlativa
	 */
	private void updateSubjectSelectedInCorrelative(int position) {
		ProcessingString data = new ProcessingString();
		descriptionCorrelativeSelected = (TextView) getView().findViewById(
				R.id.description_correlative);
		String nameCorrelativeSelected = correltivesSubject[position];
		// cuando cargemos las materia vamos a mostrar su codigo, le paso el
		// codigo a esta consulta
		String info = CareersActivity.request
				.getDescriptionCorrelativeSelected(nameCorrelativeSelected);
		String description = data.getDescriptionCorrelative(info);
		descriptionCorrelativeSelected.setText(description);
	}

	public void updateSubjectView(int position) {
		ProcessingString data = new ProcessingString();
		// seteo la carrera que se eligio, de la lista de carreras.
		CareersActivity.request.setCurrentSubject(position);

		String subjectName = CareersActivity.request.getCurrentSubjectName();
		correltivesSubject = CareersActivity.request
				.getCurrentSubjectsCorrelatives();

		String info = CareersActivity.request.getDescriptionCurrentSubject();
		String year = CareersActivity.request.getYearSelected();
		String description = data.getDescriptionOfSubject(info, year);

		descriptionSubject = (TextView) getView().findViewById(
				R.id.description_subject);
		font = Typeface.createFromAsset(getActivity().getAssets(),
				"Roboto-Regular.ttf");
		descriptionSubject.setTypeface(font);

		titleSubjectName = (TextView) getView()
				.findViewById(R.id.title_subject);
		Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(),
				"LondonBetween.ttf");
		titleSubjectName.setTypeface(font1);

		subtitleSubjectName = (TextView) getView().findViewById(
				R.id.subtitle_subject);
		subtitleSubjectName.setTypeface(font);
		subtitleSubjectName.setText(CareersActivity.request.getCurrentCareer());

		linkWeb = (TextView) getView().findViewById(R.id.description_link_Web);
		linkWeb.setTypeface(font);
		linkWeb.setText(Html.fromHtml("<a href=" + data.getLink()
				+ ">Mas Información</a>"));
		linkWeb.setMovementMethod(LinkMovementMethod.getInstance());

		descriptionSubject.setText(description);
		titleSubjectName.setText(subjectName);

		if (correltivesSubject != null) {
			linerarLayout = (LinearLayout) getView().findViewById(
					R.id.list_text_view_corralative);
			TextListListener listener = new TextListListener();
			Resources r = getActivity().getResources();

			for (int i = 0; i < correltivesSubject.length; i++) {
				TextList child = new TextList(getActivity(), i);
				child.setBackgroundResource(R.drawable.box_list);
				child.setText(correltivesSubject[i].trim());
				child.setTypeface(font);
				child.setTextColor(r.getColor(R.color.text_list_view));
				child.setTextSize(20);
				child.setOnClickListener(listener);
				linerarLayout.addView(child);
			}

			// pinta la posicion que selecciono el usuario antes de ir a otra
			// app, cuando vuelve deja como esta
			if (rememberPosition != -1) {
				((TextView) linerarLayout.getChildAt(rememberPosition))
						.setTextColor(r.getColor(R.color.light_blue));
			}

			mCurrentPosition = position;
		} else {
			TextView message = (TextView) getView().findViewById(
					R.id.description_correlative);
			message.setText(R.string.message_correlative);
			TextView correlativeSelect = (TextView) getView().findViewById(
					R.id.subtitle_info);
			correlativeSelect.setVisibility(TextView.INVISIBLE);
		}
	}

	class TextListListener implements OnClickListener {
		Resources r = getActivity().getResources();

		@Override
		public void onClick(View v) {
			int position = ((TextList) v).getPosition();
			if (vOld != null) {
				((TextList) vOld).setTextColor(r
						.getColor(R.color.text_list_view));
			}

			// Vuelve al color normal la posicion que se pinto por haber salido
			// de la app
			if (rememberPosition != -1) {
				((TextView) linerarLayout.getChildAt(rememberPosition))
						.setTextColor(r.getColor(R.color.text_list_view));
			}

			((TextList) v).setTextColor(r.getColor(R.color.light_blue));
			updateSubjectSelectedInCorrelative(position);
			vOld = v;
			rememberPosition = position;
		}
	}

	class TextList extends TextView {
		int position;

		public TextList(Context context, int position) {
			super(context);
			this.position = position;
		}

		public int getPosition() {
			return this.position;
		}
	}
}