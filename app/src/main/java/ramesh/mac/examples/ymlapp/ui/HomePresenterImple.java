package ramesh.mac.examples.ymlapp.ui;

import android.util.Log;

import java.util.List;

import ramesh.mac.examples.ymlapp.apicalls.RetrofitHelper;
import ramesh.mac.examples.ymlapp.entities.Follower;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class HomePresenterImple implements HomeActivityPresenterInterface {
    private static final String TAG = "HomePresenterImpleTAG_";
    private HomeActivityViewInterface myHomeActivity;

    public HomePresenterImple(HomeActivityViewInterface myHomeView) {
        myHomeActivity = myHomeView;
    }

    @Override
    public void getDetailsObserver(String name) {
        Observable<List<Follower>> resultObservable = RetrofitHelper.Factory.createObservable(name);
        Observer<List<Follower>> followerObserver = new Observer<List<Follower>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onNext(List<Follower> followers) {
                myHomeActivity.getFollowerDetails(followers);
            }
        };

        resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followerObserver);
    }

    @Override
    public void onItemClick(Follower follower) {
        myHomeActivity.goToDetails(follower.getLogin());
    }
}
