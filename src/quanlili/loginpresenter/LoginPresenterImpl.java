package quanlili.loginpresenter;

import quanlili.beans.LoginPeople;
import quanlili.interfaces.OnReqLoginInfoListener;
import quanlili.model.LoginModel;
import quanlili.model.LoginModelImpl;
import quanlili.loginview.LoginCallback;

/**
 * Created by Administrator on 2016/7/23 0023.
 */
public class LoginPresenterImpl implements LoginPresenter, OnReqLoginInfoListener
{

    private LoginCallback loginCallback;
    private LoginModel mLoginModel;
    public LoginPresenterImpl(LoginCallback loginCallBack) {
        this.loginCallback = loginCallBack;
        this.mLoginModel = new LoginModelImpl();
    }
    @Override
    public void reqLoginInfo(String account, String pwd) {
        if (!account.isEmpty()&&!pwd.isEmpty())
        { if(account.length()==11&&pwd.length()>=6&&pwd.length()<=20)
        {this.loginCallback.showProgress();
        this.mLoginModel.reqLoginInfo(account, pwd, this);}
        else if (account.length()!=11)
            loginCallback.showToast("������11λ�ֻ���");
            else if (pwd.length()<6||pwd.length()>20)
            loginCallback.showToast("���������6~20λ֮��");
        }
        else if (account.isEmpty())
            loginCallback.showToast("�������ֻ���");
        else if (pwd.isEmpty())
            loginCallback.showToast("����������");
    }

    @Override
    public void onSuccess(LoginPeople sBean) {
        this.loginCallback.hideProgress();
        this.loginCallback.showStuffInfo(sBean);
        this.loginCallback.movetointent();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        this.loginCallback.hideProgress();
        this.loginCallback.showLoadFailMsg(msg + e);
    }

    @Override
    public void onFailure(String msg) {
        this.loginCallback.hideProgress();
        this.loginCallback.showLoadFailMsg(msg);
    }
}
