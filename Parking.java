/**
 * La clase representa a un parking de una ciudad europea
 * que dispone de dos tarifas de aparcamiento para los clientes
 * que lo usen: la tarifa regular (que incluye una tarifa plana para
 * entradas "tempranas") y la tarifa comercial para clientes que trabajan
 * cerca del parking, aparcan un nº elevado de horas y se benefician de esta 
 * tarifa más económica
 * (leer enunciado)
 * @author David Sena
 */
public class Parking
{
    private final char REGULAR = 'R';
    private final char COMERCIAL = 'C';
    private final double PRECIO_BASE_REGULAR = 2.0;
    private final double PRECIO_MEDIA_REGULAR_HASTA11 = 3.0;
    private final double PRECIO_MEDIA_REGULAR_DESPUES11 =5.0;
    private final int HORA_INICIO_ENTRADA_TEMPRANA = 6 * 60;
    private final int HORA_FIN_ENTRADA_TEMPRANA = 8 * 60 + 30;
    private final int HORA_INICIO_SALIDA_TEMPRANA = 15 * 60;
    private final int HORA_FIN_SALIDA_TEMPRANA = 18 * 60;
    private final double PRECIO_TARIFA_PLANA_REGULAR = 15.0;
    private final double PRECIO_PRIMERAS3_COMERCIAL = 5.0;
    private final double PRECIO_MEDIA_COMERCIAL = 3.0;
    private String nombre;
    private double importeTotal;
    private int cliente;
    private int regular;
    private int comercial;
    private int clientesLunes;
    private int clientesSabado;
    private int clientesDomingo;
    private int clienteMaximoComercial;
    private double importeMaximoComercial;

    /**
     * Inicializa el parking con el nombre indicada por el parámetro.
     * El resto de atributos se inicializan a 0 
     */
    public Parking(String queNombre) {
        nombre = queNombre;
        cliente = 0;
        importeTotal = 0;
        regular = 0;
        comercial = 0;
        clientesLunes = 0;
        clientesSabado = 0;
        clientesDomingo = 0;
        clienteMaximoComercial = 0;
        importeMaximoComercial = 0;
    }

    /**
     * accesor para el nombre del parking
     *  
     */
    public String getNombre () {
        return nombre;
    }

    /**
     * mutador para el nombre del parking
     *  
     */
    public void setNombre (String queNombre) {
        nombre = queNombre;
    }

    /**
     *  Recibe cuatro parámetros que supondremos correctos:
     *    tipoTarifa - un carácter 'R' o 'C'
     *    entrada - hora de entrada al parking
     *    salida – hora de salida del parking
     *    dia – nº de día de la semana (un valor entre 1 y 7)
     *    
     *    A partir de estos parámetros el método debe calcular el importe
     *    a pagar por el cliente y mostrarlo en pantalla 
     *    y  actualizará adecuadamente el resto de atributos
     *    del parking para poder mostrar posteriormente (en otro método) las estadísticas
     *   
     *    Por simplicidad consideraremos que un cliente entra y sale en un mismo día
     *    
     *    (leer enunciado del ejercicio)
     */
    public void facturarCliente(char tipoTarifa, int entrada, int salida, int dia) {
        double importe = 0;
        int horaEntrada = (entrada / 100 );
        int horaSalida = (salida / 100);
        int salidaMin = salida % 100;
        int entradaMin = entrada % 100;
        int salidaEnMinutos = horaSalida * 60 + salidaMin;
        int entradaEnMinutos = horaEntrada * 60 + entradaMin;
        cliente ++;
        String tarifa = " ";
        int tiempoAparcado = salidaEnMinutos - entradaEnMinutos;

        switch (tipoTarifa){
            case 'R': 
            if((horaEntrada > HORA_INICIO_ENTRADA_TEMPRANA && horaEntrada < HORA_FIN_ENTRADA_TEMPRANA) &&
            (horaSalida > HORA_INICIO_SALIDA_TEMPRANA && horaSalida < HORA_FIN_SALIDA_TEMPRANA)){
                importe = PRECIO_TARIFA_PLANA_REGULAR;
                tarifa = "Regular";
            } 
            else{
                if(entrada < 1100){
                    if (salida > 1100){
                        importe = ((11 * 60 - horaEntrada) / 30 * PRECIO_MEDIA_REGULAR_HASTA11)
                        + ((horaSalida - 11 * 60) / 30 * PRECIO_MEDIA_REGULAR_DESPUES11);
                    }
                    else{
                        importe = tiempoAparcado / 30 * PRECIO_MEDIA_REGULAR_HASTA11;
                    }
                }
                else{
                    importe = tiempoAparcado / 30 * PRECIO_MEDIA_REGULAR_DESPUES11;
                }
                tarifa = "Regular";
            }
            regular ++;
            break;
            case 'C': 
            if( tiempoAparcado < 180){
                importe = PRECIO_PRIMERAS3_COMERCIAL;
                tarifa = "Comercial";
            }
            else{
                importe = (tiempoAparcado - 180) / 30 * PRECIO_MEDIA_COMERCIAL + PRECIO_PRIMERAS3_COMERCIAL;
                tarifa = "Comercial ";
            }
            comercial ++;
            break;
        }
        System.out.println("********************************************");
        System.out.println("Cliente nº: " + cliente);
        System.out.println("Hora de entrada: " + horaEntrada + ":" + entradaMin);
        System.out.println("Hora de salida: " + horaSalida + ":" + salidaMin);
        System.out.println("Tarifa a aplicar: " + tarifa);
        System.out.println("Importe a pagar: " + importe + "€");
        System.out.println("********************************************");

        importeTotal = importeTotal + importe;

        if (importeMaximoComercial < importe){
            importeMaximoComercial = importe;
            clienteMaximoComercial = cliente;
        }

        switch(dia){
            case 1:  clientesLunes ++;
            break;
            case 6:  clientesSabado ++;
            break;
            case 7:  clientesDomingo ++;
            break;
        }
    }

    /**
     * Muestra en pantalla las estadísticcas sobre el parking  
     *   
     * (leer enunciado)
     *  
     */
    public void printEstadísticas() {
        System.out.println("************************************************************************");
        System.out.println("  Importe total entre todos los clientes:  " + importeTotal + "€");
        System.out.println("  Nº de clientes con tarifa regular:  " + regular);
        System.out.println("  Nº de clientes con tarifa comercial:  " + comercial);
        System.out.println("  Cliente tarifa Comercial con factura máxima fue el nº " + clienteMaximoComercial +
            " y pagó " + importeMaximoComercial + "€");
        System.out.println("************************************************************************");

    }

    /**
     *  Calcula y devuelve un String que representa el nombre del día
     *  en el que más clientes han utilizado el parking - "SÁBADO"   "DOMINGO" o  "LUNES"
     */
    public String diaMayorNumeroClientes() {
        String dia = " ";
        if (clientesLunes > clientesSabado && clientesLunes >clientesDomingo){
            dia = "LUNES";
        }
        else if(clientesSabado > clientesDomingo && clientesSabado > clientesLunes){
            dia = "SÁBADO";
        }
        else if (clientesDomingo > clientesSabado && clientesDomingo > clientesLunes){
            dia = "DOMINGO";
        }
        else if(clientesDomingo == clientesSabado || clientesSabado == clientesLunes || clientesDomingo == clientesLunes){
            dia = "COINCIDEN";
        }
        return dia;
    }
}    