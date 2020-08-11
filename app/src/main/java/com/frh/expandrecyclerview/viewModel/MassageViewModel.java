package com.frh.expandrecyclerview.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.frh.expandrecyclerview.Model.MassageModel;
import com.frh.expandrecyclerview.Model.TransactionModel;
import com.frh.expandrecyclerview.data.MassageApi;
import com.frh.expandrecyclerview.data.TransactionApi;

import java.util.ArrayList;

public class MassageViewModel extends ViewModel {

    MutableLiveData<ArrayList<MassageModel>> massageModel  = new MutableLiveData<>();
    MassageApi MassageApi = new MassageApi();

    public MutableLiveData<ArrayList<MassageModel>> getMassageModel() {
        return massageModel;
    }

    public void callMassageApi(MassageViewModel massageViewModel) {
        MassageApi.callMassageList(massageViewModel);
    }
}

