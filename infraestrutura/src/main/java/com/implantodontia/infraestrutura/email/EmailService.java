package com.implantodontia.infraestrutura.email;

import com.implantodontia.dominio.support.notificacoes.Notificacao;
import com.implantodontia.dominio.support.notificacoes.NotificacaoObserver;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements NotificacaoObserver {

    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailSender;



    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void atualizarNotificacao(Notificacao notificacao) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailSender);
        message.setTo(notificacao.getDestinatario());
        message.setSubject("Pagamento não recebido");
        message.setText(notificacao.getMensagem());

        mailSender.send(message);
    }

    @Override
    public TipoNotificacao getTipoNotificacao() {
        return TipoNotificacao.PAGAMENTO;
    }
}
