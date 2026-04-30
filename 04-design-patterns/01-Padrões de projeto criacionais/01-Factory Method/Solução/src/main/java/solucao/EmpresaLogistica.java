package src.main.java.solucao;

public abstract class EmpresaLogistica {
    public void planejarEntrega() {
        Transporte transporte = criarTransporte();
        transporte.entregar();
    }

    protected abstract Transporte criarTransporte();
}
