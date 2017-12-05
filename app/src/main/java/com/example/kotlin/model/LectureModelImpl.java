package com.example.kotlin.model;

import com.example.kotlin.Net.RetrofitManager;
import com.example.kotlin.Net.RxUtils;
import com.handarui.acquire.server.api.bean.AuthorInfoBean;
import com.handarui.acquire.server.api.service.AuthorService;

import rx.Single;

/**
 * Created by zx on 2017/8/16 0016.
 */

public class LectureModelImpl extends BaseModelImpl {
    public Single<AuthorInfoBean> getAuthorInfo(Long id) {
        AuthorService authorService = RetrofitManager.INSTANCE.getRetrofit().create(AuthorService.class);
        return RxUtils.wrapRestCall(authorService.getAuthorInfo(createLongRequest(id)));
    }
}
