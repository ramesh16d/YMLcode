package ramesh.mac.examples.ymlapp.ui;

import java.util.List;

import ramesh.mac.examples.ymlapp.entities.Follower;


public interface HomeActivityViewInterface {
     void getFollowerDetails(List<Follower> followers);
     void goToDetails(String loginName);
}
