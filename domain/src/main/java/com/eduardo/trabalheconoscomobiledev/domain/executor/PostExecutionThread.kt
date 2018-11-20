package com.eduardo.trabalheconoscomobiledev.domain.executor

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}