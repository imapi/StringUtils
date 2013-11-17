package com.imapi.stringutils;

public interface Function<T> {
    void apply(int position);
    T result();
}
