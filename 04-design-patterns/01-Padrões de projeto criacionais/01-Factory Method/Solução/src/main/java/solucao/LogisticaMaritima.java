package src.main.java.solucao;

public class LogisticaMaritima extends EmpresaLogistica {
    @Override
    protected Transporte criarTransporte() {
        return new Navio();
    }
}
