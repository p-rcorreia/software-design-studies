package problema.solucao;

public class GeradorRelatorios {

    String periodo = null;

    public Relatorio criarRelatorioFinanceiro() {
        return new Relatorio(
                "Resumo financeiro",
                "Equipe Financeira",
                periodo,
                "Corporativo",
                true,
                true,
                true,
                "Confidencial",
                "PDF");
    }
}
