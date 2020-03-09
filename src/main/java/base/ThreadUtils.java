package base;

public class ThreadUtils {

    public static void sleep(long millisecondsTimeout){
        try {
            Thread.sleep(millisecondsTimeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
