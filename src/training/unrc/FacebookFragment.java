package training.unrc;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

@SuppressLint("ValidFragment")
public class FacebookFragment extends Fragment {

	private String ID_PAGE = "140848402747632";

	private String PICURE = "http://sphotos-g.ak.fbcdn.net/hphotos-ak-prn1/18261_141719119327227_872043133_n.png";
	@SuppressWarnings("unused")
	private String LINK_PAGE = "";
	private String LINK_GOOGLE_PLAY = "http://play.google.com/store/apps/details?id=training.unrc";
	private String DESCRIPTION = "La nueva forma de tener la universidad de Rio Cuarto en la palma de tu mano. Unrc 2.0 es una aplicación gratuita destinada al alumno, brindándole toda la información que necesita: sus materias, las aulas, los horarios de colectivos y mucho mas. ¡Conócela!";
	private String NAME = "vive la unrc 2.0 con android";
	private String CAPTION = "sumate a la comunidad 2.0 de la universidad";
	private String BLANK = "";

	// selector para ver si comparte o publica
	private String SHARED = "95175385264";

	private UiLifecycleHelper uiHelper;

	private Button shareButton;
	private EditText editMessage;
	private Button commentButton;
	private String messageToComment;
	private TextView tvShared;
	private TextView tvComment;
	private TextView tvInitial;
	private View lineSeparatingFacebook;

	private static final List<String> PERMISSIONS = Arrays.asList(
			"publish_actions", "manage_pages", "publish_stream");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;

