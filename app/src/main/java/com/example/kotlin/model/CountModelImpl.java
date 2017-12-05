package com.example.kotlin.model;

import android.util.Log;

import com.example.kotlin.Net.RetrofitManager;
import com.example.kotlin.Net.RxUtils;
import com.handarui.acquire.server.api.service.AnalyticsService;
import com.handarui.acquire.server.api.service.UserCourseService;

import rx.functions.Action1;

/**
 * Created by zx on 2017/7/3 0003.
 */

public class CountModelImpl extends BaseModelImpl {


    static CountModelImpl countModelImpl;

    public static CountModelImpl getInstance() {
        if (countModelImpl == null) {
            return new CountModelImpl();
        }
        return countModelImpl;
    }

    public void restartLearnTime() {
//        if (!LoginUtils.isAppHasSignIn()) {
//            return;
//        }
        UserCourseService userCourseService = RetrofitManager.INSTANCE.getRetrofit().create(UserCourseService.class);
        RxUtils.wrapRestCall(userCourseService.restartLearnTime(createVoidRequest())).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    private static final String TAG = "CountModelImpl";

    public void appendLearnTime() {
        Log.i(TAG, "appendLearnTime:");
//        if (!LoginUtils.isAppHasSignIn()) {
//            return;
//        }
        UserCourseService userCourseService = RetrofitManager.INSTANCE.getRetrofit().create(UserCourseService.class);
        RxUtils.wrapRestCall(userCourseService.appendLearnTime(createVoidRequest())).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                i = 0;
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    static int i = 0;

    public void appendTime() {
        i++;
        Log.i(TAG, "appendTime: i = " + i);
        if (i > 60 * 10) {
            appendLearnTime();
        }
    }


    public void active() {
//        if (!LoginUtils.isPostActiveInfo(MyApplication.getInstance())) {
//            AnalyticsService analyticsService = RetrofitManager.INSTANCE.getRetrofit().create(AnalyticsService.class);
//
//            RxUtils.wrapRestCall(analyticsService.active(createVoidRequest())).subscribe(new Action1<String>() {
//                @Override
//                public void call(String s) {
//                    LoginUtils.setIsPostActiveInfo(MyApplication.getInstance(), true);
//                }
//            }, new Action1<Throwable>() {
//                @Override
//                public void call(Throwable throwable) {
//                    LoginUtils.setIsPostActiveInfo(MyApplication.getInstance(), false);
//                }
//            });
//        }
    }

    public void actionEvent(String action, Long id) {
        AnalyticsService analyticsService = RetrofitManager.INSTANCE.getRetrofit().create(AnalyticsService.class);
        RxUtils.wrapRestCall(analyticsService.actionEvent(createActionEventQueryRequest(action, id))).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "call: " + s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(TAG, "call: " + throwable.getMessage());
            }
        });
    }
}
