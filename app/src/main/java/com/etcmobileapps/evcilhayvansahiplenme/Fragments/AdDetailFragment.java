package com.etcmobileapps.evcilhayvansahiplenme.fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.etcmobileapps.evcilhayvansahiplenme.model.AdsModel;
import com.etcmobileapps.evcilhayvansahiplenme.model.UserModel;
import com.etcmobileapps.evcilhayvansahiplenme.api.ApiClient;
import com.etcmobileapps.evcilhayvansahiplenme.api.Interface;
import com.etcmobileapps.ucretsizevcilhayvansahiplenme.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdDetailFragment extends Fragment {

    private List<UserModel> specList;
    PhotoView adimagex,adimagex2;
    TextView adName,adDetail,adCountry,dateValue;
    Integer adId;
    TextView ageValue,sexValue,CategoryValue,cinsValue,memberName;
    GoogleSignInAccount account;
    LinearLayout wpButton;
    Button allAdsButton,buttonTelephone;
    LinearLayout callButton;
    View view;
    String ownerId;
    String photo1url,photo2url;
    GoogleSignInClient mGoogleSignInClient;
    String  userId;
    AdsModel repo;
    CircleImageView profileImage;
    Dialog dialog;
    private List<UserModel> userRepo;
    String providerId,name,email,uid;
    Uri photoUrl;
    FirebaseUser user;
    String  telephone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // bu fragment'in layout'unu haz??r hale getirelim
         view = inflater.inflate(R.layout.ads_detail, container, false);
        setView();
        getExtras();
        getAccountDetails();
        setClick();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    public  void getAccountDetails () {

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                providerId = profile.getProviderId();

                // UID specific to the provider
                uid = profile.getUid();

                // Name, email address, and profile photo Url
                name = profile.getDisplayName();
                email = profile.getEmail();
                photoUrl = profile.getPhotoUrl();


                userId = profile.getUid();

                Log.i("Bilgi",uid.toString());
            }
        }


    }






    public void setView()
    {
        adimagex = view.findViewById(R.id.adimagex);
        adName = view.findViewById(R.id.ad_name);
        adDetail = view.findViewById(R.id.ad_detail);
        adCountry = view.findViewById(R.id.country);
        dateValue = view.findViewById(R.id.date);
        memberName = view.findViewById(R.id.profileName);
        ageValue = view.findViewById(R.id.yasvalue);
        sexValue = view.findViewById(R.id.adsexvalue);
        CategoryValue = view.findViewById(R.id.adcategoryvalue);
        cinsValue = view.findViewById(R.id.cinsValue);
        allAdsButton = view.findViewById(R.id.allAdsBt);
        wpButton =view.findViewById(R.id.wpButton);
        buttonTelephone= view.findViewById(R.id.buttonTelephone);
        adimagex2 = view.findViewById(R.id.adimagex2);
        profileImage = view.findViewById(R.id.profileImage);
        callButton = view.findViewById(R.id.callButton);

    }    public void getExtras() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            adId = bundle.getInt("adid");
            lastAds(adId);

        }
    }

    public void getProfileSpecs(String id) {



        final Interface[] restInterface = new Interface[1];
        restInterface[0] = ApiClient.getClient().create(Interface.class);
        Call<List<UserModel>> call = restInterface[0].getUserSpecs(id);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {


                userRepo = response.body();


                if (userRepo.get(0).getUserPhoto()==null) {


                    Picasso.get().load(R.drawable.nopic).fit().into(profileImage);

                } else {

                    Picasso.get().load(userRepo.get(0).getUserPhoto()).fit().into(profileImage);

                }

                Log.i("Bilgi",response.toString());

            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {


                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();


            }
        });
    }



    public void setClick() {

        wpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_VIEW);
                sendIntent.setPackage("com.whatsapp");
                String url = "https://api.whatsapp.com/send?phone=9" + telephone;
                sendIntent.setData(Uri.parse(url));
                if(sendIntent.resolveActivity(getContext().getPackageManager()) != null){
                    startActivity(sendIntent);
                }

            }
        });


        adimagex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog("1");
            }
        });

        adimagex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog("2");
            }
        });

        allAdsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                replaceFragmentsSearch(ownerId,SearchFragment.class);

            }
        });


        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onCallBtnClick();

            }
        });



    }


    public void replaceFragmentsSearch(String id,Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        Bundle args = new Bundle();
        args.putString("searchValue","id");
        args.putString("userId",id);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragmentLayout, fragment)
                .commit();
    }

    public void setViewStatic(Integer  id, Integer view) {

        final Interface[] restInterface = new Interface[1];
        restInterface[0] = ApiClient.getClient().create(Interface.class);
        Call<AdsModel> call = restInterface[0].view(id);
        call.enqueue(new Callback<AdsModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<AdsModel> call, Response<AdsModel> response) {

                repo= response.body();



            }

            @Override
            public void onFailure(Call<AdsModel> call, Throwable t) {
                Log.e("Hata",t.toString());
            }
        });

    }

    public void lastAds(Integer id) {

        final Interface[] restInterface = new Interface[1];
        restInterface[0] = ApiClient.getClient().create(Interface.class);
        Call<AdsModel> call = restInterface[0].getAdDetails(id);
        call.enqueue(new Callback<AdsModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<AdsModel> call, Response<AdsModel> response) {

                repo= response.body();

                ownerId =  repo.getAdOwnerid();

                getProfileSpecs(ownerId);




            adCountry.setText(repo.getCountry());
            adName.setText("\n" +repo.getAdName() + "\n");


            dateValue.setText(repo.getDate().toString());

            telephone = repo.getAdOwnertelephone();

                Picasso
                        .get()
                        .load(repo.getAdImage())
                        .fit()
                        .into(adimagex);

                photo1url =repo.getAdImage();

               if (repo.getAdImage2()==null) {

                   adimagex2.setVisibility(View.GONE);

               } else {

                   Picasso
                           .get()
                           .load(repo.getAdImage2())
                           .fit()
                           .into(adimagex2);
                   photo2url =repo.getAdImage2();


               }



                adDetail.setText("\n" + repo.getAdDetail() + "\n");

                setViewStatic(repo.getId(),repo.getAdViews()+1);

                ageValue.setText(repo.getAdAge());
                sexValue.setText(repo.getAdSex());
                CategoryValue.setText(repo.getAdCategory());
                cinsValue.setText(repo.getAdAltcategory());
                memberName.setText(repo.getAdOwnername());

                buttonTelephone.setText("Mesaj G??nder +" + repo.getAdOwnertelephone());


            }

            @Override
            public void onFailure(Call<AdsModel> call, Throwable t) {
                Log.e("Hata",t.toString());
            }
        });

    }

    private void onCallBtnClick(){
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        }else {

            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall();
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 9);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch(requestCode){
            case 9:
                permissionGranted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                phoneCall();
                break;
        }
        if(permissionGranted){
            phoneCall();

        }else {
            Toast.makeText(getActivity(), "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void phoneCall(){
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + telephone));
            getActivity().startActivity(callIntent);
        }else{
            Toast.makeText(getActivity(), "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }



    private void showDialog(String url) {
        // custom dialog
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog);

        // set the custom dialog components - text, image and button
        ImageButton close = (ImageButton) dialog.findViewById(R.id.btnClose);
        Button buy = (Button) dialog.findViewById(R.id.btnBuy);
        ImageView photo = (ImageView) dialog.findViewById(R.id.photo);

       if (url.equals("1")) {
       Picasso.get().load(photo1url).fit().into(photo); }

       else {
           Picasso.get().load(photo2url).fit().into(photo);


       }


        // Close Button
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //TODO Close button action
            }
        });

        // Buy Button
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //TODO Buy button action
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }
}