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

import junit.framework.TestCase;

public class UrlBuilderTest extends TestCase {
    public void testBaseUrl() {
        UrlBuilder bob = new UrlBuilder("http://foo.bar.com");
        assertEquals("http://foo.bar.com", bob.build());
    }

    public void testAddingPath() {
        UrlBuilder bob = new UrlBuilder("http://foo.bar.com");
        bob.addPath("/baz");
        assertEquals("http://foo.bar.com/baz", bob.build());
    }

    public void testAddingPathWithExistingSlashOnBase() {
        UrlBuilder bob = new UrlBuilder("http://foo.bar.com/");
        bob.addPath("baz");
        assertEquals("http://foo.bar.com/baz", bob.build());
    }

    public void testAddingPathWithExistingSlashOnBaseAndTrailingSlash() {
        UrlBuilder bob = new UrlBuilder("http://foo.bar.com/");
        bob.addPath("baz/");
        assertEquals("http://foo.bar.com/baz/", bob.build());
    }

    public void testAddingPathWithExistingSlashOnBaseAndPath() {
        UrlBuilder bob = new UrlBuilder("http://foo.bar.com/");
        bob.addPath("/baz");
        assertEquals("http://foo.bar.com/baz", bob.build());
    }

    public void testAddingTwoPathSegments() {
        UrlBuilder bob = new UrlBuilder("http://foo.bar.com/");
        bob.addPath("/baz").addPath("/qux");
        assertEquals("http://foo.bar.com/baz/qux", bob.build());
    }

    public void testAddingParameter() {
        UrlBuilder bob = new UrlBuilder("http://foo.bar.com/");
        bob.addParam("key", "value");
        assertEquals("http://foo.bar.com/?key=value", bob.build());
    }

    public void testAddingSeveralParameters() {
        UrlBuilder bob = new UrlBuilder("http://foo.bar.com/");
        bob.addParam("k1", "v1").addParam("k2", "v2").addParam("k3", "v3");
        assertEquals("http://foo.bar.com/?k1=v1&k2=v2&k3=v3", bob.build());
    }

    public void testPathSegmentsAndParameters() {
        String urlString = new UrlBuilder("http://foo.bar.com/").addPath("/baz").
                addPath("/qux").addParam("k1", "v1").addParam("k2", "v2").
                addParam("k3", "v3").build();
        assertEquals("http://foo.bar.com/baz/qux?k1=v1&k2=v2&k3=v3", urlString);
    }

    public void testParameterValuesAreUrlEncoded() {
        String urlString = new UrlBuilder("http://foo.bar.com/").addPath("/baz").
                addPath("/qux").addParam("k1", "100%").addParam("k2", "&me too").
                addParam("k3", "+that guy").build();
        assertEquals("http://foo.bar.com/baz/qux?k1=100%25&k2=%26me+too&k3=%2Bthat+guy", urlString);
    }

    public void testAddingOptionalParameter() {
        String urlString = new UrlBuilder("http://foo.bar.com/").addPath("/baz").
                addPath("/qux").addParam("k1", "100%").addOptionalParam("k2", null).
                addOptionalParam("k3", "+that guy").build();
        assertEquals("http://foo.bar.com/baz/qux?k1=100%25&k3=%2Bthat+guy", urlString);
    }
}
