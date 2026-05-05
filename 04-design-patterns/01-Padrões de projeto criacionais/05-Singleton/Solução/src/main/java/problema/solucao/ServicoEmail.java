package problema.solucao;

public class ServicoEmail {

    public void enviarEmailBoasVindas() {
        ConfiguracaoGlobal configuracaoGlobal = ConfiguracaoGlobal.getInstancia();

        System.out.println("Enviando e-mail de boas-vindas...");
        System.out.println("Remetente: " + configuracaoGlobal.getRemetenteEmail());
    }
}
