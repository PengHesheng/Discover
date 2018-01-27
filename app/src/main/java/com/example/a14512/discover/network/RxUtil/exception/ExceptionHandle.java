package com.example.a14512.discover.network.RxUtil.exception;

import retrofit2.HttpException;

/**
 * Created by 14512 on 2017/8/15.
 */

public class ExceptionHandle {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {
        ApiException exception;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            exception = new ApiException(e, Error.HTTP_ERROR);
            /**
             * 执行完所有，判断错误
             * */
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    exception.setDisplayMessage("401");
                case FORBIDDEN:
                    exception.setDisplayMessage("403");
                case NOT_FOUND:
                    exception.setDisplayMessage("404");
                case REQUEST_TIMEOUT:
                    exception.setDisplayMessage("408");
                case GATEWAY_TIMEOUT:
                    exception.setDisplayMessage("500");
                case INTERNAL_SERVER_ERROR:
                    exception.setDisplayMessage("502");
                case BAD_GATEWAY:
                    exception.setDisplayMessage("503");
                case SERVICE_UNAVAILABLE:
                    exception.setDisplayMessage("504");
                default:
                    exception.setDisplayMessage("网络错误");
                    break;
            }
            return exception;
        } else if (e instanceof ServiceException) {
            ServiceException resultException = (ServiceException) e;
            exception = new ApiException(resultException, resultException.getCode());
            exception.setDisplayMessage(resultException.getMsg());
            return exception;
        } else {
            exception = new ApiException(e, Error.UNKNOWN);
            exception.setDisplayMessage("未知错误");
            return exception;
        }
    }
}
