package com.example.cricketclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FullNetStart extends AppCompatActivity {
    ListView names;
    Spinner batsman;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection("users");
    List<String> users = new ArrayList<>();
    List<String> id = new ArrayList<>();
    EditText targetRuns,targetBowls;
    Button submit;
    ArrayAdapter<String> adapter,adapter1;
    String selectedBatsman,selectedBatsmanId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_net_start);
        batsman = findViewById(R.id.spinner);
        names = (ListView) findViewById(R.id.allBowlers);
        targetBowls = findViewById(R.id.targetBowls);
        targetRuns = findViewById(R.id.targetRuns);
        submit = findViewById(R.id.button8);
        names.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.checked_list, users);
        adapter1 = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, users);
        collectionReference.orderBy("username").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments())
                {
                    id.add(documentSnapshot.getId());
                    String username = documentSnapshot.get("username").toString();
                    users.add(username);
                }
                adapter.notifyDataSetChanged();
                adapter1.notifyDataSetChanged();
                for(int i=0;i<users.size();i++)
                {
                    names.setItemChecked(i,true);
                }
            }
        });
        names.setAdapter(adapter);
        batsman.setAdapter(adapter1);
        batsman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long ids) {
                selectedBatsman = users.get(position);
                selectedBatsmanId = id.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedUsers = new ArrayList<>();
                List<String> selectedId = new ArrayList<>();
                SparseBooleanArray array = names.getCheckedItemPositions();
                for(int i=0;i<array.size();i++)
                {
                    if(array.valueAt(i))
                    {
                        selectedId.add(id.get(i));
                        selectedUsers.add(users.get(i));
                    }
                }
                if(selectedUsers.size()==0||selectedBatsman==null)
                {
                    Toast.makeText(getBaseContext(),"Please Select batsman name and bowlers list",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(getBaseContext(),FullNet.class);
                    intent.putExtra("names", (ArrayList<String>) selectedUsers);
                    intent.putExtra("id", (ArrayList<String>) selectedId);
                    intent.putExtra("selectedBatsman",selectedBatsman);
                    intent.putExtra("selectedBatsmanId",selectedBatsmanId);
                    if(targetRuns.getText()!=null)
                    {
                        intent.putExtra("targetRuns",targetRuns.getText().toString());
                    }
                    else
                    {
                        intent.putExtra("targetRuns","0");
                    }
                    if(targetRuns.getText()!=null)
                    {
                        intent.putExtra("targetBowls",targetBowls.getText().toString());
                    }
                    else
                    {
                        intent.putExtra("targetBowls","0");
                    }
                    startActivity(intent);
                }
            }
        });
    }
}