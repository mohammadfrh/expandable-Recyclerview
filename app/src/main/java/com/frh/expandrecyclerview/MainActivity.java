package com.frh.expandrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
        final ExpandableTestAdapter testAdapter = new ExpandableTestAdapter(EnumType.LISTMASSAGES, EnumType.LISTMASSAGES);
        recyclerView.setAdapter(testAdapter);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonID) {
                switch (radioButtonID) {

                    case R.id.radioMassages:
                        final ExpandableTestAdapter testAdapter = new ExpandableTestAdapter(EnumType.LISTMASSAGES, EnumType.LISTMASSAGES);
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
