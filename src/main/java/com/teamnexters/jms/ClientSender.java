package com.teamnexters.jms;

import com.teamnexters.dto.MemberDTO;


public interface ClientSender {
	public void sendInfo(final MemberDTO member);
}
