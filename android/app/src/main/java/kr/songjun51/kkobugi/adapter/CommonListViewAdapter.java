package kr.songjun51.kkobugi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.songjun51.kkobugi.models.ListData;
import kr.songjun51.kkobugi.R;

/**
 * Created by Chad on 7/25/16.
 */


public class CommonListViewAdapter extends ArrayAdapter<ListData> {

    int type;
    ArrayList<ListData> arr;
    Context context;
    ListData data;

    public CommonListViewAdapter(int type, Context context, ArrayList<ListData> arr) {
        super(context, 0, arr);
        this.context = context;
        this.type = type;
        this.arr = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.main_common_listview_content, null);
        data = arr.get(position);
        if (data != null) {
            TextView title = (TextView) view.findViewById(R.id.main_common_listview_name);
            TextView percentage = (TextView) view.findViewById(R.id.main_common_listview_percentage);
            TextView ranking = (TextView) view.findViewById(R.id.main_common_listview_ranking);
            title.setText(data.getTitle());
            percentage.setText(data.getPercentage());
            if (type == 0) {
                ranking.setVisibility(View.VISIBLE);
                ranking.setText(data.getRanking() + "");
            }
        }
        return view;
    }
}

