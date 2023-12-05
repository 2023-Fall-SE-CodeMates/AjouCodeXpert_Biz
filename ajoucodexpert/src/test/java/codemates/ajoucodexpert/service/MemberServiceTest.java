package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Authority;
import codemates.ajoucodexpert.domain.Major;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.domain.StudentInfo;
import codemates.ajoucodexpert.dto.MemberDto;
import codemates.ajoucodexpert.repository.MemberRepository;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@WebAppConfiguration
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    MemberService memberService;

    MemberFactory mf;

    @BeforeEach
    public void setUp() {
        memberService = new MemberServiceImpl(memberRepository);
        mf = memberService.getMemberFactory();
    }

    @Test
    @DisplayName("회원 생성 테스트")
    void createMember() {
        System.out.println("테스트 실행");
        // given
        MemberDto.Signup signupDto = MemberDto.Signup.builder()
                .id("testUser001")
                .pw("testUser001")
                .name("홍길동")
                .roleCode(1)
                .majorCode("001")
                .studentId("201820807")
                .build();
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority(1, "ROLE_USER"));
        StudentInfo studentInfo = new StudentInfo("201820807", "홍길동");
        Major major = new Major("001", "컴퓨터공학과");

        // when
        Member member = mf.createMember(signupDto, authorities, studentInfo, major);

        // then
        Assertions.assertAll(
                () -> assertEquals(member.getLoginId(), signupDto.getId()),
                () -> assertEquals(member.getPw(), signupDto.getPw()),
                () -> assertEquals(member.getName(), signupDto.getName()),
                () -> assertEquals(member.getMajor().getCode(), signupDto.getMajorCode()),
                () -> assertEquals(member.getStudentInfo().getCode(), signupDto.getStudentId())
        );

    }
}