import com.imapi.stringutils.StringUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * String utilities methods
 *
 * @author <a href="mailto:ext-i.bondarenko@netbiscuits.com">Ivan Bondarenko</a>
 */
public class StringUtilsTest {

    private static final String source = "Welcome to RegExr 0.3b, an intuitive tool for learning, writing, and testing Regular Expressions. Key features include: \n" +
            "\n" +
            "* real time results: shows results as you type \n" +
            "* code hinting: roll over your expression to see info on specific elements \n" +
            "* detailed results: roll over a match to see details & view group info below \n" +
            "* built in regex guide: double click entries to insert them into your expression \n" +
            "* online & desktop: regexr.com or download the desktop version for Mac, Windows, or Linux \n" +
            "* save your expressions: My Saved expressions are saved locally \n" +
            "* search Community expressions and add your own \n" +
            "* create Share Links to send your expressions to co-workers or link to them on Twitter or your blog [ex. http://RegExr.com?2rjl6] \n" +
            "\n" +
            "Built by gskinner.com with Flex 3 [adobe.com/go/flex] and Spelling Plus Library for text highlighting [gskinner.com/products/spl].";

    @Test
    public void testReplace() throws Exception {
        assertEquals(source.replace("a", "b"), StringUtils.replace(source, "a", "b"));
        assertEquals(source.replace("a", ""), StringUtils.replace(source, "a", ""));
        assertEquals(source.replace("ea", ""), StringUtils.replace(source, "ea", ""));
        assertEquals(source.replace("easfdadsa", ""), StringUtils.replace(source, "easfdadsa", ""));
        assertEquals(source.replace("\\be(\\w*)s\\b", ""), StringUtils.replace(source, "\\be(\\w*)s\\b", ""));
        assertEquals("".replace("a", "b"), StringUtils.replace("", "a", "b"));
        assertEquals("".replace("", ""), StringUtils.replace("", "", ""));
        assertEquals(source.replace("", ""), StringUtils.replace(source, "", ""));
    }

    @Test
    public void testReplaceOnce() throws Exception {
        assertEquals(source.replaceFirst("a", "b"), StringUtils.replaceOnce(source, "a", "b"));
        assertEquals(source.replaceFirst("a", ""), StringUtils.replaceOnce(source, "a", ""));
        assertEquals(source.replaceFirst("ea", ""), StringUtils.replaceOnce(source, "ea", ""));
        assertEquals(source.replaceFirst("easfdadsa", ""), StringUtils.replaceOnce(source, "easfdadsa", ""));
        assertEquals(source.replaceFirst("x", ""), StringUtils.replaceOnce(source, "x", ""));
        assertEquals("".replaceFirst("a", "b"), StringUtils.replaceOnce("", "a", "b"));
        assertEquals("".replaceFirst("", ""), StringUtils.replaceOnce("", "", ""));
        assertEquals(source.replaceFirst("", ""), StringUtils.replaceOnce(source, "", ""));
    }

    @Test
    public void testReplaceAll() throws Exception {
        assertEquals(source.replaceAll("\\be(\\w*)s\\b", "b"), StringUtils.replaceAll(source, "\\be(\\w*)s\\b", "b"));
        assertEquals(source.replaceAll("[a-zA-Z]", ""), StringUtils.replaceAll(source, "[a-zA-Z]", ""));
        assertEquals(source.replaceAll("\\b0x(?:[0-9A-Fa-f]{6}|0-9A-Fa-f]{8})\\b", ""), StringUtils.replaceAll(source, "\\b0x(?:[0-9A-Fa-f]{6}|0-9A-Fa-f]{8})\\b", ""));
    }

    @Test
    public void testReplaceFirst() throws Exception {
        assertEquals(source.replaceFirst("\\be(\\w*)s\\b", "b"), StringUtils.replaceFirst(source, "\\be(\\w*)s\\b", "b"));
        assertEquals(source.replaceFirst("[a-zA-Z]", ""), StringUtils.replaceFirst(source, "[a-zA-Z]", ""));
        assertEquals(source.replaceFirst("\\b0x(?:[0-9A-Fa-f]{6}|0-9A-Fa-f]{8})\\b", ""), StringUtils.replaceFirst(source, "\\b0x(?:[0-9A-Fa-f]{6}|0-9A-Fa-f]{8})\\b", ""));
    }

    @Test
    public void testIndexOf() throws Exception {
        assertEquals(source.indexOf("a"), StringUtils.indexOf(source, "a"));
        assertEquals(source.indexOf("a"), StringUtils.indexOf(source, "a"));
        assertEquals(source.indexOf("ea"), StringUtils.indexOf(source, "ea"));
        assertEquals(source.indexOf("easfdadsa"), StringUtils.indexOf(source, "easfdadsa"));
        assertEquals(source.indexOf(""), StringUtils.indexOf(source, ""));
        assertEquals("".indexOf(""), StringUtils.indexOf("", ""));
        assertEquals("".indexOf(" "), StringUtils.indexOf("", " "));
        assertEquals("  ".indexOf(""), StringUtils.indexOf("  ", ""));
        assertEquals(source.indexOf(source), StringUtils.indexOf(source, source));
        assertEquals(source.indexOf(""), StringUtils.indexOf(source, ""));
        assertEquals("".indexOf(source), StringUtils.indexOf("", source));
    }

    @Test
    public void testFind() throws Exception {
        assertEquals(source.indexOf("a"), StringUtils.find(source, "a"));
        assertEquals(source.indexOf("a"), StringUtils.find(source, "a"));
        assertEquals(source.indexOf("ea"), StringUtils.find(source, "ea"));
        assertEquals(source.indexOf("easfdadsa"), StringUtils.find(source, "easfdadsa"));
        assertEquals(source.indexOf(""), StringUtils.find(source, ""));
        assertEquals("".indexOf(""), StringUtils.find("", ""));
        assertEquals("".indexOf(" "), StringUtils.find("", " "));
        assertEquals("  ".indexOf(""), StringUtils.find("  ", ""));
        assertEquals(source.indexOf(""), StringUtils.find(source, ""));
        assertEquals("".indexOf(source), StringUtils.find("", source));
    }
}
