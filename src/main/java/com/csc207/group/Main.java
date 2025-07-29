package com.csc207.group;

import com.csc207.group.data_access.NewsClient;
import com.csc207.group.model.Game;
import com.csc207.group.model.News;
import com.csc207.group.service.GameService;
import com.csc207.group.service.GenreService;
import com.csc207.group.service.NewsService;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
//        String gameName = "gta 5";
//        GameService service = new GameService();
//        int id = service.getGameIdByName(gameName);
//
//        Game game = service.getGameById(id);
//        System.out.println(game);
//        System.out.println("/n/n");
//
//        GenreService genreService = new GenreService();
//        String genre = "action";
//        List<Game> games = genreService.getGamesByGenre(genre);
//        for (Game g : games) {
//            System.out.println(g);
//        }
//        List<String> genreids = game.getGenres();
//        for (String genreid : genreids) {
//            System.out.println(new GenreService().getGenresById(Integer.parseInt(genreid)));
//        }
//        NewsClient newsClient = new NewsClient();
//        System.out.println(newsClient.getGamingNews());
        NewsService newsService = new NewsService();
        List<News> x = newsService.ArticleBuilder();
        System.out.println(x.get(0).getTitle());

    }
}
