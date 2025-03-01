package application.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Criptografia {

    public static String hashSenha(String senha) {
        return BCrypt.withDefaults().hashToString(12, senha.toCharArray());
    }

    public static boolean verificarSenha(String senha, String hash) {
        return BCrypt.verifyer().verify(senha.toCharArray(), hash).verified;
    }
}
