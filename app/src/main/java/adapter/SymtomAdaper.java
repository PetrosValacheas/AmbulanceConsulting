package adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambulanceconsulting.R;

import java.util.ArrayList;

import model.Symptom;


public class SymtomAdaper extends RecyclerView.Adapter<SymtomAdaper.ViewHolder>{

    private ArrayList<Symptom> symptomList;

    public SymtomAdaper(ArrayList<Symptom> symptoms){
        symptomList = symptoms;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_symptom_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.symptomName.setText(symptomList.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return symptomList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        TextView symptomName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            symptomName = itemView.findViewById(R.id.symptom_name);
        }
    }
}
