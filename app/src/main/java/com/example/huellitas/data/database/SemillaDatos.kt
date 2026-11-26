package com.example.huellitas.data.database

import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.huellitas.data.security.PasswordHasher

object SemillaDatos {

    fun insertar(db: SupportSQLiteDatabase) {
        val ahora = System.currentTimeMillis()

        db.execSQL(
            "INSERT INTO usuario (nombre, correo, telefono, contrasena_hash, rol, fecha_registro) " +
                "VALUES (?, ?, ?, ?, ?, ?)",
            arrayOf<Any?>(
                "Administrador", "admin@huellitas.org", "5512345678",
                PasswordHasher.hash("Admin123!"), "ADMIN", ahora
            )
        )

        val sql = "INSERT INTO perro (nombre, edad_meses, tamano, personalidad, historia, cuidados, estado, fecha_ingreso) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        val perros = listOf(
            arrayOf<Any?>("Luna", 24, "MEDIANO", "Cariñosa y tranquila",
                "Rescatada de la calle, ya convive con otros perros.", "Paseo diario y vacunas al corriente.", "DISPONIBLE", ahora),
            arrayOf<Any?>("Max", 8, "PEQUENO", "Juguetón y curioso",
                "Cachorro entregado por una familia que se mudó.", "Segunda dosis de vacunas pendiente.", "DISPONIBLE", ahora),
            arrayOf<Any?>("Milo", 48, "GRANDE", "Leal y protector",
                "Llegó con heridas leves, ya se recupera.", "Medicación y revisión semanal.", "EN_CUIDADO", ahora),
            arrayOf<Any?>("Nala", 12, "MEDIANO", "Activa y sociable",
                "Nació en el albergue y es muy cariñosa con los niños.", "Esterilización realizada.", "DISPONIBLE", ahora)
        )
        perros.forEach { db.execSQL(sql, it) }
    }
}
