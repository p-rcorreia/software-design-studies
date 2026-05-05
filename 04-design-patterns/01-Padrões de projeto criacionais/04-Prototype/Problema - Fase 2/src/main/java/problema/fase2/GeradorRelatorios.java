package problema.fase2;

public class GeradorRelatorios {

    public Relatorio criarRelatorioFinanceiroJaneiro() {
        return new Relatorio(
                "Resumo financeiro",
                "Equipe Financeira",
                "Janeiro/2026",
                "Corporativo",
                true,
                true,
                true,
                "Confidencial",
                "PDF");
    }

    public Relatorio criarRelatorioFinanceiroFevereiro() {
        return new Relatorio(
                "Resumo financeiro",
                "Equipe Financeira",
                "Fevereiro/2026",
                "Corporativo",
                true,
                true,
                true,
                "Confidencial",
                "PDF");
    }

    public Relatorio criarRelatorioFinanceiroMarco() {
        return new Relatorio(
                "Resumo financeiro",
                "Equipe Financeira",
                "Março/2026",
                "Corporativo",
                true,
                true,
                true,
                "Confidencial",
                "PDF");
    }
}
