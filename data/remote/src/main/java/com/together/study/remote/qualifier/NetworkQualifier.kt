package com.together.study.remote.qualifier

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class JWT

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuth

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DUMMY
