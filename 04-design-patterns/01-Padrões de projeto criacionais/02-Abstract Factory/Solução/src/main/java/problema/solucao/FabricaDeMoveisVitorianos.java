package src.main.java.problema.solucao;

public class FabricaDeMoveisVitorianos implements FabricaDeMoveis {

    @Override
    public Cadeira criarCadeira() {
        return new CadeiraVitoriana();
    }

    @Override
    public MesaDeCentro criarMesaDeCentro() {
        return new MesaDeCentroVitoriana();
    }

    @Override
    public Sofa criarSofa() {
        return new SofaVitoriano();
    }
    
}
