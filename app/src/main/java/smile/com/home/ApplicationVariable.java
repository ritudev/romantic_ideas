package smile.com.home;

/**
 * Created by   on 21-08-2017.
 */

public class ApplicationVariable {

    private static int noOfTimeInterstitialAdShown = 0;
    private static int noOfTimeDescriptionActivityOpened = 0;

    public static int getNoOfTimeInterstitialAdShown() {
        return noOfTimeInterstitialAdShown;
    }

    public static void updateNoOfTimeInterstitialAdShownByOne() {
        noOfTimeInterstitialAdShown++;
    }

    public static int getNoOfTimeDescriptionActivityOpened() {
        return noOfTimeDescriptionActivityOpened;
    }

    public static void setNoOfTimeDescriptionActivityOpened(int noOfTimeDescriptionActivityOpened) {
        ApplicationVariable.noOfTimeDescriptionActivityOpened = noOfTimeDescriptionActivityOpened;
    }

    public static void updateNoOfTimeDescriptionActivityOpenedByOne() {
        noOfTimeDescriptionActivityOpened++;
    }
}
