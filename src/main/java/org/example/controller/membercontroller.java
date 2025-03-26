package org.example.controller;

import org.example.dto.Member;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class membercontroller extends Controller {

    private Scanner sc;
    private List<Member> members;
    private String cmd;

    int lastMemberId = 3;

    public membercontroller(Scanner sc) {
        this.sc = sc;
        members = new ArrayList<>();
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;

        switch (actionMethodName) {
            case "join":
                doJoin();
                break;
            case "list":
                showList();
                break;
            case "login":
                doLogin();
                break;
            case "logout":
                doLogout();
                break;
            default:
                System.out.println("Unknown action method");
                break;
        }
    }

    private void doLogout() {
        if(Controller.login == false) {
            System.out.println("로그인이 되어있지 않습니다.");
            return;
        }
        System.out.println(Controller.loginname + "님 로그아웃되었습니다.");
        Controller.login = false; //비활성 상태로 만듬
        Controller.loginname = null; //사용자 비우기

    }

    private void doLogin() {
        if(Controller.login){
            System.out.println("이미 로그인 되어 있습니다.");
            return;
        }
        System.out.print("ID 입력: ");
        String loginId = sc.nextLine();
        System.out.print("PassWord 입력: ");
        String loginPw = sc.nextLine();
        int count = 0; // 아이디가 일치하지 않는 갯수 확인
        for (Member member : members) {
            if (member.getLoginId().equals(loginId) && member.getPassword().equals(loginPw)) {
                Controller.loginname = member.getLoginId(); //아이디와 비번이 같다면 로그인
                System.out.println(Controller.loginname + "님 로그인되었습니다.");
                Controller.login = true;
                break;
            } else if (member.getLoginId().equals(loginId)) { //아이디는 같은데 비번이 다를경우
                System.out.println("Password 확인바랍니다.");
                break;
            } else {
                count++; //아이디가 일치하지 않을 경우
            }
        }
        if (count == members.size()) { //count가 배열의 사이즈와같으면 존재하지 않는 ID
            System.out.println("존재하지 않는 ID입니다.");
        }
    }

    private void showList() {
        System.out.println("==member 목록==");
        if (members.isEmpty()) {
            System.out.println("등록된 회원이 없습니다");
        } else {
            System.out.println("   고유ID    /     가입날짜       /   ID    /    이름   ");
            for (int i = members.size() - 1; i >= 0; i--) {
                Member member = members.get(i);
                if (Util.getNowStr().split(" ")[0].equals(member.getRegDate().split(" ")[0])) {
                    System.out.printf("  %d   /    %s        /    %s     /    %s   \n", member.getId(), member.getRegDate().split(" ")[1], member.getLoginId(), member.getName());
                } else {
                    System.out.printf("  %d   /    %s        /    %s     /    %s   \n", member.getId(), member.getRegDate().split(" ")[0], member.getLoginId(), member.getName());
                }
            }
        }
    }

    private void doJoin() {
        if(Controller.login) {
            System.out.println("이미 회원가입 되어있습니다.");
            return;
        }
        System.out.println("==회원가입==");
        int id = lastMemberId + 1;
        String regDate = Util.getNowStr();
        String loginId = null;
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();
            if (!isJoinableLoginId(loginId)) {
                System.out.println("이미 사용중이야");
                continue;
            }
            break;
        }
        String password = null;
        while (true) {
            System.out.print("비밀번호 : ");
            password = sc.nextLine().trim();
            System.out.print("비밀번호 확인: ");
            String passwordConfirm = sc.nextLine().trim();

            if (!password.equals(passwordConfirm)) {
                System.out.println("비번 확인해");
                continue;
            }
            break;
        }

        System.out.print("이름 : ");
        String name = sc.nextLine().trim();

        Member member = new Member(id, regDate, loginId, password, name);
        members.add(member);

        System.out.println(id + "번 회원이 가입되었습니다");
        lastMemberId++;
    }

    private boolean isJoinableLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return false;
            }
        }
        return true;
    }

    public void makeTestData() {
        System.out.println("==회원 테스트 데이터 생성==");
        members.add(new Member(1, Util.getNowStr(), "test1", "test1", "test1"));
        members.add(new Member(2, Util.getNowStr(), "test2", "test2", "test2"));
        members.add(new Member(3, Util.getNowStr(), "test3", "test3", "test3"));
    }
}
