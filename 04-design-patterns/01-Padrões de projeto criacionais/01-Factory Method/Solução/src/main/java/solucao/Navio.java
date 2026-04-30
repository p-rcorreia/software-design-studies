package src.main.java.solucao;

public class Navio implements Transporte {

    @Override
    public void entregar() {
        System.out.println("Entrega pelo mar em um container.");
    }
}
