package com.imapi.stringutils.functions;

import com.imapi.stringutils.Function;

public class Replace implements Function<String> {

    private final StringBuilder result;
    private final String content;
    private final String with;
    private final int length;
    private int lastPosition = 0;

    public Replace(String source, String what, String with) {
        this.with = with;
        this.length = what.length();
        this.content = source;
        int increase = with.length() - what.length();
        increase = increase < 0 ? 0 : increase;
        this.result = new StringBuilder(source.length() + increase * 64);
    }

    @Override
    public void apply(int position) {
        result.append(content.substring(lastPosition, position)).append(with);
        lastPosition = position + length;
    }

    @Override
    public String result() {
        result.append(content.substring(lastPosition));
        return result.toString();
    }
}
