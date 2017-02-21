package ramesh.mac.examples.ymlapp.apicalls;

import java.util.List;

import ramesh.mac.examples.ymlapp.entities.Follower;
import ramesh.mac.examples.ymlapp.entities.RepoUser;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;



public class RetrofitHelper {
    public static class Factory {
        static Retrofit retrofit = create();
        static GithubServices service = retrofit.create(GithubServices.class);

        public static Retrofit create() {
            return new Retrofit.Builder().baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build();
        }

        public static Observable<List<Follower>> createObservable(String username) {
            return service.getfollowers(username);
        }

        public static Observable<RepoUser> createUserDetailsObservable(String username) {
            return service.getUserDetails(username);
        }
    }
}
