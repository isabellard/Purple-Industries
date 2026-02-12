package co.edu.unbosque.purpleindustries.util;

public class ConversorAltura {

    public static String metrosTexto(double metros) {
        return redondear(metros) + " m";
    }

    public static String cmTexto(double metros) {
        return redondear(metros * 100) + " cm";
    }

    public static String dmTexto(double metros) {
        return redondear(metros * 10) + " dm";
    }

    public static String piesTexto(double metros) {
        return redondear(metros / 0.3048) + " ft";
    }

    public static String codosTexto(double metros) {
        return redondear(metros / 0.4572) + " codo";
    }

    private static double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
