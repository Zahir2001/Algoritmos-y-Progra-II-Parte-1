import java.util.Scanner;

public class Simpletron
{
    private int[] memoria = new int[100];
    private Procesador procesador;
    private static  Scanner scanner = new Scanner(System.in);
    private static boolean prendida;
    private static boolean modoInteractivo = false;
    private static boolean modoDictado = false;
    private static int ordenPararInteractiva = 9999;

    Simpletron() {
        prender();
        setModoDeUso();
    }

void correr() {
    if(!modoInteractivo && !modoDictado) {
        procesador.procesarMemoria();
        memoria = procesador.getMemoriaProcesada();
    }
    else if(modoDictado) {
        dictar();
    }
    else {
        interpreteDeOrdenes();
    }
}

void cargarPrograma(int[] programa) {
    if(!modoInteractivo) {
        System.arraycopy(programa, 0, memoria, 0, programa.length);
    }
    procesador = new Procesador(memoria);
}

private void interpreteDeOrdenes() {
    System.out.print("... ");
    int orden = getEntrada();
    while(true) {
        if(orden == ordenPararInteractiva) {
            break;
        }
        procesador.darOrden(orden);
        System.out.print("... ");
        orden = getEntrada();
    }
    Simpletron.apagar();
}

private void dictar() { procesador.dictar(); }

static int getEntrada() { return scanner.nextInt(); }

static int getInstruccion() {
    int instruc = getEntrada();
    if(!isInstruccionValida(instruc)) {
        System.out.println("Instrucción inválida.");
        getInstruccion();
    }
    return instruc;
}

static boolean isInstruccionValida(int instruccion) {
    return Integer.toString(instruccion).length() == 4 || instruccion == 0;
}

private static void prender() {
    prendida = true;
    System.out.println("SIMPLETON ON");
}

static void apagar() {
    System.out.println("SIMPLETON OFF");
    prendida = false;
}

static boolean isPrendida() { return prendida;}

    private static void usarModoInteractivo() { modoInteractivo = true; }

    private static void setModoDeUso() {
        System.out.println("*** Bienvenido a Simpletron! ***");
        System.out.println("*** Por favor, introduzca en su programa una instruccion ***");
        System.out.println("*** (o palabra de datos) a la vez. Yo le mostrare ***");
        System.out.println("*** el numero de ubicacion y un signo de interrogacion (?) ***");
        System.out.println("*** Entonces usted escribira la palabra para esa ubicacion. ***");
        System.out.println("*** Teclee 9999 para dejar de introducir su programa. ***");
        System.out.println("Modo de uso: ");
        String modoDeUso = scanner.next();
        switch(modoDeUso) {
            case "interactivo":
                usarModoInteractivo();
                System.out.println("MODO INTERACTIVO");
                System.out.printf("Introduzca \"%d\" para salir", ordenPararInteractiva);
                break;
            case "lector":
                System.out.println("MODO LECTOR");
                break;
            case "dictado":
                System.out.println("MODO DICTADO");
                modoDictado = true;
                break;
            default:
                System.out.println("Modo de uso inválido");
                setModoDeUso();
        }
    }
}