package com.luis.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.luis.cursomc.domain.Cliente;
import com.luis.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
