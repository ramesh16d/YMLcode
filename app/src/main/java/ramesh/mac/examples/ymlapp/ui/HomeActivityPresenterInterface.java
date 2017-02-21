package ramesh.mac.examples.ymlapp.ui;

import ramesh.mac.examples.ymlapp.entities.Follower;



public interface HomeActivityPresenterInterface {
     void getDetailsObserver(String name);
     void onItemClick(Follower follower);
}
