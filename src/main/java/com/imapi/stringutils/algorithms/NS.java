package com.imapi.stringutils.algorithms;

import com.imapi.stringutils.Algorithm;
import com.imapi.stringutils.Function;
import com.imapi.stringutils.StringUtils;

public class NS implements Algorithm {

    @Override
    public void find(String source, String pattern, Function job, boolean onlyFirst) {
        int start = 0;
        int end = source.indexOf(pattern, start);
        int length = pattern.length();

        if (end == StringUtils.INDEX_NOT_FOUND) return;

        while (end != StringUtils.INDEX_NOT_FOUND) {
            job.apply(end);
            if (onlyFirst) return;

            start = end + length;
            end = source.indexOf(pattern, start);
        }
    }
}