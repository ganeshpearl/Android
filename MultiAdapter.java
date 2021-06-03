package com.timemoneywaste.flames;
//Helps to use the recycler view ..it will gether data into different xml file then mergae to the mdisplay data
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder> {

    private Context context;
    private ArrayList<Result> result_data;

    public MultiAdapter(Context context, ArrayList<Result> result_data) {
        this.context = context;
        this.result_data = result_data;
    }

    public void setEmployees(ArrayList<Result> result_data) {
        this.result_data = new ArrayList<>();
        this.result_data = result_data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //This one is systan, we cant change it
        View view = LayoutInflater.from(context).inflate(R.layout.item_for_recycler_view, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(result_data.get(position));
    }

    @Override
    public int getItemCount() {
        return result_data.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

//        Only 2 willl be displayed inside the recycler view

        private TextView textView;
        private ImageView imageView;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void bind(final Result result_data) {
            imageView.setVisibility(result_data.isChecked() ? View.VISIBLE : View.GONE);
            textView.setText(result_data.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    result_data.setChecked(!result_data.isChecked());
                    imageView.setVisibility(result_data.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public ArrayList<Result> getAll() {
        return result_data;
    }

    public ArrayList<Result> getSelected() {
        ArrayList<Result> selected = new ArrayList<>();
        for (int i = 0; i < result_data.size(); i++) {
            if (result_data.get(i).isChecked()) {
                selected.add(result_data.get(i));
            }
        }
        return selected;
    }
}