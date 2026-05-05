package problema.fase1;

public class ConfiguracaoSistema {

    private String ambiente;
    private String urlBancoDados;
    private String remetenteEmail;
    private String formatoRelatorio;

    public ConfiguracaoSistema(String ambiente, String urlBancoDados, String remetenteEmail, String formatoRelatorio) {
        this.ambiente = ambiente;
        this.urlBancoDados = urlBancoDados;
        this.remetenteEmail = remetenteEmail;
        this.formatoRelatorio = formatoRelatorio;
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

    public void exibirResumo() {
        System.out.println("Ambiente: " + ambiente);
        System.out.println("Banco: " + urlBancoDados);
        System.out.println("Remetente: " + remetenteEmail);
        System.out.println("Formato de relatório: " + formatoRelatorio);
    }
}
