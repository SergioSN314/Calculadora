/**
 * Clase que crea arboles de expresiones
 * @author Sergio Salazar Núñez
 */
public class ArbolBinarioExpresiones {
    NodoArbol raiz;


    /**
     * Constructor de ArbolBinarioExpresioens, inicializa la raiz apuntando a null
     */
    public ArbolBinarioExpresiones(){
        raiz=null;
    }

    /**
     * Metodo que recibe una expresion tipo String para luego ejecutar el metodo creador del Arbol de Expresiones
     * y posteriormente asignar la raiz del arbol creado al nodo raiz
     * @param expresion String de expresion matematica
     */
    public ArbolBinarioExpresiones(String expresion){
        raiz=creaArbolBE(expresion);
    }

    /**
     * Este metodo crea los nodos del arbol, enviando datos del tipo de objeto que sean
     * @param dato Dato a almacenar en el nodo
     */
    public void creaNodo(Object dato){
        raiz=new NodoArbol(dato);
    }

    /**
     * Metodo que va creando cada sub Arbol del arbol binario de expresiones
     * @param dato2 segundo dato tipo nodoarbol que almacena un entero (hijo)
     * @param dato1 primer dato tipo nodoarbol que almacena un entero (hijo)
     * @param operador dato tipo nodoarbol que almacena un operador (padre)
     * @return Retorna el nodo del operador utilizado
     */
    public NodoArbol creaSubArbol(NodoArbol dato2,NodoArbol dato1,NodoArbol operador){
        operador.izquierdo=dato1;
        operador.derecho=dato2;
        return operador;
    }

    /**
     * Metodo que averigua si el arbol no posee nodos
     * @return true en caso de que la raiz apunte a null, false en caso contrario
     */
    public boolean arbolVacio(){
        return raiz==null;
    }

    /**
     * Metodo que asigna el orden prioridad a los operadores
     * @param op Caracter tipo char que recibe un operador
     * @return El valor de prioridad del operador
     */
    private int prioridad(char op){
        int valor=100;
        switch (op){
            case '~':
            case '^':
                valor=40;
                break;
            case '*':
            case '/':
            case '%':
            case '&':
                valor=30;
                break;
            case '+':
            case '-':
            case '|':
                valor=20;
                break;
            case 'x':
                valor=10;
                break;
            //El caso de los parentesis
            default:
                valor=0;
        }
        return valor;
    }

    /**
     * Este Metodo averigua si un caracter es un operador o no
     * @param caracter Caracter tipo char a analizar
     * @return true en caso de que sea operador, false en caso contrario
     */
    private boolean esOperador (char caracter){
        boolean resultado;
        switch (caracter){
            case '(':
            case ')':
            case '^':
            case '*':
            case '/':
            case '+':
            case '-':
            case '%':
            case '~':
            case '&':
            case '|':
            case 'x':
                resultado=true;
                break;
            default:
                resultado =false;
        }
        return resultado;
    }

    /**
     * Metodo para almacenar el numero completo en el nodo cuando se lee la expresion caracter a caracter
     * @param expresion expresion matematica tipo String la cual vamos a analizar
     * @param pos indice apartir que vamos a analizar hasta que se encuentre con un opeerador
     * @return Retorna un String con el numero completo
     */
    private String numerocompleto(String expresion, int pos){
        String Numerocompleto="";
        char caracterEvaluado;
        for (int i=pos; i<expresion.length();i++){
            caracterEvaluado =expresion.charAt(i);
            if (!esOperador(caracterEvaluado)){
                Numerocompleto=Numerocompleto+caracterEvaluado;
            }else{
                break;
            }
        }
        return Numerocompleto;
    }

