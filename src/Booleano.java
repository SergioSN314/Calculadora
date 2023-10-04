 class Booleano extends ExpresionBooleana {
    private boolean valor;
    Booleano(boolean valor){
        this.valor=valor;
    }
    boolean evaluar(){
        return valor;
    }
}