package com.example.kotlin.model;

import com.example.kotlin.Net.RetrofitManager;
import com.example.kotlin.Net.RxUtils;
import com.handarui.acquire.server.api.service.UserLoginService;
import com.handarui.acquire.server.api.service.UserService;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jly on 2016/9/20.
 */
public class MyInfoModelImpl extends BaseModelImpl {
    private final static String TAG = MyInfoModelImpl.class.getSimpleName();

    public Single<String> updateAvatar(String avatarName) {
        final UserService userService = RetrofitManager.INSTANCE.getRetrofit().create(UserService.class);
        return RxUtils.wrapRestCall(userService.modifyPortrait(createStringRequest(avatarName)));
    }

    public void loinOut() {
        UserLoginService restService = RetrofitManager.INSTANCE.getRetrofit().create(UserLoginService.class);
        restService.loginOut().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }


}