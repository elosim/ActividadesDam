package com.example.ivan.actividadesdam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ivan.actividadesdam.adaptador.AdaptadorActividades;
import com.example.ivan.actividadesdam.interfaces.ClienteRestInterfaz;
import com.example.ivan.actividadesdam.pojo.Actividad;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private List<Actividad> listaActividades = new ArrayList<>();
    private AdaptadorActividades adpA;
    private ListView lv;
    private Context ctx;
    private ClienteRestInterfaz api;
    private Retrofit retrofit;
    private final int EDIT = 2, ADD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goAdd();
            }
        });

        retrofit = new Retrofit.Builder().baseUrl("http://ieszv.x10.bz/").addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(ClienteRestInterfaz.class);

        init();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getActivities();
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        long id = item.getItemId();
        AdapterView.AdapterContextMenuInfo vistaInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int posicion = vistaInfo.position;

        if (id == R.id.menu_borrar){
            delActivity(listaActividades.get(posicion));
        }
        return super.onContextItemSelected(item);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD){
            addActivity((Actividad) data.getExtras().getSerializable("act"));
        }
        if(requestCode == EDIT){
            if(data.getExtras().getString("delete")!=null){
                delActivity((Actividad) data.getExtras().getSerializable("act"));

            }else{
                putActivity((Actividad) data.getExtras().getSerializable("act"));
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void init(){

        lv = (ListView)findViewById(R.id.listView);

    }

    public void goAdd(){
        Intent i = new Intent(this, Add.class);
        startActivityForResult(i,ADD);
    }
    public void goEdit(Actividad act){
        Intent i = new Intent(this, Edit.class);
        i.putExtra("act", act);
        startActivityForResult(i, EDIT);
    }
    public void getActivities(){

        Call<List<Actividad>> call = api.getActividades();
        listaActividades = new ArrayList<>();

        call.enqueue(new Callback<List<Actividad>>() {
            @Override
            public void onResponse(Response<List<Actividad>> response, Retrofit retrofit) {
                for (Actividad act : response.body()) {
                    listaActividades.add(act);

                }
                adpA = new AdaptadorActividades(ctx, listaActividades);
                lv.setAdapter(adpA);
                lv.setTag(listaActividades);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        goEdit(listaActividades.get(position));
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });

    }
    public void addActivity(Actividad act){
        Call<String> call = api.addActivity(act);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                listaActividades = null;
                getActivities();

            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });


    }
    public void delActivity(Actividad act){
        Call<String> call = api.delActivity(Integer.parseInt(act.getId()));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                listaActividades = null;
                getActivities();
                Log.v("está", "acabado");
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
    public void putActivity(Actividad act){

        Call<String> call = api.putActivity(act);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                listaActividades = null;
                getActivities();

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}
