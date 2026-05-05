package problema.solucao;

public class ServicoRelatorio {

    public void gerarRelatorioMensal() {
        ConfiguracaoGlobal configuracaoGlobal = ConfiguracaoGlobal.getInstancia();

        System.out.println("Gerando relatório mensal...");
        System.out.println("Formato: " + configuracaoGlobal.getFormatoRelatorio());
    }

    public void alterarFormatoRelatorioParaTeste() {
        ConfiguracaoGlobal configuracaoGlobal = ConfiguracaoGlobal.getInstancia();

        configuracaoGlobal.alterarFormatoRelatorioParaTeste();
        System.out.println("Formato global alterado para teste.");
    }
}
