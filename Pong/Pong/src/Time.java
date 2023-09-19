public class Time
{
    /*public static double timeStarted = System.nanoTime();

    public static double getTime() { return (System.nanoTime() - timeStarted) * 1E-9; }*/

    private static final double timeStarted = System.nanoTime();

    public static double getTimeStarted() { return timeStarted; }

    public static double getTimeSeconds() { return (System.nanoTime() - timeStarted) * 1E-9; }

    public static double getTimeMilliseconds() { return (System.nanoTime() - timeStarted) * 1E-6; }
}
