class Suma extends ExpresionConstante {
    private ExpresionConstante izquierda;
    private ExpresionConstante derecha;

    Suma(ExpresionConstante izquierda, ExpresionConstante derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    @Override
    double evaluar() {
        return izquierda.evaluar()+derecha.evaluar();
    }



}