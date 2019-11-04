package com.zhang.vertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class VertxTest
{
    public static void main(String[] args)
    {
        VertxOptions options = new VertxOptions();
        Vertx vertx = Vertx.vertx(options);
        WebClientOptions webClientOptions = new WebClientOptions();
        webClientOptions.setUserAgentEnabled(false);
        webClientOptions.setMaxPoolSize(10);
        webClientOptions.setConnectTimeout(10);
        webClientOptions.setTcpKeepAlive(true);
        WebClient webClient = WebClient.create(vertx, webClientOptions);
        HttpRequest<Buffer> bufferHttpRequest = webClient.get("http://www.baidu.com");
        CompletableFuture<AsyncResult<HttpResponse<Buffer>>> completableFuture = new CompletableFuture<AsyncResult<HttpResponse<Buffer>>>();
        bufferHttpRequest.send(completableFuture::complete);
        try
        {
            AsyncResult<HttpResponse<Buffer>> o = completableFuture.get();
            if(o.succeeded())
            {
                HttpResponse<Buffer> result = o.result();
                String str = result.bodyAsString();
                System.out.println(str);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }

    }

}
