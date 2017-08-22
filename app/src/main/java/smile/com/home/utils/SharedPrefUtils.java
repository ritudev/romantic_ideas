package smile.com.home.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import smile.com.home.AppUtil;

/**
 * Created by   05 on 22-08-2017.
 */

public class SharedPrefUtils {

    private static final String TAG = "SharedPrefUtils";
    private static final String VALUE = "value : ";
    private static final String SHARED_PREFS_FILE = "my_pref";
    private static SharedPrefUtils sInstance = new SharedPrefUtils();


    public static final String SHOW_NEVER = "show_never";
    public static final String SESSION_COUNT = "session_count";




    /**
     * Constructor of the singelton class
     */
    private SharedPrefUtils() {
        // not now
    }

    /**
     * This method returns the single instance of SharedPreferencesUtils
     *
     * @return single instance of the class
     */
    public static  SharedPrefUtils getInstance() {
        return sInstance;
    }


    /**
     * Retrieve a boolean value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or false.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a boolean.
     */
    public  boolean getBoolean(String key) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return false;
        }
        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            return mSharedPreferences.getBoolean(key, false);
        }catch (Exception e){
            printError(TAG , e);
            return false;
        }
    }

    private void printError(String tag, Exception e) {
        Log.e(tag, "PrintError: ", e.fillInStackTrace());
    }

    /**
     * Retrieve a boolean value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defaultValue - default value if not exists preference
     * @return Returns the preference value if it exists, or false.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a boolean.
     */
    public  boolean getBoolean(String key,boolean defaultValue) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return false;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            return mSharedPreferences.getBoolean(key, defaultValue);
        }catch (Exception e){
            printError(TAG , e);
            return defaultValue;
        }
    }

    /**
     * Set a boolean value in the preferences editor, to be written back
     * once apply or apply are called.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    public void putBoolean(String key, boolean value) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.apply();
        }catch (Exception e){
            printError(TAG , e);
        }
    }

    /**
     * Retrieve a float value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return returns the preference value if it exists, or 0.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a float.
     */
    public  float getFloat(String key) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return 0;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            return mSharedPreferences.getFloat(key, 0);
        }catch (Exception e){
            printError(TAG , e);
            return 0;
        }
    }

    /**
     * Retrieve a float value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return returns the preference value if it exists, or 0.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a float.
     */
    public  float getFloat(String key , float defaultvalue) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return defaultvalue;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            return mSharedPreferences.getFloat(key, defaultvalue);
        }catch (Exception e){
            printError(TAG , e);
            return defaultvalue;
        }
    }

    /**
     * Set a float value in the preferences editor, to be written back once
     * apply or apply are called.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    public void putFloat(String key, float value) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            Log.i(TAG, VALUE + value);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putFloat(key, value);
            editor.apply();
        }catch (Exception e){
            printError(TAG , e);
        }
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or 0.  Throws
     * ClassCastException if there is a preference with this name that is not
     * an int.
     */
    public  int getInt(String key) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return 0;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            return mSharedPreferences.getInt(key, 0);
        }catch (Exception e){
            printError(TAG , e);
            return 0;
        }
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or 0.  Throws
     * ClassCastException if there is a preference with this name that is not
     * an int.
     */
    public  int getInt(String key,int defaultValue) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return 0;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            return mSharedPreferences.getInt(key, defaultValue);
        }catch (Exception e){
            printError(TAG , e);
            return defaultValue;
        }
    }

    /**
     * Set an int value in the preferences editor, to be written back once
     * apply or apply are called.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    public void putInt(String key, int value) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            Log.i(TAG, VALUE + value);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putInt(key, value);
            editor.apply();
        }catch (Exception e){
            printError(TAG , e);
        }
    }

    /**
     * Retrieve a long value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or 0.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a long.
     */
    public  long getLong(String key) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return 0;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            return mSharedPreferences.getLong(key, 0);
        }catch (Exception e){
            printError(TAG , e);
            return 0;
        }
    }

    /**
     * Retrieve an long value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or defaultValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a long.
     */
    public  long getLong(String key,long defaultValue) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return 0;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            return mSharedPreferences.getLong(key, defaultValue);
        }catch (Exception e){
            printError(TAG , e);
            return defaultValue;
        }
    }

    /**
     * Set a long value in the preferences editor, to be written back once
     * apply or apply are called.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    public void putLong(String key, long value) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            Log.i(TAG, VALUE + value);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putLong(key, value);
            editor.apply();
        }catch (Exception e){
            printError(TAG , e);
        }
    }

    /**
     * Retrieve a String value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or null.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a String.
     */
    public  String getString(String key) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return null;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            return mSharedPreferences.getString(key, null);
        }catch (Exception e){
            printError(TAG , e);
            return null;
        }
    }

    /**
     * Retrieve a String value from the preferences with default value.
     *
     * @param key The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or default value.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a String.
     */
    public  String getString(String key, String defVal) {
        if (AppUtil.getInstance().getApplicationContext() == null) {
            return null;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            return mSharedPreferences.getString(key, defVal);
        }catch (Exception e){
            printError(TAG , e);
            return defVal;
        }
    }

    /**
     * Set a String value in the preferences editor, to be written back once
     * apply or apply are called.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference. Supplying null
     *    as the value is equivalent to calling remove String with
     *    this key.
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    public void putString(String key, String value) {
        if(AppUtil.getInstance().getApplicationContext() == null){
            return;
        }

        try {
            SharedPreferences mSharedPreferences = AppUtil.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            Log.e(TAG, VALUE + value);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }catch (Exception e){
            printError(TAG , e);
        }
    }
    
}
