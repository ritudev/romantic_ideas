package smile.com.home;

/**
 * Created by   05 on 21-08-2017.
 */

public class AdUtil {

    private static final int MAX_TIME_SHOW_AD_IN_A_SESSION = 5;
    private static final int SHOW_AD_IN_A_SESSION_AFTER_CLICK = 2;
    public static final long TIME_AFTER_INTERSTITIAL_AD_SHOW = 5000;
    public static final String TEST_DEVICE_ID = "FA820BBB5CAA564507F009738E5E7F7F";

    public static boolean showMoreInterstitialAd() {
        if(ApplicationVariable.getNoOfTimeDescriptionActivityOpened() >= SHOW_AD_IN_A_SESSION_AFTER_CLICK) {
            ApplicationVariable.setNoOfTimeDescriptionActivityOpened(0);
            if (ApplicationVariable.getNoOfTimeInterstitialAdShown() <= MAX_TIME_SHOW_AD_IN_A_SESSION) {
                return true;
            }
        }
        return false;
    }

}
