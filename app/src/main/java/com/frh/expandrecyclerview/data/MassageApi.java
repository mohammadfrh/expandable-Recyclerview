package com.frh.expandrecyclerview.data;

import com.frh.expandrecyclerview.Model.MassageModel;
import com.frh.expandrecyclerview.Model.TransactionModel;
import com.frh.expandrecyclerview.viewModel.MassageViewModel;
import com.frh.expandrecyclerview.viewModel.TransactionViewModel;

import java.util.ArrayList;

public class MassageApi {

    ArrayList<MassageModel> massageModels = new ArrayList<>();

    public void callMassageList(MassageViewModel massageViewModel) {
        for (int i = 0; i < 4; i++) {

            MassageModel model = new MassageModel();

            model.setTitle(DataListMassages.titleArray[i]);
            model.setDescription(DataListMassages.descriptionArray[i]);
            model.setDrawable(DataListMassages.drawableArray[i]);
            model.setState(DataListMassages.stateArray[i]);
            model.setType(DataListMassages.type[i]);

            massageModels.add(model);
        }
        massageViewModel.getMassageModel().postValue(massageModels);
    }
}
