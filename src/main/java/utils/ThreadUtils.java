package utils;

import java.util.Arrays;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getAnonymousLogger;

public class ThreadUtils {

    private ThreadUtils(){}

    public static void sleep(long millisecondsTimeout){
        try {
            Thread.sleep(millisecondsTimeout);
        } catch (InterruptedException e) {
            getAnonymousLogger().log(SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }
}
