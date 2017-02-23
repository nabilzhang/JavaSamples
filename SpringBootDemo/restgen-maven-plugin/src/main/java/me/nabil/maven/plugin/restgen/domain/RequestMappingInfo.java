/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */

package me.nabil.maven.plugin.restgen.domain;

/**
 * 解析出的controller信息
 *
 * @author zhangbi
 */
public class RequestMappingInfo implements Comparable {

    /**
     * 方法和路径之间的分隔符
     */
    public static final String METHOD_PATH_SPLITOR = ":";

    /**
     * 空方法
     */
    public static final String EMPTY_METHOD = "";

    /**
     * 无参构造方法
     */
    public RequestMappingInfo() {

    }

    /**
     * 构造方法
     *
     * @param method     method名
     * @param urlPattern pattern名称
     */
    public RequestMappingInfo(String method, String urlPattern) {
        this.method = method;
        this.urlPattern = urlPattern;
    }

    /**
     * http method,大写
     */
    private String method;

    /**
     * URL pattern
     */
    private String urlPattern;

    /**
     * 所在class的class名称,暂未使用
     */
    private String className = "";

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String toPatternString() {
        return toPatternString("");
    }

    public String toPatternString(String prefix) {
        return (this.method + METHOD_PATH_SPLITOR + prefix + urlPattern).replace("//", "/");
    }

    public String toOutputString() {
        return this.toPatternString("");
    }

    public String toOutputString(String prefix) {
        return this.toPatternString(prefix);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequestMappingInfo that = (RequestMappingInfo) o;

        if (!method.equals(that.method)) {
            return false;
        }
        if (!urlPattern.equals(that.urlPattern)) {
            return false;
        }
        return className.equals(that.className);

    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + urlPattern.hashCode();
        result = 31 * result + className.hashCode();
        return result;
    }

    @Override
    public int compareTo(Object o) {
        RequestMappingInfo other = (RequestMappingInfo) o;

        int pathCompare = this.urlPattern.compareTo(other.getUrlPattern());
        if (pathCompare != 0) {
            return pathCompare;
        }

        int methodCompare = this.method.compareTo(other.getMethod());
        if (methodCompare != 0) {
            return methodCompare;
        }
        return this.getClassName().compareTo(other.getClassName());
    }
}