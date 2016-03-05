package com.example.ivan.actividadesdam.adaptador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ivan.actividadesdam.R;
import com.example.ivan.actividadesdam.pojo.Actividad;

import java.util.List;

/**
 * Created by ivan on 3/3/2016.
 */
public class AdaptadorActividades extends ArrayAdapter<Actividad> {


        private Context ctx;
        private LayoutInflater lInflator;
        private List<Actividad> values;

        static class ViewHolder {
            TextView tv1, tv2;
        }

        public AdaptadorActividades(Context context, List<Actividad> objects) {
            super(context, R.layout.item, objects);
            this.ctx = context;
            this.lInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.values = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = new ViewHolder();

            if (convertView == null) {
                convertView = lInflator.inflate(R.layout.item, null);
                TextView tv = (TextView) convertView.findViewById(R.id.tvNom);
                vh.tv1 = tv;
                tv = (TextView)convertView.findViewById(R.id.tvId);
                vh.tv2 = tv;
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            vh.tv1.setText(values.get(position).getDescripcion());
            vh.tv2.setText(values.get(position).getId()+"");

            return convertView;
        }




}
