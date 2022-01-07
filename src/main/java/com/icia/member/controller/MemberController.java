package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.icia.member.common.SessionConst.LOGIN_EMAIL;

@Controller
@RequestMapping ("/member/")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService ms;

    //회원가입폼
    @GetMapping("save")
    public String saveForm() {
        System.out.println("MemberController.saverForm");
        return "member/save";
    }

    //회원가입처리
    @PostMapping("save")
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO) {
        System.out.println("MemberController.save");
        Long memberId = ms.save(memberSaveDTO);
        return "member/login";
    }

    //로그인 폼
    @GetMapping("login")
    public String loginForm()   {
        System.out.println("MemberController.loginForm");
        return "member/login";
    }

    //로그인 처리
    @PostMapping("login")
    public String login(@ModelAttribute MemberLoginDTO memberLoginDTO, HttpSession session)   {
        System.out.println("MemberController.login");
        boolean loginResult = ms.login(memberLoginDTO);
            if (loginResult)    {
                session.setAttribute(LOGIN_EMAIL, memberLoginDTO.getMemberEmail());
                return "redirect:/member/";
            }   else    {
                return "member/login";
            }

    }

    //회원목록
    @GetMapping
    public String findAll(Model model)  {
        List<MemberDetailDTO> memberList = ms.findAll();
        model.addAttribute("memberList",memberList);
        return "member/findAll";

    }

    //회원조회 (/member/5)
    @GetMapping("{memberId}")
    public String findByID(@PathVariable("memberId") Long memberId, Model model)    {
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member", member);
        return "member/findById";
    }

    @PostMapping("{memberId}")
    public @ResponseBody MemberDetailDTO detail(@PathVariable("memberId") Long memberId)    {
        MemberDetailDTO member = ms.findById(memberId);
        return member;
    }




    // 회원삭제 (/member/delete/5)
    @GetMapping ("delete/{memberId}")
    public String deleteById(@PathVariable("memberId") Long memberId)   {
        ms.deleteById(memberId);
        return "redirect:/member/";
    }

}
