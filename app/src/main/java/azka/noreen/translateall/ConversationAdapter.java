package azka.noreen.translateall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter {
    List<ConversationModel> callArrayList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ConversationModel.LeftConversation:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_type, parent, false);
                return new TextTypeViewHolder(view);
            case ConversationModel.RightConversation:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_type, parent, false);
                return new ImageTypeViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public void setData(List<ConversationModel> CallArrayList){
        this.callArrayList=CallArrayList;
        notifyDataSetChanged();

    }

    public static class LeftViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;

        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtType = (TextView) itemView.findViewById(R.id.type);
        }

    }
    public static class RightViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;

        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtType = (TextView) itemView.findViewById(R.id.type);
        }

    }
}
