package problema.fase1;

public class ServicoAuditoria {

    private ConfiguracaoSistema configuracaoSistema = new ConfiguracaoSistema(
            "produção",
            "jdbc:mysql://localhost:3306/app",
            "sistema@empresa.com",
            "PDF");

    public void registrarEvento(String evento) {
        System.out.println("Registrando evento: " + evento);
        System.out.println("Ambiente: " + configuracaoSistema.getAmbiente());
        System.out.println("Banco: " + configuracaoSistema.getUrlBancoDados());
    }
}
