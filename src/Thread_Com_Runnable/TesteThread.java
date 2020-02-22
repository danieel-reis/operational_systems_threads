package Thread_Com_Runnable;

public class TesteThread {

    public static void main(String[] args) {
        ThreadSimples t1 = new ThreadSimples("Thread 1");
        ThreadSimples t2 = new ThreadSimples("Thread 2");

        Thread thread1 = new Thread(t1);
        Thread thread2 = new Thread(t2);

        thread1.start();
        thread2.start();
    }

}
