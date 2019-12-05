package com.mindertech.xxnetwork;

import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2019-12-05 09:45
 * @description 描述
 */
public class XXHttpCallFactory extends CallAdapter.Factory {

    public XXHttpCallFactory() {

    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        TypeToken<?> token = TypeToken.get(returnType);
        if (token.getRawType() != XXHttpCall.class) {
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException("Call must have generic type (e.g., Call<ResponseBody>)");
        }
        final Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);

        return new CallAdapter<Object, XXHttpCall<?>>() {
            @Override public Type responseType() {
                return responseType;
            }

            @Override public XXHttpCall<?> adapt(retrofit2.Call<Object> call) {
                return new XXHttpCallAdapter<>(call);
            }
        };
    }
}
