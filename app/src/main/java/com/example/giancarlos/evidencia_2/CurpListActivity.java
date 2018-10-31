package com.example.giancarlos.evidencia_2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.giancarlos.evidencia_2.adapter.CurpRecyclerAdapter;
import com.example.giancarlos.evidencia_2.model.Curp;
import com.example.giancarlos.evidencia_2.sql.DatabaseHelper;


import java.util.ArrayList;

public class CurpListActivity extends AppCompatActivity {

    private AppCompatActivity activity = CurpListActivity.this;
    Context context = CurpListActivity.this;
    private RecyclerView recyclerViewCurp;
    private ArrayList<Curp> listCurp;
    private CurpRecyclerAdapter curpRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    SearchView searchBox;
    private ArrayList<Curp> filteredList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /**
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         **/
        initViews();
        initObjects();

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("Primer Apellido")) {

            //get all needed extras intent

            String primerApellido = getIntent().getExtras().getString("Primer Apellido");
            String segundoApellido = getIntent().getExtras().getString("Segundo Apellido");
            String nombre = getIntent().getExtras().getString("Nombre");
            String nacimiento = getIntent().getExtras().getString("Fecha de Nacimiento");
            String sexo = getIntent().getExtras().getString("Sexo");
            String entidadFederativa = getIntent().getExtras().getString("Entidad Federativa");


        }else{

            Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewCurp = (RecyclerView) findViewById(R.id.recyclerViewCurp);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listCurp = new ArrayList<>();
        curpRecyclerAdapter = new CurpRecyclerAdapter(listCurp, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCurp.setLayoutManager(mLayoutManager);
        recyclerViewCurp.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCurp.setHasFixedSize(true);
        recyclerViewCurp.setAdapter(curpRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        getDataFromSQLite();

    }


    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listCurp.clear();
                listCurp.addAll(databaseHelper. getAllCurp());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                curpRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}