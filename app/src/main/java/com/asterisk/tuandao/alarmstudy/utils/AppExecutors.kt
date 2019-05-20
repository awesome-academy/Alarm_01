package com.asterisk.tuandao.alarmstudy.utils

class AppExecutors(
    val diskIO: DiskIOThreadExecutor = DiskIOThreadExecutor(),
    val mainThread: MainThreadExecutor = MainThreadExecutor()
)
