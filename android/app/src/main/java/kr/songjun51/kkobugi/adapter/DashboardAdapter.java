package kr.songjun51.kkobugi.adapter;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.songjun51.kkobugi.R;

/*
 *  * Created by MalangDesktop on 2016-06-04.
 */


public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    ArrayList<Pair<Integer, Integer>> items;
    Context context;
    LinearLayout.LayoutParams params;

    public DashboardAdapter(Context context, ArrayList<Pair<Integer, Integer>> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_dashboard_graph_item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Pair<Integer, Integer> item = items.get(position);
        holder.percentage.setText(item.second + "%");
        holder.date.setText(item.first + "");
        holder.holder.setWeightSum(100);
        params = (LinearLayout.LayoutParams) holder.minus.getLayoutParams();
        params.weight = 100 - (item.second);
        holder.minus.setLayoutParams(params);
        params = (LinearLayout.LayoutParams) holder.plus.getLayoutParams();
        params.weight = item.second;
        holder.plus.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, percentage;
        LinearLayout minus, plus, holder;

        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.main_dashboard_recyclerview_content_date);
            percentage = (TextView) itemView.findViewById(R.id.main_dashboard_recyclerview_content_percentage);
            minus = (LinearLayout) itemView.findViewById(R.id.main_dashboard_content_minus);
            plus = (LinearLayout) itemView.findViewById(R.id.main_dashboard_content_plus);
            holder = (LinearLayout) itemView.findViewById(R.id.main_dashboard_content_holder);
        }
    }

}