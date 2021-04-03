package org.webweaving;

import java.lang.invoke.MutableCallSite;
import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Base45 {
    private static final String qrCharset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:";

    static public String encode(byte[] buffer) {
        StringBuilder out = new StringBuilder();

        if (buffer.length == 0)
            return "";

        BigInteger divident = new BigInteger(buffer);
        BigInteger bigQrCharsetLen = BigInteger.valueOf(45);

        while (divident.bitCount() != 0) {
            BigInteger[] r = divident.divideAndRemainder(bigQrCharsetLen);
            divident = r[0];
            int idx = r[1].intValue();
            out.append(qrCharset.charAt(idx));
        }
        // postfix (prefix) any leading zeros - as they are not in the bignum.
        for (int i = 0; i < buffer.length && buffer[i] == 0; i++)
            out.append(qrCharset.charAt(0));

        return out.reverse().toString();
    }

    static public byte[] decode(String input) throws Exception {
        BigInteger result = BigInteger.ZERO;

        if (input.length() == 0)
            return new byte[0];

        int leadingz = 0;
        boolean lead = true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int idx = qrCharset.indexOf(c);
            if (-1 == idx)
                throw new Exception("Illegal char in base45 string");

            if (idx == 0 && lead) leadingz++;
            else lead = false;

            BigInteger factor = BigInteger.valueOf(idx);
            BigInteger weight = BigInteger.valueOf(45);
            result = result.add(weight.pow(input.length() - i - 1).multiply(factor));
        }
        if (result.equals(BigInteger.ZERO))
            leadingz --;

        return addAll(new byte[leadingz], result.toByteArray());
    }

    public static byte[] addAll(final byte[] array1, byte[] array2) {
        byte[] joinedArray = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    ;

    /*
		factor := gobig.NewInt(int64(charsetIndex))

		weight := new(gobig.Int)
		weight.Exp(bigQrCharsetLen, gobig.NewInt(int64(inputLength-i-1)), nil)

		result = result.Add(result, new(gobig.Int).Mul(factor, weight))
 */
}