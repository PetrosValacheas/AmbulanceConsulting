package adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambulanceconsulting.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import model.Symptom;


public class SymtomAdaper extends RecyclerView.Adapter<SymtomAdaper.ViewHolder>{

     ArrayList<Symptom> symptomList;
    public ArrayList<Symptom> checkedSymptoms = new ArrayList<>();



    public SymtomAdaper(ArrayList<Symptom> symptoms ){
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

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox chk =(CheckBox) v;

                if(chk.isChecked()){

                    checkedSymptoms.add(symptomList.get(pos));

                }else {

                    checkedSymptoms.remove(symptomList.get(pos));

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return symptomList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView symptomName;
        CheckBox chekbox;
        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            symptomName = (TextView) itemView.findViewById(R.id.symptom_name);
            chekbox = (CheckBox) itemView.findViewById(R.id.checkboxId);

            chekbox.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener ic){

            this.itemClickListener= ic;

        }

        @Override
        public void onClick(View v) {

            this.itemClickListener.onItemClick(v,getLayoutPosition());

        }
    }
}
