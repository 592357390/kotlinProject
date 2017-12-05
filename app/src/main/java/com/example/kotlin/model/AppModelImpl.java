package com.example.kotlin.model;

import com.example.kotlin.Net.RetrofitManager;
import com.example.kotlin.Net.RxUtils;
import com.handarui.acquire.server.api.bean.AppVersionBean;
import com.handarui.acquire.server.api.bean.OSSConfigBean;
import com.handarui.acquire.server.api.service.AppVersionSevice;
import com.handarui.acquire.server.api.service.OSSService;
import com.handarui.acquire.server.api.service.UserService;

import rx.Single;

/**
 * Created by zx on 2017/8/8 0008.
 */

public class AppModelImpl extends BaseModelImpl {
    public Single<AppVersionBean> checkNewVersion() {
        AppVersionSevice appVersionSevice = RetrofitManager.INSTANCE.getRetrofit().create(AppVersionSevice.class);
        return RxUtils.wrapRestCall(appVersionSevice.getLatestVersion(createVoidRequest()));
    }

    public Single<OSSConfigBean> getOSSConfig() {
        OSSService ossService = RetrofitManager.INSTANCE.getRetrofit().create(OSSService.class);
        return RxUtils.wrapRestCall(ossService.getOSSConfig(createVoidRequest()));
    }

    public Single<String> feedBack(String content, String contactInfo) {
        UserService userService = RetrofitManager.INSTANCE.getRetrofit().create(UserService.class);
        return RxUtils.wrapRestCall(userService.sendFeedback(createNewFeedbackRequest(content, contactInfo)));
    }
}
