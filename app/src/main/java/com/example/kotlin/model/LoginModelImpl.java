package com.example.kotlin.model;

import com.example.kotlin.Net.RetrofitManager;
import com.example.kotlin.Net.RxUtils;
import com.facebook.login.LoginResult;
import com.handarui.acquire.server.api.bean.FacebookLoginBean;
import com.handarui.acquire.server.api.bean.UserInfo;
import com.handarui.acquire.server.api.service.UserLoginService;
import com.handarui.acquire.server.api.service.UserService;

import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jly on 2016/8/26.
 */
public class LoginModelImpl extends BaseModelImpl  {

    Subscription facebookLoginSubscribe;
    UserService userService;
    private UserLoginService userLoginService;
    private Subscription googleLoginSubscribe;

    public LoginModelImpl() {
        userLoginService = RetrofitManager.INSTANCE.getRetrofit().create(UserLoginService.class);
        userService = RetrofitManager.INSTANCE.getRetrofit().create(UserService.class);
    }

    public void facebookLogin(LoginResult loginResult, final Action1<? super UserInfo> a) {

        FacebookLoginBean facebookLoginBean = new FacebookLoginBean();
        facebookLoginBean.userId = loginResult.getAccessToken().getUserId();
        facebookLoginBean.accessToken = loginResult.getAccessToken().getToken();


        facebookLoginSubscribe = userLoginService.loginWithFacebook(createFacebookRequest(loginResult.getAccessToken().getUserId(),
                loginResult.getAccessToken().getToken()
        )).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        getUserInfo().subscribe(a, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    public Single<UserInfo> getUserInfo() {
      return RxUtils.wrapRestCall(userService.getUserInfo(createVoidRequest()));
    }
}
