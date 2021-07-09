package com.etcmobileapps.evcilhayvansahiplenme.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.etcmobileapps.evcilhayvansahiplenme.Model.SearchModel;
import com.etcmobileapps.ucretsizevcilhayvansahiplenme.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class SearchAdaptor extends ArrayAdapter<SearchModel> {
    List<SearchModel> listData;
    Context context;
    int resource;



    public SearchAdaptor(@NonNull Context context, int resource, @NonNull List<SearchModel> listData) {
        super(context, resource, listData);


        this.context=context;
        this.resource=resource;
        this.listData=listData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.search_row,null,true);
        }
        SearchModel listdata=getItem(position);
        ImageView img=(ImageView)convertView.findViewById(R.id.ad_image);
        TextView txtCountry=convertView.findViewById(R.id.ad_status);
        TextView txtName=convertView.findViewById(R.id.ad_Name);
        TextView txtDetail=convertView.findViewById(R.id.ad_Detail);
        TextView txtAltDetail=convertView.findViewById(R.id.ad_Detail2);

        txtAltDetail.setText(listdata.getAdAltcategory());
        txtName.setText(listdata.getAdName());
        txtCountry.setText(listdata.getCountry());
        txtDetail.setText(String.valueOf(listdata.getDate()));

        Picasso
                .get()
                .load(listdata.getAdImage())
                .fit()
                .into(img);


        return convertView;
    }

}
