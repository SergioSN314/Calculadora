class Resta extends ExpresionConstante {
    private ExpresionConstante izquierda;
    private ExpresionConstante derecha;

    Resta(ExpresionConstante izquierda, ExpresionConstante derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    @Override
    double evaluar() {
        return izquierda.evaluar()-derecha.evaluar();
    }



}