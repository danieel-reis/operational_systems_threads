package Thread_Com_Extensao_Classe;

public class TesteThread {

    public static void main(String[] args) {
        ThreadSimples t1 = new ThreadSimples("Thread 1");
        ThreadSimples t2 = new ThreadSimples("Thread 2");

        t1.start();
        t2.start();
    }

}
