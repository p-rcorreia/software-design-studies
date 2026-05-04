package src.main.java.problema.solucao;

public class LojaMoveis {

    private FabricaDeMoveis fabricaDeMoveis;

    public LojaMoveis(FabricaDeMoveis fabricaDeMoveis){
        this.fabricaDeMoveis = fabricaDeMoveis;
    }

    public void montarSala() {
        Cadeira cadeira = fabricaDeMoveis.criarCadeira();
        MesaDeCentro mesaDeCentro = fabricaDeMoveis.criarMesaDeCentro();
        Sofa sofa = fabricaDeMoveis.criarSofa();

        cadeira.sentar();
        mesaDeCentro.apoiarObjeto();
        sofa.deitar();
    }
}
