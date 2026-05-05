package problema.fase2;

public class Principal {

    public static void main(String[] args) {
        ServicoEmail servicoEmail = new ServicoEmail();
        ServicoRelatorio servicoRelatorio = new ServicoRelatorio();
        ServicoAuditoria servicoAuditoria = new ServicoAuditoria();

        ConfiguracaoGlobal.exibirResumo();
        System.out.println();

        servicoEmail.enviarEmailBoasVindas();
        System.out.println();

        servicoRelatorio.gerarRelatorioMensal();
        System.out.println();

        servicoAuditoria.registrarEvento("Usuário acessou o sistema");
        System.out.println();

        servicoRelatorio.alterarFormatoRelatorioParaTeste();
        servicoRelatorio.gerarRelatorioMensal();
    }
}
