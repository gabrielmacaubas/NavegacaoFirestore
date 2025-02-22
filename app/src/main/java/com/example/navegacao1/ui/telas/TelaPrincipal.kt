package com.example.navegacao1.ui.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navegacao1.model.dados.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun TelaPrincipal(modifier: Modifier = Modifier, onLogoffClick: () -> Unit) {
    var scope = rememberCoroutineScope()

    Column(modifier = modifier) {
        Text(text = "Tela Principal")
        val usuarios = remember { mutableStateListOf<Usuario>() }

        Button(onClick = {
            scope.launch(Dispatchers.IO) {
                usuarioDAO.buscar( callback = { usuariosRetornados ->
                    usuarios.clear()
                    usuarios.addAll(usuariosRetornados)
                })
            }
        }) {
            Text("Carregar")
        }
        Button(onClick = { onLogoffClick() }) {
            Text("Sair")
        }

        Text(
            text = "Usuários",
            textAlign = TextAlign.Center,
            fontSize = 64.sp
        )

        //Carrega sob demanda à medida que o usuário rola na tela
        LazyColumn(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(usuarios) { usuario ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(text = usuario.nome, style = MaterialTheme.typography.headlineLarge)
                    }
                }
            }
        }

    }

}