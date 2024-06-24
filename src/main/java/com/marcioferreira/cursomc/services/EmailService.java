package com.marcioferreira.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.marcioferreira.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}