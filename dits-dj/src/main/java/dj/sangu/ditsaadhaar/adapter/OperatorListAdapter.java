package dj.sangu.ditsaadhaar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.activity.OperatorProfileActivity;
import dj.sangu.ditsaadhaar.modal.OperatorModal;
import dj.sangu.ditsaadhaar.utils.Constants;

public class OperatorListAdapter extends RecyclerView.Adapter<OperatorListAdapter.operatorViewHolder> {
    private Context mCtx;
    private ArrayList<OperatorModal> operatorList;

    public OperatorListAdapter(Context mCtx, ArrayList<OperatorModal> operatorList) {
        this.operatorList = operatorList;
        this.mCtx = mCtx;
    }


    @NonNull
    @Override
    public operatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.operator_item_layout, parent, false);
        return new operatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull operatorViewHolder holder, int position) {
        final OperatorModal operator = operatorList.get(position);
        String name = operator.getCustName();
        String stationId = operator.getStationId();
        String location = operator.getMachineLocation();

        holder.tvName.setText(name);
        holder.tvStationId.setText(stationId);
        holder.tvMachineLocation.setText(location);
        holder.mainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, OperatorProfileActivity.class);
                intent.putExtra(Constants.OPERATOR_ID, operator.getCustId());
                mCtx.startActivity(intent);
                ((Activity) mCtx).overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public void updateList(ArrayList<OperatorModal> newList) {
        operatorList.clear();
        operatorList.addAll(newList);
        notifyDataSetChanged();
    }

    class operatorViewHolder extends RecyclerView.ViewHolder {
        private CardView mainCard;
        private TextView tvName;
        private TextView tvStationId;
        private TextView tvMachineLocation;


        public operatorViewHolder(@NonNull View itemView) {
            super(itemView);
            mainCard = itemView.findViewById(R.id.mainCard);
            tvName = itemView.findViewById(R.id.tvName);
            tvStationId = itemView.findViewById(R.id.tvStationId);
            tvMachineLocation = itemView.findViewById(R.id.tvMachineLocation);

        }
    }
}
