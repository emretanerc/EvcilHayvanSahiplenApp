package com.etcmobileapps.evcilhayvansahiplenme.Fragments;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.etcmobileapps.evcilhayvansahiplenme.Api.ApiClient;
import com.etcmobileapps.evcilhayvansahiplenme.Api.Interface;
import com.etcmobileapps.evcilhayvansahiplenme.MainActivity;


import com.etcmobileapps.evcilhayvansahiplenme.Model.AdsModel;
import com.etcmobileapps.evcilhayvansahiplenme.Model.UserModel;
import com.etcmobileapps.ucretsizevcilhayvansahiplenme.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class RegisterFragment extends Fragment {
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 007;
    SignInButton button4;
    LinearLayout action;
    BottomNavigationView menu;
    GoogleSignInAccount account;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // bu fragment'in layout'unu hazır hale getirelim
        View view = inflater.inflate(R.layout.register_fragment, container, false);


        button4 = view.findViewById(R.id.button4);

        action = getActivity().findViewById(R.id.action);
        menu = getActivity().findViewById(R.id.bottom_navigation);



        action.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);



        TextView textView = (TextView) button4.getChildAt(0);
        textView.setText("GMAİL İLE GİRİŞ YAP");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)

                .requestEmail ()
                .build ();





        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        account = GoogleSignIn.getLastSignedInAccount(getContext());

      if (account==null) {

          LinearLayout loading = getActivity().findViewById(R.id.loading);
          loading.setVisibility(View.GONE);

      } else {

          replaceFragments(AdsFragment.class);

      }

        return  view;


    }






    @Override
    public void onStart() {
        super.onStart();
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                signIn();


            }
        });


    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
             account = completedTask.getResult(ApiException.class);
            registerUser();
            // Signed in successfully, show authenticated UI.
           // registerUser(account.getId(),account.getEmail(),account.getDisplayName());
                    replaceFragments(AdsFragment.class);
            //  updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

            //    updateUI(null);
        }
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


    public void registerUser() {

        final Interface[] restInterface = new Interface[1];
        restInterface[0] = ApiClient.getClient().create(Interface.class);
        Call<UserModel> call = restInterface[0].registerUser(account.getId(),account.getDisplayName(),account.getEmail(),account.getPhotoUrl().toString());
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

             UserModel repo= response.body();




            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

    }
}