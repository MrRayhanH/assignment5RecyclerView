package com.example.assignment5_recyclerview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment5_recyclerview.CustomRecyclerViewAdapter;
import com.example.assignment5_recyclerview.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomRecyclerViewAdapter adapter;
    private List<String> groupList;
    private HashMap<String, List<String>> childMap;
    private List<Boolean> isExpandedList; // To track expanded/collapsed states of each group
    private int[] images = {R.drawable.one, R.drawable.two, R.drawable.three};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        // Prepare data
        groupList = new ArrayList<>();
        childMap = new HashMap<>();
        isExpandedList = new ArrayList<>();

        groupList.add("CSE");
        groupList.add("EEE");
        groupList.add("CIVIL");

        List<String> cseList = new ArrayList<>();
        cseList.add("Section");
        cseList.add("Batch");
        cseList.add("Group");

        List<String> eeeList = new ArrayList<>();
        eeeList.add("Section");
        eeeList.add("Batch");
        eeeList.add("Group");

        List<String> civilList = new ArrayList<>();
        civilList.add("Section");
        civilList.add("Batch");
        civilList.add("Group");

        childMap.put(groupList.get(0), cseList);
        childMap.put(groupList.get(1), eeeList);
        childMap.put(groupList.get(2), civilList);

        // Initialize expand/collapse states
        for (int i = 0; i < groupList.size(); i++) {
            isExpandedList.add(false); // Initially, all groups are collapsed
        }

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomRecyclerViewAdapter(this, groupList, childMap, isExpandedList, images);
        recyclerView.setAdapter(adapter);
    }
}
