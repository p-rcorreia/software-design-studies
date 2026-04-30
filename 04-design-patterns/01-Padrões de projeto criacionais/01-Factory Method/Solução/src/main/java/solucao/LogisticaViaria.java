package src.main.java.solucao;

public class LogisticaViaria extends EmpresaLogistica {
    @Override
    protected Transporte criarTransporte() {
        return new Caminhao();
    }
}
