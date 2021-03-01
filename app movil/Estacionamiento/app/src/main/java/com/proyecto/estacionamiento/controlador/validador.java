package com.proyecto.estacionamiento.controlador;

public class validador {

    public boolean verificaCedula(String cedula) {
        int tamaño = cedula.length();
        int suma = 0, inmediatosuperior, resp, digit;
        double inm;
        try {
            if (tamaño == 10) {
                for (int i = 0; i < 9; i++) {
                    int mult;
                    if (i % 2 == 0) {
                        mult = (Integer.valueOf(cedula.substring(i, i + 1))) * 2;
                        if (mult > 9) {
                            mult = mult - 9;
                        }
                        suma += mult;
                    } else {
                        suma += Integer.valueOf(cedula.substring(i, i + 1));
                    }
                }
                inm = (double) suma / 10;
                digit = suma % 10;

                if (digit > 0) {
                    inmediatosuperior = (int) (inm + 1) * 10;
                } else {
                    inmediatosuperior = (int) inm * 10;
                }

//                System.out.println("Inmediatodividido: " + inm);
//                inmediatosuperior = (int) Math.ceil(inm) * 10;
                resp = inmediatosuperior - suma;
                if (resp == Integer.valueOf(cedula.substring(9))) {
                    System.out.println("Cedula Correcta");
                    return true;
                } else {
                    System.out.println("Cedula Incorrecta");
                }
            } else {
                System.out.println("Ingrese un número de 10 digitos");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error " + e);
        }
        return false;
    }

}
