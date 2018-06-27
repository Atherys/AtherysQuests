package com.atherys.quests.script.js.lib.util;

@FunctionalInterface
public interface TriFunction<R, T1, T2, T3> {

    R apply(T1 arg1, T2 arg2, T3 arg3);

}
