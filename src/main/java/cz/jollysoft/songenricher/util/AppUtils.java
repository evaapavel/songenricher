package cz.jollysoft.songenricher.util;



/**
 * Defines application wide static utility methods.
 * 
 * @author Pavel Foltyn
 */
public final class AppUtils {



    /** Disallow instantiation of this class. */
    private AppUtils() {}



    /**
     * Checks whether a given text part matches a given pattern.
     * 
     * @param textPart Text part to check.
     * @param regexPattern Pattern to check the given text part against.
     * @return Returns true :-: the given text part matches the given pattern, false :-: the given text part does NOT match the given pattern.
     */
    public static boolean matchesPattern(String textPart, String regexPattern) {
        return textPart.matches(regexPattern);
    }



}
