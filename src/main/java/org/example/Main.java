package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<Article> articles = new ArrayList<>();
    static List<Member> members = new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("==프로그램 시작==");

        int lastArticleId = 3; // 게시물 고유 번호
        int memberId = 3; //회원 고유 번호
        int active = 0; // 로그인 활성 상태 1: 활성, 0:비활성
        String loginname = null; // 로그인 member 이름

        makeTestData(); //테스트 데이터 생성
        makeTestMember(); //테스트 회원 생성

        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();

            if (cmd.length() == 0) {
                System.out.println("명령어를 입력하세요");
                continue;
            }
            if (cmd.equals("exit")) {
                break;
            }

            if (cmd.equals("article write")) {
                System.out.println("==게시글 작성==");
                int id = lastArticleId + 1;
                String regDate = Util.getNowStr();
                String updateDate = Util.getNowStr();
                System.out.print("제목 : ");
                String title = sc.nextLine().trim();
                System.out.print("내용 : ");
                String body = sc.nextLine().trim();

                Article article = new Article(id, regDate, updateDate, title, body);
                articles.add(article);

                System.out.println(id + "번 글이 작성되었습니다");
                lastArticleId++;
            } else if (cmd.contains("article list")) {
                if(cmd.equals("article list")) {
                    System.out.println("==게시글 목록==");
                    if (articles.size() == 0) {
                        System.out.println("아무것도 없어");
                    } else {
                        System.out.println("   번호    /     날짜       /   제목     /    내용   ");
                        for (int i = articles.size() - 1; i >= 0; i--) {
                            Article article = articles.get(i);
                            if (Util.getNowStr().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                                System.out.printf("  %d   /    %s        /    %s     /    %s   \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                            } else {
                                System.out.printf("  %d   /    %s        /    %s     /    %s   \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                            }

                        }
                    }
                } else {
                    String findword = cmd.split(" ")[2];
                    List<Article> words = new ArrayList<>();

                    for (Article article : articles) {
                        if (article.getTitle().contains(findword)) {
                            words.add(article);
                        }
                    }
                    for (Article article : articles) {
                        if (article.getTitle().contains(findword)) {
                            words.add(article);
                        }
                    }
                    if(words.size() > 0){
                        System.out.println("   번호    /     날짜       /   제목     /    내용   ");
                        for (int i = words.size() - 1; i >= 0; i--) {
                            Article article = words.get(i);
                            if (Util.getNowStr().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                                System.out.printf("  %d   /    %s        /    %s     /    %s   \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                            } else {
                                System.out.printf("  %d   /    %s        /    %s     /    %s   \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                            }
                        }
                    }
                    else{
                        System.out.println("헤당 게시물은 존재하지 않습니다.");
                    }
                }
            } else if (cmd.startsWith("article detail")) {
                System.out.println("==게시글 상세보기==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                System.out.println("번호 : " + foundArticle.getId());
                System.out.println("작성날짜 : " + foundArticle.getRegDate());
                System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
                System.out.println("제목 : " + foundArticle.getTitle());
                System.out.println("내용 : " + foundArticle.getBody());

            } else if (cmd.startsWith("article delete")) {
                System.out.println("==게시글 삭제==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                articles.remove(foundArticle);
                System.out.println(id + "번 게시글이 삭제되었습니다");

            } else if (cmd.startsWith("article modify")) {
                System.out.println("==게시글 수정==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                System.out.println("기존 제목 : " + foundArticle.getTitle());
                System.out.println("기존 내용 : " + foundArticle.getBody());
                System.out.print("새 제목 : ");
                String newTitle = sc.nextLine().trim();
                System.out.print("새 내용 : ");
                String newBody = sc.nextLine().trim();

                foundArticle.setTitle(newTitle);
                foundArticle.setBody(newBody);

                foundArticle.setUpdateDate(Util.getNowStr());

                System.out.println(id + "번 게시글이 수정되었습니다");

            } else if(cmd.equals("member join")) {
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

            } else if(cmd.equals("member list")) {
                System.out.println("==member 목록==");
                if (members.size() == 0) {
                    System.out.println("아무도 없어");
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
                //로그인 기능
            } else if(cmd.equals("login")) {
                if(active == 0) { //login이 되어있지 않다면 login 진행
                    System.out.print("ID 입력: ");
                    String loginId = sc.nextLine();
                    System.out.print("PassWord 입력: ");
                    String loginPw = sc.nextLine();
                    int count = 0; // 아이디가 일치하지 않는 갯수 확인
                    for (Member member : members) {
                        if (member.getLoginId().equals(loginId) && member.getLoginPw().equals(loginPw)) {
                            loginname = member.getName(); //아이디와 비번이 같다면 로그인
                            System.out.println(loginname + "님 로그인되었습니다.");
                            active++; //active = 1 ;활성
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

            } else if(cmd.equals("logout")) {
                if(active == 1) { // active가 활성되어 있다면 로그아웃
                    System.out.println(loginname + "님 로그아웃되었습니다.");
                    active--; //비활성 상태로 만듬
                    loginname = null; //사용자 비우기
                }
                else{ //활성 상태가 아니라면
                    System.out.println("로그인이 되어있지 않습니다.");
                }

            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }

        }

        System.out.println("==프로그램 끝==");
        sc.close();
    }
    //테스트 회원 생성
    private static void makeTestMember() {
        System.out.println("==테스트 회원 생성==");
        members.add(new Member(1,"이름1","아이디1","비밀번호1",Util.getNowStr()));
        members.add(new Member(2,"이름2","아이디2","비밀번호2",Util.getNowStr()));
        members.add(new Member(3,"이름3","아이디3","비밀번호3",Util.getNowStr()));
    }
    //id에 해당하는 객체 찾기
    private static Article getArticleById(int id) {
//        for (int i = 0; i < articles.size(); i++) {
//            Article article = articles.get(i);
//            if (article.getId() == id) {
//                return article;
//            }
//        }

        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    /**
     * 테스트 데이터 생성 함수
     **/
    private static void makeTestData() {
        System.out.println("==테스트 데이터 생성==");
        articles.add(new Article(1, "2024-12-12 12:12:12", "2024-12-12 12:12:12", "제목1", "내용1"));
        articles.add(new Article(2, Util.getNowStr(), Util.getNowStr(), "제목2", "내용2"));
        articles.add(new Article(3, Util.getNowStr(), Util.getNowStr(), "제목3", "내용3"));
    }
}

class Article {
    private int id;
    private String regDate;
    private String updateDate;
    private String title;
    private String body;

    public Article(int id, String regDate, String updateDate, String title, String body) {
        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.title = title;
        this.body = body;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

class Member{
    private int id = 1;
    private String name;
    private String loginId;
    private String loginPw;
    private String regDate;
    public Member(int id, String name, String loginId, String loginPw, String regDate) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.regDate = regDate;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getLoginPw() {
        return loginPw;
    }
    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }
    public String getRegDate() {
        return regDate;
    }
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}