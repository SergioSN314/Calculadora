public class Xor extends ExpresionBooleana{
    private ExpresionBooleana izquierda;
    private ExpresionBooleana derecha;

    Xor(ExpresionBooleana izquierda, ExpresionBooleana derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    @Override
    boolean evaluar() {
        return (izquierda.evaluar() || derecha.evaluar()) && !(izquierda.evaluar() && derecha.evaluar());
    }
}
