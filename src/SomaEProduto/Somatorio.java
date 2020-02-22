package SomaEProduto;

public class Somatorio implements Runnable {

    private int limite;
    private int valorSoma;

    public Somatorio(int limite) {
        this.limite = limite;
        this.valorSoma = 0;
    }

    public void run() {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) { e.printStackTrace(); }

        for (int i = 1; i <= limite; i++) {
            valorSoma += i;
        }
    }

    public int getResultado() {
        return valorSoma;
    }
}
