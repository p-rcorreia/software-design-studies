package src.main.java.solucao;

public class Principal {
    public static void main(String[] args) {
        EmpresaLogistica logisticaViaria = new LogisticaViaria();
        EmpresaLogistica logisticaMaritima = new LogisticaMaritima();

        logisticaViaria.planejarEntrega();
        logisticaMaritima.planejarEntrega();
    }
}
