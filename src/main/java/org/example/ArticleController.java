package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController {
    static Scanner sc;
    static List<Article> articles;
    private static int lastArticleId = 3;

    public ArticleController(Scanner sc) {
        this.sc = sc;
        articles = new ArrayList<>();
    }

    public static void doWrite() {
        if(App.active==0){
            System.out.println("로그인 후 이용 바랍니다.");
        }
        else{
            System.out.println("==게시글 작성==");
            int id = lastArticleId + 1;
            String regDate = Util.getNowStr();
            String updateDate = Util.getNowStr();
            System.out.print("제목 : ");
            String title = sc.nextLine().trim();
            System.out.print("내용 : ");
            String body = sc.nextLine().trim();
            String writer = App.loginname;

            Article article = new Article(id, regDate, updateDate, title, body, writer);
            articles.add(article);

            System.out.println(id + "번 글이 작성되었습니다");
            lastArticleId++;
        }
    }

    public static void doList(String cmd) {
        if(cmd.equals("article list")) {
            System.out.println("==게시글 목록==");
            if (articles.isEmpty()) {
                System.out.println("아무것도 없어");
            } else {
                System.out.println("   번호    /     날짜       /   제목     /        내용       /      작성자");
                for (int i = articles.size() - 1; i >= 0; i--) {
                    Article article = articles.get(i);
                    if (Util.getNowStr().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                        System.out.printf("  %d   /    %s        /    %s     /       %s      /      %s \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody(), article.getWriter());
                    } else {
                        System.out.printf("  %d   /    %s        /    %s     /       %s      /      %s \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody(), article.getWriter());
                    }
                }
            }
        }else {
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
            if (!words.isEmpty()) {
                System.out.println("헤당 게시물은 존재하지 않습니다.");
            } else {
                System.out.println("   번호    /     날짜       /   제목     /        내용       /      작성자");
                for (int i = words.size() - 1; i >= 0; i--) {
                    Article article = words.get(i);
                    if (Util.getNowStr().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                        System.out.printf("  %d   /    %s        /    %s     /       %s      /      %s \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody(), article.getWriter());
                    } else {
                        System.out.printf("  %d   /    %s        /    %s     /       %s      /      %s \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody(), article.getWriter());
                    }
                }
            }
        }
    }

    public static void doDelete(String cmd) {
        if(App.active == 0){
            System.out.println("로그인 후 이용바랍니다.");
        }
        else{
            System.out.println("==게시글 삭제==");

            int id = Integer.parseInt(cmd.split(" ")[2]);

            Article foundArticle = getArticleById(id);
            if (foundArticle == null) {
                System.out.println("해당 게시글은 없습니다");
                return;
            }
            if(App.loginname.equals(foundArticle.getWriter())){
                articles.remove(foundArticle);
                System.out.println(id + "번 게시글이 삭제되었습니다");
            }
            else{
                System.out.println("삭제 권한이 없습니다.");
            }

        }
    }

    public static void doModify(String cmd) {
        if(App.active == 0){
            System.out.println("로그인 후 이용바랍니다.");
        }
        else{
            System.out.println("==게시글 수정==");

            int id = Integer.parseInt(cmd.split(" ")[2]);

            Article foundArticle = getArticleById(id);

            if (foundArticle == null) {
                System.out.println("해당 게시글은 없습니다");
                return;
            }

            if(App.loginname.equals(foundArticle.getWriter())){
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
            }
            else{
                System.out.println("수정 권한이 없습니다.");
            }
        }
    }

    public static void doDetail(String cmd) {
        System.out.println("==게시글 상세보기==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        }
        System.out.println("번호 : " + foundArticle.getId());
        System.out.println("작성날짜 : " + foundArticle.getRegDate());
        System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
        System.out.println("제목 : " + foundArticle.getTitle());
        System.out.println("내용 : " + foundArticle.getBody());
        System.out.println("작성자 : " + foundArticle.getWriter());


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
    static void makeTestData() {
        System.out.println("==테스트 데이터 생성==");
        articles.add(new Article(1, "2024-12-12 12:12:12", "2024-12-12 12:12:12", "제목1", "내용1","작성자1"));
        articles.add(new Article(2, Util.getNowStr(), Util.getNowStr(), "제목2", "내용2","작성자2"));
        articles.add(new Article(3, Util.getNowStr(), Util.getNowStr(), "제목3", "내용3","작성자3"));
    }

}
