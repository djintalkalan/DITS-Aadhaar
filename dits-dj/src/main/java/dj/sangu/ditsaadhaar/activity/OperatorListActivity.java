package dj.sangu.ditsaadhaar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import dj.sangu.ditsaadhaar.adapter.OperatorListAdapter;
import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.modal.OperatorModal;
import dj.sangu.ditsaadhaar.modal.responses.OperatorListResponse;
import dj.sangu.ditsaadhaar.network.ApiClient;
import dj.sangu.ditsaadhaar.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperatorListActivity extends AppCompatActivity {

    private RecyclerView recyclerOperator;
    private ArrayList<OperatorModal> operatorList;
    private OperatorListAdapter operatorListAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_list);
        initViews();
        initializeRecycler();
        callOperatorApi();
    }

    private void callOperatorApi() {
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.getClient().getOperatorList().enqueue(new Callback<OperatorListResponse>() {
            @Override
            public void onResponse(Call<OperatorListResponse> call, Response<OperatorListResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getSuccess()) {
                            operatorListAdapter.updateList(response.body().getOperatorList());
                        } else {
                            Toast.makeText(OperatorListActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OperatorListResponse> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(OperatorListActivity.this, "Internet not available", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(OperatorListActivity.this);
        recyclerOperator.setLayoutManager(layoutManager);
        recyclerOperator.setAdapter(operatorListAdapter);
    }

    private void initViews() {
        recyclerOperator = findViewById(R.id.recyclerOperator);
        progressBar = findViewById(R.id.progressBar);
        Utils.setToolbar(this, (Toolbar) findViewById(R.id.toolbar), "Operator List");
        operatorList = new ArrayList<>();
        operatorListAdapter = new OperatorListAdapter(OperatorListActivity.this, operatorList);
    }

}
