package ramesh.mac.examples.ymlapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ramesh.mac.examples.ymlapp.adapters.CircleTransformation;
import ramesh.mac.examples.ymlapp.apicalls.RetrofitHelper;
import ramesh.mac.examples.ymlapp.entities.RepoUser;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserDetailsAcivity extends AppCompatActivity {




    @BindView(R.id.detailImageView) ImageView imageView;
    @BindView(R.id.logInName) TextView loginNametx;
    @BindView(R.id.tvFollowing) TextView followingtx;
    @BindView(R.id.tvFollowers) TextView followertx;
    @BindView(R.id.tvRepositories) TextView reopstx;
    @BindView(R.id.tvEmail) TextView emailtx;
    @BindView(R.id.tvLocation) TextView locationtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_acivity);
        //tv = (TextView) findViewById(R.id.tvDetailUser);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String loginNameUser = bundle.getString("loginName");
        getUserData(loginNameUser);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.myLinearLayOut);
        linearLayout.setBackgroundColor(Color.rgb(204,255,229));
    }


    public  void getUserData(final String loginName)
    {


        Observable<RepoUser> resultObservable = RetrofitHelper.Factory.createUserDetailsObservable(loginName);


        Observer userObserver  = new Observer<RepoUser>() {
            @Override
            public void onCompleted() {
                Log.d("IMplementaion ","Done");
            }

            @Override
            public void onError(Throwable e) {

                Log.d("IMplementaion ",e.getMessage());

            }

            @Override
            public void onNext(RepoUser repoUser) {

                //    setUserDetails(repoUser);


                Log.d("IMplementaion On Next ",repoUser.toString());
                //tv.setText(repoUser.toString());

                Picasso.with(getApplicationContext()).load(repoUser.getAvatarUrl()).transform(new CircleTransformation()).into(imageView);
                loginNametx.setText(repoUser.getLogin());
                followertx.setText(repoUser.getFollowers().toString());
                followingtx.setText(repoUser.getFollowing().toString());
                reopstx.setText(repoUser.getPublicRepos().toString());
                emailtx.setText(repoUser.getEmail());
                locationtx.setText(repoUser.getLocation());
            }

        };

        resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userObserver);


    }

}
