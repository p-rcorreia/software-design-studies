package problema.fase1;

public class Relatorio {

    private String titulo;
    private String autor;
    private String periodo;
    private String temaVisual;

    public Relatorio(String titulo, String autor, String periodo, String temaVisual) {
        this.titulo = titulo;
        this.autor = autor;
        this.periodo = periodo;
        this.temaVisual = temaVisual;
    }

    public void exibir() {
        System.out.println("Relatorio:");
        System.out.println("Titulo: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Periodo: " + periodo);
        System.out.println("Tema visual: " + temaVisual);
    }
}
