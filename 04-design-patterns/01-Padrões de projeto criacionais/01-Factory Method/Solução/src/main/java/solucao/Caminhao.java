package src.main.java.solucao;

public class Caminhao implements Transporte {

    @Override
    public void entregar() {
        System.out.println("Entrega por terra em uma caixa.");
    }
}
