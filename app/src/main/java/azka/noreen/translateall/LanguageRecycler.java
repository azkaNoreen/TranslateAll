package azka.noreen.translateall;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class LanguageRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<LanguageModel> callArrayList;
    MyInterface myInterface;

    public void setMyInterface(MyInterface myInterface){
        this.myInterface=myInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelanguage,parent,false);
        return new LanguageRecycler.CallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LanguageModel st = callArrayList.get(position);
        LanguageRecycler.CallHolder callHolder = (LanguageRecycler.CallHolder) holder;

        callHolder.Name.setText(st.getName());

        callHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent in = new Intent(view.getContext(), CallDetail.class);
//                in.putExtra("Date", dateString);
//                view.getContext().startActivity(in);
                myInterface.onLanguageClick(st);
            }
        });
    }

    @Override
    public int getItemCount() {
        return callArrayList.size();
    }
    public void setData(List<LanguageModel> CallArrayList){
        this.callArrayList=CallArrayList;
        notifyDataSetChanged();

    }
    //to find views of single list xml file
    public static class CallHolder extends RecyclerView.ViewHolder{

        TextView Name;

        public CallHolder(@NonNull View itemView) {
            super(itemView);

            Name=itemView.findViewById(R.id.name);


        }
    }
}
