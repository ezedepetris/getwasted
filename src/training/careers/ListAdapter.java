package training.careers;

import training.unrc.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adpatador personalizado para una Lisview
 * 
 */
public class ListAdapter extends ArrayAdapter<ListRow> {
	Context context;
	int layoutResourseID;
	ListRow[] data = null;

	/**
	 * @param context
	 *            {@link Context} usar con getActivity()
	 * @param textViewResourceId
	 *            xml asociado a la fila
	 * @param data
	 *            arreglo de filas
	 */
	public ListAdapter(Context context, int textViewResourceId, ListRow[] data) {
		super(context, textViewResourceId, data);
		this.context = context;
		this.layoutResourseID = textViewResourceId;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RowHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourseID, parent, false);

			holder = new RowHolder();
			holder.tittle = (TextView) row.findViewById(R.id.textRow);

			row.setTag(holder);
		} else {
			holder = (RowHolder) row.getTag();
		}

		ListRow listRow = data[position];
		holder.tittle.setText(listRow.tittle);
		int color = context.getResources().getColor(R.color.text_list_view);
		holder.tittle.setTextColor(color);
		return row;
	}

	static class RowHolder {
		TextView tittle;
	}
}