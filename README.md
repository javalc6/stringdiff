# StringDiff
This java application highlights text differences between two plain strings by generating html fragment to show changes
The buildLcsList() method finds longest substrings common to both text1 and text2 recursively producing a list of longest common substrings used in markTextDiff() to markup changes between text1 and text2.
Java 8 or later is required to run the application.

# Compile
Run following command to build the application:
```
javac lcs\StringDiff.java
```

# Run
Just use the following command to run the application:
```
java lcs.StringDiff
```

# Example

```
text1 = "Do not change this section. Please check any misqelling! Note that this section is obsolete.";
text2 = "New section added. Do not change this section. Please check any mispelling!";

Running the application with command "java lcs.StringDiff":

<ins style='background-color:#00ff66'>New section added. </ins>Do not change this section. Please check any mis<del style='background-color:#ff9933'>q</del><ins style='background-color:#00ff66'>p</ins>elling!<del style='background-color:#ff9933'> Note that this section is obsolete.</del>
```
If you load the html fragment with a browser, you get the following result:


# Credits
The longest common substrings are found using the LongestCommonSubstring (LCS) algorithm credited to https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/LongestCommonSubstring.java.html
Credits go to Robert Sedgewick and Kevin Wayne that provide the LCS algorithm with GNU General Public License
