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

        while(true) {
            System.out.print("회원가입하시겠습니까?(yes/no): ");
            String input = sc.nextLine();
            if(input.equals("yes")) {
                System.out.println("==회원가입==");
                int memberId = 0;
                memberId++;
                System.out.print("이름 입력: ");
                String name = sc.nextLine();
                String loginId;
                while(true){
                    System.out.print("ID 입력: ");
                    loginId = sc.nextLine();
                    int num = 0;
                    for (Member member : members) {
                        if (loginId.equals(member.getLoginId())) {
                            num++;
                        }
                    }
                    if(num == 0){
                        break;
                    }
                    System.out.println("아이디가 중복되었습니다.");
                }
                System.out.print("PassWord 입력: ");
                String loginPw = sc.nextLine();

                while(true){
                    System.out.print("PassWord 확인: ");
                    String loginPw2 = sc.nextLine();

                    if(loginPw.equals(loginPw2)){
                        break;
                    }
                    System.out.println("PassWord가 일치하지 않습니다.");
                }
                String aregDate = Util.getNowStr();
                Member member = new Member(memberId,name,loginId,loginPw,aregDate);
                members.add(member);
                System.out.println("회원가입이 완료되었습니다.");
            }

            else if(input.equals("no")) {
                break;
            }
            else {
                System.out.println("올바른 대답이 아닙니다.");
            }
        }

        int lastArticleId = 3;

        makeTestData();

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
            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }

        }

        System.out.println("==프로그램 끝==");
        sc.close();
    }

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