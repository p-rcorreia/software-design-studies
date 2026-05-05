package problema.solucao;

public class ConfiguracaoGlobal {

    private static ConfiguracaoGlobal instancia;

    private String ambiente;
    private String urlBancoDados;
    private String remetenteEmail;
    private String formatoRelatorio;

    private ConfiguracaoGlobal() {
        this.ambiente = "produção";
        this.urlBancoDados = "jdbc:mysql://localhost:3306/app";
        this.remetenteEmail = "sistema@empresa.com";
        this.formatoRelatorio = "PDF";
    }

    public static ConfiguracaoGlobal getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracaoGlobal();
        }

        return instancia;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public String getUrlBancoDados() {
        return urlBancoDados;
    }

    public String getRemetenteEmail() {
        return remetenteEmail;
    }

    public String getFormatoRelatorio() {
        return formatoRelatorio;
    }

    public void alterarFormatoRelatorioParaTeste() {
        this.formatoRelatorio = "TXT";
    }

    public void exibirResumo() {
        System.out.println("Ambiente: " + ambiente);
        System.out.println("Banco: " + urlBancoDados);
        System.out.println("Remetente: " + remetenteEmail);
        System.out.println("Formato de relatório: " + formatoRelatorio);
    }
}
