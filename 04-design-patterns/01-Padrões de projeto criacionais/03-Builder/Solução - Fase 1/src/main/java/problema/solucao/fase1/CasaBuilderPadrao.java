package problema.solucao.fase1;

public class CasaBuilderPadrao implements CasaBuilder {

    private int paredes;
    private int portas;
    private int janelas;
    private String teto;
    private boolean garagem;
    private boolean jardim;
    private boolean piscina;
    private boolean aquecimento;
    private boolean encanamento;
    private boolean energiaEletrica;
    private String material;
    private String observacao;

    @Override
    public void construirParedes(int numeroDeParedes) {
        this.paredes = numeroDeParedes;
    }

    @Override
    public void construirPortas(int numeroDePortas) {
        this.portas = numeroDePortas;
    }

    @Override
    public void construirJanelas(int numeroDeJanelas) {
       this.janelas = numeroDeJanelas;
    }

    @Override
    public void construirTeto(String teto) {
        this.teto = teto;
    }

    @Override
    public void construirGaragem() {
        this.garagem = true;
    }

    @Override
    public void construirJardim() {
        this.jardim = true;
    }

    @Override
    public void construirPiscina() {
        this.piscina = true;
    }

    @Override
    public void construirAquecimento() {
        this.aquecimento = true;
    }

    @Override
    public void construirEncanamento() {
        this.encanamento = true;
    }

    @Override
    public void construirEnergiaEletrica() {
        this.energiaEletrica = true;
    }

    @Override
    public void definirMaterial(String material) {
        this.material = material;
    }

    @Override
    public void adicionarObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public Casa construirCasa() {
        return new Casa(
                paredes,
                portas,
                janelas,
                teto,
                garagem,
                jardim,
                piscina,
                aquecimento,
                encanamento,
                energiaEletrica,
                material,
                observacao);
    }
    
}
