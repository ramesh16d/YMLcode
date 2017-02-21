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
import ramesh.mac.examples.ymlapp.entities.Follower;
import ramesh.mac.examples.ymlapp.ui.HomeActivityViewInterface;
import ramesh.mac.examples.ymlapp.ui.HomeActivityPresenterInterface;
import ramesh.mac.examples.ymlapp.ui.HomePresenterImple;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener, HomeActivityViewInterface {
    private static final String TAG = "HomeActivityTAG_";
    private List<Follower> myFollowers = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FollowerAdapter followerAdapter;
    private HomeActivityPresenterInterface homeActivityPresenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        followerAdapter = new FollowerAdapter(myFollowers, this);

        GridLayoutManager mGridManagerGrid = new GridLayoutManager(this, 3);

        recyclerView.setLayoutManager(mGridManagerGrid);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(followerAdapter);
        followerAdapter.setClickListener(this);
        homeActivityPresenterInterface = new HomePresenterImple(this);

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
                homeActivityPresenterInterface.getDetailsObserver(query);
                followerAdapter.notifyDataSetChanged();
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                HomeActivity.this.setTitle(query);
                setScreen(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }

        });


        return true;
    }

    public void setScreen(String s)
    {

        homeActivityPresenterInterface.getDetailsObserver(s);

    }

    @Override
    public void onClick(View view, int position) {
        Follower follower = myFollowers.get(position);
        homeActivityPresenterInterface.onItemClick(follower);
    }

    @Override
    public void getFollowerDetails(List<Follower> followers) {
        myFollowers.clear();
        for (Follower follower : followers) {
            myFollowers.add(follower);
            Log.d(TAG, "getFollowerDetails: " + follower.getLogin());
        }
        followerAdapter.notifyDataSetChanged();
    }

    @Override
    public void goToDetails(String loginName) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        intent.putExtra("loginName", loginName);
        startActivity(intent);
    }
}
