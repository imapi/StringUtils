package com.imapi.stringutils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.imapi.stringutils.algorithms.BNDM;
import com.imapi.stringutils.algorithms.NS;
import com.imapi.stringutils.functions.Find;
import com.imapi.stringutils.functions.FindAll;
import com.imapi.stringutils.functions.Replace;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String search and replace utilities.
 * StringSearch lib is used for searching, which could be 10-50 faster depending on the source and pattern to search.
 *
 * @author <a href="mailto:bondarenko.ivan.v@gmail.com">Ivan Bondarenko</a>
 */
public class StringUtils {

    public static final int INDEX_NOT_FOUND = -1;
    private static final LoadingCache<String, Pattern> PATTERNS = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .initialCapacity(1000)
            .build(new CacheLoader<String, Pattern>() {
                @Override
                public Pattern load(String key) throws Exception {
                    return Pattern.compile(key);
                }
            });
    private static final Algorithm NS = new NS();
    private static final Algorithm BNDM = new BNDM();

    /**
     * Static helper class
     */
    private StringUtils() {
    }

    /**
     * <p>Replaces a String with another String inside a larger String, once.</p>
     * <p/>
     * <p>A {@code null} reference passed to this method is a no-op.</p>
     * <p/>
     * <pre>
     * StringSR.replaceOnce(null, *, *)        = null
     * StringSR.replaceOnce("", *, *)          = ""
     * StringSR.replaceOnce("any", null, *)    = "any"
     * StringSR.replaceOnce("any", *, null)    = "any"
     * StringSR.replaceOnce("any", "", *)      = "any"
     * StringSR.replaceOnce("aba", "a", null)  = "aba"
     * StringSR.replaceOnce("aba", "a", "")    = "ba"
     * StringSR.replaceOnce("aba", "a", "z")   = "zba"
     * </pre>
     *
     * @param source text to search and replace in, may be null
     * @param what   the String to search for, may be null
     * @param with   the String to replace with, may be null
     * @return the text with any replacements processed,
     *         {@code null} if null String input
     */
    public static String replaceOnce(String source, String what, String with) {
        return replace(source, what, with, true);
    }

    private static String replace(String source, String what, String with, boolean onlyFirst) {
        if (notValid(source, what, with)) return source;
        Function<String> replace = new Replace(source, what, with);
        algorithm(what).find(source, what, replace, onlyFirst);
        return replace.result();
    }

    private static Algorithm algorithm(String pattern) {
        if (pattern.length() > 1) return BNDM;
        return NS;
    }

    private static boolean notValid(String source, String what, String with) {
        return notValid(source, what) < 1 || with == null;
    }

    private static int notValid(String source, String pattern) {
        if (isEmpty(pattern)) {
            return 0;
        } else if (isEmpty(source)) {
            return -1;
        }
        return 1;
    }

    private static boolean isEmpty(String cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>Replaces all occurrences of a String within another String.</p>
     * <p/>
     * <p>A {@code null} reference passed to this method is a no-op.</p>
     * <p/>
     * <pre>
     * StringSR.replace(null, *, *)        = null
     * StringSR.replace("", *, *)          = ""
     * StringSR.replace("any", null, *)    = "any"
     * StringSR.replace("any", *, null)    = "any"
     * StringSR.replace("any", "", *)      = "any"
     * StringSR.replace("aba", "a", null)  = "aba"
     * StringSR.replace("aba", "a", "")    = "b"
     * StringSR.replace("aba", "a", "z")   = "zbz"
     * </pre>
     *
     * @param source text to search and replace in, may be null
     * @param what   the String to search for, may be null
     * @param with   the String to replace it with, may be null
     * @return the text with any replacements processed,
     *         {@code null} if null String input
     */
    public static String replace(String source, String what, String with) {
        return replace(source, what, with, false);
    }

    /**
     * <p>Replaces first occurrence of a pattern within another String.</p>
     *
     * @param source  text to search and replace in, may be null
     * @param pattern the String to search for, may be null
     * @param with    the String pattern to replace it with, may be null
     * @return the text with any replacements processed,
     *         {@code null} if null String input
     */
    public static String replaceFirst(String source, String pattern, String with) {
        if (notValid(source, pattern, with)) return source;
        return pattern(pattern).matcher(source).replaceFirst(with);
    }

    private static Pattern pattern(String regexp) {
        try {
            return PATTERNS.get(regexp);
        } catch (ExecutionException e) {
            return Pattern.compile(regexp);
        }
    }

    /**
     * <p>Replaces all occurrences of a pattern within another String.</p>
     *
     * @param source  text to search and replace in, may be null
     * @param pattern the String to search for, may be null
     * @param with    the String pattern to replace it with, may be null
     * @return the text with any replacements processed,
     *         {@code null} if null String input
     */
    public static String replaceAll(String source, String pattern, String with) {
        if (notValid(source, pattern, with)) return source;
        return pattern(pattern).matcher(source).replaceAll(with);
    }

    /**
     * <p>Finds the first index in the {@code String} that matches the
     * specified {@code String}.</p>
     *
     * @param source the {@code String} to be processed, not null
     * @param what   the {@code String} to be searched for
     * @return the index where the search {@code String} was found, -1 if not found
     */
    public static int indexOf(String source, String what) {
        return indexOf(source, what, true);
    }

    private static int indexOf(String source, String what, boolean onlyFirst) {
        int index = notValid(source, what);
        if (index < 1) return index;
        return indexOf(source, what, new Find(), onlyFirst);
    }

    private static <T> T indexOf(String source, String what, Function<T> job, boolean onlyFirst) {
        algorithm(what).find(source, what, job, onlyFirst);
        return job.result();
    }

    /**
     * <p>Finds the last index in the {@code String} that matches the
     * specified {@code String}.</p>
     *
     * @param source the {@code String} to be processed, not null
     * @param what   the {@code String} to be searched for
     * @return the index where the search {@code String} was found, -1 if not found
     */
    public static int lastIndexOf(String source, String what) {
        return indexOf(source, what, false);
    }

    /**
     * <p>Finds all indexes in the {@code String} that matches the
     * specified {@code String}.</p>
     *
     * @param source the {@code String} to be processed, not null
     * @param what   the {@code String} to be searched for
     * @return the {@code List} where the search {@code String} was found, empty {@code List} if was not found
     */
    public static List<Integer> indexesOf(String source, String what) {
        return indexOf(source, what, new FindAll(), false);
    }

    /**
     * <p>Finds the first index in the {@code String} that matches the
     * specified character.</p>
     *
     * @param source  the {@code String} to be processed, not null
     * @param pattern the pattern to be searched for
     * @return the index where the search char was found, -1 if not found
     */
    public static int find(String source, String pattern) {
        int index = notValid(source, pattern);
        if (index < 1) return index;
        Matcher m = pattern(pattern).matcher(source);
        return m.find() ? m.start() : INDEX_NOT_FOUND;
    }
}
