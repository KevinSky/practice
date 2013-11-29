package kevin.parctice.btrace;

public class ThreadPool {
    
    public static String println() {
        long s = System.currentTimeMillis();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long e = System.currentTimeMillis();
        return "test:"+(e-s);
    }

    public static void main(String[] args) {
        while(true) {
            long s = System.currentTimeMillis();
            println();
            long e = System.currentTimeMillis();
            System.out.println(e-s);
        }
    }
}
