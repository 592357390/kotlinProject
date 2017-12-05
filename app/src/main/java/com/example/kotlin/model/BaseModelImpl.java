package com.example.kotlin.model;

import com.handarui.acquire.server.api.bean.FacebookLoginBean;
import com.handarui.acquire.server.api.bean.GoogleLoginBean;
import com.handarui.acquire.server.api.query.ActionEventQuery;
import com.handarui.acquire.server.api.query.GPPurchaseCallbackQuery;
import com.handarui.acquire.server.api.query.NewFeedbackQuery;
import com.handarui.acquire.server.api.util.ObjectId;
import com.zhexinit.ov.common.bean.RequestBean;
import com.zhexinit.ov.common.query.PagerQuery;

/**
 * Created by zx on 2017/8/15 0015.
 */

public class BaseModelImpl {

    public RequestBean<Void> createVoidRequest() {
        return new RequestBean<>(ObjectId.get().toHexString(), System.currentTimeMillis(), null);
    }

    public RequestBean<FacebookLoginBean> createFacebookRequest(String aid, String token) {
        FacebookLoginBean facebookLoginBean = new FacebookLoginBean();
        facebookLoginBean.accessToken = token;
        facebookLoginBean.userId = aid;
        return new RequestBean<>(ObjectId.get().toHexString(), System.currentTimeMillis(), facebookLoginBean);
    }

    public RequestBean<GoogleLoginBean> createGoogleRequest(String token) {
        GoogleLoginBean googleLoginBean = new GoogleLoginBean();
        googleLoginBean.setToken(token);
        return new RequestBean<>(ObjectId.get().toHexString(), System.currentTimeMillis(), googleLoginBean);
    }

    public RequestBean<Integer> createIntegerRequest(Integer integer) {
        return new RequestBean<>(ObjectId.get().toHexString(), System.currentTimeMillis(), integer);
    }

    public RequestBean<Long> createLongRequest(Long id) {
        return new RequestBean<>(ObjectId.get().toHexString(), System.currentTimeMillis(), id);
    }

    public RequestBean<String> createStringRequest(String s) {
        return new RequestBean<>(ObjectId.get().toHexString(), System.currentTimeMillis(), s);
    }

    public RequestBean<PagerQuery<Void>> createPagerQueryRequest(int page, int pageSize) {
        return new RequestBean<>(ObjectId.get().toHexString(), System.currentTimeMillis(), new PagerQuery<Void>(page, pageSize, null));
    }

    public RequestBean<NewFeedbackQuery> createNewFeedbackRequest(String content, String contactInfo) {
        return new RequestBean<>(ObjectId.get().toHexString(), System.currentTimeMillis(), new NewFeedbackQuery(content, contactInfo));
    }

    public RequestBean<GPPurchaseCallbackQuery> createGPPRequest(String productId, String token, String gpOrderId) {
        return new RequestBean<>(ObjectId.get().toHexString(), System.currentTimeMillis(), new GPPurchaseCallbackQuery(productId, token, gpOrderId));
    }

    public RequestBean<ActionEventQuery> createActionEventQueryRequest(String action, Long id) {
        ActionEventQuery actionEventQuery = new ActionEventQuery();
        actionEventQuery.setAction(action);
        if (id != null) {
            actionEventQuery.setAssociatedId(id);
        }
        return new RequestBean<>(ObjectId.get().toHexString(), System.currentTimeMillis(), actionEventQuery);
    }
}
