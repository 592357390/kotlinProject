package com.example.kotlin.model;

import com.example.kotlin.Net.RetrofitManager;
import com.example.kotlin.Net.RxUtils;
import com.handarui.acquire.server.api.bean.BannerInfoBean;
import com.handarui.acquire.server.api.bean.CourseBean;
import com.handarui.acquire.server.api.bean.LessonInfoBean;
import com.handarui.acquire.server.api.service.BannerService;
import com.handarui.acquire.server.api.service.CourseService;
import com.handarui.acquire.server.api.service.UserCourseService;

import java.util.List;

import rx.Single;

/**
 * Created by zx on 2017/7/18 0018.
 */

public class CourseModelImpl extends BaseModelImpl {

    public Single<List<CourseBean>> recommendCourses() {
        CourseService courseService = RetrofitManager.INSTANCE.getRetrofit().create(CourseService.class);
        return RxUtils.wrapRestCall(courseService.getRecommendCourses(createVoidRequest()));
    }

    public Single<List<CourseBean>> getAllBuyCourses(int page, int pageSize) {
        UserCourseService userCourseService = RetrofitManager.INSTANCE.getRetrofit().create(UserCourseService
                .class);
        return RxUtils.wrapRestCall(userCourseService.getCoursesPurchased(createPagerQueryRequest(page, pageSize)));
    }

    public Single<List<CourseBean>> getCoursesByAuthorId(Long authorId) {
        CourseService courseService = RetrofitManager.INSTANCE.getRetrofit().create(CourseService.class);
        return RxUtils.wrapRestCall(courseService.getCoursesByAuthorId(createLongRequest(authorId)));
    }

    public Single<List<LessonInfoBean>> recommendAuditionLessons() {
        CourseService courseService = RetrofitManager.INSTANCE.getRetrofit().create(CourseService.class);
        return RxUtils.wrapRestCall(courseService.getRecommendLessons(createVoidRequest()));
    }

    public Single<List<BannerInfoBean>> getBanners() {
        BannerService bannerService = RetrofitManager.INSTANCE.getRetrofit().create(BannerService.class);
        return RxUtils.wrapRestCall(bannerService.getBanners(createVoidRequest()));
    }

    public Single<CourseBean> getCourseInfoByCourseId(Long courseId) {
        CourseService courseService = RetrofitManager.INSTANCE.getRetrofit().create(CourseService.class);
        return RxUtils.wrapRestCall(courseService.getCourseInfo(createLongRequest(courseId)));
    }

    public Single<List<LessonInfoBean>> getLessonInfoBeanById(Long courseId) {
        CourseService courseService = RetrofitManager.INSTANCE.getRetrofit().create(CourseService.class);
        return RxUtils.wrapRestCall(courseService.getLessonsByCourseId(createLongRequest(courseId)));
    }

    public Single<List<LessonInfoBean>> allLatestAuditionLessons(int page, int pageSize) {
        CourseService courseService = RetrofitManager.INSTANCE.getRetrofit().create(CourseService.class);
        return RxUtils.wrapRestCall(courseService.getAllRecommendLessons(createPagerQueryRequest(page, pageSize)));
    }

    public Single<List<CourseBean>> getAllCourses(int page, int pageSize) {
        CourseService courseService = RetrofitManager.INSTANCE.getRetrofit().create(CourseService.class);
        return RxUtils.wrapRestCall(courseService.getAllCourses(createPagerQueryRequest(page, pageSize)));
    }
}
