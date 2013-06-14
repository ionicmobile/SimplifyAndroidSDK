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
