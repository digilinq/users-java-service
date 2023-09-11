package org.digilinq.platform.users.util;

import java.util.function.Function;

public class With<T> {
    private final T value;

    private With(T value) {
        this.value = value;
    }

    public static <T> With<T> value(T value) {
        return new With<>(value);
    }

    public <R> With<R> perform(Function<T, R> f) {
        return With.value(f.apply(value));
    }

    public <R> With<R> map(Function<T, R> mapper) {
        return With.value(mapper.apply(value));
    }

    public T orElse(T other) {
        if (value == null)
            return other;
        return value;
    }

    public T get() {
        return value;
    }
}