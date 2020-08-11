package com.frh.expandrecyclerview.data;

import com.frh.expandrecyclerview.Model.TransactionModel;
import com.frh.expandrecyclerview.viewModel.TransactionViewModel;

import java.util.ArrayList;

public class TransactionApi {
    ArrayList<TransactionModel> transactionModel = new ArrayList<>();

    public void callTransactionList(TransactionViewModel transactionApi) {
        for (int i = 0; i < 4; i++) {

            TransactionModel model = new TransactionModel();

            model.setTitle(DataType.titleArray[i]);
            model.setDrawable(DataType.drawableArray[i]);
            model.setDescription(DataType.stateArray[i]);
            model.setDate(DataType.dateArray[i]);
            model.setTime(DataType.timeArray[i]);

            model.setTracking(DataType.trackingArray[0]);
            model.setLawyersName(DataType.lawyersNameArray[0]);
            model.setConsulting(DataType.consultingArray[0]);
            model.setDocoumentTime(DataType.docoumentTimeArray[0]);
            model.setWriteTime(DataType.writeTimeArray[0]);
            model.setAudioTime(DataType.audioTimeArray[0]);

            transactionModel.add(model);
        }

        transactionApi.getTransactionModel().postValue(transactionModel);
    }

}
