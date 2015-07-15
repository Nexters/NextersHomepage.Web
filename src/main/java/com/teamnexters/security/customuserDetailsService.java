package com.teamnexters.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamnexters.dao.LoginDAO;
import com.teamnexters.dto.MemberDTO;

@Service
public class customuserDetailsService implements UserDetailsService {

	private static LoginDAO LoginDAO;
	private MemberDTO memDto;
	  
	
	public void setLoginDAO(LoginDAO loginDAO) {
		LoginDAO = loginDAO;
	}
	
	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		memDto = (MemberDTO) LoginDAO.searchByUserName(userName);
		if(memDto==null) throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
		return setUserStatus(memDto.getUserId(), memDto.getUserPw(), memDto.getUserRole(), memDto.getUserStatus());
	}
	
	/**
	 * Set User Status on userDetails
	 * @param userName 유저 아이디
	 * @param userPw 유저 패스워드
	 * @param userRole 유저 권한(1 : 어드민, 2 : 유저)
	 * @param userStatus 유저 상태 (1 : 비활성화 계정, 2 : 계정 만료, 3 : 패스워드 만료, 4 : 계정 잠금)
	 * @return UserDetails
	 */
	private UserDetails setUserStatus(String userName, String userPw, int userRole, int userStatus) {
		switch(userStatus) {
		case 1:
			return new User(userName, userPw, false, true, true, true, getAuthorities(userRole));
		case 2:
			return new User(userName, userPw, true, false, true, true, getAuthorities(userRole));
		case 3:
			return new User(userName, userPw, true, true, false, true, getAuthorities(userRole));
		case 4:
			return new User(userName, userPw, true, true, true, false, getAuthorities(userRole));
		default:
			return new User(userName, userPw, getAuthorities(userRole));
		}
	}
	
	
	/**
	 * Retrieves a collection of {@link GrantedAuthority} based on a numerical role
	 * @param role the numerical role
	 * @return a collection of {@link GrantedAuthority
	 */
	private Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}
	
	/**
	 * Converts a numerical role to an equivalent list of roles
	 * @param role the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	private List<String> getRoles(Integer role) {
		List<String> roles = new ArrayList<String>();
		
		if (role.intValue() == 1) {
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");
		} else if (role.intValue() == 2) {
			roles.add("ROLE_USER");
		}
		
		return roles;
	}
	
	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	private static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
}
