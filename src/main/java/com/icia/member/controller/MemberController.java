package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                /*return "redirect:/member/";*/
                return "member/mypage";
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

    // 회원삭제 (/member/5)
    @DeleteMapping("{memberId}")
    public ResponseEntity deleteById2(@PathVariable("memberId") Long memberId) {
        ms.deleteById(memberId);
        /*
            // 얘네 둘은 단순 화면 출력(html리턴,일반 mvc방식)이 아닌,
               데이터를 리턴하고자 할 때 사용하는 리턴방식(RESTful 리턴방식)
            ResponseEntity : 데이터 & 상태코드를 함께 리턴할 수 있음.
            @ResponseBody : 데이터를 리턴할 수 있음.

            // ResponseEntity를 이용해 200 코드를 리턴해보자
        */
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("my-page")
    public String myPage (){
        return "mypage";
    }

    //수정화면 요청
    @GetMapping("update")
    public String updateForm(Model model,HttpSession session)  {
        /*Object attribute = session.getAttribute(LOGIN_EMAIL);*/
        String memberEmail = (String) session.getAttribute(LOGIN_EMAIL);
        MemberDetailDTO member = ms.findByEmail(memberEmail);
        model.addAttribute("member", member);
        return "member/update";
    }

    //수정처리
    @PostMapping("update")
    public String update(@ModelAttribute MemberDetailDTO memberDetailDTO)   {
        Long memberId = ms.update(memberDetailDTO);

        // return "/member/findById" <- 얘는 restful 문법에 어긋나~!
        // 수정완료 후 해당회원의 상세페이지(findById.html)를 출력!!!!
        return "redirect:/member/"+memberDetailDTO.getMemberId();
    }


    //수정처리(put)
    @PutMapping("{memberId}")
    public ResponseEntity update2(@RequestBody MemberDetailDTO memberDetailDTO) {
        //json으로 데이터가 전달되면 RequestBody로 받아줘야함.

        ms.update(memberDetailDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

}
