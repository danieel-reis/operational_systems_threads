package Thread_Com_Extensao_Classe;

public class ThreadSimples extends Thread {

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
