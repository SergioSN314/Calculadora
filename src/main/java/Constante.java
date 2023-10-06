class Constante extends ExpresionConstante {
    private double valor;
    Constante(double valor){
        this.valor=valor;
    }
    double evaluar(){
        return valor;
    }
}