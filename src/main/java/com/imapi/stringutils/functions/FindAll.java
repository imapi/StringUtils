package com.imapi.stringutils.functions;

import com.imapi.stringutils.Function;

import java.util.ArrayList;
import java.util.List;

public class FindAll implements Function<List<Integer>> {

    private List<Integer> positions = new ArrayList<>();

    @Override
    public void apply(int position) {
        positions.add(position);
    }

    @Override
    public List<Integer> result() {
        return positions;
    }
}
