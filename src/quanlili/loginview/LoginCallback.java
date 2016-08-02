package quanlili.loginview;

import quanlili.beans.LoginPeople;

/**
 * Created by Administrator on 2016/7/23 0023.
 */
public interface LoginCallback {

    void showProgress();

    void hideProgress();

    void showLoadFailMsg(String msg);

    void showStuffInfo(LoginPeople stuffBean);
    void showToast(String msg);
    void movetointent();
}
