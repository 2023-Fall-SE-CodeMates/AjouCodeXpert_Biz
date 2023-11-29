package codemates.ajoucodexpert.security.principalUser;

import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.domain.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        return memberRepository.findById(loginId)
                .map(member -> createUser(loginId, member))
                .orElseThrow(() -> new UsernameNotFoundException("User not found in DB."));
    }

    private User createUser(String loginId, Member member) {
        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> (GrantedAuthority) authority::getName)
                .toList();

        return new User(loginId,
                member.getPw(),
                grantedAuthorities);
    }
}
