package com.didihe1988.husky.http.param;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lml on 2014/9/27.
 */
public class FileParams extends Params{
    private Map<String,File> fileMap=new HashMap<String, File>();

    public Map<String, File> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, File> fileMap) {
        this.fileMap = fileMap;
    }

    public FileParams()
    {
        super();
    }

    public FileParams(Map<String,File> fileMap)
    {
        super();
        this.fileMap=fileMap;
    }

    public FileParams(Map<String,String> paramMap,Map<String,File> fileMap)
    {
        super(paramMap);
        this.fileMap=fileMap;
    }

    public void putFileEntry(String name,File file)
    {
        this.fileMap.put(name,file);
    }
}
