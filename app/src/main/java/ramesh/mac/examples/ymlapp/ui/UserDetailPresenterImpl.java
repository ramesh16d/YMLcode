package ramesh.mac.examples.ymlapp.ui;

import android.util.Log;

import ramesh.mac.examples.ymlapp.apicalls.RetrofitHelper;
import ramesh.mac.examples.ymlapp.entities.RepoUser;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class UserDetailPresenterImpl implements UserDetailActivityPresenterInterface {
    private static final String TAG = "UserDetailPresenterImpl";
    private UserDetailActivityViewInterface userDetailView;

    public UserDetailPresenterImpl(UserDetailActivityViewInterface userDetailView) {
        this.userDetailView = userDetailView;
    }

    @Override
    public void getUserData(String loginName) {
        Observable<RepoUser> resultObservable = RetrofitHelper.Factory.createUserDetailsObservable(loginName);

        Observer<RepoUser> userObserver = new Observer<RepoUser>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onNext(RepoUser repoUser) {
                userDetailView.setDetails(repoUser);

            }
        };

        resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userObserver);
    }
}
