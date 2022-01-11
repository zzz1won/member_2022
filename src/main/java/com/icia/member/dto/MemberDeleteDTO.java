package com.icia.member.dto;

import com.icia.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDeleteDTO {
    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    //여기도 entity -> dto 변경해줘야?

/*    public static MemberDeleteDTO tomemberDeleteDTO(MemberEntity memberEntity)  {
        MemberDeleteDTO memberDeleteDTO = new MemberDeleteDTO();
        memberDeleteDTO.setMemberId(memberDeleteDTO.getMemberId());
        memberDeleteDTO.setMemberEmail(memberDeleteDTO.getMemberEmail());
        memberDeleteDTO.setMemberPassword(memberDeleteDTO.getMemberPassword());
        memberDeleteDTO.setMemberName(memberDeleteDTO.getMemberName());
        return memberDeleteDTO;
    }*/

}
