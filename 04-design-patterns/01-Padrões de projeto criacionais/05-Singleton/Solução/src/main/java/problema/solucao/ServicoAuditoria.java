package problema.solucao;

public class ServicoAuditoria {

    public void registrarEvento(String evento) {
        ConfiguracaoGlobal configuracaoGlobal = ConfiguracaoGlobal.getInstancia();

        System.out.println("Registrando evento: " + evento);
        System.out.println("Ambiente: " + configuracaoGlobal.getAmbiente());
        System.out.println("Banco: " + configuracaoGlobal.getUrlBancoDados());
    }
}
