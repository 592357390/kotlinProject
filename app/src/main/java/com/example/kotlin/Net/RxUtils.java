package com.example.kotlin.Net;

/**
 * Created by zx on 2017/11/7 0007.
 */

import android.util.Log;

import com.zhexinit.ov.common.bean.ResponseBean;
import com.zhexinit.ov.common.exception.CommonException;

import java.util.Collection;

import retrofit2.HttpException;
import rx.Observable;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * RxJava相关工具类
 * Created by guofe on 2016/1/22.
 */
public class RxUtils {

    /**
     * 剥离ResponseBean
     *
     * @param call
     * @param <T>
     * @return
     */
    public static <T> Single<T> wrapRestCall(final Observable<ResponseBean<T>> call) {
        return call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<ResponseBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final ResponseBean<T> response) {
                        if (response.getCode() == 0) {
                            return Observable.just(response.getResult());
                        } else {
                            return Observable.error(new CommonException(response.getCode(), response.getMessage()));
                        }
                    }
                }, new Func1<Throwable, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(final Throwable throwable) {
                        Log.e("API ERROR", throwable.toString());
                       /* if (throwable instanceof CallCanceledException) {
                            return Observable.never();
                        } else */
                        if (throwable instanceof HttpException) {
                            HttpException httpException = (HttpException) throwable;
                            if (httpException.code() == 403 || httpException.code() == 401 || httpException.code() == 103) {
//                                return Observable.error(new AuthorizeException());
                            }
                        }

                        return Observable.error(throwable);
                    }
                }, new Func0<Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call() {
                        return Observable.empty();
                    }
                }).toSingle();
    }

    /**
     * 取消订阅
     *
     * @param subscriptions
     */
    public static void unsubscribeIfNotNull(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null) {
                subscription.unsubscribe();
            }
        }
    }

    /**
     * 取消订阅
     *
     * @param subscriptions
     */
    public static void unsubscribeIfNotNull(Collection<Subscription> subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null) {
                subscription.unsubscribe();
            }
        }
    }
}

