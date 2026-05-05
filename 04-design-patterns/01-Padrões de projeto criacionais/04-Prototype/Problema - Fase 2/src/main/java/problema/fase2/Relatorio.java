package problema.fase2;

public class Relatorio {

    private String titulo;
    private String autor;
    private String periodo;
    private String temaVisual;
    private boolean incluirGraficos;
    private boolean incluirResumoExecutivo;
    private boolean incluirComparativoAnoAnterior;
    private String marcaDagua;
    private String formatoExportacao;

    public Relatorio(
            String titulo,
            String autor,
            String periodo,
            String temaVisual,
            boolean incluirGraficos,
            boolean incluirResumoExecutivo,
            boolean incluirComparativoAnoAnterior,
            String marcaDagua,
            String formatoExportacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.periodo = periodo;
        this.temaVisual = temaVisual;
        this.incluirGraficos = incluirGraficos;
        this.incluirResumoExecutivo = incluirResumoExecutivo;
        this.incluirComparativoAnoAnterior = incluirComparativoAnoAnterior;
        this.marcaDagua = marcaDagua;
        this.formatoExportacao = formatoExportacao;
    }

    public void exibir() {
        System.out.println("Relatorio:");
        System.out.println("Titulo: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Periodo: " + periodo);
        System.out.println("Tema visual: " + temaVisual);
        System.out.println("Graficos: " + simOuNao(incluirGraficos));
        System.out.println("Resumo executivo: " + simOuNao(incluirResumoExecutivo));
        System.out.println("Comparativo ano anterior: " + simOuNao(incluirComparativoAnoAnterior));
        System.out.println("Marca d'agua: " + valorOuNenhum(marcaDagua));
        System.out.println("Formato: " + formatoExportacao);
    }

    private String simOuNao(boolean valor) {
        return valor ? "Sim" : "Nao";
    }

    private String valorOuNenhum(String valor) {
        if (valor == null || valor.isBlank()) {
            return "Nenhuma";
        }

        return valor;
    }
}
