package ramesh.mac.examples.ymlapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ramesh.mac.examples.ymlapp.adapters.FollowerAdapter;
import ramesh.mac.examples.ymlapp.adapters.OnItemClickListener;
import ramesh.mac.examples.ymlapp.apicalls.RetrofitHelper;
import ramesh.mac.examples.ymlapp.entities.Follower;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener{

    List<Follower> myFollowers = new ArrayList<>();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private FollowerAdapter folloW_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        folloW_adapter  = new FollowerAdapter(myFollowers,this);
     //   RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        GridLayoutManager mGridManagerGrid = new GridLayoutManager(this,3);

        recyclerView.setLayoutManager(mGridManagerGrid);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(folloW_adapter);
        folloW_adapter.setClickListener(this);

       // getdetailsObserver();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_followers_list, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                myFollowers.clear();



                getdetailsObserver(query);
                // Fetch the data remotely
                // fetchBooks(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                HomeActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }



        /*Search for the user--- if time permits */


    public void getdetailsObserver(String name)
    {

        Observable<List<Follower>> resultObservable = RetrofitHelper.Factory.createObservable(name);
        Observer followerObserver = new Observer<List<Follower>>() {
            @Override
            public void onCompleted() {

            }


            @Override
            public void onError(Throwable e) {
                Log.d(" Main Activity","On Error\n" +
                        "******************************************\n"+e.getMessage()+"\n");
            }

            @Override
            public void onNext(List<Follower> followers) {

                myFollowers.clear();

                for (Follower flw: followers
                     ) {
                    myFollowers.add(flw);

                    Log.d("MainACtivity", flw.getLogin());
                }

            }
        };

        resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followerObserver);
        printFollowersList();

    }


    public void printFollowersList()
    {
        for (Follower justOne: myFollowers
             ) {
            Log.d("InPrint Main Activity",justOne.toString());

        }
    }

    @Override
    public void onClick(View view, int position) {

        Follower follower = myFollowers.get(position);

        Intent intent = new Intent(this, UserDetailsAcivity.class);
        intent.putExtra("loginName", follower.getLogin());

        startActivity(intent);

          Log.d("Home",follower.getLogin());
    }
}
