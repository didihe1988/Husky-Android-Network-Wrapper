package com.didihe1988.husky.http.executor.head;

/**
 * Created by lml on 2014/11/4.
 */
public class Content {
    private int contentLength;

    private String contentEncoding;

    private String contentType;

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Content(int contentLength, String contentEncoding, String contentType) {
        this.contentLength = contentLength;
        this.contentEncoding = contentEncoding;
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "Content{" +
                "contentLength=" + contentLength +
                ", contentEncoding='" + contentEncoding + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
