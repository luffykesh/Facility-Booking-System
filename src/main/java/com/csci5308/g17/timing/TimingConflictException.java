package com.csci5308.g17.timing;

public class TimingConflictException extends Exception {

    final static long serialVersionUID=123L;

    private Timing t1;
    private Timing t2;

    public TimingConflictException(Timing t1, Timing t2) {
        super("Timing Conflict");
        this.t1 = t1;
        this.t2 = t2;
    }

    public Timing getT1() {
        return t1;
    }

    public Timing getT2() {
        return t2;
    }
}
