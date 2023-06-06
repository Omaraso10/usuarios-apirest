package com.oalvarez.backend.usuario.apirest.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    public static boolean isValidPassword(String password) {
        //Para configurar se debe agregar o quitar las siguientes condiciones solicitadas en la password
        String regex = "^(?=.*[0-9])"//al menos un 1 digito númerico
                + "(?=.*[a-z])" //Al menos una minúscula
                //+ "(?=.*[A-Z])"//Al menos una mayúscula
                //+ "(?=.*[@#$%^&+=])" //Caracter especial
                + "(?=\\S+$).{6,20}$"; //Largo de 6 a 20

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
