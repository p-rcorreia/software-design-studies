package problema.fase2;

public class ServicoRelatorio {

    public void gerarRelatorioMensal() {
        System.out.println("Gerando relatório mensal...");
        System.out.println("Formato: " + ConfiguracaoGlobal.formatoRelatorio);
    }

    public void alterarFormatoRelatorioParaTeste() {
        ConfiguracaoGlobal.formatoRelatorio = "TXT";
        System.out.println("Formato global alterado para teste.");
    }
}
