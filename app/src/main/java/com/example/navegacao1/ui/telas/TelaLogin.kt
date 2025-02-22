package com.example.navegacao1.ui.telas

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navegacao1.model.dados.UsuarioDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val usuarioDAO: UsuarioDAO = UsuarioDAO()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin(modifier: Modifier = Modifier, onSigninClick: () -> Unit ,  onSignupClick: () -> Unit ) {
    val context = LocalContext.current
    var scope = rememberCoroutineScope()

    var login by remember {mutableStateOf("")}
    var senha by remember {mutableStateOf("")}
    var mensagemErro by remember { mutableStateOf<String?>(null) }
    var mensagemSucesso by remember { mutableStateOf<String?>(null) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxWidth(0.75F)) {
        Text(
            text = "Login",
            fontSize = 64.sp,
            textAlign = TextAlign.Center
        )
        OutlinedTextField(value = login, onValueChange = {login = it}, label = { Text(text = "Login")})
        Spacer(modifier =  Modifier.height(10.dp))
        OutlinedTextField(value = senha, visualTransformation = PasswordVisualTransformation(),
            onValueChange = {senha = it}, label = { Text(text = "Senha")})
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            scope.launch(Dispatchers.IO) {
                usuarioDAO.buscarPorNome(login, callback = { usuario ->
                    if (usuario != null && usuario.senha == senha) {
                        mensagemSucesso = "Login feito com sucesso!"
                        onSigninClick()
                    } else {
                        mensagemErro = "Login ou senha inválidos!"
                    }
                })
            }
        }) {
            Text("Entrar")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            onSignupClick()
        }) {
            Text("Cadastrar")
        }

        mensagemErro?.let {
            LaunchedEffect(it) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                mensagemErro = null
            }
        }
        mensagemSucesso?.let {
            LaunchedEffect(it) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                mensagemSucesso = null
            }
        }
    }

}