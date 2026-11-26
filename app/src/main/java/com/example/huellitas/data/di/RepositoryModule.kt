package com.example.huellitas.data.di

import com.example.huellitas.data.repository.PerroRepositoryImp
import com.example.huellitas.data.repository.UsuarioRepositoryImp
import com.example.huellitas.domain.repository.PerroRepository
import com.example.huellitas.domain.repository.UsuarioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPerroRepository(perroRepository: PerroRepositoryImp): PerroRepository

    @Binds
    @Singleton
    abstract fun bindUsuarioRepository(usuarioRepository: UsuarioRepositoryImp): UsuarioRepository
}
