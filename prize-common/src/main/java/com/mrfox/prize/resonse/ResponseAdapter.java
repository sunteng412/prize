package com.mrfox.prize.resonse;

/**
 * @author z0228
 */
public interface ResponseAdapter<I> {
    /**
     * Adapter to build a proper {@link Response} instance.
     *
     * @param object data to be returned.
     * @param <T>    generic type of {@code object}.
     * @return a proper {@link Response} instance with {@code object} as its data.
     */
    <T> Response<T> apply(final I object);
}
