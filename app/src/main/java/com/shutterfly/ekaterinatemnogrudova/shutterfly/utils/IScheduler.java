package com.shutterfly.ekaterinatemnogrudova.shutterfly.utils;

import io.reactivex.Scheduler;

public interface IScheduler {

    Scheduler io();
    Scheduler ui();
    Scheduler computation();

}