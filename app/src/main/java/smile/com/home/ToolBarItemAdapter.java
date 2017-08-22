package smile.com.home;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by   05 on 22-08-2017.
 */

public class ToolBarItemAdapter extends ArrayAdapter<String> {
    private List<String> items;
    private TypedArray img;

    public ToolBarItemAdapter(Context context, int resource) {
        super(context, resource);
        items = Arrays.asList(context.getResources().getStringArray(R.array.drawer_list));
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.drawer_list_item, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_toolbar_text);
        textView.setText(items.get(position));
        textView.setCompoundDrawablePadding(20);
        return view;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
