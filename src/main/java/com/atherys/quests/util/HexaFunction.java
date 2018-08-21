package com.atherys.quests.util;

@FunctionalInterface
public interface HexaFunction<T1, T2, T3, T4, T5, T6, R> {

    R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6);

}
