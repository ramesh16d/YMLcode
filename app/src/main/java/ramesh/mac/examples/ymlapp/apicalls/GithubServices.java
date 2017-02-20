package ramesh.mac.examples.ymlapp.apicalls;

import java.util.List;

import ramesh.mac.examples.ymlapp.entities.Follower;
import ramesh.mac.examples.ymlapp.entities.RepoUser;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by User on 2/20/2017.
 */

public interface GithubServices {

    @GET("users/{user}/followers")
    Observable<List<Follower>> getfollowers(@Path("user") String user);

    @GET("users/{user}")
    Observable<RepoUser> getUserDetails(@Path("user") String user);
}
