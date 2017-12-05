package com.example.kotlin.model;

import com.example.kotlin.Net.RetrofitManager;
import com.example.kotlin.Net.RxUtils;
import com.handarui.acquire.server.api.bean.PaymentMethodBean;
import com.handarui.acquire.server.api.service.PurchaseService;
import com.handarui.acquire.server.api.service.RechargeService;

import java.util.List;

import rx.Single;

/**
 * Created by zx on 2017/6/29 0029.
 */

public class PayModelImpl extends BaseModelImpl {


    private RechargeService rechargeService;

    public PayModelImpl() {
        rechargeService = RetrofitManager.INSTANCE.getRetrofit().create(RechargeService.class);
    }

    public Single<List<PaymentMethodBean>> paymentMethods(String provider) {
        return RxUtils.wrapRestCall(rechargeService.getPaymentMethods(createVoidRequest()));
    }

    public Single<String> createOrder() {
        return RxUtils.wrapRestCall(rechargeService.createOrder(createVoidRequest()));
    }

    public Single<Void> buyCourse(Long albumId) {
        PurchaseService purchaseService = RetrofitManager.INSTANCE.getRetrofit().create(PurchaseService.class);
        return RxUtils.wrapRestCall(purchaseService.buyCourse(createLongRequest(albumId)));
    }

}
