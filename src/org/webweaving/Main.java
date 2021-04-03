package org.webweaving;

import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        boolean decode = false;
        if (args.length > 0) {
            if ((args.length > 1 || args[1].startsWith("-h")) && !args[1].startsWith("-d")) {
                System.err.println("Syntax: base45 [-d]");
                System.exit(1);
            }
            decode = true;
        }
        byte[] in = new byte[0];
        try {
            in = System.in.readAllBytes();
        } catch (Exception e) {
            System.err.printf("Read failed: "+ e.getLocalizedMessage());
            System.exit(2);

        }
        try {
        if (decode) {
            System.out.write(Base45.decode(new String(in, StandardCharsets.UTF_8)));
        } else {
            System.out.print(Base45.encode(in));
        }
        } catch (Exception e) {
            System.err.printf((decode ? "De" : "En") + "coding failed: "+ e.getLocalizedMessage());
            System.exit(3);
        }
        System.exit(0);
    }
}
