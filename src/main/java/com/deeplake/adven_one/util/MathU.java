package com.deeplake.adven_one.util;

//MathUtil
public class MathU {
    public static int mod(int divisor, int divider)
    {
        return divisor >= 0 ? (divisor%divider) : (divisor%divider+divider);
    }
}
