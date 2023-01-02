package util.checked;

@SuppressWarnings("all")
public class Commutable {
    private Commutable(){}
    interface _2<T1, T2>{
        default <R extends _2<T2, T1>> R _12(){return (R) this;}
    }
    interface _3<T1, T2, T3> extends _2<T1, T2>{
        default <R extends _3<T3, T2, T1>> R _13(){return (R) this;}
        default <R extends _3<T1, T3, T2>> R _23(){return (R) this;}
    }
    interface _4<T1, T2, T3, T4> extends  _3<T1, T2, T3>{
        default <R extends _4<T4, T2, T3, T1>> R _14(){return (R) this;}
        default <R extends _4<T1, T4, T3, T2>> R _24(){return (R) this;}
        default <R extends _4<T1, T2, T4, T3>> R _34(){return (R) this;}
    }
    interface _5<T1, T2, T3, T4, T5> extends  _4<T1, T2, T3, T4>{
        default <R extends _5<T5, T2, T3, T4, T1>> R _15(){return (R) this;}
        default <R extends _5<T1, T5, T3, T4, T2>> R _25(){return (R) this;}
        default <R extends _5<T1, T2, T5, T4, T3>> R _35(){return (R) this;}
        default <R extends _5<T1, T2, T3, T5, T4>> R _45(){return (R) this;}
    }
    interface _6<T1, T2, T3, T4, T5, T6> extends _5<T1, T2, T3, T4, T5>{
        default <R extends _6<T6, T2, T3, T4, T5, T1>> R _16(){return (R) this;}
        default <R extends _6<T1, T6, T3, T4, T5, T2>> R _26(){return (R) this;}
        default <R extends _6<T1, T2, T6, T4, T5, T3>> R _36(){return (R) this;}
        default <R extends _6<T1, T2, T3, T6, T5, T4>> R _46(){return (R) this;}
        default <R extends _6<T1, T2, T3, T4, T6, T5>> R _56(){return (R) this;}
    }
    interface _7<T1, T2, T3, T4, T5, T6, T7> extends _6<T1, T2, T3, T4, T5, T6>{
        default <R extends _7<T7, T2, T3, T4, T5, T6, T1>> R _17(){return (R) this;}
        default <R extends _7<T1, T7, T3, T4, T5, T6, T2>> R _27(){return (R) this;}
        default <R extends _7<T1, T2, T7, T4, T5, T6, T3>> R _37(){return (R) this;}
        default <R extends _7<T1, T2, T3, T7, T5, T6, T4>> R _47(){return (R) this;}
        default <R extends _7<T1, T2, T3, T4, T7, T6, T5>> R _57(){return (R) this;}
        default <R extends _7<T1, T2, T3, T4, T5, T7, T6>> R _67(){return (R) this;}
    }
    interface _8<T1, T2, T3, T4, T5, T6, T7, T8> extends _7<T1, T2, T3, T4, T5, T6, T7>{
        default <R extends _8<T8, T2, T3, T4, T5, T6, T7, T1>> R _18(){return (R) this;}
        default <R extends _8<T1, T8, T3, T4, T5, T6, T7, T2>> R _28(){return (R) this;}
        default <R extends _8<T1, T2, T8, T4, T5, T6, T7, T3>> R _38(){return (R) this;}
        default <R extends _8<T1, T2, T3, T8, T5, T6, T7, T4>> R _48(){return (R) this;}
        default <R extends _8<T1, T2, T3, T4, T8, T6, T7, T5>> R _58(){return (R) this;}
        default <R extends _8<T1, T2, T3, T4, T5, T8, T7, T6>> R _68(){return (R) this;}
        default <R extends _8<T1, T2, T3, T4, T5, T6, T8, T7>> R _78(){return (R) this;}
    }
}
