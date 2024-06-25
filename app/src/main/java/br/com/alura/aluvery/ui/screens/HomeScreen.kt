package br.com.alura.aluvery.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.aluvery.model.Product
import br.com.alura.aluvery.sampledata.sampleProducts
import br.com.alura.aluvery.sampledata.sampleSections
import br.com.alura.aluvery.ui.components.CardProductItem
import br.com.alura.aluvery.ui.components.ProductsSection
import br.com.alura.aluvery.ui.theme.AluveryTheme

@Composable
fun HomeScreen(
    sections: Map<String, List<Product>>,
    searchText: String = ""
) {
    Column {
        //a tela é redesenhada a cada interação, é preciso controlar o estado (Equivalente ao onpress do JavaScript)
        var text by remember { mutableStateOf(searchText) }
        val sampleFiltrado = remember() {
          sampleProducts.filter { Product ->
            return@filter Product.name.contains(searchText, ignoreCase = true) ||
              Product.description?.contains(searchText, ignoreCase = true) ?: false

          }
        }
        //OnValue change notifica se o valor é modificado
        OutlinedTextField(
            value = text,
            onValueChange = {newValue ->
            text = newValue
            Log.i("HomeScreen", "HOmeScreen TextField: $newValue ")
        } ,

          Modifier
            .padding(16.dp)
            .fillMaxWidth(),
            shape = RoundedCornerShape(75),
            leadingIcon = { Icon(Icons.Default.Search , contentDescription = "") },
            label = {Text(text = "O que você procura")}
        )

    LazyColumn(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
       item {  Spacer(Modifier) }
      if(text.isBlank())
      {
        for (section in sections) {
          item {
            val title = section.key
            val products = section.value
            ProductsSection(
              title = title,
              products = products
            )}
        }
      }
      else{ items(sampleFiltrado)
        {
            p -> CardProductItem(
           product = p,
           modifier = Modifier.padding(horizontal = 16.dp),
       ) }

      }

        item {  Spacer(Modifier) }
    }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    AluveryTheme {
        Surface {
           HomeScreen(sampleSections)
        }
    }
}

@Preview
@Composable
private fun HomeScreenPrevieBUSCA() {
  AluveryTheme {
    Surface {
      HomeScreen(sampleSections, "a")
    }
  }
}

