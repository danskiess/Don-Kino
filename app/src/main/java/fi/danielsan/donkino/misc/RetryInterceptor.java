package fi.danielsan.donkino.misc;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {

    private final int maxRetries;
    private final long delayInMillis;

    private int retryCounter;

    public RetryInterceptor(int maxRetries, long delayInMillis) {
        this.maxRetries = maxRetries;
        this.delayInMillis = delayInMillis;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (!response.isSuccessful()){
            while (retryCounter < maxRetries) {
                try {
                    Thread.sleep(delayInMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = chain.proceed(chain.request());
                retryCounter++;
            }
        }
        return response;
    }
}
