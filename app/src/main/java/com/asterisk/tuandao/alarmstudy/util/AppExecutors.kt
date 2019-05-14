package com.asterisk.tuandao.alarmstudy.util

class AppExecutors(
    val diskIO: DiskIOThreadExecutor = DiskIOThreadExecutor(),
    val mainThread: MainThreadExecutor = MainThreadExecutor()
)
