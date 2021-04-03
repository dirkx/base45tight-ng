package org.webweaving;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;

class Base45Test {
    void check_raw(byte [] in, String out) throws Exception {

        String encoded = Base45.encode(in);
        byte[] decoded = Base45.decode(encoded);

        System.out.println(">" + encoded + "<");
        System.out.println(" >" + byteArrayToHex(in)+"<" + in.length);
        System.out.println(" >"+ byteArrayToHex(decoded)+"<" + in.length +"\n\t");

        assert (encoded.equals(out));
        assert(in.length == decoded.length);
        assert(java.util.Arrays.equals(in,decoded));
    }
    void check(String inAsStr, String out) throws Exception {

        byte [] in = inAsStr.getBytes(StandardCharsets.UTF_8);
        check_raw(in,out);
    }

    @Test
    void encode() {
        try {
        check("hello world","K3*J+EGLBVAYYB36");
        check("foo bar","%4VVO:F$X5");
            check("ietf!","19N6HOO$");
        check("Hello!!","Q-*2/.MZ B");
        check("COVID-19","37J$4 QAWL0E");
        check("2021 Digital Green Certificates for travel",
                "HREOZC%EXPZQX3G6VGTB%7+Q2BISAC87Y5A QC8774F7A2NY6G+MIJ$YUSQ3P");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x ", b));
        return sb.toString();
    }

    @Test
    void decode() {
        try {
            check_raw(new byte[]{1, 2, 3} , "WR ");
            check_raw(new byte[]{} , "");
            check_raw(new byte[]{0} , "0");
            check_raw(new byte[]{0,0} , "00");
            check_raw(new byte[]{0,0,0} , "000");
            check_raw(new byte[]{0, 1, 2, 3}, "0WR ");
            check_raw(new byte[]{0, 0, 1, 2, 3} , "00WR ");
            check_raw(new byte[]{0, 0, 0, 1, 2, 3} , "000WR ");
            check_raw(new byte[]{0, 0, 0, 0, 1, 2, 3} , "0000WR ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}