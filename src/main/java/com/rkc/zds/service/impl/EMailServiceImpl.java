package com.rkc.zds.service.impl;

import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rkc.zds.email.SendMailUsingAuthentication;
import com.rkc.zds.entity.ContactEntity;
import com.rkc.zds.entity.EMailEntity;
import com.rkc.zds.model.EMailSend;
import com.rkc.zds.repository.EMailRepository;
import com.rkc.zds.service.EMailService;

@Service
public class EMailServiceImpl implements EMailService {

	@Autowired
	private EMailRepository eMailRepo;

	@Override
	public Page<EMailEntity> findEMails(Pageable pageable, int contactId) {

		Page<EMailEntity> page = eMailRepo.findByContactId(pageable, contactId);

		return page;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void saveEMail(EMailEntity email) {

		eMailRepo.save(email);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void updateEMail(EMailEntity email) {

		eMailRepo.saveAndFlush(email);

	}

	@Override
	public void deleteEMail(int id) {

		eMailRepo.deleteById(id);

	}

	@Override
	public EMailEntity getEMail(int id) {

		Optional<EMailEntity> email = eMailRepo.findById(id);
		if (email.isPresent())
			return email.get();
		else
			return null;
	}

	@Override
	public void sendEMail(EMailSend emailSend) {
		SendMailUsingAuthentication smtpMailSender = new SendMailUsingAuthentication();
		try {
			String[] array = emailSend.getEmailList().split(",");
			smtpMailSender.postMail(array, emailSend.getEmailSubjectTxt(), emailSend.getEmailMsgTxt(),
					emailSend.getEmailFromAddress());

		} catch (MessagingException e) {
			e.printStackTrace();

		}
	}

}
