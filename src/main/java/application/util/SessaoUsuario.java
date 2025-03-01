package application.util;

import application.model.Usuario;

public class SessaoUsuario {
    private static Usuario usuarioLogado;

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void limparSessao() {
        usuarioLogado = null;
    }
}
