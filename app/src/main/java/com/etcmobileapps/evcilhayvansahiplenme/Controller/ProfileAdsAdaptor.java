package com.etcmobileapps.evcilhayvansahiplenme.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


import com.etcmobileapps.evcilhayvansahiplenme.Api.ApiClient;
import com.etcmobileapps.evcilhayvansahiplenme.Api.Interface;
import com.etcmobileapps.evcilhayvansahiplenme.Fragments.EditYourAdsFragment;
import com.etcmobileapps.evcilhayvansahiplenme.Model.AdsModel;
import com.etcmobileapps.evcilhayvansahiplenme.Model.SearchModel;
import com.etcmobileapps.ucretsizevcilhayvansahiplenme.R;
import com.squareup.picasso.Picasso;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

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
        TextView adViewValue = convertView.findViewById(R.id.ad_ViewValue);

        Integer confirmation = listdata.getConfirmation();
        if (confirmation==1) {
            txtStatus.setText("YAYINDA");
            txtStatus.setBackgroundResource(R.color.turuncu);
            txtStatus.setTextColor(WHITE);
        } else if (confirmation==0) {
            txtStatus.setText("İNCELENİYOR.");
            txtStatus.setBackgroundResource(R.color.turuncu);
            txtStatus.setTextColor(WHITE);
        } else if (confirmation==-1) {
            txtStatus.setBackgroundColor(RED);
            txtStatus.setBackgroundResource(R.color.turuncu);
            txtStatus.setText("YAYINDA DEĞİL.");

        }
        else if (confirmation==-2) {
            txtStatus.setBackgroundColor(RED);
            txtStatus.setTextColor(WHITE);
            txtStatus.setText("REDDEDİLDİ");
        }


        adViewValue.setText("Görüntülenme: " + String.valueOf(listdata.getAdViews()));

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


        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete(listdata.getId());
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


    public void delete (Integer id) {
        new AlertDialog.Builder(context)
                .setTitle("İlan Silme İşlemi")
                .setMessage("Bu ilanı silmek istediğinize emin misiniz?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        deleteAds(id);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Hayır", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void deleteAds (Integer id) {

        final Interface[] restInterface = new Interface[1];
        restInterface[0] = ApiClient.getClient().create(Interface.class);
        Call<SearchModel> call = restInterface[0].deleteAds(id);
        call.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(getContext(), "İlan başarıyla silindi.", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getContext(), "İlan silinemedi.", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                Log.e("Hata",t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
