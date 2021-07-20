package com.etcmobileapps.evcilhayvansahiplenme.fragments;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SearchFragment extends Fragment {
    private ListView founds;
    private SearchAdaptor adapter;
    Integer profilMi=0;
    private List<SearchModel> listDataList;

    private Interface apiInterface;
    final SearchAdaptor listViewAdapter[]=new SearchAdaptor[1];

    ArrayList<SearchModel> foundData;
    ArrayList<AdsModel> foundDataOwn;
    String city;
    ListView list;
    String searchValue=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // bu fragment'in layout'unu hazır hale getirelim
        View view = inflater.inflate(R.layout.search_fragment, container, false);



        foundData = new ArrayList<>();
        founds = view.findViewById(R.id.myAdsListView);
        list = view.findViewById(R.id.myAdsListView);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

            if (profilMi==1) {

                SearchModel repo = listDataList.get(position);

                replaceFragmentsAds(AdDetailFragment.class,repo.getId());

            } else {

                SearchModel repo = foundData.get(position);

                replaceFragmentsAds(AdDetailFragment.class,repo.getId());

            }


            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getExtras();



    }



    public void getExtras() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            searchValue = bundle.getString("searchValue");
            String userId = bundle.getString("userId");
            city = bundle.getString("city");
            switch (searchValue) {


                case "id":
                   getOwnAds(userId);
                   profilMi=1;
                   break;
                case "last":
                getLast();
                break;
                case "köpek":
                    getSearch("köpek");
                    break;
                case "kedi":
                    getSearch("kedi");
                    break;
                case "kuş":
                    getSearch("kuş");
                    break;
                case "balık":
                    getSearch("balık");
                    break;
                case "tavşan":
                    getSearch("tavşan");
                    break;
                case "hamster":
                    getSearch("hamster");
                    break;
                case "tavuk":
                    getSearch("tavuk");
                    break;
                case "horoz":
                    getSearch("horoz");
                    break;
                case "kaz":
                    getSearch("kaz");
                    break;
                case "ördek":
                    getSearch("ördek");
                    break;
                case "sürüngenler":
                    getSearch("sürüngen");
                    break;
                case "others":
                    getOthers();
                    break;

                    default:

                        if (city.equals("Hepsi")) {
                            getSearch(searchValue);

                        } else  {
                            getSearchCity(searchValue,city);
                        }


                    break;

            }


        } else {



        }
    }


    public void getSearch(String searchValue) {
        final Interface[] restInterface = new Interface[1];
        restInterface[0] = ApiClient.getClient().create(Interface.class);

        Call<List<SearchModel>> repos;
        if (city==null) {
            repos = restInterface[0].getSearch(searchValue);
        } else if (city.equals("Hepsi"))  {
            repos = restInterface[0].getSearch(searchValue);
        } else {
            repos = restInterface[0].getAdsSearchWithCity(searchValue, city);
        }

        repos.enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(  Call<List<SearchModel>>  call, Response<List<SearchModel>> response) {
                if (response.body() != null) {


                   foundData.addAll(response.body());

                  //  Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_SHORT).show();

                }


                if (foundData.size() > 0) {
                    listViewAdapter[0] = new SearchAdaptor(getActivity(),R.layout.search_row, foundData);
                    founds.setAdapter(listViewAdapter[0]);
                } else {

                    Toast.makeText(getActivity(), "Herhangi bir ilan bulunamadı...", Toast.LENGTH_SHORT).show();
                    replaceFragments(AdsFragment.class);
                }

                Log.i("Bilgi",response.toString());
            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {
                Log.d(TAG, "Error: " + t.toString());
            }

        });

    }

    public void getSearchCity(String searchValue,String city) {
        final Interface[] restInterface = new Interface[1];
        restInterface[0] = ApiClient.getClient().create(Interface.class);




        Call<List<SearchModel>> repos = restInterface[0].getAdsSearchWithCity(searchValue,city);




        repos.enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(  Call<List<SearchModel>>  call, Response<List<SearchModel>> response) {
                if (response.body() != null) {


                    foundData.addAll(response.body());

                    //  Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_SHORT).show();

                }


                if (foundData.size() > 0) {
                    listViewAdapter[0] = new SearchAdaptor(getActivity(),R.layout.search_row, foundData);
                    founds.setAdapter(listViewAdapter[0]);
                } else {

                    Toast.makeText(getActivity(), "Herhangi bir ilan bulunamadı.", Toast.LENGTH_SHORT).show();
                    replaceFragments(AdsFragment.class);
                }

                Log.i("Bilgi",response.toString());

            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {
                Log.d(TAG, "Error: " + t.toString());
            }

        });

    }

    public void getLast() {
        final Interface[] restInterface = new Interface[1];
        restInterface[0] = ApiClient.getClient().create(Interface.class);


        Call<List<SearchModel>>  repos = restInterface[0].getAdsSearch();

        repos.enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(  Call<List<SearchModel>>  call, Response<List<SearchModel>> response) {
                if (response.body() != null) {


                    foundData.addAll(response.body());
                    Log.i("Bilgi",response.toString());
                   //   Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_SHORT).show();

                }


                if (foundData.size() > 0) {
                    listViewAdapter[0] = new SearchAdaptor(getActivity(),R.layout.search_row, foundData);
                    founds.setAdapter(listViewAdapter[0]);
                } else {

                    Toast.makeText(getActivity(), "Herhangi bir ilan bulunamadı.", Toast.LENGTH_SHORT).show();
                    replaceFragments(AdsFragment.class);
                }

            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {
                Log.d(TAG, "Error: " + t.toString());
            }

        });

    }


    public void getOwnAds(String userId) {

        final Interface[] restInterface = new Interface[1];
        restInterface[0] = ApiClient.getClient().create(Interface.class);
        Call<List<SearchModel>> call = restInterface[0].getOwnAdsId(userId);
        call.enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {

                listDataList=response.body();
                adapter=new SearchAdaptor(getContext(),R.layout.search_row,listDataList);
                Log.i("Bilgi",response.toString());
                founds.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {

                Toast.makeText(getContext(), "Hiç ilan paylaşmadınız.", Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void getOthers() {
        final Interface[] restInterface = new Interface[1];
        restInterface[0] = ApiClient.getClient().create(Interface.class);

        Call<List<SearchModel>>  repos = restInterface[0].getOtherAdsSearch();

        repos.enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(  Call<List<SearchModel>>  call, Response<List<SearchModel>> response) {
                if (response.body() != null) {


                    foundData.addAll(response.body());

                    //   Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_SHORT).show();

                }


                if (foundData.size() > 0) {
                    listViewAdapter[0] = new SearchAdaptor(getActivity(),R.layout.search_row, foundData);
                    founds.setAdapter(listViewAdapter[0]);
                } else {

                    Toast.makeText(getActivity(), "Herhangi bir ilan bulunamadı.", Toast.LENGTH_SHORT).show();
                    replaceFragments(AdsFragment.class);
                }

            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {
                Log.d(TAG, "Error: " + t.toString());
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