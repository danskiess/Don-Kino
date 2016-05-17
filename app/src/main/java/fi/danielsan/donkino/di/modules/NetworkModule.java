package fi.danielsan.donkino.di.modules;


import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fi.danielsan.donkino.data.APIConfiguration;
import fi.danielsan.donkino.data.OkHttpConfiguration;
import fi.danielsan.donkino.data.api.KinoService;
import fi.danielsan.donkino.data.api.xml.Serializers;
import fi.danielsan.donkino.misc.RetryInterceptor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    KinoService providesKinoService(Retrofit retrofit) {
        return retrofit.create(KinoService.class);
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(@Named("BASE_URL") final String END_POINT,
                              OkHttpClient okHttpClient,
                              Converter.Factory xmlFormatter,
                              RxJavaCallAdapterFactory rxJavaCallAdapterFactory){

        return new Retrofit.Builder()
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(xmlFormatter)
                .baseUrl(END_POINT)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @Named("BASE_URL")
    String providesEndPoint(){
        return APIConfiguration.BASE_URL;
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttp(Interceptor retryInterceptor, HttpLoggingInterceptor loggingInterceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(retryInterceptor)
                .connectTimeout(OkHttpConfiguration.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(OkHttpConfiguration.READ_TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Interceptor providesNetworkInterceptor(){
        return new RetryInterceptor(2, 3000);
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor providesLoggingInterceptor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return logging;
    }

    @Provides
    @Singleton
    Converter.Factory providesXmlFormatter(){
        return SimpleXmlConverterFactory.create(Serializers.createSerializer());
    }

    @Provides
    @Singleton
    RxJavaCallAdapterFactory providesRxAdapter(){
        return RxJavaCallAdapterFactory.create();
    }

}
