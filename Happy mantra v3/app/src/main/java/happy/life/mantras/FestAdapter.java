package happy.life.mantras;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.androidhive.expandablelistview.R;

/**
 * Created by ankit on 27/10/17.
 */

public class FestAdapter extends RecyclerView.Adapter<FestAdapter.ViewHolder>{

    private Context context;
    private List<Fest> list;

    public FestAdapter(Context context, List<Fest> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.festitem, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fest movie = list.get(position);

        holder.textTitle.setText(movie.getTitle());
        holder.textStart.setText(String.valueOf(movie.getStart()));


    }
    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle, textStart, textEnd;

        public ViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.title);
            textStart = itemView.findViewById(R.id.start);


        }
    }



}
