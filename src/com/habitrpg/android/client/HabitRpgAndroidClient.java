package com.habitrpg.android.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.habitrpg.client.AuthenticationInformations;
import com.habitrpg.client.resource.Server;
import com.habitrpg.client.resource.Task;
import com.habitrpg.client.resource.User;
import com.kodart.httpzoid.ResponseHandler;

/**
 * This is a convenience class for retrieving HabitRPG information in Android. This 
 * class uses an Httpzoid based REST client instead of Jersey + Jackson API. 
 * 
 * This is based on the habitrpg-java-client by jsmadja (https://github.com/jsmadja/habitrpg-java-client)
 * 
 * TODO Implement updating/deleting tasks
 * 
 * @author Paul T
 */
public class HabitRpgAndroidClient
{
    public static final String DEFAULT_BASE_URL = "https://www.habitrpg.com";
    private final URL serverUrl;
    private AndroidRestClient restClient;

    public HabitRpgAndroidClient(Context ctx, AuthenticationInformations authenticationInformations)
    {
        try
        {
            this.serverUrl = new URL(DEFAULT_BASE_URL);
            this.restClient = new AndroidRestClient(ctx);
            this.restClient.setHeaders(withHeaders(authenticationInformations));
        }
        catch(MalformedURLException e)
        {
            Log.wtf("Client", e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Retrieves the status of the server, passing the request result to the handler
     * @param handler The response handler.
     */
    public void getStatus(ResponseHandler<Server> handler)
    {
        this.restClient.get(api("/status"), handler, Server.class);
    }

    /**
     * Retrieves user information, passing the request result to the handler
     * @param handler The response handler.
     */
    public void getUser(ResponseHandler<User> handler)
    {
        this.restClient.get(api("/user"), handler, User.class);
    }

    /**
     * Retrieves a list of tasks, filtered by type. 
     * @param handler The response handler.
     * @param type The type of task to retrieve which can be [todo | habit | daily | reward] or null to retrieve all.
     */
    public void getTasks(ResponseHandler<Task[]> handler, Task.Type type)
    {
        String url = api("/user/tasks");
        if (type != null)
        {
            url += "?type=" + type.toString();
        }
        this.restClient.getAll(url, handler, Task.class);
    }

    /**
     * Gets a list of headers to be used with the HabitRPG API
     * @param authenticationInformations
     * @return
     */
    private Map<String, String> withHeaders(AuthenticationInformations authenticationInformations)
    {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-api-user", authenticationInformations.getApiUser());
        headers.put("x-api-key", authenticationInformations.getApiKey());

        // TODO This causes stuff to get garbled
//        headers.put("Accept-Encoding", "deflate, gzip");
        headers.put("Content-Type", "application/json");
        return headers;
    }

    /**
     * Gets the API string. Currently API V1 of HabitRPG
     * @param resource The path which will be appended to the API string
     * @return
     */
    private String api(String resource)
    {
        return serverUrl.toString() + "/api/v1" + resource;
    }
}
