package problema.fase1;

public class ServicoEmail {

    private ConfiguracaoSistema configuracaoSistema = new ConfiguracaoSistema(
            "produção",
            "jdbc:mysql://localhost:3306/app",
            "sistema@empresa.com",
            "PDF");

    public void enviarEmailBoasVindas() {
        System.out.println("Enviando e-mail de boas-vindas...");
        System.out.println("Remetente: " + configuracaoSistema.getRemetenteEmail());
    }
}
