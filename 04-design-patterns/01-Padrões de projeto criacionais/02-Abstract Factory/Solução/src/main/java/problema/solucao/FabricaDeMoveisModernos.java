package src.main.java.problema.solucao;

public class FabricaDeMoveisModernos implements FabricaDeMoveis{

    @Override
    public Cadeira criarCadeira(){
        return new CadeiraModerna();
    }
    
    @Override
    public MesaDeCentro criarMesaDeCentro(){
        return new MesaDeCentroModerna();
    }

    @Override
    public Sofa criarSofa(){
        return new SofaModerno();
    }
}
