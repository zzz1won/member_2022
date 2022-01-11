package com.icia.member.entity;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.lang.reflect.Member;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
    //우선은 entity = db의 테이블이라고 생각하면 간단.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 50, unique = true)
    private String memberEmail;

    @Column(length = 20)
    private String memberPassword;

    @Column
    private String memberName;

    // MemberSaveDTO -> MemberEntity 객체로 변환하기 위한 메소드
    public static MemberEntity saveMember (MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());

        return memberEntity;
    }


    public static MemberEntity toUpdateMember(MemberDetailDTO memberDetailDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDetailDTO.getMemberId());
        memberEntity.setMemberName(memberDetailDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDetailDTO.getMemberPassword());
        memberEntity.setMemberName(memberDetailDTO.getMemberName());
        return memberEntity;
    }
}
