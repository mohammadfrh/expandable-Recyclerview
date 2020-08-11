package com.frh.expandrecyclerview.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.frh.expandrecyclerview.data.TransactionApi;
import com.frh.expandrecyclerview.Model.TransactionModel;

import java.util.ArrayList;

public class TransactionViewModel extends ViewModel {
    MutableLiveData<ArrayList<TransactionModel>> transactionModel  = new MutableLiveData<>();
    TransactionApi transactionApi = new TransactionApi();

    public MutableLiveData<ArrayList<TransactionModel>> getTransactionModel() {
        return transactionModel;
    }

    public void calltransactionApi(TransactionViewModel transactionViewModel) {
        transactionApi.callTransactionList(transactionViewModel);
    }
}
