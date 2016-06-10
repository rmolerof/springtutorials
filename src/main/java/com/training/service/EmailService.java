package com.training.service;

import com.training.model.Greeting;
import com.training.util.AsyncResponse;

public interface EmailService {
	Boolean send(Greeting gretting);
	void sendAsync(Greeting greeting);
	AsyncResponse<Boolean> sendAsyncWithResult(Greeting greeting);
}
