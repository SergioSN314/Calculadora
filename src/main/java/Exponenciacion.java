class Exponenciacion extends ExpresionConstante {
    private ExpresionConstante izquierda;
    private ExpresionConstante derecha;

    Exponenciacion(ExpresionConstante izquierda, ExpresionConstante derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    @Override
    double evaluar() {
        return Math.pow(izquierda.evaluar(), derecha.evaluar());
    }



}