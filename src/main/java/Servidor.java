public class Servidor {

    public static void main(String[] args) {
        ExpresionConstante expresion = new Exponenciacion(new Constante(4.0), new Constante(3.0));
        ExpresionBooleana expresion2 = new Xor(new Booleano(true), new Booleano(true));
        ExpresionBooleana expresion3 = new Not(new Booleano(false));

        double resultado = expresion.evaluar();
        boolean resultado2 = expresion2.evaluar();
        boolean resultado3 = expresion3.evaluar();


        System.out.println("Resultado: " + resultado);
        System.out.println("Resultado: " + resultado2);
        System.out.println("Resultado: " + resultado3);

    }
}
