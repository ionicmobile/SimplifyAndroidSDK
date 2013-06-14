package com.simplify.android.sdk.api;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * From a base URL, add path segments and parameters to build up a full URL.
 */
public class UrlBuilder {

    private String urlBase;
    private List<NameValuePair> params;

    public UrlBuilder(String urlBase) {
        this.urlBase = urlBase;
        this.params = new LinkedList<NameValuePair>();
    }

    public String build() {
        return urlBase + (params.size() > 0 ? ("?" + URLEncodedUtils.format(params, "utf-8")) : "");
    }

    public UrlBuilder addPath(String path) {
        if (!urlBase.endsWith("/")) {
            urlBase += "/";
        }

        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        urlBase += path;
        return this;
    }

    public UrlBuilder addParam(String key, String value) {
        params.add(new BasicNameValuePair(key, value));
        return this;
    }
}
