package Thread_Com_Runnable;

public class ThreadSimples implements Runnable {

    private String nome;

    public ThreadSimples(String str) {
        nome = str;
    }

    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i + " - " + nome);
        }
        System.out.println("PRONTO! - " + nome);
    }

}
