package com.frh.expandrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.radio);

        final ExpandableRecyclerView recyclerView = findViewById(R.id.recyclerView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final ExpandableTestAdapter testAdapter = new ExpandableTestAdapter(EnumType.LISTMASSAGES_NOT_READ, EnumType.LISTMASSAGES_NOT_READ);
        recyclerView.setAdapter(testAdapter);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonID) {
                switch (radioButtonID) {

                    case R.id.radioMassages:
                        final ExpandableTestAdapter testAdapter = new ExpandableTestAdapter(EnumType.LISTMASSAGES_NOT_READ, EnumType.LISTMASSAGES_NOT_READ);
                        recyclerView.setAdapter(testAdapter);
                        break;

                    case R.id.radioTransaction:
                        final ExpandableTestAdapter testAdapterr = new ExpandableTestAdapter(EnumType.LISTTRANSACTION, EnumType.LISTTRANSACTION);
                        recyclerView.setAdapter(testAdapterr);
                        break;
                }

            }
        });


    }

}
