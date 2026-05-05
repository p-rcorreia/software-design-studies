package problema.solucao;

public class Principal {

    public static void main(String[] args) {
        GeradorRelatorios geradorRelatorios = new GeradorRelatorios();

        Relatorio relatorioBase = geradorRelatorios.criarRelatorioFinanceiro();

        Relatorio relatorioMaio = relatorioBase.clonar();
        relatorioMaio.definirPeriodo("Maio/2026");

        Relatorio relatorioOutubro = relatorioBase.clonar();
        relatorioOutubro.definirPeriodo("Outubro/2026");

        relatorioMaio.exibir();
        System.out.println();

        relatorioOutubro.exibir();
    }
}
