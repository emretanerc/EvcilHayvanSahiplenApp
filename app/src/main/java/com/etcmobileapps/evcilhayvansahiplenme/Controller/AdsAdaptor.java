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


import com.etcmobileapps.evcilhayvansahiplenme.Model.AdsModel;
import com.etcmobileapps.ucretsizevcilhayvansahiplenme.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.util.List;

public class AdsAdaptor extends ArrayAdapter<AdsModel> {
    List<AdsModel> listData;
    Context context;
    int resource;



    public AdsAdaptor(@NonNull Context context, int resource, @NonNull List<AdsModel> listData) {
        super(context, resource, listData);
        this.context=context;
        this.resource=resource;
        this.listData=listData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView=inflater.inflate(R.layout.lastads_row,null,true);
        }
        AdsModel listdata=getItem(position);





            ImageView img = (ImageView) convertView.findViewById(R.id.image_view);
            TextView txtCountry = convertView.findViewById(R.id.country);

            TextView txtName = convertView.findViewById(R.id.ads_name);


            txtCountry.setText(listdata.getCountry());
            txtName.setText(listdata.getAdAltcategory());

           Picasso
                    .get()
                    .load(listdata.getAdImage())
                    .resize(400,400)
                    .centerCrop()
                    .into(img);





        return convertView;
    }



}
