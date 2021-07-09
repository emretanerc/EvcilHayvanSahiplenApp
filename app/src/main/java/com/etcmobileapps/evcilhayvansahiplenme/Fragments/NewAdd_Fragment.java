package com.etcmobileapps.evcilhayvansahiplenme.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Path;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.etcmobileapps.evcilhayvansahiplenme.Api.ApiClient;
import com.etcmobileapps.evcilhayvansahiplenme.Api.Interface;
import com.etcmobileapps.evcilhayvansahiplenme.Controller.AdsAdaptor;
import com.etcmobileapps.evcilhayvansahiplenme.Model.AdsModel;
import com.etcmobileapps.ucretsizevcilhayvansahiplenme.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class NewAdd_Fragment extends Fragment {

    private Uri filePath,filePath2;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    ImageButton image1,image2;
    Integer placed1,placed2=null;
    TextView closePhoto1,closePhoto2;
    Integer selectedPlace = null;
    Boolean finishUpload= false;
    private List<AdsModel> addNewList;

    private String[] spinnerCityList={"Şehir Seçiniz.","Tüm Türkiye","Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin",
            "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale",
            "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir",
            "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir",
            "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya",
            "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya",
            "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak",
            "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak",
            "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"};

    private String[] spinnerGenderList={"Cinsiyet seçiniz","Erkek","Dişi","Karışık"};
    private String[] spinnerCategoryList={"Kategori Seçiniz","Köpek","Kedi","Kuş","Balık","Tavşan","Hamster","Tavuk","Horoz","Kaz","Ördek","Sürüngenler"};
    Button pushButton;
    private Spinner spinnerIller,spinnerGender,spinnerCategory;

    private ArrayAdapter<String> dataAdapterForIller,dataAdapterForGender,dataAdapterForCategory;
    EditText telephoneTv;
    TextView genusTv,titleTv,detailTv,ageTv;
    GoogleSignInClient mGoogleSignInClient;
    String  userId;
    String userName;
    StorageReference ref;
    String photo1Url;
    String iconPathFirebase=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // bu fragment'in layout'unu hazır hale getirelim
        View view = inflater.inflate(R.layout.newadd_fragment, container, false);

        spinnerIller = view.findViewById(R.id.spinnerCity);
        spinnerGender = view.findViewById(R.id.spinnerGender);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        pushButton = view.findViewById(R.id.pushButton);
        genusTv = view.findViewById(R.id.ad_altcategory);
        detailTv =view.findViewById(R.id.ad_detail);
        titleTv = view.findViewById(R.id.ad_title);
        ageTv = view.findViewById(R.id.ad_age);
        telephoneTv = view.findViewById(R.id.telephone);
        placed1=0;
        placed2=0;
        closePhoto2 = view.findViewById(R.id.closePhoto2);
        closePhoto1= view.findViewById(R.id.closePhoto1);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        image1=view.findViewById(R.id.photo1);
        image2=view.findViewById(R.id.photo2);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken (getString (R.string.server_client_id))
                .requestEmail ()
                .build ();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());

        userId = account.getId().toString();
        userName = account.getDisplayName();

        closePhoto1.setVisibility(View.INVISIBLE);
        closePhoto2.setVisibility(View.INVISIBLE);


        closePhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                placed1=0;
                image1.setImageDrawable(null);
                image1.setBackgroundResource(R.drawable.upload);
               // Toast.makeText(getContext(), placed1.toString(), Toast.LENGTH_SHORT).show();
                closePhoto1.setVisibility(View.INVISIBLE);
            }
        });

        closePhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                image2.setImageDrawable(null);
                image2.setBackgroundResource(R.drawable.upload);
                placed2=0;
               // Toast.makeText(getContext(), placed2.toString(), Toast.LENGTH_SHORT).show();

                closePhoto2.setVisibility(View.INVISIBLE);
            }
        });

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPlace=1;
                SelectImage();
                placed1=1;
                closePhoto1.setVisibility(View.VISIBLE);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPlace=2;
                placed2=1;
                SelectImage();
                closePhoto2.setVisibility(View.VISIBLE);
            }
        });







        setCity();


        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (formControl()) {

                    if ((placed1==0) & (placed2==1)) {


                        uploadImage(1,filePath2,false);


                    } else if  ((placed1==1) & (placed2==0)) {

                        uploadImage(1,filePath,false);



                    } else if ((placed1==1) & (placed2==1)) {

                        uploadImage(2,filePath,false);
                        uploadImage(2,filePath2,true);
                    } else if ((placed1==0) & (placed2==0)) {

                        Toast.makeText(getContext(), "Lütfen fotoğraf yükleyiniz.", Toast.LENGTH_SHORT).show();


                    }

                } else {




                }

            }
        });



        return view;

    }



    public void addNewAd( String userId, String ownerName,String city, String category,String gender,String altCategory,String age,String tittle,String detail,String photo1,String photo2, String  date) {

        final Interface[] restInterface = new Interface[1];
        restInterface[0] = ApiClient.getClient().create(Interface.class);
        Call<AdsModel> call = restInterface[0].addNewAdd(userId,ownerName,telephoneTv.getText().toString(),"Sahiplenme",tittle,detail,category,altCategory,age,null,city,null,photo1,photo2,altCategory,date,gender);
        call.enqueue(new Callback<AdsModel>() {
            @Override
            public void onResponse(Call<AdsModel> call, Response<AdsModel> response) {
                if (response.isSuccessful()) {

                    replaceFragments(InboxFragment.class);

                    Toast.makeText(getActivity(), "İlan incelemeye gönderildi.", Toast.LENGTH_LONG).show();

                } else {



                }

            }

            @Override
            public void onFailure(Call<AdsModel> call, Throwable t) {
                Log.e("Hata",t.toString());
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data

            if (selectedPlace == 1) {

                filePath = data.getData();
                try {

                    // Setting image on image view using Bitmap
                    Bitmap bitmap = MediaStore
                            .Images
                            .Media
                            .getBitmap(
                                    getActivity().getContentResolver(),
                                    filePath);

                    image1.setImageDrawable(null);
                    image1.setImageBitmap(bitmap);

                } catch (IOException e) {
                    // Log the exception
                    e.printStackTrace();
                }


            } else {


                filePath2 = data.getData();
                try {

                    // Setting image on image view using Bitmap
                    Bitmap bitmap = MediaStore
                            .Images
                            .Media
                            .getBitmap(
                                    getActivity().getContentResolver(),
                                    filePath2);
                    image2.setImageDrawable(null);
                    image2.setImageBitmap(bitmap);

                } catch (IOException e) {
                    // Log the exception
                    e.printStackTrace();
                }



            }





        }
    }

    // UploadImage method
    private void uploadImage(Integer Mode,Uri filePathValue,Boolean finishUploadValue) {


        if (Mode == 1) {
            if (filePathValue != null) {

                // Code for showing progressDialog while uploading
                ProgressDialog progressDialog
                        = new ProgressDialog(getContext());
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                // Defining the child of storageReference
                StorageReference ref
                        = storageReference
                        .child(
                                "photos/"
                                        + UUID.randomUUID().toString());

                UploadTask uploadTask = ref.putFile(filePathValue);

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                                                           @Override
                                                                                           public void onComplete(@NonNull Task<Uri> task) {
                                                                                               if (task.isSuccessful()) {
                                                                                                   Uri downloadUri = task.getResult();
                                                                                                   iconPathFirebase = downloadUri.toString();


                                                                                                   addNewAd(
                                                                                                           userId,
                                                                                                           userName,
                                                                                                           spinnerIller.getSelectedItem().toString(),
                                                                                                           spinnerCategory.getSelectedItem().toString(),
                                                                                                           spinnerGender.getSelectedItem().toString(),
                                                                                                           genusTv.getText().toString(),
                                                                                                           ageTv.getText().toString(),
                                                                                                           titleTv.getText().toString(),
                                                                                                           detailTv.getText().toString(),
                                                                                                           iconPathFirebase,
                                                                                                           null,
                                                                                                           "24.06.2021"
                                                                                                   );

                                                                                               } else {
                                                                                                   // Handle failures
                                                                                                   // ...
                                                                                               }


                                                                                               // Image uploaded successfully
                                                                                               // Dismiss dialog
                                                                                               progressDialog.dismiss();
                                                                                               Toast
                                                                                                       .makeText(getContext(),
                                                                                                               "Resim yüklendi.",
                                                                                                               Toast.LENGTH_SHORT)
                                                                                                       .show();


                                                                                           }
                                                                                       }
                );


            }


        } else if (Mode == 2) {

            ref
                    = storageReference
                    .child(
                            "photos/"
                                    + UUID.randomUUID().toString());

            UploadTask uploadTask = ref.putFile(filePathValue);


            if (filePathValue != null) {

                // Code for showing progressDialog while uploading
                ProgressDialog progressDialog
                        = new ProgressDialog(getContext());
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                // Defining the child of storageReference

                if (finishUploadValue == false) {


                    StorageReference ref
                            = storageReference
                            .child(
                                    "photos/"
                                            + UUID.randomUUID().toString());

                    uploadTask = ref.putFile(filePathValue);

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                photo1Url = downloadUri.toString();

                                progressDialog.dismiss();
                                Toast
                                        .makeText(getContext(),
                                                "Resim yüklendi.",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });


                } else if (finishUploadValue) {


                    ref = storageReference.child("photos/" + UUID.randomUUID().toString());


                    // adding listeners on upload


                    uploadTask = ref.putFile(filePathValue);

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                iconPathFirebase = downloadUri.toString();

                                // Image uploaded successfully
                                // Dismiss dialog
                                progressDialog.dismiss();
                                Toast
                                        .makeText(getContext(),
                                                "Resim yüklendi.",
                                                Toast.LENGTH_SHORT)
                                        .show();


                                addNewAd
                                        (
                                        userId,
                                        userName,
                                        spinnerIller.getSelectedItem().toString(),
                                        spinnerCategory.getSelectedItem().toString(),
                                        spinnerGender.getSelectedItem().toString(),
                                        genusTv.getText().toString(),
                                        ageTv.getText().toString(),
                                        titleTv.getText().toString(),
                                        detailTv.getText().toString(),
                                        photo1Url,
                                        iconPathFirebase,
                                        String.valueOf(LocalDate.now())
                                );
                            }

                        }

                    });





                }


            }


        }


    };






    public void setCity() {


        // spinnerIlceler = (Spinner) getActivity().findViewById(R.id.spinner2);

        //Spinner'lar için adapterleri hazırlıyoruz.
        dataAdapterForIller = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, spinnerCityList);
        dataAdapterForCategory = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, spinnerCategoryList);
        dataAdapterForGender = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, spinnerGenderList);
        //dataAdapterForIlceler = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,ilceler0);

        //Listelenecek verilerin görünümünü belirliyoruz.
        dataAdapterForIller.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //dataAdapterForIlceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.
        spinnerIller.setAdapter(dataAdapterForIller);
        spinnerGender.setAdapter(dataAdapterForGender);
        spinnerCategory.setAdapter(dataAdapterForCategory);
        //spinnerIlceler.setAdapter(dataAdapterForIlceler);

        // Listelerden bir eleman seçildiğinde yapılacakları tanımlıyoruz.
        spinnerIller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //Hangi il seçilmişse onun ilçeleri adapter'e ekleniyor.
                /* if(parent.getSelectedItem().toString().equals(iller[0]))
                    dataAdapterForIlceler = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,ilceler0);
                else if(parent.getSelectedItem().toString().equals(iller[1]))
                    dataAdapterForIlceler = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,ilceler1);

                dataAdapterForIlceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerIlceler.setAdapter(dataAdapterForIlceler); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

/*        spinnerIlceler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //Seçilen il ve ilçeyi ekranda gösteriyoruz.
            //    sehirAdi = spinnerIller.getSelectedItem().toString();
              //  ilceAdi = parent.getSelectedItem().toString();

                //eczaneVericek(sehirAdi,ilceAdi);
                //Toast.makeText(getActivity(), sehirAdi + ilceAdi, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        }); */

    }

    private void getUrlAsync (String parametre){
        // Points to the root reference
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference dateRef = storageRef.child("photos/");
        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri downloadUrl)
            {
                //do something with downloadurl
            }
        });
    }


    public boolean formControl() {

        boolean control= true;

        if(TextUtils.isEmpty(titleTv.getText())) {
            titleTv.setError("İlan Başlığı boş bırakılamaz.");
             control= false;
        }

        if(TextUtils.isEmpty(detailTv.getText())) {
            detailTv.setError("Açıklama kısmı boş bırakılamaz.");
             control= false;
        }

        if(TextUtils.isEmpty(genusTv.getText())) {
            genusTv.setError("Cins kısmı boş bırakılamaz.");
             control= false;
        }

        if(TextUtils.isEmpty(ageTv.getText())) {
            genusTv.setError("Yaş kısmı boş bırakılamaz.");
            control= false;
        }

        if(TextUtils.isEmpty(telephoneTv.getText())) {
            genusTv.setError("Telefon kısmı boş bırakılamaz.");
            control= false;
        }

        if (spinnerCategory.getSelectedItem().toString().equals("Kategori Seçiniz")) {
            TextView errorText = (TextView)spinnerCategory.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Lütfen kategori seçiniz.");
            control= false;
        }

        if (spinnerIller.getSelectedItem().toString().equals("Şehir Seçiniz.")) {
            TextView errorText = (TextView)spinnerIller.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Lütfen Şehir seçiniz.");
            control= false;
        }

        if (spinnerGender.getSelectedItem().toString().equals("Cinsiyet seçiniz")) {
            TextView errorText = (TextView)spinnerGender.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Lütfen Şehir seçiniz.");
            control= false;
        }



        return control;

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
