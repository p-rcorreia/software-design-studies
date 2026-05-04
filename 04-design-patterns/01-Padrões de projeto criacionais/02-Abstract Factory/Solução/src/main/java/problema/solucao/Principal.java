package src.main.java.problema.solucao;

public class Principal {
    public static void main(String[] args) {
        LojaMoveis lojaModerna = new LojaMoveis(new FabricaDeMoveisModernos());
        LojaMoveis lojaVitoriana = new LojaMoveis(new FabricaDeMoveisVitorianos());

        lojaModerna.montarSala();
        lojaVitoriana.montarSala();
    }
}
