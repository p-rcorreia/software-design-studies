package problema.fase2;

public class ServicoAuditoria {

    public void registrarEvento(String evento) {
        System.out.println("Registrando evento: " + evento);
        System.out.println("Ambiente: " + ConfiguracaoGlobal.ambiente);
        System.out.println("Banco: " + ConfiguracaoGlobal.urlBancoDados);
    }
}
