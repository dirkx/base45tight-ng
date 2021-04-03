package org.webweaving;

import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        try {
        byte[] in = "hello world".getBytes(StandardCharsets.UTF_8);
        String encoded = Base45.encode(in);
        byte[] decoded = new byte[0];
            decoded = Base45.decode(encoded);
            System.out.println(new String(in, StandardCharsets.UTF_8));
            System.out.println(encoded);
            System.out.println(new String(decoded,StandardCharsets.UTF_8));
            assert (in == decoded);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert(false);
    }
}
