package problema.fase1;

public class ServicoRelatorio {

    private ConfiguracaoSistema configuracaoSistema = new ConfiguracaoSistema(
            "produção",
            "jdbc:mysql://localhost:3306/app",
            "sistema@empresa.com",
            "PDF");

    public void gerarRelatorioMensal() {
        System.out.println("Gerando relatório mensal...");
        System.out.println("Formato: " + configuracaoSistema.getFormatoRelatorio());
    }
}
