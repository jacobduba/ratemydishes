package sh.duba.rmd.frontend.app;

public class AppVars {
    public static final String API_URL = "https://rmd.duba.sh";
    public static String userToken;
    public static String netId;
    public static boolean isAdmin;

    public static enum Restaurant{
        DINING_CENTER,
        CAFE,
        FAST_CASUAL,
        GET_AND_GO,
        CONVENIENCE_STORES
    }
}
