package com.icia.member.service;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.icia.member.dto.MemberDetailDTO.toMemberDetailDTO;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {
        //JpaRepository는 무조건 Entity만 받음.

        //MemberSaveDTO -> MemberEntity
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);
        /*Long id = mr.save(memberEntity).getId();*/
        System.out.println("MemberServiceImpl.save");
        return mr.save(memberEntity).getId();
    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        System.out.println("MemberService.login");

        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());

        if (memberEntity != null)   {
            if (memberEntity.getMemberPassword().equals(memberLoginDTO.getMemberPassword()))   {
                return true;
            } else {
                return false;
            }
        }   else    {
            return false;
        }

    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        List<MemberDetailDTO> memberList = new ArrayList<>();
        for (MemberEntity m: memberEntityList)  {
            memberList.add(toMemberDetailDTO(m));
        }
        return memberList;
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        Optional<MemberEntity> memberEntityOptional = mr.findById(memberId);
        MemberEntity memberEntity = memberEntityOptional.get();

        MemberDetailDTO memberDetailDTO = toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
    }

    @Override
    public void deleteById(Long memberId) {
        mr.deleteById(memberId);

    }

    @Override
    public MemberDetailDTO findByEmail(String memberEmail) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
    }

    @Override
    public Long update(MemberDetailDTO memberDetailDTO) {
        // update 처리시 save 메서드를 호출
        // repository는 entity 객체만 받기때문에
        // memberDetailDTO > MemberEntity 해줘야함.

        /*MemberEntity memberEntity = MemberEntity.toUpdateMember(memberDetailDTO);
        Long memberId = mr.save(memberEntity).getId();*/

        return mr.save(MemberEntity.toUpdateMember(memberDetailDTO)).getId();
        //Jpa에서 save를 할 때 pk값을 같이 보내면 코드 실행시 pk값을 인지해서,
        // 계속 재생성하지않고 해당 데이터를 찾아 수정해준다.
    }
}
