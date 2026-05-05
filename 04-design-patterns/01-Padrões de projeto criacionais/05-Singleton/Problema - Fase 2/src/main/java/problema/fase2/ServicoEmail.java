package problema.fase2;

public class ServicoEmail {

    public void enviarEmailBoasVindas() {
        System.out.println("Enviando e-mail de boas-vindas...");
        System.out.println("Remetente: " + ConfiguracaoGlobal.remetenteEmail);
    }
}
