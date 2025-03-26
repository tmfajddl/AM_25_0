package org.example;
import java.util.Scanner;

public class App {
    static int active = 0; // 로그인 활성 상태 1: 활성, 0:비활성
    static String loginname = null; // 로그인 member 이름

    public void run(){
        Scanner sc = new Scanner(System.in);
        new membercontroller(sc);
        new ArticleController(sc);

        System.out.println("==프로그램 시작==");

        ArticleController.makeTestData(); //테스트 데이터 생성
        membercontroller.makeTestMember(); //테스트 회원 생성

        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();

            if (cmd.isEmpty()) {
                System.out.println("명령어를 입력하세요");
                continue;
            }
            if (cmd.equals("exit")) {
                break;
            }
            if (cmd.equals("article write")) {
                ArticleController.doWrite();
            } else if (cmd.contains("article list")) {
                ArticleController.doList(cmd);
            } else if (cmd.startsWith("article detail")) {
                ArticleController.doDetail(cmd);
            } else if (cmd.startsWith("article delete")) {
                ArticleController.doDelete(cmd);
            } else if (cmd.startsWith("article modify")) {
                ArticleController.doModify(cmd);
            } else if(cmd.equals("member join")) {
                membercontroller.doJoin();
            } else if(cmd.equals("member list")) {
                membercontroller.doList();
                //로그인 기능
            } else if(cmd.equals("login")) {
                membercontroller.doLogin();
            } else if(cmd.equals("logout")) {
                membercontroller.doLogout();
            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }

        }
        System.out.println("==프로그램 끝==");
        sc.close();
    }
}
