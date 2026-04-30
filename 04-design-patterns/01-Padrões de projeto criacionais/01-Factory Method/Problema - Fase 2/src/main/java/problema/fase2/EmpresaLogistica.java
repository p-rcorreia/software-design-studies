package src.main.java.problema.fase2;

public class EmpresaLogistica {
    public void planejarEntrega(String tipoTransporte) {
        if ("viario".equalsIgnoreCase(tipoTransporte)) {
            Caminhao caminhao = new Caminhao();
            caminhao.entregar();
            return;
        }

        if ("maritimo".equalsIgnoreCase(tipoTransporte)) {
            Navio navio = new Navio();
            navio.entregar();
            return;
        }

        throw new IllegalArgumentException("Tipo de transporte desconhecido: " + tipoTransporte);
    }
}
