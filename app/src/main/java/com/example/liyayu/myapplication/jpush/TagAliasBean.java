package com.example.liyayu.myapplication.jpush;

import java.util.Set;

/**
 * Created by liyayu on 2018/6/13.
 */

public class TagAliasBean{
    public int action;
    public Set<String> tags;
    public String alias;
    public boolean isAliasAction;

    @Override
    public String toString() {
        return "TagAliasBean{" +
                "action=" + action +
                ", tags=" + tags +
                ", alias='" + alias + '\'' +
                ", isAliasAction=" + isAliasAction +
                '}';
    }
}