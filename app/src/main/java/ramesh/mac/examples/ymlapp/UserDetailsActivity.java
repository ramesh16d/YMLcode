package ramesh.mac.examples.ymlapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import ramesh.mac.examples.ymlapp.adapters.CircleTransformation;
import ramesh.mac.examples.ymlapp.entities.RepoUser;
import ramesh.mac.examples.ymlapp.ui.UserDetailActivityPresenterInterface;
import ramesh.mac.examples.ymlapp.ui.UserDetailActivityViewInterface;
import ramesh.mac.examples.ymlapp.ui.UserDetailPresenterImpl;

public class UserDetailsActivity extends AppCompatActivity implements UserDetailActivityViewInterface {
    UserDetailActivityPresenterInterface myUserDetailsViewPresenter;

    @BindView(R.id.detailImageView)
    ImageView imageView;
    @BindView(R.id.logInName)
    TextView loginNametx;
    @BindView(R.id.tvFollowing)
    TextView followingtx;
    @BindView(R.id.tvFollowers)
    TextView followertx;
    @BindView(R.id.tvRepositories)
    TextView reopstx;
    @BindView(R.id.tvEmail)
    TextView emailtx;
    @BindView(R.id.tvLocation)
    TextView locationtx;
    @BindView(R.id.myLinearLayOut)
    LinearLayout myLinearLayoutGroup;
    @BindView(R.id.myFrameLayout)
    FrameLayout myFrameGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_acivity);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        String loginNameUser = bundle.getString("loginName");

        myUserDetailsViewPresenter = new UserDetailPresenterImpl(this);
        myUserDetailsViewPresenter.getUserData(loginNameUser);
    }

    @Override
    public void setDetails(RepoUser repoUser) {
        Picasso.with(getApplicationContext()).load(repoUser.getAvatarUrl()).transform(new CircleTransformation()).into(imageView);
        loginNametx.setText(repoUser.getLogin());
        followertx.setText(repoUser.getFollowers().toString());
        followingtx.setText(repoUser.getFollowing().toString());
        reopstx.setText(repoUser.getPublicRepos().toString());
        emailtx.setText(repoUser.getEmail());
        locationtx.setText(repoUser.getLocation());

        Picasso.with(this).load(repoUser.getAvatarUrl()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                myLinearLayoutGroup.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }
}
