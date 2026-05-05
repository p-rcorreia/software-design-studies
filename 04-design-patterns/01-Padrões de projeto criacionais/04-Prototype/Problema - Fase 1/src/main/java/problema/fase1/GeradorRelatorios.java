package problema.fase1;

public class GeradorRelatorios {

    public Relatorio criarRelatorioMensal() {
        return new Relatorio(
                "Resumo financeiro",
                "Equipe Financeira",
                "Janeiro/2026",
                "Corporativo");
    }
}
