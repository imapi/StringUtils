package com.imapi.stringutils.algorithms;

import com.imapi.stringutils.Algorithm;
import com.imapi.stringutils.Function;

public class BNDM implements Algorithm {

    @Override
    public void find(String source, String pattern, Function job, boolean onlyFirst) {

        if (pattern.length() == source.length() && pattern.equals(source)) {
            job.apply(0);
            return;
        }

        char[] x = pattern.toCharArray(), y = source.toCharArray();
        int i, j, s, d, last, m = x.length, n = y.length;
        int[] b = new int[65536];

		/* Pre processing */
        for (i = 0; i < b.length; i++) {
            b[i] = 0;
        }
        s = 1;
        for (i = m - 1; i >= 0; i--) {
            b[x[i]] |= s;
            s <<= 1;
        }

		/* Searching phase */
        j = 0;
        while (j <= n - m) {
            i = m - 1;
            last = m;
            d = ~0;
            while (i >= 0 && d != 0) {
                d &= b[y[j + i]];
                i--;
                if (d != 0) {
                    if (i >= 0) {
                        last = i + 1;
                    } else {
                        job.apply(j);
                        if (onlyFirst) return;
                    }
                }
                d <<= 1;
            }
            j += last;
        }
    }
}
