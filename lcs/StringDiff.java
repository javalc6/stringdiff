/** This class highlights text differences between two plain strings by generating html fragment to show changes

The buildLcsList() method finds longest substrings common to both text1 and text2 recursively producing a list of longest common substrings used in markTextDiff() to markup changes between text1 and text2.

The longest common substrings are found using the LongestCommonSubstring (LCS) algorithm credited to https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/LongestCommonSubstring.java.html
Credits go to Robert Sedgewick and Kevin Wayne that provide the LCS algorithm with GNU General Public License

Dependencies: SuffixArray.java 

version 1.0, 10-11-2022, first release

*/
package lcs;

import java.util.ArrayList;

public class StringDiff {

    private static final String INSERT_COLOR = "#00ff66";
    private static final String DELETE_COLOR = "#ff9933";
    private static final int lcs_threshold = 1;//minimum threshold for longest common subsequence

    public static void main(String[] args) {

        String text1 = "Do not change this section. Please check any misqelling! Note that this section is obsolete.";
        String text2 = "New section added. Do not change this section. Please check any mispelling!";

        ArrayList<String> lcsList = new ArrayList<>();
        buildLcsList(lcsList, text1, text2);

        String result = markTextDiff(text1, text2, lcsList, INSERT_COLOR, DELETE_COLOR);
        System.out.println(result);
    }

    //build list lcsl of valid longest common subsequences between text1 and text2
    public static void buildLcsList(ArrayList<String> lcsl, String text1, String text2) {
        String mLcs = lcs(text1, text2);
        if (mLcs.length() >= lcs_threshold) {
            int idx1 = text1.indexOf(mLcs);
            int idx2 = text2.indexOf(mLcs);
            buildLcsList(lcsl, text1.substring(0, idx1), text2.substring(0, idx2));
            lcsl.add(mLcs);
            buildLcsList(lcsl, text1.substring(idx1 + mLcs.length()), text2.substring(idx2 + mLcs.length()));
        }
    }

	//highlights with htlm tags the changes from text1 to text2 using lcsList
    public static String markTextDiff(String text1, String text2,
                                       ArrayList<String> lcsList, String insertColor, String deleteColor) {
        StringBuilder stringBuilder = new StringBuilder();

        int cur1 = 0, cur2 = 0;//cursors
        for (int k = 0; k < lcsList.size(); k++) {
            String mLcs = lcsList.get(k);
            int idx1 = text1.indexOf(mLcs, cur1);
            int idx2 = text2.indexOf(mLcs, cur2);
            if (idx1 > cur1) {
                stringBuilder.append("<del style='background-color:").append(deleteColor).append("'>").append(text1.substring(cur1, idx1)).append("</del>");
            }
            if (idx2 > cur2) {
                stringBuilder.append("<ins style='background-color:").append(insertColor).append("'>").append(text2.substring(cur2, idx2)).append("</ins>");
            }
            stringBuilder.append(lcsList.get(k));
            cur1 = idx1 + mLcs.length();
            cur2 = idx2 + mLcs.length();
        }
        if (cur1 < text1.length()) {
            stringBuilder.append("<del style='background-color:").append(deleteColor).append("'>").append(text1.substring(cur1)).append("</del>");
        }
        if (cur2 < text2.length()) {
            stringBuilder.append("<ins style='background-color:").append(insertColor).append("'>").append(text2.substring(cur2)).append("</ins>");
        }
        return stringBuilder.toString();
    }


    // return the longest common prefix of suffix s[p..] and suffix t[q..]
    private static String lcp(String s, int p, String t, int q) {
        int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i))
                return s.substring(p, p + i);
        }
        return s.substring(p, p + n);
    }

    // compare suffix s[p..] and suffix t[q..]
    private static int compare(String s, int p, String t, int q) {
        int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i))
                return s.charAt(p + i) - t.charAt(q + i);
        }
        return Integer.compare(s.length() - p, t.length() - q);
    }

    /**
     * Returns the longest common string of the two specified strings.
     *
     * @param  s one string
     * @param  t the other string
     * @return the longest common string that appears as a substring
     *         in both <tt>s</tt> and <tt>t</tt>; the empty string
     *         if no such string
     */
    public static String lcs(String s, String t) {
        SuffixArray suffix1 = new SuffixArray(s);
        SuffixArray suffix2 = new SuffixArray(t);

        // find longest common substring by "merging" sorted suffixes
        String lcs = "";
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            int p = suffix1.index(i);
            int q = suffix2.index(j);
            String x = lcp(s, p, t, q);
            if (x.length() > lcs.length()) lcs = x;
            if (compare(s, p, t, q) < 0)
                i++;
            else j++;
        }
        return lcs;
    }

}
