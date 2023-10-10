public class ArbolBinarioExpresiones {
    NodoArbol raiz;
    public ArbolBinarioExpresiones(){
        raiz=null;
    }
    public ArbolBinarioExpresiones(String cadena){
        raiz=creaArbolBE(cadena);//Este metodo crea el arbol de expresiones
    }
    public void reiniciaArbol(){
        raiz=null;
    }
    public void creaNodo(Object dato){
        raiz=new NodoArbol(dato);
    }
    public NodoArbol creaSubArbol(NodoArbol dato2,NodoArbol dato1,NodoArbol operador){
        operador.izquierdo=dato1;
        operador.derecho=dato2;
        return operador;
    }
    public boolean arbolVacio(){
        return raiz==null;
    }
    private String preorden(NodoArbol subArbol, String c){
        String cadena;
        cadena="";
        if (subArbol !=null){
            cadena = c + subArbol.dato.toString()+"\n"+preorden(subArbol.izquierdo,c)+preorden(subArbol.derecho,c);
        }
        return cadena;
    }
    private String inorden(NodoArbol subArbol, String c) {
        String cadena;
        cadena = "";
        if (subArbol != null) {
            cadena = c + inorden(subArbol.izquierdo, c) + subArbol.dato.toString() + "\n" + inorden(subArbol.derecho, c);
        }
        return cadena;
    }
    private String posorden(NodoArbol subArbol, String c) {
        String cadena;
        cadena = "";
        if (subArbol != null) {
            cadena = c + posorden(subArbol.izquierdo, c) + posorden(subArbol.derecho, c) + subArbol.dato.toString() + "  ";
        }
        return cadena;
    }
    public String toString(int a){
        String cadena="";
        switch (a){
            case 0:
                cadena=preorden(raiz,cadena);
                break;
            case 1:
                cadena= inorden(raiz,cadena);
                break;
            case 2:
                cadena = posorden(raiz,cadena);
                break;
        }
        return cadena;
    }
    private int prioridad(char c){
        int p=100;
        switch (c){
            case '~':
            case '^':
                p=40;
                break;
            case '*':
            case '/':
            case '%':
            case '&':
                p=30;
                break;
            case '+':
            case '-':
            case '|':
                p=20;
                break;
            case 'x':
                p=10;
                break;
            default://El caso de los parentesis
                p=0;
        }
        return p;
    }
    private boolean esOperador (char c){
        boolean resultado;
        switch (c){
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


    private String numerocompleto(String cadena, int pos){
        String Numerocompleto="";
        char caracterEvaluado;
        for (int i=pos; i<cadena.length();i++){
            caracterEvaluado =cadena.charAt(i);
            if (!esOperador(caracterEvaluado)){
                Numerocompleto=Numerocompleto+caracterEvaluado;
            }else{
                break;
            }
        }
        return Numerocompleto;
    }
    private NodoArbol creaArbolBE (String cadena){
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
        for (int i=0; i<cadena.length();i++){
            caracterEvaluado =cadena.charAt(i);
            if(!esOperador(caracterEvaluado)){
                Operando= numerocompleto(cadena,i);
                ajuste=Operando.length();
                i=i+ajuste-1;
                token=new NodoArbol(Operando);
                Output_Queue.insertar(token);
                //Si es un operando se ingresa en la lista enlazada de operandos
            }
            else {   //Es un operador
                token=new NodoArbol(caracterEvaluado);
                switch (caracterEvaluado){
                    case'(':
                        Stack.insertar(token);
                        break;
                    case')':
                        while (!Stack.pilaVacia() && !Stack.topePila().dato.equals('(')){
                            op2=Output_Queue.quitar();
                            op1=Output_Queue.quitar();
                            op =Stack.quitar();
                            System.out.println(op2);
                            op=creaSubArbol(op2,op1,op);
                            Output_Queue.insertar(op);
                        }
                        Stack.quitar();
                        break;
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
        }//Lee el siguiente caracter
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
    public double EvaluaExpresion(){
        return evalua(raiz);
    }
    public boolean EvaluaExpresionBoolean(){
        return evaluabooleano(raiz);
    }
    private double evalua(NodoArbol subArbol){
        double acum=0;
        if(!esOperador(subArbol.dato.toString().charAt(0))){
            return Double.parseDouble(subArbol.dato.toString());
        }else {
            switch ((subArbol.dato.toString().charAt(0))) {
                case '^':
                    acum = acum + Math.pow(evalua(subArbol.izquierdo), evalua(subArbol.derecho));
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
                    acum = acum + evalua(subArbol.izquierdo) - evalua(subArbol.derecho);
                    break;
                case '%':
                    acum = acum + evalua(subArbol.izquierdo) % evalua(subArbol.derecho);
                    break;
                case '&':
                    acum = acum + evalua(subArbol.izquierdo) - evalua(subArbol.derecho);
                    break;
            }

        }
        return acum;
    }
    private boolean evaluabooleano(NodoArbol subArbol){
        boolean acum=true;
        if(!esOperador(subArbol.dato.toString().charAt(0))){
            switch ((subArbol.dato.toString().charAt(0))) {
                case 'T':
                    return true;

                case 'F':
                    return false;
            }
        }else {
            switch ((subArbol.dato.toString().charAt(0))) {
                case '~':
                    acum = acum&(!evaluabooleano(subArbol.derecho));
                    break;
                case '&':
                    acum =  acum&(evaluabooleano(subArbol.izquierdo) && evaluabooleano(subArbol.derecho));
                    break;
                case '|':
                    acum = acum&(evaluabooleano(subArbol.izquierdo) || evaluabooleano(subArbol.derecho));
                    break;
                case 'x':
                    acum = acum&(evaluabooleano(subArbol.izquierdo) ^ evaluabooleano(subArbol.derecho));
                    break;
            }
        }
        return acum;
    }

}

