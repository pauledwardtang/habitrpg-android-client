package com.habitrpg.android.client;

import java.util.Map;

import android.content.Context;

import com.kodart.httpzoid.Cancellable;
import com.kodart.httpzoid.Http;
import com.kodart.httpzoid.HttpFactory;
import com.kodart.httpzoid.ResponseHandler;

/**
 * Android REST client using generics for retrieving information from a RESTful API.
 * This is a wrapper around the Httpzoid REST client
 * @author Paul T
 */
public class AndroidRestClient
{
    private Http http;
    private Map<String, String> headers;

    /**
     * Creates an instance of this class given a Context
     * @param ctx The context to use for HTTP requests
     */
    public AndroidRestClient(Context ctx)
    {
        this.http = HttpFactory.create(ctx);
    }

    /**
     * Sends a GET request. On success, callback will contain a list of items. Success/Failure conditions should be handled by the callback
     * @param url The URL from which to make the request
     * @param handler The response callback handler
     * @param clazz Base class for JSON conversions
     * @return
     */
    public <T> Cancellable getAll(String url, ResponseHandler<T[]> handler,  Class<T> clazz)
    {
        return this.http.get(url)
                        .handler(handler)
                        .headers(this.headers)
                        .send();
    }
    /**
     * Sends a GET request. On success, callback will contain a single JSON object. Success/Failure conditions should be handled by the callback
     * @param url The URL from which to make the request
     * @param handler The response callback handler
     * @param clazz Base class for JSON conversions
     * @return
     */
    public <T> Cancellable get(String url, ResponseHandler<T> handler,  Class<T> clazz)
    {
        return this.http.get(url)
                        .handler(handler)
                        .headers(this.headers)
                        .send();
    }

    /**
     * TODO
     * @param url The URL from which to make the request
     * @param handler The response callback handler
     * @param clazz Base class for JSON conversions
     * @return
     */
    public <T> Cancellable delete(String url, ResponseHandler<T> handler, Class<T> clazz)
    {
        return this.http.delete(url)
                        .headers(this.headers)
                        .handler(handler)
                        .send();
    }

    /**
     * Sends a POST request with the given data. Success/Failure conditions should be handled by the callback
     * @param t The data to be used in the POST message
     * @param url The URL from which to make the request
     * @param handler The response callback handler
     * @param clazz Base class for JSON conversions
     * @return
     */
    public <T> Cancellable post(T t, String url, ResponseHandler<T> handler, Class<T> clazz)
    {
        return this.http.post(url)
                        .data(t)
                        .headers(this.headers)
                        .handler(handler)
                        .send();
    }

    /**
     * Sends a POST request with no data. Success/Failure conditions should be handled by the callback
     * @param url The URL from which to make the request
     * @param handler The response callback handler
     * @param clazz Base class for JSON conversions
     * @return
     */
    public <T> Cancellable post(String url, ResponseHandler<T> handler, Class<T> clazz)
    {
        return this.http.post(url)
                        .headers(this.headers)
                        .handler(handler)
                        .send();
    }

    /**
     * Sends a PUT request with the given data. Success/Failure conditions should be handled by the callback
     * @param t The data to be used in the POST message
     * @param url The URL from which to make the request
     * @param handler The response callback handler
     * @param clazz Base class for JSON conversions
     * @return
     */
    public <T> Cancellable put(T t, String url, Class<T> clazz)
    {
        return this.http.put(url)
                        .data(t)
                        .headers(this.headers)
                        .send();
    }

    /**
     * Sets the headers to be used in all HTTP requests
     * @param headers The headers to set
     */
    public void setHeaders(Map<String, String> headers)
    {
        this.headers = headers;
    }

}
