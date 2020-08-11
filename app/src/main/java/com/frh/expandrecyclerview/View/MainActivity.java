package com.frh.expandrecyclerview.View;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.frh.expandrecyclerview.Model.MassageModel;
import com.frh.expandrecyclerview.Model.TransactionModel;
import com.frh.expandrecyclerview.R;
import com.frh.expandrecyclerview.viewModel.MassageViewModel;
import com.frh.expandrecyclerview.viewModel.TransactionViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    ExpandableRecyclerView recyclerView;
    ExpandableTestAdapter expandableAdapter;
    TransactionViewModel transactionViewModel;
    MassageViewModel massageViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        massageViewModel = new ViewModelProvider(this).get(MassageViewModel.class);

        transactionViewModel.calltransactionApi(transactionViewModel);
        massageViewModel.callMassageApi(massageViewModel);


        transactionViewModel.getTransactionModel().observe(this, new Observer<ArrayList<TransactionModel>>() {
            @Override
            public void onChanged(ArrayList<TransactionModel> transactionModels) {
                expandableAdapter.setDataTransaction(transactionModels);
            }
        });

        massageViewModel.getMassageModel().observe(this, new Observer<ArrayList<MassageModel>>() {
            @Override
            public void onChanged(ArrayList<MassageModel> massageModels) {
                expandableAdapter.setDataMassage(massageModels);

            }
        });


        radioGroup = findViewById(R.id.radio);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        expandableAdapter = new ExpandableTestAdapter(EnumType.LISTMASSAGES_NOT_READ, EnumType.LISTMASSAGES_NOT_READ);
        recyclerView.setAdapter(expandableAdapter);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonID) {
                switch (radioButtonID) {

                    case R.id.radioMassages:
                        final ExpandableTestAdapter massageAdapter = new ExpandableTestAdapter(EnumType.LISTMASSAGES_NOT_READ, EnumType.LISTMASSAGES_NOT_READ);

                        massageViewModel.getMassageModel().observe(MainActivity.this, new Observer<ArrayList<MassageModel>>() {
                            @Override
                            public void onChanged(ArrayList<MassageModel> massageModels) {
                                massageAdapter.setDataMassage(massageModels);

                            }
                        });
                        recyclerView.setAdapter(massageAdapter);
                        break;


                    case R.id.radioTransaction:
                        final ExpandableTestAdapter transactionAdapter = new ExpandableTestAdapter(EnumType.LISTTRANSACTION, EnumType.LISTTRANSACTION);

                        transactionViewModel.getTransactionModel().observe(MainActivity.this, new Observer<ArrayList<TransactionModel>>() {
                            @Override
                            public void onChanged(ArrayList<TransactionModel> transactionModels) {
                                transactionAdapter.setDataTransaction(transactionModels);
                            }
                        });
                        recyclerView.setAdapter(transactionAdapter);
                        break;
                }

            }
        });


    }

}