    /**
     * Metodo creador del Arbol de expresiones, este metodo va creando el arbol de expresiones con ayuda de dos pilas
     * Una pila llamada Stack almacenará los operadores momentaneamente mientras que se va agregando la expresion
     * matematica en formato posfijo en otra pila llamada output_Queue.
     * @param expresion expresion matemica tipo String
     * @return Retorna la raiz del Arbol
     */
    private NodoArbol creaArbolBE (String expresion){
        PilaArbolExp Stack;
        PilaArbolExp Output_Queue;
        NodoArbol token;
        NodoArbol op1;
        NodoArbol op2;
        NodoArbol op;
        Stack = new PilaArbolExp();
        Output_Queue= new PilaArbolExp();
        char caracterEvaluado;
        String Operando;
        int ajuste;
        for (int i=0; i<expresion.length();i++){
            caracterEvaluado =expresion.charAt(i);
            // Sección de operandos
            // En caso de que reciba un entero verifica cuantos digitos tiene para agregarlos
            // Actualiza la posicion del ciclo for y va agregando los numeros en forma de nodos a la Output_Queue
            if(!esOperador(caracterEvaluado)){
                Operando= numerocompleto(expresion,i);
                ajuste=Operando.length();
                i=i+ajuste-1;
                token=new NodoArbol(Operando);
                Output_Queue.insertar(token);

            }
            // Seccion de operadores
            else {
                token=new NodoArbol(caracterEvaluado);
                switch (caracterEvaluado){
                    // Si es un parentesis abierto solo se agrega al Stack
                    case'(':
                        Stack.insertar(token);
                        break;
                    // Si es un parentesis cerrado se ingresa a un ciclo while que mientras el Stack contenga datos
                    // y mientras que el tope de la pila no sea igual a un parentesis abierto se van creando subarboles
                    // a su vez se van quitando los operadores del Stack y se van agregando al Output_Queue
                    case')':
                        while (!Stack.pilaVacia() && !Stack.topePila().dato.equals('(')){
                            op2=Output_Queue.quitar();
                            op1=Output_Queue.quitar();
                            op =Stack.quitar();

                            op=creaSubArbol(op2,op1,op);
                            Output_Queue.insertar(op);
                        }
                        Stack.quitar();
                        break;
                    // Se hará por defecto que mientras que el stack no este vacio y la prioridad del caracter evaluado
                    // sea menor o igual a la prioridad del caracter del tope del Stack, esto para que se
                    default:
                        while (!Stack.pilaVacia() && prioridad(caracterEvaluado)<=prioridad(Stack.topePila().dato.toString().charAt(0))) {
                            op2 = Output_Queue.quitar();
                            op1 = Output_Queue.quitar();
                            op = Stack.quitar();
                            op = creaSubArbol(op2, op1, op);
                            Output_Queue.insertar(op);
                        }
                        Stack.insertar(token);
                }
            }
        }
        while (!Stack.pilaVacia()){
            op2 = Output_Queue.quitar();
            op1 = Output_Queue.quitar();
            op = Stack.quitar();
            op = creaSubArbol(op2, op1, op);
            Output_Queue.insertar(op);

        }
        op = Output_Queue.quitar();

        return op;
    }

    /**
     * Metodo que evalua el Arbol Binario de expresiones mandando la raiz del arbol al metodo evalua
     * @return Retorna un entero siendo el resultado de la evaluacion
     */
    public int evaluaExpresion(){
        return evalua(raiz);
    }

    /**
     * Este metodo recibe la raiz del arbol para irla evaluando poco a poco
     * @param subArbol variable tipo nodoarbol donde se almacena la raiz del subarbol analizado
     * @return Resultado entero del arbol de expresiones
     */
    private int evalua(NodoArbol subArbol){
        int acum=0;
        if(!esOperador(subArbol.dato.toString().charAt(0))){
            return Integer.parseInt(subArbol.dato.toString());
        }else {
            switch ((subArbol.dato.toString().charAt(0))) {
                case '^':
                    acum = acum + (int) Math.pow(evalua(subArbol.izquierdo), evalua(subArbol.derecho));
                    break;
                case '*':
                    acum = acum + evalua(subArbol.izquierdo) * evalua(subArbol.derecho);
                    break;
                case '/':
                    acum = acum + evalua(subArbol.izquierdo) / evalua(subArbol.derecho);
                    break;
                case '+':
                    acum = acum + evalua(subArbol.izquierdo) + evalua(subArbol.derecho);
                    break;
                case '-':
                    if (subArbol.izquierdo==null){
                        acum = -evalua(subArbol.derecho);
                    }else{
                        acum = acum + evalua(subArbol.izquierdo)-evalua(subArbol.derecho);
                    }
                    break;
                case '~':
                    if (subArbol.izquierdo==null){
                        acum = acum+(~(int)evalua(subArbol.derecho));
                    }else{
                        acum = acum + evalua(subArbol.izquierdo)*(~(int)evalua(subArbol.derecho));
                    }

                    break;
                case '%':
                    acum = acum + evalua(subArbol.izquierdo) % evalua(subArbol.derecho);
                    break;

                case '&':
                    acum = acum + ((int)evalua(subArbol.izquierdo) & (int)evalua(subArbol.derecho));
                    break;
                case '|':
                    acum = acum + ((int)evalua(subArbol.izquierdo) | (int)evalua(subArbol.derecho));
                    break;
                case 'x':
                    acum = acum + ((int)evalua(subArbol.izquierdo) ^ (int)evalua(subArbol.derecho));
                    break;
            }

        }
        return acum;
    }

}


