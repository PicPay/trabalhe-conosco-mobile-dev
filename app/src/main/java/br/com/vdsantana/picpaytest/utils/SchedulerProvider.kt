package br.com.vdsantana.picpaytest.utils

import io.reactivex.Scheduler

/**
 * Created by vd_sa on 30/03/2018.
 */
interface SchedulerProvider {
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
}