package com.didihe1988.husky.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lml on 2014/9/22.
 */
public class HttpUtils {
    public static String addPrefix(String url)
    {
        String pString="(http:\\/\\/$)(.*)";
        Pattern pattern= Pattern.compile(pString);
        Matcher matcher=pattern.matcher(url);
        if(!matcher.find())
        {
            url="http://"+url;
        }
        return url;
    }
}
