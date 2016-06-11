package com.training.service;

import java.util.concurrent.CompletableFuture;

import com.training.model.Greeting;

public interface EmailService {
	Boolean send(Greeting gretting);
	void sendAsync(Greeting greeting);
	CompletableFuture<Boolean> sendAsyncWithResultCF(Greeting greeting);
}
