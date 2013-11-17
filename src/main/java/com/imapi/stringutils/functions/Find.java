package com.imapi.stringutils.functions;

import com.imapi.stringutils.Function;

public class Find implements Function<Integer> {

    private Integer result = -1;

    @Override
    public void apply(int position) {
        result = position;
    }

    @Override
    public Integer result() {
        return result;
    }
}
