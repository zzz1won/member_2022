package com.icia.member;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService ms;

    @Test
    @DisplayName("회원데이터 생성")
    public void newMembers() {
        IntStream.rangeClosed(1, 15).forEach(i -> {
            ms.save(new MemberSaveDTO("email" + i, "pw" + i, "name" + i));
        });
    }


    /* 회원삭제 테스트코드를 만들어봅시다! 회원삭제 시나리오를 작성해보고 코드로 짜보도록, 11:15까지
     */

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원삭제 테스트")
    public void delMember() {

        /*
        1. given : 테스트 조건
        2. when : 테스트 수행
        3. then : 테스트 결과 검증
         */

        //회원 가입 -> 목록출력 후 삭제를 눌렀을 때 memberId = null이면 성공?
        //given
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("삭제용회원이메일1", "삭제용회원비밀번호1", "삭제용회원이름1");
        Long memberId = ms.save(memberSaveDTO);
        System.out.println(ms.findById(memberId)); // id가 잘 들어갔는지 확인...
        //when
        ms.deleteById(memberId);    //삭제를 했으니 (삭제 된 id)를 조회할 수 없음...
        /*System.out.println(ms.findById(memberId));*/

        //then
        //삭제한 회원의 id로 조회를 시도했을 때 null이어야 테스트 통과
        // NoSuchElementException은 무시하고, 테스트를 수행한다.
        assertThrows(NoSuchElementException.class, () -> {
            assertThat(ms.findById(memberId)).isNull(); // 삭제회원 id 조회결과가 null이면 테스트 통과
        });

    }


    //0111 수정테스트코드 짜보기
    /*
    회원가입 시키고 그 회원의 id를 sout으로 찍어본다.
    update 실행...
    id가 있으면 update 실행...
     */
    @Test
    @Transactional
    @Rollback
    @DisplayName("업데이트 테스트코드")
    public void updateTest()    {

        //given 기본 테스트조건 생성

        String memberEmail = "수정메일1";
        String memberPassword = "수정비번1";
        String memberName = "수정이름1";

        MemberSaveDTO memberSaveDTO = new MemberSaveDTO(memberEmail, memberPassword,memberName);
        Long memberId = ms.save(memberSaveDTO);
        System.out.println(ms.findById(memberId));

        //when 테스트 수행
        /*ms.findById(memberId); //아이디를 찾아서 수정을 해줘야겠지...*/
        /*ms.findById(memberId).getMemberName();*/
        String updateName = "수정용이름1";
        String saveMemberName = ms.findById(memberId).getMemberName();
        MemberDetailDTO updateMember = new MemberDetailDTO(memberId, memberEmail, memberPassword, updateName);
        ms.update(updateMember);
        //수정 후 DB에서 이름 조회
        String updateMemberName = ms.findById(memberId).getMemberName();


        //then 테스트 확인
        // 이름만 수정이 가능한 상황. 업데이트 전과 후의 데이터를 비교한다.
        assertThat(saveMemberName).isNotEqualTo(updateMemberName);

    }

}
