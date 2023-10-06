public class And extends ExpresionBooleana{
    private ExpresionBooleana izquierda;
    private ExpresionBooleana derecha;

    And(ExpresionBooleana izquierda, ExpresionBooleana derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    @Override
    boolean evaluar() {
        return izquierda.evaluar() && derecha.evaluar();
    }
}
