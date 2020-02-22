package SomaEProduto;

public class Produtorio implements Runnable {

    private int limite;
    private int valorProduto;

    public Produtorio(int limite) {
        this.limite = limite;
        this.valorProduto = 1;
    }

    public void run() {
        for (int i = 2; i <= limite; i++) {
            valorProduto *= i;
        }
    }

    public int getResultado() {
        return valorProduto;
    }
}
