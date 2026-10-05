package com.example.proyecto6

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.proyecto6.data.local.AppDatabase
import com.example.proyecto6.data.local.Transaccion
import com.example.proyecto6.data.repository.WalletRepository
import com.example.proyecto6.viewmodel.WalletViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "alke_wallet.db"
        )
            .fallbackToDestructiveMigration()
            .build()

        val repository = WalletRepository(
            database.usuarioDao(),
            database.transaccionDao()
        )

        viewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return WalletViewModel(repository) as T
                }
            }
        )[WalletViewModel::class.java]

        mostrarLogin()
    }

    private fun mostrarLogin() {
        setContentView(R.layout.activity_main)

        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val login = findViewById<Button>(R.id.btnLogin)
        val register = findViewById<Button>(R.id.btnRegister)
        val message = findViewById<TextView>(R.id.tvMessage)

        register.setOnClickListener {
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString()

            if (emailText.isEmpty() || passwordText.isEmpty()) {
                message.text = "Completa correo y contraseña"
                return@setOnClickListener
            }

            viewModel.registrarUsuario(
                nombre = "Usuario Alke",
                email = emailText,
                password = passwordText
            )
        }

        login.setOnClickListener {
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString()

            if (emailText.isEmpty() || passwordText.isEmpty()) {
                message.text = "Completa correo y contraseña"
                return@setOnClickListener
            }

            viewModel.iniciarSesion(
                email = emailText,
                password = passwordText
            )
        }

        lifecycleScope.launch {
            viewModel.mensaje.collect { mensaje ->
                if (mensaje.isNotEmpty()) {
                    message.text = mensaje

                    if (mensaje == "Inicio de sesión correcto") {
                        mostrarWallet()
                    }
                }
            }
        }
    }

    private fun mostrarWallet() {
        setContentView(R.layout.wallet_home)

        val welcome = findViewById<TextView>(R.id.tvWelcome)
        val transactions = findViewById<TextView>(R.id.tvTransactions)
        val amount = findViewById<EditText>(R.id.etAmount)
        val description = findViewById<EditText>(R.id.etDescription)

        val sendMoney = findViewById<Button>(R.id.btnSendMoney)
        val requestMoney = findViewById<Button>(R.id.btnRequestMoney)
        val profile = findViewById<Button>(R.id.btnProfile)
        val logout = findViewById<Button>(R.id.btnLogout)

        welcome.text = "Bienvenido a Alke Wallet"

        viewModel.cargarTransacciones()

        lifecycleScope.launch {
            viewModel.transacciones.collect { lista ->
                if (lista.isEmpty()) {
                    transactions.text = "No hay transacciones"
                } else {
                    transactions.text = lista.joinToString("\n\n") {
                        "${it.fecha} - ${it.tipo}\n$${it.monto}\n${it.descripcion}"
                    }
                }
            }
        }

        sendMoney.setOnClickListener {
            guardarNuevaTransaccion(
                amount,
                description,
                "Envío"
            )
        }

        requestMoney.setOnClickListener {
            guardarNuevaTransaccion(
                amount,
                description,
                "Solicitud"
            )
        }

        profile.setOnClickListener {
            mostrarPerfil()
        }

        logout.setOnClickListener {
            mostrarLogin()
        }
    }

    private fun mostrarPerfil() {
        setContentView(R.layout.profile)

        val profileImage = findViewById<ImageView>(R.id.ivProfile)
        val profileName = findViewById<TextView>(R.id.tvProfileName)
        val profileEmail = findViewById<TextView>(R.id.tvProfileEmail)
        val back = findViewById<Button>(R.id.btnBack)

        profileName.text = viewModel.usuario.value?.nombre
            ?: "Usuario Alke"

        profileEmail.text = viewModel.usuario.value?.email
            ?: "usuario@email.com"

        Picasso.get()
            .load("https://i.pravatar.cc/300")
            .placeholder(android.R.drawable.ic_menu_myplaces)
            .error(android.R.drawable.ic_menu_myplaces)
            .into(profileImage)

        back.setOnClickListener {
            mostrarWallet()
        }
    }

    private fun guardarNuevaTransaccion(
        amount: EditText,
        description: EditText,
        tipo: String
    ) {
        val monto = amount.text.toString().toDoubleOrNull()

        if (monto == null || monto <= 0) {
            amount.error = "Ingresa un monto válido"
            return
        }

        val fecha = SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale.getDefault()
        ).format(Date())

        viewModel.guardarTransaccion(
            Transaccion(
                fecha = fecha,
                monto = monto,
                descripcion = description.text.toString().ifEmpty {
                    "Sin descripción"
                },
                tipo = tipo
            )
        )

        amount.text.clear()
        description.text.clear()
    }
}
