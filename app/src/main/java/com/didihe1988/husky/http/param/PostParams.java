package com.didihe1988.husky.http.param;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lml on 2014/9/27.
 */
public class PostParams extends Params{
    protected Map<String,String> paramMap=new HashMap<String, String>();

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public PostParams()
    {

    }

    public PostParams(Map<String, String> paramMap)
    {
        this.paramMap=paramMap;
    }

    public void putParamEntry(String name,String value)
    {
        paramMap.put(name,value);
    }





}
