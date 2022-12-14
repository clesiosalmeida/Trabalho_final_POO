package br.com.newchatproject.controllers;

import java.sql.SQLException;

import br.com.newchatproject.model.Signup;
import br.com.newchatproject.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class cadastroCt {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignup;

    @FXML
    private PasswordField txtConfirmaSenha;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    @FXML
    private PasswordField txtSenha;

    
    @FXML
    private void initialize() {
        Main.addOnChangeScreenListener(new Main.OnChangeScreen() {

            @Override
            public void onScreenChanged(String newScreen, Usuario userData) {
                if (newScreen.equals("add")) {
                    txtNome.setText("");
                    txtEmail.setText("");
                    txtSenha.setText("");
                    txtConfirmaSenha.setText("");
                }
            }
        });
    }


    @FXML
    void retornar(ActionEvent event) {
        Main.changeScreen("acessarConta", null);
    }

    @FXML
    void signup(ActionEvent event) throws SQLException{
        if (this.txtNome.getText().isEmpty() || this.txtEmail.getText().isEmpty() || this.txtSenha.getText().isEmpty()
                || this.txtConfirmaSenha.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Preencha todos os dados antes de prosseguir!");
            alert.showAndWait();
        } else {
            Signup novoUsuario = new Signup(this.txtNome.getText(), this.txtEmail.getText(), this.txtSenha.getText());

            boolean confereSenha = novoUsuario.verificaSenha(this.txtConfirmaSenha.getText());
            boolean emailEmUso = novoUsuario.verificaEmail();

            if (emailEmUso) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Email em uso!");
                alert.setHeaderText("Email j?? cadastrado, efetue o Login!!");
                alert.showAndWait();
                Main.changeScreen("login", null);
            } else {
                if (!confereSenha) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Error!");
                    alert.setHeaderText("As senhas digitadas n??o conferem!");
                    alert.showAndWait();
                    this.txtSenha.setText("");
                    this.txtConfirmaSenha.setText("");
                } else {
                    novoUsuario.insereUsuario();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Usu??rio cadastrado com sucesso!");
                    alert.setContentText("Agora ?? s?? fazer o Login e interagir com v??rios usu??rios!");
                    alert.showAndWait();
                    Main.changeScreen("login", null);
                }
            }
        }

    }

}



