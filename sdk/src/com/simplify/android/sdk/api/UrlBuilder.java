/*
 * Copyright (c) 2013, Asynchrony Solutions, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     * Neither the name of Asynchrony nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ASYNCHRONY SOLUTIONS, INC. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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

    public UrlBuilder addOptionalParam(String key, String value) {
        if (value != null && value.trim().length() > 0 && !"null".equalsIgnoreCase(value)) {
            params.add(new BasicNameValuePair(key, value));
        }
        return this;
    }
}
