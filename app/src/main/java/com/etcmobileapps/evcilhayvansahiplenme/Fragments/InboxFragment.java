package com.etcmobileapps.evcilhayvansahiplenme.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.etcmobileapps.evcilhayvansahiplenme.api.ApiClient;
import com.etcmobileapps.evcilhayvansahiplenme.api.Interface;
import com.etcmobileapps.evcilhayvansahiplenme.controller.AdsAdaptor;
import com.etcmobileapps.evcilhayvansahiplenme.controller.ProfileAdsAdaptor;
import com.etcmobileapps.evcilhayvansahiplenme.Controller.SearchAdaptor;
import com.etcmobileapps.evcilhayvansahiplenme.model.AdsModel;
import com.etcmobileapps.evcilhayvansahiplenme.model.SearchModel;
import com.etcmobileapps.ucretsizevcilhayvansahiplenme.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxFragment extends Fragment {
    GoogleSignInClient mGoogleSignInClient;
    String  userId;
    private List<AdsModel> myList;
    ListView myAdsListView;
    private ProfileAdsAdaptor adapter;
    FirebaseUser user;
    String providerId,name,email,uid;
    Uri photoUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // bu fragment'in layout'unu hazır hale getirelim

        View view = inflater.inflate(R.layout.inbox_fragment, container, false);


        myAdsListView = view.findViewById(R.id.myAdsListView);
        setClicks();

         user = FirebaseAuth.getInstance().getCurrentUser();


        return view;

    }


    @Override
    public void onStart() {
        super.onStart();

        getAccount();
        getOwnAds();

    }

    public void getAccount() {
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
            }
        }
    }


    public void setClicks () {

        myAdsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                    AdsModel repo = myList.get(position);

                    replaceFragmentsAds(AdDetailFragment.class,repo.getId());

               BottomNavigationView navigation = getActivity().findViewById(R.id.bottom_navigation);
                navigation.setClickable(true);



            }
        });


    }

    public void getOwnAds() {

        final Interface[] restInterface = new Interface[1];
        restInterface[0] = ApiClient.getClient().create(Interface.class);
        Call<List<AdsModel>> call = restInterface[0].getOwnAds(uid);
        call.enqueue(new Callback<List<AdsModel>>() {
            @Override
            public void onResponse(Call<List<AdsModel>> call, Response<List<AdsModel>> response) {
                myList=response.body();


                if (getActivity()!=null){
                    adapter=new ProfileAdsAdaptor(getContext(),R.layout.profileads_row,myList,getActivity());
                    Log.i("Bilgi",response.toString());
                    myAdsListView.setAdapter(adapter);
                }


              
               if (myList.toString().equals("[]")) {


                       replaceFragments(AddYourFirstAdFragment.class);



               }
            }

            @Override
            public void onFailure(Call<List<AdsModel>> call, Throwable t) {

                Toast.makeText(getContext(), "Hiç ilan paylaşmadınız.", Toast.LENGTH_SHORT).show();



            }
        });

    }



    public void replaceFragments(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragmentLayout, fragment)
                .commit();
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
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragmentLayout, fragment)
                .commit();
    }



}
