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

import azka.noreen.translateall.database.TextEntity;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<TextEntity> callArrayList;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlehistory,parent,false);
        return new HistoryRecyclerAdapter.CallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextEntity st=callArrayList.get(position);
        HistoryRecyclerAdapter.CallHolder callHolder= (HistoryRecyclerAdapter.CallHolder) holder;


        callHolder.text.setText(st.getInput_word());
//        callHolder.Type.setText(st.getCallType());
        callHolder.translation.setText(st.getOutput_word());

        callHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(view.getContext(), MainActivity.class);
                in.putExtra("word",st.getInput_word());
                in.putExtra("outputWord",st.getOutput_word());
                in.putExtra("inputLang",st.getInput_language());
                in.putExtra("translation",st.getTranslation_language());
                in.setAction("History");
                view.getContext().startActivity(in);
//
             }
        });
    }

    @Override
    public int getItemCount() {
        return callArrayList.size();
    }
    public void setData(List<TextEntity> CallArrayList){
        this.callArrayList=CallArrayList;
        notifyDataSetChanged();

    }
    //to find views of single list xml file
    public static class CallHolder extends RecyclerView.ViewHolder{

        TextView text;
        TextView translation;


        ImageView image;
        public CallHolder(@NonNull View itemView) {
            super(itemView);

            text=itemView.findViewById(R.id.text);
            translation=itemView.findViewById(R.id.translation);


        }
    }
}
