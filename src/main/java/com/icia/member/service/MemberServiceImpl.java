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
            memberList.add(MemberDetailDTO.toMemberDetailDTO(m));
        }
        return memberList;
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        Optional<MemberEntity> memberEntityOptional = mr.findById(memberId);
        MemberEntity memberEntity = memberEntityOptional.get();

        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
    }
}
