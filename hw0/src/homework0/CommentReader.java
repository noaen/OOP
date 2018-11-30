package homework0;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * CommentReader is class intended to parse a Java source file and print its
 * comments to stdout
 */
public class CommentReader {

    /**
     * @modifies Nothing
     * @effects Prints (to stdout) the comments in the file, based on the Java
     *          commenting convention
     */
    public static void main(String[] args) {
        if (!(args.length == 1))
            throw new IllegalArgumentException("CommentReader accepts exactly one argument!");
        String filePath = args[0];
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader(filePath));
            Boolean multiLineMode = false;
            String lineInFile = bufReader.readLine();
            while (lineInFile != null) {
                Boolean shouldPrintRemaining = false;
                Boolean firstSlash = false;
                Boolean firstAsterisk = false;
                StringTokenizer dblBackslash = new StringTokenizer(lineInFile, "/*", true);
                while (dblBackslash.hasMoreTokens()) {
                    String token = dblBackslash.nextToken();
                    if (shouldPrintRemaining == true) {
                        System.out.println(token);
                    } else if ((token.equals("/")) && (firstSlash == true)) {
                        shouldPrintRemaining = true;
                    } else if ((token.equals("/")) && (firstAsterisk == true)) {
                        multiLineMode = false;
                    } else if ((token.equals("*") && (firstSlash == true))) {
                        multiLineMode = true;
                    } else if (token.equals("/")) {
                        firstSlash = true;
                    } else if (token.equals("*")) {
                        firstAsterisk = true;
                    } else if (multiLineMode == true) {
                        System.out.println(token);
                    } else if (firstSlash == true) {
                        firstSlash = false;
                    }
                }
                lineInFile = bufReader.readLine();
            }
            bufReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
