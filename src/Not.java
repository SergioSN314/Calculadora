public class Not extends ExpresionBooleana{
    private ExpresionBooleana valorverdad;

    Not( ExpresionBooleana valorverdad) {
        this.valorverdad = valorverdad;

    }

    @Override
    boolean evaluar() {
        return !valorverdad.evaluar();
    }
}
