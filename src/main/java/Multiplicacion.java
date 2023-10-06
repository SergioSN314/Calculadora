class Multiplicacion extends ExpresionConstante {
    private ExpresionConstante izquierda;
    private ExpresionConstante derecha;

    Multiplicacion(ExpresionConstante izquierda, ExpresionConstante derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    @Override
    double evaluar() {
        return izquierda.evaluar()*derecha.evaluar();
    }



}