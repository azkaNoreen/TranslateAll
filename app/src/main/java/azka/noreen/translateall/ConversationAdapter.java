package azka.noreen.translateall;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
                return new LeftViewHolder(view);
            case ConversationModel.RightConversation:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_type, parent, false);
                return new RightViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConversationModel object = callArrayList.get(position);
        if (object != null) {
            switch (object.type) {
                case ConversationModel.LeftConversation:
                    ((LeftViewHolder) holder).txtType.setText(object.getTextEntered());
                    ((LeftViewHolder) holder).translated.setText(object.getTranslated());

                    ((LeftViewHolder) holder).txtType.measure(0, 0);       //must call measure!
                    int width=((LeftViewHolder) holder).txtType.getMeasuredWidth();  //get width
                    int hight=((LeftViewHolder) holder).txtType.getMeasuredHeight();  //get width

                    RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width,1);

                    parms.setMargins(35,hight+30,0,5);
                    ((LeftViewHolder) holder).line.setLayoutParams(parms);
                    break;
                case ConversationModel.RightConversation:
                    ((RightViewHolder) holder).txtType.setText(object.getTextEntered());
                    ((RightViewHolder) holder).translated.setText(object.getTranslated());

                    break;
            }
        }
    }
    @Override
    public int getItemViewType(int position) {

        switch (callArrayList.get(position).type) {
            case 0:
                return ConversationModel.RightConversation;
            case 1:
                return ConversationModel.LeftConversation;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return callArrayList.size();
    }
    public void setData(List<ConversationModel> CallArrayList){
        this.callArrayList=CallArrayList;
        notifyDataSetChanged();

    }

    public static class LeftViewHolder extends RecyclerView.ViewHolder {

        TextView txtType,translated;
        RelativeLayout line;

        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtType = (TextView) itemView.findViewById(R.id.leftText);
            this.line=(RelativeLayout) itemView.findViewById(R.id.line);
            this.translated = (TextView) itemView.findViewById(R.id.belowc_chatl);

        }

    }
    public static class RightViewHolder extends RecyclerView.ViewHolder {

        TextView txtType,translated;

        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtType = (TextView) itemView.findViewById(R.id.rightText);
            this.translated = (TextView) itemView.findViewById(R.id.belowc_chat2);

        }

    }
}
