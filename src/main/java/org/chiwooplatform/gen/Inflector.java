package org.chiwooplatform.gen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Transforms words to singular, plural, humanized (human readable), underscore, camel case, or ordinal form. This is inspired by
 * the <a href="http://api.rubyonrails.org/classes/Inflector.html">Inflector</a> class in <a
 * href="http://www.rubyonrails.org">Ruby on Rails</a>, which is distributed under the <a
 * href="http://wiki.rubyonrails.org/rails/pages/License">Rails license</a>.
 */
public class Inflector {

    /**
     * Utility method to replace all occurrences given by the specific backreference with its uppercased form, and remove all
     * other backreferences.
     * <p>
     * The Java {@link Pattern regular expression processing} does not use the preprocessing directives <code>\l</code>,
     * <code>&#92;u</code>, <code>\L</code>, and <code>\U</code>. If so, such directives could be used in the replacement string
     * to uppercase or lowercase the backreferences. For example, <code>\L1</code> would lowercase the first backreference, and
     * <code>&#92;u3</code> would uppercase the 3rd backreference.
     * </p>
     *
     * @param input
     * @param regex
     * @param groupNumberToUppercase
     * @return the input string with the appropriate characters converted to upper-case
     */
    protected static String replaceAllWithUppercase( String input, String regex, int groupNumberToUppercase ) {
        Pattern underscoreAndDotPattern = Pattern.compile( regex );
        Matcher matcher = underscoreAndDotPattern.matcher( input );
        StringBuffer sb = new StringBuffer();
        while ( matcher.find() ) {
            matcher.appendReplacement( sb, matcher.group( groupNumberToUppercase ).toUpperCase() );
        }
        matcher.appendTail( sb );
        return sb.toString();
    }

    /**
     * By default, this method converts strings to UpperCamelCase. If the <code>uppercaseFirstLetter</code> argument to false,
     * then this method produces lowerCamelCase. This method will also use any extra delimiter characters to identify word
     * boundaries.
     * <p>
     * Examples:
     *
     * <pre>
     *   inflector.camelCase(&quot;active_record&quot;,false)    #=&gt; &quot;activeRecord&quot;
     *   inflector.camelCase(&quot;active_record&quot;,true)     #=&gt; &quot;ActiveRecord&quot;
     *   inflector.camelCase(&quot;first_name&quot;,false)       #=&gt; &quot;firstName&quot;
     *   inflector.camelCase(&quot;first_name&quot;,true)        #=&gt; &quot;FirstName&quot;
     *   inflector.camelCase(&quot;name&quot;,false)             #=&gt; &quot;name&quot;
     *   inflector.camelCase(&quot;name&quot;,true)              #=&gt; &quot;Name&quot;
     * </pre>
     *
     * </p>
     *
     * @param lowerCaseAndUnderscoredWord the word that is to be converted to camel case
     * @param uppercaseFirstLetter true if the first character is to be uppercased, or false if the first character is to be
     *        lowercased
     * @param delimiterChars optional characters that are used to delimit word boundaries
     * @return the camel case version of the word
     */
    public String camelCase( String lowerCaseAndUnderscoredWord, boolean uppercaseFirstLetter,
                             char... delimiterChars ) {
        if ( lowerCaseAndUnderscoredWord == null )
            return null;
        lowerCaseAndUnderscoredWord = lowerCaseAndUnderscoredWord.trim();
        if ( lowerCaseAndUnderscoredWord.length() == 0 )
            return "";
        if ( uppercaseFirstLetter ) {
            String result = lowerCaseAndUnderscoredWord;
            // Replace any extra delimiters with underscores (before the underscores are converted in the next step)...
            if ( delimiterChars != null ) {
                for ( char delimiterChar : delimiterChars ) {
                    result = result.replace( delimiterChar, '_' );
                }
            }
            // Change the case at the beginning at after each underscore ...
            return replaceAllWithUppercase( result, "(^|_)(.)", 2 );
        }
        if ( lowerCaseAndUnderscoredWord.length() < 2 )
            return lowerCaseAndUnderscoredWord;
        return "" + Character.toLowerCase( lowerCaseAndUnderscoredWord.charAt( 0 ) )
            + camelCase( lowerCaseAndUnderscoredWord, true, delimiterChars ).substring( 1 );
    }

    /**
     * Converts strings to lowerCamelCase. This method will also use any extra delimiter characters to identify word boundaries.
     * <p>
     * Examples:
     *
     * <pre>
     *   inflector.lowerCamelCase(&quot;active_record&quot;)       #=&gt; &quot;activeRecord&quot;
     *   inflector.lowerCamelCase(&quot;first_name&quot;)          #=&gt; &quot;firstName&quot;
     *   inflector.lowerCamelCase(&quot;name&quot;)                #=&gt; &quot;name&quot;
     *   inflector.lowerCamelCase(&quot;the-first_name&quot;,'-')  #=&gt; &quot;theFirstName&quot;
     * </pre>
     *
     * </p>
     *
     * @param lowerCaseAndUnderscoredWord the word that is to be converted to camel case
     * @param delimiterChars optional characters that are used to delimit word boundaries
     * @return the lower camel case version of the word
     */
    public String lowerCamelCase( String lowerCaseAndUnderscoredWord, char... delimiterChars ) {
        return camelCase( lowerCaseAndUnderscoredWord, false, delimiterChars );
    }

    /**
     * Converts strings to UpperCamelCase. This method will also use any extra delimiter characters to identify word boundaries.
     * <p>
     * Examples:
     *
     * <pre>
     *   inflector.upperCamelCase(&quot;active_record&quot;)       #=&gt; &quot;SctiveRecord&quot;
     *   inflector.upperCamelCase(&quot;first_name&quot;)          #=&gt; &quot;FirstName&quot;
     *   inflector.upperCamelCase(&quot;name&quot;)                #=&gt; &quot;Name&quot;
     *   inflector.lowerCamelCase(&quot;the-first_name&quot;,'-')  #=&gt; &quot;TheFirstName&quot;
     * </pre>
     *
     * </p>
     *
     * @param lowerCaseAndUnderscoredWord the word that is to be converted to camel case
     * @param delimiterChars optional characters that are used to delimit word boundaries
     * @return the upper camel case version of the word
     */
    public String upperCamelCase( String lowerCaseAndUnderscoredWord, char... delimiterChars ) {
        return camelCase( lowerCaseAndUnderscoredWord, true, delimiterChars );
    }
}
