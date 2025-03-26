package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class membercontroller {
    static Scanner sc;
    static List<Member> members;
    static int memberId = 3; //회원 고유 번호

    public membercontroller(Scanner sc) {
        this.sc = sc;
        members = new ArrayList<>();
    }

    public static void doList() {
        System.out.println("==member 목록==");
        if (members.size() == 0) {
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

    public static void doJoin() {
        if (App.active == 0) {
            System.out.println("==회원가입==");
            memberId++; //member고유 아이디 부여
            System.out.print("이름 입력: ");
            String name = sc.nextLine();
            String loginId;
            while (true) {
                System.out.print("ID 입력: ");
                loginId = sc.nextLine();
                int num = 0;
                for (Member member : members) {
                    if (loginId.equals(member.getLoginId())) { // 겹치는 아니디가 존재하는지 확인
                        num++; //겹치면 ++
                    }
                }
                if (num == 0) { // 겹치는게 없다면 pass
                    break;
                }
                System.out.println("ID가 중복되었습니다."); //겹치면 다시 입력
            }
            String loginPw;
            while (true) {
                System.out.print("PassWord 입력: ");
                loginPw = sc.nextLine();
                System.out.print("PassWord 확인: "); //password 맞는지 재입력
                String loginPw2 = sc.nextLine(); // 재입력한 password

                if (loginPw.equals(loginPw2)) { // 두개가 동일하면 pass
                    break;
                }
                System.out.println("PassWord가 일치하지 않습니다."); //아니면 다시입력
            }

            String aregDate = Util.getNowStr();
            Member member = new Member(memberId, name, loginId, loginPw, aregDate);
            members.add(member); //member list 추가
            System.out.println("회원가입이 완료되었습니다.");

        }
        else{
            System.out.println("이미 회원가입 되어있습니다.");
        }
    }

    public static void doLogin() {
        if(App.active == 0) { //login이 되어있지 않다면 login 진행
            System.out.print("ID 입력: ");
            String loginId = sc.nextLine();
            System.out.print("PassWord 입력: ");
            String loginPw = sc.nextLine();
            int count = 0; // 아이디가 일치하지 않는 갯수 확인
            for (Member member : members) {
                if (member.getLoginId().equals(loginId) && member.getLoginPw().equals(loginPw)) {
                    App.loginname = member.getName(); //아이디와 비번이 같다면 로그인
                    System.out.println(App.loginname + "님 로그인되었습니다.");
                    App.active++; //active = 1 ;활성
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
        else{ // active가 0이 아니라면 => 이미 로그인 상태
            System.out.println("이미 로그인 되어 있습니다.");
        }

    }

    public static void doLogout() {
        if(App.active == 1) { // active가 활성되어 있다면 로그아웃
            System.out.println(App.loginname + "님 로그아웃되었습니다.");
            App.active--; //비활성 상태로 만듬
            App.loginname = null; //사용자 비우기
        }
        else{ //활성 상태가 아니라면
            System.out.println("로그인이 되어있지 않습니다.");
        }
    }

    //테스트 회원 생성
    static void makeTestMember() {
        System.out.println("==테스트 회원 생성==");
        members.add(new Member(1,"이름1","아이디1","비밀번호1",Util.getNowStr()));
        members.add(new Member(2,"이름2","아이디2","비밀번호2",Util.getNowStr()));
        members.add(new Member(3,"이름3","아이디3","비밀번호3",Util.getNowStr()));
    }
}
