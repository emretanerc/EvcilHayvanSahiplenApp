package com.etcmobileapps.evcilhayvansahiplenme.fragments;

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
import android.widget.TextView;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddYourFirstAdFragment extends Fragment {
  TextView ilanekle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // bu fragment'in layout'unu hazır hale getirelim

        View view = inflater.inflate(R.layout.addfirstads, container, false);


        ilanekle = view.findViewById(R.id.ilanekle);

        ilanekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                replaceFragments(NewAdd_Fragment.class);

            }
        });

        return view;

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

}
