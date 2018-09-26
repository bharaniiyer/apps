package happy.life.mantras;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.androidhive.expandablelistview.R;


public class MantraAdapter extends RecyclerView.Adapter<MantraAdapter.ViewHolder>{

    private Context context;
    private List<Mantras> list;
    boolean isPlay = false;


    public MantraAdapter(Context context, List<Mantras> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Mantras movie = list.get(position);

        holder.textTitle.setText(movie.getTitle());
        holder.textDesc.setText(String.valueOf(movie.getDesc()));

        holder.textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlay){
                    holder.textDesc.setVisibility(View.GONE);
                }else{
                    holder.textDesc.setVisibility(View.VISIBLE);
                }

                isPlay = !isPlay;
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle, textDesc;

        public ViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.title);
            textDesc = itemView.findViewById(R.id.desc);
        }
    }



}