	private ProgressDialogFragment dialogFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}
		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// por las duadas que este pidiendo un permiso y rote
		outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
		uiHelper.onSaveInstanceState(outState);
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.facebook_main, container, false);

		tvInitial = (TextView) view.findViewById(R.id.textInitialFacebook);
		tvShared = (TextView) view.findViewById(R.id.textComment);
		tvComment = (TextView) view.findViewById(R.id.textShared);
		editMessage = (EditText) view.findViewById(R.id.editTextToPublish);
		commentButton = (Button) view.findViewById(R.id.commentButton);
		shareButton = (Button) view.findViewById(R.id.shareButton);
		lineSeparatingFacebook = (View) view
				.findViewById(R.id.lineSeparatingFacebook);
		LoginButton authButton = (LoginButton) view
				.findViewById(R.id.authButton);
		authButton.setFragment(this);
		authButton.setReadPermissions(Arrays
				.asList("user_likes", "user_status"));

		shareButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isWifiOn()) {
					publish(SHARED);
					String messageDialog = getResources().getString(
							R.string.facebook_activity_loading_shared);
					dialogFragment = new ProgressDialogFragment(messageDialog);
					dialogFragment.show(getFragmentManager(),
							"MyProgressDialog");
				} else
					Toast.makeText(
							getActivity().getApplicationContext(),
							getResources().getString(
									R.string.facebook_activity_not_wifi),
							Toast.LENGTH_LONG).show();

			}
		});

		commentButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isWifiOn()) {
					messageToComment = editMessage.getText().toString().trim();
					if (messageToComment.length() > 0) {
						publish(messageToComment);
						String messageDialog = getResources().getString(
								R.string.facebook_activity_loading_comment);
						dialogFragment = new ProgressDialogFragment(
								messageDialog);
						dialogFragment.show(getFragmentManager(),
								"MyProgressDialog");
					} else {
						Toast.makeText(
								getActivity().getApplicationContext(),
								getResources().getString(
										R.string.facebook_activity_not_comment),
								Toast.LENGTH_LONG).show();
					}

				} else
					Toast.makeText(
							getActivity().getApplicationContext(),
							getResources().getString(
									R.string.facebook_activity_not_wifi),
							Toast.LENGTH_LONG).show();
			}
		});

		// para cuando se recrea el fragmento.
		if (savedInstanceState != null) {
			pendingPublishReauthorization = savedInstanceState.getBoolean(
					PENDING_PUBLISH_KEY, false);
		}
		return view;
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {

		if (state.isOpened()) {
			// si esta abierta la session puede mostrar el boton de compartir
			setVisivility(View.VISIBLE);
			// si tiene todos los permisos va a postear
			if (pendingPublishReauthorization
					&& state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
				pendingPublishReauthorization = false;
				publish(messageToComment);
			}
		} else if (state.isClosed()) {
			setVisivility(View.INVISIBLE);
		}
	}

	private void publish(String message) {
		Session session = Session.getActiveSession();
		String toId;
		if (session != null) {

			// Check for publish permissions
			List<String> permissions = session.getPermissions();
			if (!isSubsetOf(PERMISSIONS, permissions)) {
				pendingPublishReauthorization = true;
				Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
						this, PERMISSIONS);
				session.requestNewPublishPermissions(newPermissionsRequest);
				return;
			}
			Bundle postParams = new Bundle();
			// arma el el bundle para compartir
			if (message.compareTo(SHARED) == 0) {
				toId = "me";
				postParams.putString("name", NAME);
				postParams.putString("caption", CAPTION);
				postParams.putString("description", DESCRIPTION);
				postParams.putString("link", LINK_GOOGLE_PLAY);
				postParams.putString("picture", PICURE);
			} else {
				// arma el Bundle para publicar
				toId = ID_PAGE;
				postParams.putString("message", message);
				postParams.putString("name", BLANK);
				postParams.putString("caption", BLANK);
				postParams.putString("description", BLANK);
				postParams.putString("link", BLANK);
				postParams.putString("picture", BLANK);
			}

			Request.Callback callback = new Request.Callback() {
				public void onCompleted(Response response) {
					JSONObject graphResponse = response.getGraphObject()
							.getInnerJSONObject();
					@SuppressWarnings("unused")
					String postId = null;
					try {
						postId = graphResponse.getString("id");
					} catch (JSONException e) {
					}
					FacebookRequestError error = response.getError();
					if (error != null) {
						Toast.makeText(getActivity().getApplicationContext(),
								error.getErrorMessage(), Toast.LENGTH_SHORT)
								.show();
						dialogFragment.dismiss();
					} else {
						Toast.makeText(
								getActivity().getApplicationContext(),
								getResources().getString(
										R.string.facebook_activity_thanks),
								Toast.LENGTH_LONG).show();
						dialogFragment.dismiss();
						getActivity().finish();
						// aca volver a la initial !!!
					}
				}
			};

			Request request = new Request(session, toId + "/feed", postParams,
					HttpMethod.POST, callback);

			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();
		}

	}

	private boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;

	}

	private void setVisivility(int visivility) {
		switch (visivility) {
		case View.VISIBLE:
			tvInitial.setVisibility(View.GONE);
			shareButton.setVisibility(View.VISIBLE);
			commentButton.setVisibility(View.VISIBLE);
			editMessage.setVisibility(View.VISIBLE);
			tvShared.setVisibility(View.VISIBLE);
			tvComment.setVisibility(View.VISIBLE);
			lineSeparatingFacebook.setVisibility(View.VISIBLE);

			break;
		case View.INVISIBLE:
			tvInitial.setVisibility(View.VISIBLE);
			shareButton.setVisibility(View.INVISIBLE);
			commentButton.setVisibility(View.INVISIBLE);
			editMessage.setVisibility(View.INVISIBLE);
			tvComment.setVisibility(View.INVISIBLE);
			tvShared.setVisibility(View.INVISIBLE);
			lineSeparatingFacebook.setVisibility(View.INVISIBLE);
			break;
		}

	}

	class ProgressDialogFragment extends DialogFragment {

		String message;

		public ProgressDialogFragment(String messageDialog) {
			message = messageDialog;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			ProgressDialog dialog = new ProgressDialog(getActivity());
			dialog.setMessage(message);
			return dialog;
		}
	}

	public boolean isWifiOn() {
		// WifiManager wifi = (WifiManager) getActivity().getSystemService(
		// Context.WIFI_SERVICE);
		// return wifi.isWifiEnabled();
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

}