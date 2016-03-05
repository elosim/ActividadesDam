package com.example.ivan.actividadesdam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.ivan.actividadesdam.interfaces.ClienteRestInterfaz;
import com.example.ivan.actividadesdam.pojo.Actividad;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by ivan on 3/5/2016.
 */
public class Edit extends AppCompatActivity {

    private EditText etDescr, etFechai, etFechaf, etLugari, etLugarf, etIdProfe;
    private Actividad act;
    private Retrofit retrofit;
    private ClienteRestInterfaz api;
    private Actividad actExt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        retrofit = new Retrofit.Builder().baseUrl("http://ieszv.x10.bz/").addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(ClienteRestInterfaz.class);
        actExt = (Actividad) getIntent().getExtras().getSerializable("act");
        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void init(){
        etDescr = (EditText)findViewById(R.id.etDescr);
        etFechaf = (EditText)findViewById(R.id.etFechaf);
        etFechai = (EditText)findViewById(R.id.etFechai);
        etLugarf = (EditText)findViewById(R.id.etLugarf);
        etLugari = (EditText)findViewById(R.id.etLugari);
        etIdProfe = (EditText)findViewById(R.id.etIdProfesor);

        etDescr.setText(actExt.getDescripcion());
        etFechaf.setText(actExt.getFechaf());
        etFechai.setText(actExt.getFechai());
        etLugarf.setText(actExt.getLugarf());
        etLugari.setText(actExt.getLugari());
        etIdProfe.setText(actExt.getIdprofesor());

    }
    public void addActividad(View v){

        act = new Actividad();
        act.setId(actExt.getId());
        act.setTipo("excursion");
        act.setAlumno("ivanvaldes");
        act.setLugari(etLugari.getText().toString());
        act.setLugarf(etLugarf.getText().toString());
        act.setFechaf(etFechaf.getText().toString());
        act.setFechai(etFechai.getText().toString());
        act.setIdprofesor(etIdProfe.getText().toString());
        act.setDescripcion(etDescr.getText().toString());

        Intent i = new Intent(Edit.this, Edit.class );
        i.putExtra("act", act);
        setResult(Activity.RESULT_OK, i);
        Edit.this.finish();
        finish();
    }
    public void delActividad(View v){
        Intent i = new Intent(Edit.this, Edit.class );
        i.putExtra("act", (Actividad) getIntent().getExtras().getSerializable("act"));
        i.putExtra("delete", "yes");
        setResult(Activity.RESULT_OK, i);
        Edit.this.finish();
        finish();
    }

    public void cancel(View v){
        finish();
    }
}
