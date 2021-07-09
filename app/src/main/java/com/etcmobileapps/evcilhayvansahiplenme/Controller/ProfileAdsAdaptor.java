package com.etcmobileapps.evcilhayvansahiplenme.Controller;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


import com.etcmobileapps.evcilhayvansahiplenme.Fragments.AdDetailFragment;
import com.etcmobileapps.evcilhayvansahiplenme.Fragments.EditYourAdsFragment;
import com.etcmobileapps.evcilhayvansahiplenme.Fragments.InboxFragment;
import com.etcmobileapps.evcilhayvansahiplenme.Model.AdsModel;
import com.etcmobileapps.ucretsizevcilhayvansahiplenme.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.util.List;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;

public class ProfileAdsAdaptor extends ArrayAdapter<AdsModel> {
    List<AdsModel> listData;
    Context context;
    int resource;
    FragmentActivity fragmentx;


    public ProfileAdsAdaptor(@NonNull Context context, int resource, @NonNull List<AdsModel> listData,FragmentActivity fragmentx) {

        super(context, resource, listData);


        this.context=getContext();
        this.resource=resource;
        this.listData=listData;
        this.fragmentx=fragmentx;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.profileads_row,null,false);
        }
        AdsModel listdata=getItem(position);





        ImageView img = (ImageView) convertView.findViewById(R.id.ad_image);
        TextView txtStatus = convertView.findViewById(R.id.ad_status);
        TextView adDetail = convertView.findViewById(R.id.ad_Detail2);
        TextView adDetail2 = convertView.findViewById(R.id.ad_Detail);
        TextView txtName = convertView.findViewById(R.id.ad_Name);
        Button changeBt = convertView.findViewById(R.id.changeBt);
        Button deleteBt = convertView.findViewById(R.id.deleteBt);

        Integer confirmation = listdata.getConfirmation();
        if (confirmation==1) {
            txtStatus.setText("YAYINDA");
            txtStatus.setBackgroundResource(R.color.green);
            txtStatus.setTextColor(WHITE);
        } else if (confirmation==0) {
            txtStatus.setText("İNCELENİYOR.");
            txtStatus.setBackgroundResource(R.color.mor);
            txtStatus.setTextColor(WHITE);
        } else if (confirmation==-1) {
            txtStatus.setBackgroundColor(RED);
            txtStatus.setTextColor(WHITE);
            txtStatus.setText("YAYINDA DEĞİL.");

        }
        else if (confirmation==-2) {
            txtStatus.setBackgroundColor(RED);
            txtStatus.setTextColor(WHITE);
            txtStatus.setText("REDDEDİLDİ");
        }

        txtName.setText(listdata.getAdName());
        adDetail2.setText("\n" + listdata.getAdDetail());
        adDetail.setText(listdata.getAdAltcategory());

        Picasso
                .get()
                .load(listdata.getAdImage())
                .into(img);


           if (adDetail2.length()>35) {
          String detail=listdata.getAdDetail().substring(0,20)+"...";
            adDetail2.setText(detail);
              }

        changeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragmentsAds(EditYourAdsFragment.class,listdata.getId());
            }
        });




        return convertView;
    }


    public void replaceFragmentsAds(Class fragmentClass, Integer adid) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        Bundle args = new Bundle();
        args.putInt("adid",adid);
        fragment.setArguments(args);
        FragmentManager fragmentManager = fragmentx.getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragmentLayout, fragment)
                .commit();
    }

}
