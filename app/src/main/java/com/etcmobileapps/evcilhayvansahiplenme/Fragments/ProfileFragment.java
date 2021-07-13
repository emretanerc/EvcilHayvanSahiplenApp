package com.etcmobileapps.evcilhayvansahiplenme.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.etcmobileapps.evcilhayvansahiplenme.Api.ApiClient;
import com.etcmobileapps.evcilhayvansahiplenme.Api.Interface;
import com.etcmobileapps.evcilhayvansahiplenme.Model.AdsModel;
import com.etcmobileapps.evcilhayvansahiplenme.Model.UserModel;
import com.etcmobileapps.ucretsizevcilhayvansahiplenme.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    CircleImageView profile_image;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 007;
    Button logOutButton;
    GoogleSignInAccount account;
    private List<UserModel> specList;
    TextView username,userid,usermail;
    UserModel repo;
    private List<UserModel> userList;
    String providerId,name,email,uid;
    Uri photoUrl;
    FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // bu fragment'in layout'unu hazır hale getirelim
        View view = inflater.inflate(R.layout.profile_fragment, container, false);




        account = GoogleSignIn.getLastSignedInAccount(getContext());



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build ();


        mGoogleSignInClient = GoogleSignIn.getClient(getContext(),gso);

        getAccount();
        getProfileSpecs();


        profile_image = view.findViewById(R.id.profile_image);
        logOutButton = view.findViewById(R.id.logOutButton);
        usermail = view.findViewById(R.id.usermail);

        username = view.findViewById(R.id.username);


         BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
        navigation.setClickable(true);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              logOut();

            }
        });




        return view;

    }


    public void getAccount() {

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

                Log.i("Bilgi",uid.toString());
            }
        }
    }

    public void getProfileSpecs() {

            final Interface[] restInterface = new Interface[1];
            restInterface[0] = ApiClient.getClient().create(Interface.class);
            Call<List<UserModel>>call = restInterface[0].getUserSpecs(uid);
            call.enqueue(new Callback<List<UserModel>>() {
                @Override
                public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

                  userList = response.body();


                    Picasso.get().load(photoUrl).fit().into(profile_image);

                    profile_image.setBorderColor(660099);



                    username.setText(name);
                    usermail.setText(email);



                }

                @Override
                public void onFailure(Call<List<UserModel>> call, Throwable t) {


                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
    }

                public  void logOut () {

    mGoogleSignInClient.signOut()
            .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    replaceFragments(RegisterFragment.class);

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
        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, fragment)
                .commit();
    }
}
